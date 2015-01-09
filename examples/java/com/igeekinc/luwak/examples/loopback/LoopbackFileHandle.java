package com.igeekinc.luwak.examples.loopback;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

import com.igeekinc.luwak.inode.FUSEReqInfo;
import com.igeekinc.luwak.inode.exceptions.InodeException;
import com.igeekinc.luwak.inode.exceptions.InodeIOException;
import com.igeekinc.luwak.util.FUSEFileHandle;

public class LoopbackFileHandle extends FUSEFileHandle
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