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

public class FUSEOutHeader extends BufferStructure
{
	public static final int kLenOffset = 0;
	public static final int kErrorOffset = kLenOffset + 4;
	public static final int kUniqueOffset = kErrorOffset + 4;
	public static final int kFUSEOutHeaderSize = kUniqueOffset + 8;

	public FUSEOutHeader()
	{
		super(new byte[kFUSEOutHeaderSize]);
	}
	
	public void setLength(int length)
	{
		setIntAtOffset(length, kLenOffset);
	}
	
	public void setError(int error)
	{
		setIntAtOffset(error, kErrorOffset);
	}
	
	public void setUnique(long unique)
	{
		setLongAtOffset(unique, kUniqueOffset);
	}
	
	public int getLength()
	{
		return getIntAtOffset(kLenOffset);
	}
	
	public int getError()
	{
		return getIntAtOffset(kErrorOffset);
	}
	
	public long getUnique()
	{
		return getLongAtOffset(kUniqueOffset);
	}
	
	public ByteBuffer getBuffer()
	{
		return super.getBuffer();
	}
}
