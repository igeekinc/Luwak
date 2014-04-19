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
 
package com.igeekinc.luwak.lowlevel;

import org.apache.log4j.Logger;

import com.igeekinc.luwak.FUSEDispatch;
import com.igeekinc.luwak.inode.exceptions.InodeException;
import com.igeekinc.luwak.messages.FUSEFlushInMessage;
import com.igeekinc.luwak.messages.FUSEInMessage;
import com.igeekinc.luwak.messages.FUSEInitInMessage;
import com.igeekinc.luwak.messages.FUSEInitOutMessage;
import com.igeekinc.luwak.messages.FUSEOpOutMessage;
import com.igeekinc.luwak.messages.FUSEOutMessage;
import com.igeekinc.luwak.messages.FUSEReleaseInMessage;
import com.igeekinc.luwak.util.FUSEDirHandle;
import com.igeekinc.luwak.util.FUSEFileHandle;
import com.igeekinc.luwak.util.FUSEHandleManager;

/**
 * FUSELowLevelBase is a good starting point for a low level FUSE file system.  It provides
 * default functions for many of the file system entry points and convenience functions.
 * 
 * Low Level filesystems do not need to be based on FUSELowLevelBase, however.  This class is for convenience only.
 *
 */
public abstract class FUSELowLevelBase<F extends FUSEFileHandle, D extends FUSEDirHandle, H extends FUSEHandleManager<F, D>> implements FUSELowLevel
{

	protected FUSEDispatch dispatch;
	protected Logger logger = Logger.getLogger(getClass());
	
	public FUSELowLevelBase()
	{
	}
	
	public void init(FUSEInMessage message, FUSEInitInMessage initMessage)
	{
		FUSEOutMessage reply = new FUSEOutMessage(message);
		FUSEInitOutMessage opReply = new FUSEInitOutMessage();
		opReply.setMajor(initMessage.getMajor());
		opReply.setMinor(initMessage.getMinor());
		opReply.setFlags(initMessage.getFlags());
		opReply.setMaxReadAhead(0);
		opReply.setMaxWrite(1024*1024);
		
		reply.setOpReply(opReply);
		dispatch.sendReply(reply);
	}

	public void setDispatch(FUSEDispatch dispatch)
	{
		this.dispatch = dispatch;
	}

	
	public int maxThreadsToRun()
	{
		return 1;
	}

	protected void replyWithError(FUSEInMessage messagetoReplyTo, int errorNum)
	{
		if (errorNum > 0)
			throw new IllegalArgumentException("Error numbers must be negative");
		FUSEOutMessage reply = new FUSEOutMessage(messagetoReplyTo);
		reply.setError(errorNum);
		dispatch.sendReply(reply);
	}
	

	protected void replyWithError(FUSEInMessage messagetoReplyTo, InodeException e)
	{
		replyWithError(messagetoReplyTo, -e.getErrorNum());
	}
	
	protected void replyOK(FUSEInMessage messageToReplyTo)
	{
		FUSEOutMessage reply = new FUSEOutMessage(messageToReplyTo);
		dispatch.sendReply(reply);
	}

	protected void replyOK(FUSEInMessage messageToReplyTo, FUSEOpOutMessage opReply)
	{
		FUSEOutMessage reply = new FUSEOutMessage(messageToReplyTo);
		reply.setOpReply(opReply);
		dispatch.sendReply(reply);
	}
	
	public void flush(FUSEInMessage message, FUSEFlushInMessage flush)
	{
		replyOK(message);
	}

	public void release(FUSEInMessage message, FUSEReleaseInMessage releaseMessage)
	{
		F releaseHandle = getHandleManager().retrieveFileHandle(releaseMessage.getFileHandleNum());
		if (releaseHandle != null)
			getHandleManager().releaseFileHandle(releaseHandle);
		replyOK(message);
	}

	public void releasedir(FUSEInMessage message, FUSEReleaseInMessage releaseDirMessage)
	{
		D releaseHandle = getHandleManager().retrieveDirHandle(releaseDirMessage.getFileHandleNum());
		if (releaseHandle != null)
			getHandleManager().releaseDirHandle(releaseHandle);
		replyOK(message);
	}
	
	
	public void batchForget(FUSEInMessage inMessage)
	{
		replyOK(inMessage);
	}

	public void notifyReply(FUSEInMessage inMessage)
	{
		replyOK(inMessage);
	}

	public abstract H getHandleManager();
}
