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

public class FUSEOutMessage 
{
	FUSEOutHeader header;
	FUSEOpOutMessage opReply;
	
	public FUSEOutMessage(long unique, int error)
	{
		header = new FUSEOutHeader();
		header.setUnique(unique);
		header.setError(error);
	}
	

	/**
	 * Use this to reply with no error
	 * @param replyToMessage
	 */
	public FUSEOutMessage(FUSEInMessage replyToMessage)
	{
		header = new FUSEOutHeader();
		header.setUnique(replyToMessage.getUnique());
		header.setError(0);
	}
	
	public void setOpReply(FUSEOpOutMessage opReply)
	{
		this.opReply = opReply;
	}
	
	public ByteBuffer [] getByteBuffers()
	{
		int numOpReplyBuffers = 0;
		if (opReply != null)
			numOpReplyBuffers = opReply.getNumBuffers();
		ByteBuffer [] returnBuffers = new ByteBuffer[1 + numOpReplyBuffers];
		if (opReply != null)
			opReply.getBuffers(returnBuffers, 1);
		int totalLength = FUSEOutHeader.kFUSEOutHeaderSize;
		for (int curBufferNum = 1; curBufferNum < returnBuffers.length; curBufferNum++)
			totalLength += returnBuffers[curBufferNum].limit();
		header.setLength(totalLength);
		returnBuffers[0] = header.getBuffer();
		return returnBuffers;
	}
	
	public void setError(int error)
	{
		header.setError(error);
	}
	
	public int getError()
	{
		return header.getError();
	}
	
	public long getUnique()
	{
		return header.getUnique();
	}
}
