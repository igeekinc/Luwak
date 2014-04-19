/*
 * Copyright 2002-2014 iGeek, Inc.
 * All Rights Reserved
 * @Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.@
 */
 
package com.igeekinc.luwak.examples;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

import com.igeekinc.luwak.FUSEAttr;
import com.igeekinc.luwak.FUSEDirEntry;
import com.igeekinc.luwak.FUSEStatFS;
import com.igeekinc.luwak.LibC;
import com.igeekinc.luwak.Stat;
import com.igeekinc.luwak.StatFS;
import com.igeekinc.luwak.inode.CreateInfo;
import com.igeekinc.luwak.inode.DirectoryEntryBuffer;
import com.igeekinc.luwak.inode.FUSEInode;
import com.igeekinc.luwak.inode.FUSEInodeAdapter;
import com.igeekinc.luwak.inode.FUSEReqInfo;
import com.igeekinc.luwak.inode.FUSEVolumeBase;
import com.igeekinc.luwak.inode.InodeManager;
import com.igeekinc.luwak.inode.SetAttrs;
import com.igeekinc.luwak.inode.exceptions.ExistsException;
import com.igeekinc.luwak.inode.exceptions.InodeException;
import com.igeekinc.luwak.inode.exceptions.InodeIOException;
import com.igeekinc.luwak.inode.exceptions.IsDirectoryException;
import com.igeekinc.luwak.inode.exceptions.NoEntryException;
import com.igeekinc.luwak.inode.exceptions.NotDirectoryException;
import com.igeekinc.luwak.inode.exceptions.PermissionException;
import com.igeekinc.luwak.util.FUSEDirHandle;
import com.igeekinc.luwak.util.FUSEFileHandle;
import com.igeekinc.luwak.util.FUSEHandleManager;
import com.igeekinc.util.linux.LinuxOSConstants;
import com.sun.jna.Native;

class LoopbackInode extends FUSEInode<LoopbackVolume>
{
	File baseFile;
	public LoopbackInode(LoopbackVolume volume, File baseFile, long inodeNum, long generation, FUSEAttr attr)
	{
		super(volume, inodeNum, generation, attr);
		this.baseFile = baseFile;
	}

	public File getBaseFile()
	{
		return baseFile;
	}

	String openFlagsToRAFFlags(int openFlags)
	{
		int accFlags = openFlags & LinuxOSConstants.O_ACCMODE;
		if ((accFlags == LinuxOSConstants.O_RDONLY))
			return "r";
		if ((accFlags & LinuxOSConstants.O_WRONLY) != 0 || (accFlags & LinuxOSConstants.O_RDWR) != 0)
			return "rw";
		throw new InternalError("How did we get here?  This is not my beautiful wife!");
	}

	public LoopbackInode lookup(FUSEReqInfo reqInfo, String name) throws InodeException
	{
		File lookupFile = new File(getBaseFile(), name);
		if (lookupFile.exists())
		{
			String pathname = lookupFile.getAbsolutePath();
			LoopbackInode returnInode = ((LoopbackInodeManager)volume.getInodeManager()).retrieveInode(pathname);
			if (returnInode == null)
			{
				long newInodeNum = volume.nextInodeNum();
				FUSEAttr attr = attrForFile(lookupFile, newInodeNum);
				returnInode = new LoopbackInode(volume, lookupFile, newInodeNum, 0L, attr);
				volume.getInodeManager().addInode(returnInode);
			}
			return returnInode;
		}
		else
		{
			throw new NoEntryException();
		}
	}
	
	public LoopbackFileHandle open(FUSEReqInfo reqInfo, int flags) throws InodeException
	{
		LoopbackFileHandle returnHandle = (LoopbackFileHandle) volume.getAdapter().allocateFileHandle();
		if (baseFile.isDirectory() && (((flags & LinuxOSConstants.O_ACCMODE) & (LinuxOSConstants.O_WRONLY | LinuxOSConstants.O_RDWR)) != 0))
			throw new IsDirectoryException();
		try
		{
			returnHandle.setRandomAccessFile(new RandomAccessFile(baseFile, openFlagsToRAFFlags(flags)));
		} catch (FileNotFoundException e)
		{
			throw new NoEntryException();
		}

		if ((flags & LinuxOSConstants.O_TRUNC) != 0)
			try
		{
				returnHandle.getFile().setLength(0L);
		} catch (IOException e)
		{
			throw new PermissionException();
		}
		return returnHandle;
	}

	public LoopbackDirHandle opendir(FUSEReqInfo reqInfo, int flags) throws InodeException
	{
		LoopbackDirHandle returnHandle = (LoopbackDirHandle) volume.getAdapter().allocateDirHandle();
		if (baseFile.isDirectory() && (((flags & LinuxOSConstants.O_ACCMODE) & (LinuxOSConstants.O_WRONLY | LinuxOSConstants.O_RDWR)) != 0))
			throw new IsDirectoryException();
		returnHandle.setFile(getBaseFile());
		return returnHandle;
	}


	public CreateInfo<LoopbackFileHandle, LoopbackInode> create(FUSEReqInfo reqInfo, String name, int mode, int uMask) throws InodeException
	{
		CreateInfo<LoopbackFileHandle, LoopbackInode> returnInfo;
		LoopbackFileHandle createHandle = (LoopbackFileHandle) volume.getAdapter().allocateFileHandle();

		try
		{
			synchronized(this)
			{
				File parentFile = getBaseFile();
				File createFile = new File(parentFile, name);
				if (createFile.exists())
					throw new ExistsException();
				createHandle.setRandomAccessFile(new RandomAccessFile(createFile, "rw"));
				long newInodeNum = volume.nextInodeNum();
				FUSEAttr attr = attrForFile(createFile, newInodeNum);
				LoopbackInode createInode = new LoopbackInode(volume, createFile, newInodeNum, 0L, attr);
				volume.getInodeManager().addInode(createInode);
				returnInfo = new CreateInfo<LoopbackFileHandle, LoopbackInode>(createHandle, createInode);
			}
		} catch (FileNotFoundException e)
		{
			throw new NoEntryException();
		}

		return returnInfo;
	}

	
	public LoopbackInode mkdir(FUSEReqInfo reqInfo, String name, int mode, int uMask)
			throws InodeException
	{
		if (baseFile.isDirectory())
		{
			synchronized(this)
			{
				File mkFile = new File(baseFile, name);
				if (!mkFile.mkdir())
					throw new PermissionException();
				long newInodeNum = volume.nextInodeNum();
				FUSEAttr attr = attrForFile(mkFile, newInodeNum);
				LoopbackInode returnInode = new LoopbackInode(volume, mkFile, newInodeNum, 0L, attr);
				volume.getInodeManager().addInode(returnInode);
				return returnInode;
			}
		}
		else
		{
			throw new NotDirectoryException();
		}
	}
	
	public FUSEAttr getAttr(FUSEReqInfo reqInfo) throws InodeException
	{
		return attrForFile(baseFile, getInodeNum());
	}

	
	public FUSEAttr setAttr(FUSEReqInfo reqInfo, SetAttrs newAttr)
			throws InodeException
	{
		return attrForFile(getBaseFile(), getInodeNum());
	}
	
	public FUSEAttr attrForFile(File attrFile, long inodeNum) throws InodeException
	{
		FUSEAttr returnAttr = new FUSEAttr();
		returnAttr.setInode(inodeNum);
		Stat stat = LibC.newStat();
		int errno = LibC.lstat(attrFile.getAbsolutePath(), stat);

		if (errno != 0)
		{
			throw InodeException.exceptionForErrorNum(Native.getLastError());
		}

		returnAttr.setMode(stat.get_st_mode());
		returnAttr.setNLink(stat.get_st_nlink());
		returnAttr.setUID(stat.get_st_uid());
		returnAttr.setGID(stat.get_st_gid());
		returnAttr.setRDev((int)stat.get_st_rdev());
		returnAttr.setSize(stat.get_st_size());
		returnAttr.setBlockSize(stat.get_st_blksize());
		returnAttr.setBlocks(stat.get_st_blocks());

		returnAttr.setATime(stat.get_st_atime());
		returnAttr.setATimeNSec((int)stat.get_st_atimensec());
		returnAttr.setCTime(stat.get_st_ctime());
		returnAttr.setCTimeNSec((int)stat.get_st_ctimensec());
		returnAttr.setMTime(stat.get_st_mtime());
		returnAttr.setMTimeNSec((int)stat.get_st_mtimensec());
		return returnAttr;
	}

	@Override
	public String[] listXAttr(FUSEReqInfo reqInfo) throws InodeException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getXAttr(FUSEReqInfo reqInfo, String xattrName, int offset, byte[] buffer)
			throws InodeException
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}

class LoopbackFileHandle extends FUSEFileHandle
{
	RandomAccessFile file;
	LoopbackFileHandle(long handleNum)
	{
		super(handleNum);
	}

	public void setRandomAccessFile(RandomAccessFile file)
	{
		this.file = file;
	}
	public int read(FUSEReqInfo reqInfo, long offset, byte[] returnBuffer, int flags, int readFlags) throws InodeIOException
	{
		RandomAccessFile loopFile = getFile();
		synchronized(loopFile)
		{
			try
			{
				int bytesRead = 0;
				if (offset < loopFile.length())
				{
					getFile().seek(offset);
					bytesRead =getFile().read(returnBuffer);
				}
				return bytesRead;
			} catch (IOException e)
			{
				throw new InodeIOException();
			}
		}
	}

	public int write(FUSEReqInfo reqInfo, long offset, ByteBuffer writeBytes, int writeFlags) throws InodeException
	{
		int bytesWritten;
		synchronized(this)
		{
			try
			{
				getFile().seek(offset);
				bytesWritten = getFile().getChannel().write(writeBytes);
			}
			catch (IOException e)
			{
				throw new InodeIOException();
			}
		}
		return bytesWritten;
	}


	public void flush(FUSEReqInfo reqInfo, long lockOwnerFlags) throws InodeException
	{
		synchronized(this)
		{
			try
			{
				getFile().getFD().sync();
			}
			catch (IOException e)
			{
				throw new InodeIOException();
			}
		}
	}

	public void release()
	{
		try
		{
			file.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public RandomAccessFile getFile()
	{
		return file;
	}
}

class LoopbackHandleManager extends FUSEHandleManager<LoopbackFileHandle, LoopbackDirHandle>
{

	@Override
	protected LoopbackDirHandle allocateDirHandle(long handleNum)
	{
		return new LoopbackDirHandle(handleNum);
	}

	@Override
	protected LoopbackFileHandle allocateFileHandle(long handleNum)
	{
		return new LoopbackFileHandle(handleNum);
	}

}

class LoopbackInodeManager extends InodeManager<LoopbackInode>
{
	HashMap<String, LoopbackInode>pathMap = new HashMap<String, LoopbackInode>();
	public LoopbackInodeManager(LoopbackVolume parent)
	{
		super(parent);
	}
	@Override
	public boolean addInode(LoopbackInode addInode)
	{
		boolean returnValue;
		if (super.addInode(addInode))
		{
			synchronized(pathMap)
			{
				LoopbackInode addLoopbackInode = (LoopbackInode)addInode;
				String path = addLoopbackInode.getBaseFile().getAbsolutePath();
				if (pathMap.containsKey(path))
				{
					returnValue = false;
				}
				else
				{
					pathMap.put(path, addLoopbackInode);
					returnValue = true;
				}
			}
			if (!returnValue)
				super.removeInode(addInode);
		}
		else
		{
			returnValue = false;
		}
		return returnValue;
	}
	@Override
	public void removeInode(LoopbackInode removeInode)
	{
		synchronized(pathMap)
		{
			LoopbackInode removeLoopbackInode = (LoopbackInode)removeInode;
			pathMap.remove(removeLoopbackInode.getBaseFile().getAbsolutePath());
		}
		super.removeInode(removeInode);
	}

	public LoopbackInode retrieveInode(String path)
	{
		synchronized(pathMap)
		{
			return pathMap.get(path);
		}
	}
}

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
public class LoopbackVolume extends FUSEVolumeBase<LoopbackInode, LoopbackFileHandle, LoopbackDirHandle, LoopbackInodeManager, LoopbackHandleManager>
{
	File loopRootFile;
	LoopbackInode root;
	int inodeNum;

	public LoopbackVolume(File loopRootFile) throws InodeException
	{
		this.loopRootFile = loopRootFile;
		FUSEAttr attr = attrForFile(loopRootFile, 1);
		root = new LoopbackInode(this, loopRootFile, 1, 0, attr);
		inodeNum = 2;
	}

	public long nextInodeNum()
	{
		return inodeNum++;
	}

	public LoopbackInode getRoot()
	{
		return root;
	}

	public FUSEStatFS getStatFS(FUSEReqInfo reqInfo) throws InodeException
	{
		LoopbackInode inode = getRoot();
		return statFSForFile(inode.getBaseFile());
	}

	public LoopbackInode retrieveInode(long inodeNum)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public FUSEAttr attrForFile(File attrFile, long inodeNum) throws InodeException
	{
		FUSEAttr returnAttr = new FUSEAttr();
		returnAttr.setInode(inodeNum);
		Stat stat = LibC.newStat();
		int errno = LibC.lstat(attrFile.getAbsolutePath(), stat);

		if (errno != 0)
		{
			throw InodeException.exceptionForErrorNum(Native.getLastError());
		}

		returnAttr.setMode(stat.get_st_mode());
		returnAttr.setNLink(stat.get_st_nlink());
		returnAttr.setUID(stat.get_st_uid());
		returnAttr.setGID(stat.get_st_gid());
		returnAttr.setRDev((int)stat.get_st_rdev());
		returnAttr.setSize(stat.get_st_size());
		returnAttr.setBlockSize(stat.get_st_blksize());
		returnAttr.setBlocks(stat.get_st_blocks());

		returnAttr.setATime(stat.get_st_atime());
		returnAttr.setATimeNSec((int)stat.get_st_atimensec());
		returnAttr.setCTime(stat.get_st_ctime());
		returnAttr.setCTimeNSec((int)stat.get_st_ctimensec());
		returnAttr.setMTime(stat.get_st_mtime());
		returnAttr.setMTimeNSec((int)stat.get_st_mtimensec());
		return returnAttr;
	}

	public FUSEStatFS statFSForFile(File statFSFile) throws InodeException
	{
		FUSEStatFS returnStatFS = new FUSEStatFS();
		StatFS statFSStructure = LibC.newStatFS();
		int errno = LibC.statfs(statFSFile.getAbsolutePath(), statFSStructure);

		if (errno != 0)
		{
			throw InodeException.exceptionForErrorNum(Native.getLastError());
		}

		returnStatFS.setBAvail(statFSStructure.get_f_bavail());
		returnStatFS.setBlocks(statFSStructure.get_f_blocks());
		returnStatFS.setBSize(statFSStructure.get_f_bsize());
		returnStatFS.setFFree(statFSStructure.get_f_ffree());
		returnStatFS.setFiles(statFSStructure.get_f_files());
		returnStatFS.setFRSize(statFSStructure.get_f_bsize());
		returnStatFS.setNameLen(256);
		return returnStatFS;
	}

	@Override
	public LoopbackHandleManager allocateHandleManager()
	{
		return new LoopbackHandleManager();
	}

	@Override
	public LoopbackInodeManager allocateInodeManager()
	{
		LoopbackInodeManager returnManager = new LoopbackInodeManager(this);

		returnManager.addInode(getRoot());
		return returnManager;
	}

	public FUSEInodeAdapter<LoopbackInode, ? extends InodeManager<LoopbackInode>, ?, ?, ?> getAdapter()
	{
		return adapter;
	}	
}
