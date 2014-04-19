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
 
package com.igeekinc.luwak.inode;

import com.igeekinc.luwak.FUSEDirEntry;
import com.igeekinc.luwak.messages.FUSEReaddirOutMessage;

public class DirectoryEntryBuffer
{
	int maxSize, spaceUsed;
	FUSEReaddirOutMessage outMessage = new FUSEReaddirOutMessage();
	
	public DirectoryEntryBuffer(int maxSize)
	{
		this.maxSize = maxSize;
		spaceUsed = 0;
	}
	
	public boolean addDirEntry(FUSEDirEntry addEntry)
	{
		if (spaceUsed + addEntry.getLength() > maxSize)
			return false;
		spaceUsed += addEntry.getLength();
		outMessage.addDirEntry(addEntry);
		return true;
	}
	
	public int getMaxSize()
	{
		return maxSize;
	}
	
	public int getSpaceUsed()
	{
		return spaceUsed;
	}
	
	protected FUSEReaddirOutMessage getOutMessage()
	{
		return outMessage;
	}
	
	public String toString()
	{
		return outMessage.toString();
	}
}
