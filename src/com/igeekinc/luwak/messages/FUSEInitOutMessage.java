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

public class FUSEInitOutMessage extends FUSEOpOutMessage 
{
	public static final int kMajorOffset = 0;
	public static final int kMinorOffset = kMajorOffset + 4;
	public static final int kMaxReadAheadOffset = kMinorOffset + 4;
	public static final int kFlagsOffset = kMaxReadAheadOffset + 4;
	public static final int kUnusedOffset = kFlagsOffset + 4;
	public static final int kMaxWriteOffset = kUnusedOffset + 4;
	
	public static final int kInitOutLength = kMaxWriteOffset + 4;
	
	public FUSEInitOutMessage()
	{
		super(kInitOutLength);
	}
	
	public void setMajor(int major)
	{
		setIntAtOffset(major, kMajorOffset);
	}
	
	public void setMinor(int minor)
	{
		setIntAtOffset(minor, kMinorOffset);
	}
	
	public void setMaxReadAhead(int maxReadAhead)
	{
		setIntAtOffset(maxReadAhead, kMaxReadAheadOffset);
	}
	
	public void setFlags(int flags)
	{
		setIntAtOffset(flags, kFlagsOffset);
	}
	
	public void setMaxWrite(int maxWrite)
	{
		setIntAtOffset(maxWrite, kMaxWriteOffset);
	}
}
