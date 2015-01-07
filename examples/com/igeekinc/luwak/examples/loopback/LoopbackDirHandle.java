package com.igeekinc.luwak.examples.loopback;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;

import com.igeekinc.luwak.FUSEDirEntry;
import com.igeekinc.luwak.inode.DirectoryEntryBuffer;
import com.igeekinc.luwak.inode.FUSEReqInfo;
import com.igeekinc.luwak.inode.exceptions.InodeException;
import com.igeekinc.luwak.util.FUSEDirHandle;

class LoopbackDirHandle extends FUSEDirHandle
{
	File file;
	ArrayList<FUSEDirEntry>dirEntries;

	static Charset utf8 = Charset.forName("UTF-8");
	LoopbackDirHandle(long handleNum)
	{
		super(handleNum);
	}

	public void setFile(File file)
	{
		if (!file.isDirectory())
			throw new IllegalArgumentException(file.toString()+" is not a directory");

		this.file = file;
		File [] files = file.listFiles();
		dirEntries = new ArrayList<FUSEDirEntry>(files.length);
		long offset = 0;
		FUSEDirEntry dotEntry = new FUSEDirEntry(1, offset, FUSEDirEntry.DT_DIR, ".".getBytes(utf8));
		offset += dotEntry.getLength();
		dotEntry.setOffset(offset);
		dirEntries.add(dotEntry);

		FUSEDirEntry dotdotEntry = new FUSEDirEntry(1, offset, FUSEDirEntry.DT_DIR, "..".getBytes(utf8));
		offset += dotdotEntry.getLength();
		dotdotEntry.setOffset(offset);
		dirEntries.add(dotdotEntry);

		for (File curFile:files)
		{
			int type;
			if (curFile.isDirectory())
				type = FUSEDirEntry.DT_DIR;
			else
				if (curFile.isFile())
					type = FUSEDirEntry.DT_REG;
				else
					type = FUSEDirEntry.DT_UNKNOWN;	// Can we get here?
			byte [] nameBytes = curFile.getName().getBytes(utf8);
			FUSEDirEntry curEntry = new FUSEDirEntry(1, offset, type, nameBytes);	// inode is always 1 for now
			offset += curEntry.getLength();
			curEntry.setOffset(offset);
			dirEntries.add(curEntry);
		}
	}

	public int getIndexForOffset(long offset)
	{
		if (offset == 0)
			return 0;
		int index = 0;
		for (FUSEDirEntry curEntry:dirEntries)
		{
			// The offsets in the dir entries are the offset of the NEXT entry.
			if (curEntry.getOffset() == offset)
				return index + 1;
			index++;
		}
		return -1;
	}

	public FUSEDirEntry getEntry(int index)
	{
		if (index >= dirEntries.size())
			return null;
		return dirEntries.get(index);
	}


	public DirectoryEntryBuffer readdir(FUSEReqInfo reqInfo, long offset, int size, int flags, int readFlags)
		throws InodeException
	{
		DirectoryEntryBuffer returnBuffer = new DirectoryEntryBuffer(size);
		int index = getIndexForOffset(offset);
		int startIndex = index;
		if (index >= 0)
		{
			while(returnBuffer.getSpaceUsed() < returnBuffer.getMaxSize() && (index - startIndex < 15))
			{
				FUSEDirEntry curEntry = getEntry(index);
				if (curEntry == null)
					break;	// End of the line
				if (!returnBuffer.addDirEntry(curEntry))
					break;	// Out of space in the buffer
				index++;
			}
		}
		System.out.println("readdir, offset = "+offset+", size = "+size);
		System.out.println("returnBuffer, spaceUsed = "+returnBuffer.getSpaceUsed()+", maxSize = "+returnBuffer.getMaxSize());
		System.out.println(returnBuffer.toString());
		return returnBuffer;
	}
	
	public void release()
	{

	}
}