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

import com.igeekinc.util.unix.UnixDate;

public class FUSESetAttrInMessage extends FUSEOpInMessage
{
	public static final int kValidOffset = 0;
	public static final int kPaddingOffset = kValidOffset + 4;
	public static final int kFileHandleOffset = kPaddingOffset + 4;
	public static final int kSizeOffset = kFileHandleOffset + 8 ;
	public static final int kLockOwnerOffset = kSizeOffset + 8;
	public static final int kAccessTimeOffset = kLockOwnerOffset + 8;
	public static final int kModifyTimeOffset = kAccessTimeOffset + 8 ;
	public static final int kUnused2Offset = kModifyTimeOffset + 8;
	public static final int kAccessTimeNSecsOffset = kUnused2Offset + 8;
	public static final int kModifyTimeNSecsOffset = kAccessTimeNSecsOffset + 4;
	public static final int kUnused3Offset = kModifyTimeNSecsOffset + 4;
	public static final int kModeOffset = kUnused3Offset +4;
	public static final int kUnused4Offset = kModeOffset + 4;
	public static final int kUIDOffset = kUnused4Offset + 4;
	public static final int kGIDOffset = kUIDOffset + 4;
	public static final int kUnused5Offset = kGIDOffset + 4;
	
	public static final int kSetAttrMessageLength = kUnused5Offset +4;
	
	public static final int FATTR_MODE =		(1 << 0);
	public static final int FATTR_UID = 		(1 << 1);
	public static final int FATTR_GID = 		(1 << 2);
	public static final int FATTR_SIZE =		(1 << 3);
	public static final int FATTR_ATIME	=		(1 << 4);
	public static final int FATTR_MTIME	=		(1 << 5);
	public static final int FATTR_FH =			(1 << 6);
	public static final int FATTR_ATIME_NOW	=	(1 << 7);
	public static final int FATTR_MTIME_NOW	=	(1 << 8);
	public static final int FATTR_LOCKOWNER	=	(1 << 9);
	
	public FUSESetAttrInMessage(FUSEInMessage parent)
	{
		super(parent);
	}
	
	public int getValid()
	{
		return getIntAtOffset(kValidOffset);
	}
	
	public long getFileHandle()
	{
		return getLongAtOffset(kFileHandleOffset);
	}
	
	public long getSize()
	{
		return getLongAtOffset(kSizeOffset);
	}
	
	public long getLockOwner()
	{
		return getLongAtOffset(kLockOwnerOffset);
	}
	
	public long getAccessTimeSecs()
	{
		return getLongAtOffset(kAccessTimeOffset);
	}
	
	public int getAccessTimeNSecs()
	{
		return getIntAtOffset(kAccessTimeNSecsOffset);
	}
	
	public UnixDate getAccessTime()
	{
		return new UnixDate(getAccessTimeSecs(), getAccessTimeNSecs());
	}
	
	public long getModifyTimeSecs()
	{
		return getLongAtOffset(kModifyTimeOffset);
	}
	
	public int getModifyTimeNSecs()
	{
		return getIntAtOffset(kModifyTimeNSecsOffset);
	}
	
	public UnixDate getModifyTime()
	{
		return new UnixDate(getModifyTimeSecs(), getModifyTimeNSecs());
	}
	
	public int getMode()
	{
		return getIntAtOffset(kModeOffset);
	}
	
	public int getUID()
	{
		return getIntAtOffset(kUIDOffset);
	}
	
	public int getGID()
	{
		return getIntAtOffset(kGIDOffset);
	}
}
