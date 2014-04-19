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
 
package com.igeekinc.luwak.messages;

import java.nio.ByteBuffer;

import com.igeekinc.util.BufferStructure;

public class FUSEInMessage extends BufferStructure
{
	public static final int kLenOffset = 0;
	public static final int kOpcodeOffset = kLenOffset + 4;
	public static final int kUniqueOffset = kOpcodeOffset + 4;
	public static final int kNodeIDOffset = kUniqueOffset + 8;
	public static final int kUIDOffset = kNodeIDOffset + 8;
	public static final int kGIDOffset = kUIDOffset + 4;
	public static final int kPIDOffset = kGIDOffset + 4;
	public static final int kPaddingOffset = kPIDOffset + 4;
	
	public static final int kHeaderLength = kPaddingOffset + 4;
	
	public static final int FUSE_LOOKUP	   = 1;
	public static final int FUSE_FORGET	   = 2;  /* no reply */
	public static final int FUSE_GETATTR	   = 3;
	public static final int FUSE_SETATTR	   = 4;
	public static final int FUSE_READLINK	   = 5;
	public static final int FUSE_SYMLINK	   = 6;
	public static final int FUSE_MKNOD	   = 8;
	public static final int FUSE_MKDIR	   = 9;
	public static final int FUSE_UNLINK	   = 10;
	public static final int FUSE_RMDIR	   = 11;
	public static final int FUSE_RENAME	   = 12;
	public static final int FUSE_LINK	   = 13;
	public static final int FUSE_OPEN	   = 14;
	public static final int FUSE_READ	   = 15;
	public static final int FUSE_WRITE	   = 16;
	public static final int FUSE_STATFS	   = 17;
	public static final int FUSE_RELEASE       = 18;
	public static final int FUSE_FSYNC         = 20;
	public static final int FUSE_SETXATTR      = 21;
	public static final int FUSE_GETXATTR      = 22;
	public static final int FUSE_LISTXATTR     = 23;
	public static final int FUSE_REMOVEXATTR   = 24;
	public static final int FUSE_FLUSH         = 25;
	public static final int FUSE_INIT          = 26;
	public static final int FUSE_OPENDIR       = 27;
	public static final int FUSE_READDIR       = 28;
	public static final int FUSE_RELEASEDIR    = 29;
	public static final int FUSE_FSYNCDIR      = 30;
	public static final int FUSE_GETLK         = 31;
	public static final int FUSE_SETLK         = 32;
	public static final int FUSE_SETLKW        = 33;
	public static final int FUSE_ACCESS        = 34;
	public static final int FUSE_CREATE        = 35;
	public static final int FUSE_INTERRUPT     = 36;
	public static final int FUSE_BMAP          = 37;
	public static final int FUSE_DESTROY       = 38;
	public static final int FUSE_IOCTL         = 39;
	public static final int FUSE_POLL          = 40;
	public static final int FUSE_NOTIFY_REPLY  = 41;
	public static final int FUSE_BATCH_FORGET  = 42;
	
	/* CUSE specific operations */
	public static final int CUSE_INIT          = 4096;

	public FUSEInMessage(byte [] buffer, int baseOffset, int length)
	{
		super(buffer, baseOffset, length);
		if (length < kHeaderLength)
			throw new IllegalArgumentException("Length "+length+" is less than kHeaderLength ("+kHeaderLength+")");
		if (baseOffset + length > buffer.length)
			throw new ArrayIndexOutOfBoundsException(baseOffset + length);
	}
	
	public FUSEInMessage(ByteBuffer buffer, int baseOffset, int length)
	{
		this(buffer.array(), baseOffset, length);
	}
	
	public int getLen()
	{
		return getIntAtOffset(kLenOffset);
	}
	
	public int getOpcode()
	{
		return getIntAtOffset(kOpcodeOffset);
	}
	
	public long getUnique()
	{
		return getLongAtOffset(kUniqueOffset);
	}
	
	public long getNodeID()
	{
		return getLongAtOffset(kNodeIDOffset);
	}
	
	public int getUID()
	{
		return getIntAtOffset(kUIDOffset);
	}
	
	public int getGID()
	{
		return getIntAtOffset(kGIDOffset);
	}
	
	public int getPID()
	{
		return getIntAtOffset(kPIDOffset);
	}
	
	protected void initOpMessage(FUSEOpInMessage messageToInit)
	{
		messageToInit.init(buffer, baseOffset+kHeaderLength, length - kHeaderLength);
	}
}
