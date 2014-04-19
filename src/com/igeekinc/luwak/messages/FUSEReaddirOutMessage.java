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
import java.util.ArrayList;

import com.igeekinc.luwak.FUSEDirEntry;

public class FUSEReaddirOutMessage extends FUSEOpOutMessage
{
	ArrayList<FUSEDirEntry>dirEntries = new ArrayList<FUSEDirEntry>();
	public FUSEReaddirOutMessage()
	{
		super(0);
	}
	
	public void addDirEntry(FUSEDirEntry addEntry)
	{
		dirEntries.add(addEntry);
	}
	
	public int getNumBuffers()
	{
		return dirEntries.size();
	}
	
	public void getBuffers(ByteBuffer[] bufferList, int bufferListOffset)
	{
		for (int curDirEntryNum = 0; curDirEntryNum < dirEntries.size(); curDirEntryNum++)
		{
			bufferList[bufferListOffset + curDirEntryNum] = dirEntries.get(curDirEntryNum).getBuffer();
		}
	}
	
	public String toString()
	{
		int size = 0, offset = 0;
		String dirStrings = "";
		for (FUSEDirEntry curEntry:dirEntries)
		{
			size += curEntry.getLength();
			dirStrings = dirStrings + " bufOffset = "+offset+" size = "+curEntry.getLength()+" entry = "+curEntry.toString()+" : ";
			offset += curEntry.getLength();
		}
		
		String returnString = "size = "+size + dirStrings;

		return returnString;
	}
}
