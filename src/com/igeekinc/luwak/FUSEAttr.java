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
 
package com.igeekinc.luwak;

import java.nio.ByteBuffer;

import com.igeekinc.util.BufferStructure;
import com.igeekinc.util.unix.UnixDate;

public class FUSEAttr extends BufferStructure 
{
	public static final int kInodeOffset = 0;
	public static final int kSizeOffset = kInodeOffset + 8;
	public static final int kBlocksOffset = kSizeOffset + 8;
	
	public static final int kATimeOffset = kBlocksOffset + 8;
	public static final int kMTimeOffset = kATimeOffset + 8;
	public static final int kCTimeOffset = kMTimeOffset + 8;
	
	public static final int kATimeNSecOffset;
	public static final int kCRTimeOffset;
	static {
	    switch (LibC.getSystemType())
	    {
	    case kLinux:
	        kATimeNSecOffset = kCTimeOffset + 8;
	        kCRTimeOffset = -1;
	        break;
	    case kMacOSX:
	        kCRTimeOffset = kCTimeOffset + 8;
	        kATimeNSecOffset = kCRTimeOffset + 8;
	        break;
	    default:
	        throw new InternalError("Unrecognized OS");
	    }
	}
	public static final int kMTimeNSecOffset = kATimeNSecOffset + 4;
	public static final int kCTimeNSecOffset = kMTimeNSecOffset + 4;
	
	public static final int kModeOffset;
	public static final int kCRTimeNSecOffset;
	static {
	    switch (LibC.getSystemType())
	    {
	    case kLinux:
	        kModeOffset = kCTimeNSecOffset + 4;
	        kCRTimeNSecOffset = -1;
	        break;
	    case kMacOSX:
	        kCRTimeNSecOffset  = kCTimeNSecOffset + 4;
	        kModeOffset = kCRTimeNSecOffset + 4;
	        break;
	    default:
	        throw new InternalError("Unrecognized OS");
	    }
	}
	
	public static final int kNLinkOffset = kModeOffset + 4;
	public static final int kUIDOffset = kNLinkOffset + 4;
	public static final int kGIDOffset = kUIDOffset + 4;
	public static final int kRDEVOffset = kGIDOffset + 4;
	
	public static final int kBlockSizeOffset;
	public static final int kPaddingOffset;
	
	public static final int kFlagsOffset;
	
	public static final int kFUSEAttrLength;

	static {
	    switch (LibC.getSystemType())
	    {
	    case kLinux:
	        kBlockSizeOffset = kRDEVOffset + 4;
	        kPaddingOffset = kBlockSizeOffset + 4;
	        kFUSEAttrLength = kPaddingOffset + 4;
	        kFlagsOffset = -1;
	        break;
	    case kMacOSX:
	        kFlagsOffset = kRDEVOffset + 4;
	        kFUSEAttrLength = kFlagsOffset + 4;
	        kBlockSizeOffset = -1;
	        kPaddingOffset = -1;
	        break;
	    default:
	        throw new InternalError("Unrecognized OS");
	    }
	}
	
	public FUSEAttr()
	{
		super(new byte[kFUSEAttrLength]);
	}
	
	public FUSEAttr(byte [] buffer, int offset, int length)
	{
		super(buffer, offset, length);
	}
	public long getInode()
	{
		return getLongAtOffset(kInodeOffset);
	}
	
	public void setInode(long inodeNum)
	{
		setLongAtOffset(inodeNum, kInodeOffset);
	}
	
	public long getSize()
	{
		return getLongAtOffset(kSizeOffset);
	}
	
	public void setSize(long size)
	{
		setLongAtOffset(size, kSizeOffset);
	}
	
	public long getBlocks()
	{
		return getLongAtOffset(kBlocksOffset);
	}
	
	public void setBlocks(long blocks)
	{
		setLongAtOffset(blocks, kBlocksOffset);
	}
	
	public long getATime()
	{
		return getLongAtOffset(kATimeOffset);
	}
	
	public void setATime(long atime)
	{
		setLongAtOffset(atime, kATimeOffset);
	}
	
	public int getATimeNSec()
	{
		return getIntAtOffset(kATimeNSecOffset);
	}
	
	public void setATimeNSec(int atimeNSec)
	{
		setIntAtOffset(atimeNSec, kATimeNSecOffset);
	}
	
	public UnixDate getATimeDate()
	{
		return new UnixDate(getATime(), getATimeNSec());
	}
	
	public void setATime(UnixDate atime)
	{
		setATime(atime.getSecsLong());
		setATimeNSec(atime.getNSecs());
	}
	
	public long getMTime()
	{
		return getLongAtOffset(kMTimeOffset);
	}
	
	public void setMTime(long mTime)
	{
		setLongAtOffset(mTime, kMTimeOffset);
	}
	
	public int getMTimeNSec()
	{
		return getIntAtOffset(kMTimeNSecOffset);
	}
	
	public void setMTimeNSec(int mTimeNSec)
	{
		setIntAtOffset(mTimeNSec, kMTimeNSecOffset);
	}
	
	public UnixDate getMTimeDate()
	{
		return new UnixDate(getMTime(), getMTimeNSec());
	}
	
	public void setMTime(UnixDate mtime)
	{
		setMTime(mtime.getSecsLong());
		setMTimeNSec(mtime.getNSecs());
	}
	
	public long getCTime()
	{
		return getLongAtOffset(kCTimeOffset);
	}
	
	public void setCTime(long atime)
	{
		setLongAtOffset(atime, kCTimeOffset);
	}
	
	public int getCTimeNSec()
	{
		return getIntAtOffset(kCTimeNSecOffset);
	}
	
	public void setCTimeNSec(int atimeNSec)
	{
		setIntAtOffset(atimeNSec, kCTimeNSecOffset);
	}
	
	public UnixDate getCTimeDate()
	{
		return new UnixDate(getCTime(), getCTimeNSec());
	}
	
	public void setCTime(UnixDate ctime)
	{
		setCTime(ctime.getSecsLong());
		setCTimeNSec(ctime.getNSecs());
	}
	
	public int getMode()
	{
		return getIntAtOffset(kModeOffset);
	}
	
	public void setMode(int mode)
	{
		setIntAtOffset(mode, kModeOffset);
	}
	
	public int getNLink()
	{
		return getIntAtOffset(kNLinkOffset);
	}
	
	public void setNLink(int nLink)
	{
		setIntAtOffset(nLink, kNLinkOffset);
	}
	
	public int getUID()
	{
		return getIntAtOffset(kUIDOffset);
	}
	
	public void setUID(int uid)
	{
		setIntAtOffset(uid, kUIDOffset);
	}
	
	public int getGID()
	{
		return getIntAtOffset(kGIDOffset);
	}
	
	public void setGID(int gid)
	{
		setIntAtOffset(gid, kGIDOffset);
	}
	
	public int getRDev()
	{
		return getIntAtOffset(kRDEVOffset);
	}
	
	public void setRDev(int rDev)
	{
		setIntAtOffset(rDev, kRDEVOffset);
	}
	
	public int getBlockSize()
	{
		if (kBlockSizeOffset >= 0) // Not used on OS X
			return getIntAtOffset(kBlockSizeOffset);
		return 0;
	}
	
	public void setBlockSize(int blockSize)
	{
	    if (kBlockSizeOffset >= 0) // Not used on OS X
	        setIntAtOffset(blockSize, kBlockSizeOffset);
	}
	
	public ByteBuffer getBuffer()
	{
		return super.getBuffer();
	}
	
	public String toString()
	{
		return "inode # = "+getInode()+", size = "+getSize()+", "+", blocks = "+getBlocks()+", aTime = "+getATimeDate()+
			", mTime = "+getMTimeDate()+", cTime = "+getCTimeDate()+", mode = "+getMode()+", nLink = "+getNLink()+
			", uid = "+getUID()+", gid = "+getGID()+", rdev = "+getRDev()+", block size = "+getBlockSize();
	}
}
