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

public class FUSEOpOutMessage extends BufferStructure 
{
	public FUSEOpOutMessage(int bufferSize)
	{
		super(new byte[bufferSize]);
	}
	
	protected FUSEOpOutMessage(byte [] buffer)
	{
		super(buffer);
	}
	
	protected FUSEOpOutMessage(byte [] buffer, int baseOffset, int length)
	{
		super(buffer, baseOffset, length);
	}
	
	public int getNumBuffers()
	{
		return 1;	// Override to have more buffers
	}
	
	public void getBuffers(ByteBuffer[] bufferList, int bufferListOffset)
	{
		bufferList[bufferListOffset] = getBuffer();
	}
}
