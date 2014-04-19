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

public class FUSEReadInMessage extends FUSEOpInMessage
{
	public static final int kFileHandleOffset = 0;
	public static final int kOffsetOffset = kFileHandleOffset + 8;
	public static final int kSizeOffset = kOffsetOffset + 8;
	public static final int kReadFlagsOffset = kSizeOffset + 4;
	public static final int kLockOwnerOffset = kReadFlagsOffset + 4;
	public static final int kFlagsOffset = kLockOwnerOffset + 8;
	public static final int kPaddingOffset = kFlagsOffset + 4;
	
	public static final int kReadInMessageLength = kPaddingOffset + 4;
	
	public FUSEReadInMessage(FUSEInMessage parent)
	{
		super(parent);
	}
	
	public long getFileHandleNum()
	{
		return getLongAtOffset(kFileHandleOffset);
	}
	
	public long getOffset()
	{
		return getLongAtOffset(kOffsetOffset);
	}
	
	public int getSize()
	{
		return getIntAtOffset(kSizeOffset);
	}
	
	public int getReadFlags()
	{
		return getIntAtOffset(kReadFlagsOffset);
	}
	
	public long getLockOwner()
	{
		return getLongAtOffset(kLockOwnerOffset);
	}
	
	public int getFlags()
	{
		return getIntAtOffset(kFlagsOffset);
	}
}
