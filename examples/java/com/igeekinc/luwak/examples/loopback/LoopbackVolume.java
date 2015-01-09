/*
 * Copyright 2002-2014 iGeek, Inc.
 * All Rights Reserved
 * @@OpenSource@@
 */
 
package com.igeekinc.luwak.examples.loopback;

import java.io.File;

import com.igeekinc.luwak.FUSEAttr;
import com.igeekinc.luwak.FUSEStatFS;
import com.igeekinc.luwak.LibC;
import com.igeekinc.luwak.Stat;
import com.igeekinc.luwak.StatFS;
import com.igeekinc.luwak.inode.FUSEInodeAdapter;
import com.igeekinc.luwak.inode.FUSEReqInfo;
import com.igeekinc.luwak.inode.FUSEVolumeBase;
import com.igeekinc.luwak.inode.FUSEInodeManager;
import com.igeekinc.luwak.inode.exceptions.InodeException;
import com.sun.jna.Native;

public class LoopbackVolume extends FUSEVolumeBase<LoopbackInode, LoopbackFileHandle, LoopbackDirHandle, LoopbackInodeManager, LoopbackHandleManager>
{
	private File loopRootFile;
	private LoopbackInode root;
	private int inodeNum;
	private LoopbackInodeManager inodeManager;
	
	public LoopbackVolume(File loopRootFile) throws InodeException
	{
		this.loopRootFile = loopRootFile;
		FUSEAttr attr = attrForFile(loopRootFile, 1);
		root = new LoopbackInode(this, loopRootFile, 1, 0, attr);
		inodeManager.addInode(root);
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
		inodeManager = new LoopbackInodeManager(this);
		return inodeManager;
	}

	public FUSEInodeAdapter<LoopbackInode, ? extends FUSEInodeManager<LoopbackInode>, ?, ?, ?> getAdapter()
	{
		return adapter;
	}	
}
