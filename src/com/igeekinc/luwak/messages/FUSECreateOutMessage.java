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

public class FUSECreateOutMessage extends FUSEOpOutMessage 
{
	FUSEOpenOutMessage openMessage;
	FUSEEntryOutMessage entryMessage;
		
	public FUSECreateOutMessage()
	{
		super(FUSEEntryOutMessage.kEntryOutMessageLength + FUSEOpenOutMessage.kOpenOutMessageLength);
	}
	
	public void setOpenReply(FUSEOpenOutMessage openMessage)
	{
		this.openMessage = openMessage;
	}
	
	public void setEntryReply(FUSEEntryOutMessage entryMessage)
	{
		this.entryMessage = entryMessage;
	}
	
	public int getNumBuffers()
	{
		return entryMessage.getNumBuffers() + openMessage.getNumBuffers();
	}
	
	public void getBuffers(ByteBuffer[] bufferList, int bufferListOffset)
	{
		entryMessage.getBuffers(bufferList, bufferListOffset);
		bufferListOffset = entryMessage.getNumBuffers() + bufferListOffset;
		openMessage.getBuffers(bufferList, bufferListOffset);
	}
}
