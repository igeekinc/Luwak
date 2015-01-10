/*
 * Copyright 2002-2014 iGeek, Inc.
 * All Rights Reserved
 * @@OpenSource@@
 */
package com.igeekinc.luwak.examples.loopback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.igeekinc.luwak.FUSEAttr;
import com.igeekinc.luwak.LibC;
import com.igeekinc.luwak.Stat;
import com.igeekinc.luwak.inode.CreateInfo;
import com.igeekinc.luwak.inode.FUSEInode;
import com.igeekinc.luwak.inode.FUSEReqInfo;
import com.igeekinc.luwak.inode.SetAttrs;
import com.igeekinc.luwak.inode.exceptions.ExistsException;
import com.igeekinc.luwak.inode.exceptions.InodeException;
import com.igeekinc.luwak.inode.exceptions.IsDirectoryException;
import com.igeekinc.luwak.inode.exceptions.NoEntryException;
import com.igeekinc.luwak.inode.exceptions.NotDirectoryException;
import com.igeekinc.luwak.inode.exceptions.PermissionException;
import com.igeekinc.util.linux.LinuxOSConstants;
import com.igeekinc.util.unix.UnixDate;
import com.sun.jna.Native;

public class LoopbackInode extends FUSEInode<LoopbackVolume>
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
	
	
	@Override
	public FUSEAttr getAttr() throws InodeException
	{
		return attrForFile(baseFile, getInodeNum());
	}

	@Override
	public void setAttr(FUSEAttr attr)
	{
		
	}

	@Override
	public FUSEAttr getAttr(FUSEReqInfo reqInfo) throws InodeException
	{
		return getAttr();
	}

	
	public FUSEAttr setAttr(FUSEReqInfo reqInfo, SetAttrs newAttr)
			throws InodeException
	{
		int errno = 0;
		if (newAttr.isModeValid())
			errno = LibC.chmod(baseFile.getAbsolutePath(), newAttr.getMode());
		if (errno != 0)
			throw InodeException.exceptionForErrorNum(Native.getLastError());
		if (newAttr.isUIDValid() || newAttr.isGIDValid())
		{
			int uid = -1, gid = -1;
			if (newAttr.isUIDValid())
				uid = newAttr.getUID();
			if (newAttr.isGIDValid())
				gid = newAttr.getGID();
			errno = LibC.chown(baseFile.getAbsolutePath(), uid, gid);
			if (errno != 0)
				throw InodeException.exceptionForErrorNum(Native.getLastError());
		}

		if (newAttr.isAccessTimeValid() || newAttr.isModifyTimeValid())
		{
			UnixDate accessTime, modifyTime;
			if (newAttr.isAccessTimeValid())
			{
				accessTime = newAttr.getAccessTime();
			}
			else
			{
				FUSEAttr oldAttr = attrForFile(getBaseFile(), getInodeNum());
				accessTime = oldAttr.getATimeDate();
			}
			if (newAttr.isModifyTimeValid())
			{
				modifyTime = newAttr.getModifyTime();
			}
			else
			{
				FUSEAttr oldAttr = attrForFile(getBaseFile(), getInodeNum());
				modifyTime = oldAttr.getMTimeDate();
			}
			errno = LibC.utimes(baseFile.getAbsolutePath(), accessTime, modifyTime);
			if (errno != 0)
				throw InodeException.exceptionForErrorNum(Native.getLastError());
		}
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