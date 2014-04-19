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
 
package com.igeekinc.luwak.examples;

import java.nio.ByteBuffer;

import com.igeekinc.luwak.FUSEAttr;
import com.igeekinc.luwak.FUSEDirEntry;
import com.igeekinc.luwak.OSErrorCodes;
import com.igeekinc.luwak.inode.DirectoryEntryBuffer;
import com.igeekinc.luwak.inode.FUSEReqInfo;
import com.igeekinc.luwak.inode.exceptions.InodeException;
import com.igeekinc.luwak.lowlevel.FUSELowLevelBase;
import com.igeekinc.luwak.messages.FUSEAttrOutMessage;
import com.igeekinc.luwak.messages.FUSECreateInMessage;
import com.igeekinc.luwak.messages.FUSEDataOutMessage;
import com.igeekinc.luwak.messages.FUSEEntryOutMessage;
import com.igeekinc.luwak.messages.FUSEGetXattrInMessage;
import com.igeekinc.luwak.messages.FUSEInMessage;
import com.igeekinc.luwak.messages.FUSELookupInMessage;
import com.igeekinc.luwak.messages.FUSEMkdirInMessage;
import com.igeekinc.luwak.messages.FUSEOpenInMessage;
import com.igeekinc.luwak.messages.FUSEOpenOutMessage;
import com.igeekinc.luwak.messages.FUSEOutMessage;
import com.igeekinc.luwak.messages.FUSEReadInMessage;
import com.igeekinc.luwak.messages.FUSEReaddirOutMessage;
import com.igeekinc.luwak.messages.FUSESetAttrInMessage;
import com.igeekinc.luwak.messages.linux.LinuxFUSEWriteInMessage;
import com.igeekinc.luwak.messages.macosx.OSXFUSEWriteInMessage;
import com.igeekinc.luwak.util.FUSEDirHandle;
import com.igeekinc.luwak.util.FUSEFileHandle;
import com.igeekinc.luwak.util.FUSEHandleManager;
import com.igeekinc.util.linux.LinuxOSConstants;

class HelloFileHandle extends FUSEFileHandle
{
	protected HelloFileHandle(long handleNum)
	{
		super(handleNum);
	}

	@Override
	public int read(FUSEReqInfo reqInfo, long offset, byte[] returnBuffer,
			int flags, int readFlags) throws InodeException
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int write(FUSEReqInfo reqInfo, long offset, ByteBuffer writeBytes,
			int writeFlags) throws InodeException
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void flush(FUSEReqInfo reqInfo, long lockOwnerFlags)
			throws InodeException
	{
		// TODO Auto-generated method stub
		
	}
}

class HelloDirHandle extends FUSEDirHandle
{
	protected HelloDirHandle(long handleNum)
	{
		super(handleNum);
	}

	@Override
	public DirectoryEntryBuffer readdir(FUSEReqInfo reqInfo, long offset,
			int size, int flags, int readFlags) throws InodeException
	{
		// TODO Auto-generated method stub
		return null;
	}	
}

class HelloHandleManager extends FUSEHandleManager
{

	@Override
	protected FUSEDirHandle allocateDirHandle(long handleNum)
	{
		return new HelloDirHandle(handleNum);
	}

	@Override
	protected FUSEFileHandle allocateFileHandle(long handleNum)
	{
		return new HelloFileHandle(handleNum);
	}
	
}

public class HelloLowLevel extends FUSELowLevelBase 
{
	static final String kHelloString = "Hello World!\n";
	static final String kHelloName = "hello";	
	
	HelloHandleManager handleManager = new HelloHandleManager();
	public void access(FUSEInMessage message) {
		// TODO Auto-generated method stub

	}

	public void bmap(FUSEInMessage message) {
		// TODO Auto-generated method stub

	}

	public void create(FUSEInMessage message, FUSECreateInMessage createMessage) 
	{
		// TODO Auto-generated method stub

	}
	
	public void destroy(FUSEInMessage message) {
		// TODO Auto-generated method stub

	}

	public void forget(FUSEInMessage message) {
		// TODO Auto-generated method stub

	}

	public void fsync(FUSEInMessage message) {
		// TODO Auto-generated method stub

	}

	public void fsyncdir(FUSEInMessage message) {
		// TODO Auto-generated method stub

	}

	int hello_stat(int inodeNum, FUSEAttr stat)
	{
		int retVal = 0;
		stat.setInode(inodeNum);
		switch(inodeNum)
		{
		case 1:
			stat.setMode(LinuxOSConstants.S_IFDIR | 0755);
			stat.setNLink(2);
			break;
		case 2:
			stat.setMode(LinuxOSConstants.S_IFREG | 0444);
			stat.setNLink(1);
			stat.setSize(kHelloString.length());
			break;
		default:
			retVal = -1;
			break;
		}
		return retVal;
	}
	
	public void getattr(FUSEInMessage message) 
	{
		FUSEAttr stat = new FUSEAttr();
		if (hello_stat((int)message.getNodeID(), stat) == -1)
		{
			
		}
		else
		{
			FUSEOutMessage reply = new FUSEOutMessage(message);
			
			FUSEAttrOutMessage opReply = new FUSEAttrOutMessage(stat);
			reply.setOpReply(opReply);
			
			dispatch.sendReply(reply);
			
		}
	}

	public void getlk(FUSEInMessage message) {
		// TODO Auto-generated method stub

	}

	public void getxattr(FUSEInMessage message) 
	{
		replyWithError(message, -OSErrorCodes.ENOSYS);
	}

	public void interrupt(FUSEInMessage message) {
		// TODO Auto-generated method stub

	}

	public void ioctl(FUSEInMessage message) {
		// TODO Auto-generated method stub

	}

	public void link(FUSEInMessage message) {
		// TODO Auto-generated method stub

	}

	public void listxattr(FUSEInMessage message) {
		// TODO Auto-generated method stub

	}

	public void lookup(FUSEInMessage message, FUSELookupInMessage lookupMessage) 
	{
		FUSEOutMessage reply = new FUSEOutMessage(message);
		if (message.getNodeID() != 1 || !lookupMessage.getName().equals(kHelloName))
		{
			reply.setError(-OSErrorCodes.ENOENT);
		}
		else
		{
			FUSEEntryOutMessage opReply = new FUSEEntryOutMessage();
			opReply.setNodeID(2);
			opReply.setAttrValid(1);
			opReply.setEntryValid(1);
			hello_stat(2, opReply.getAttr());
			reply.setOpReply(opReply);
		}
		dispatch.sendReply(reply);
	}

	public void mkdir(FUSEInMessage message, FUSEMkdirInMessage mkdirMessage) 
	{
		// TODO Auto-generated method stub

	}

	public void mknod(FUSEInMessage message) {
		// TODO Auto-generated method stub

	}

	public void open(FUSEInMessage message, FUSEOpenInMessage openMessage) 
	{
		if (message.getNodeID() != 2)
			replyWithError(message, -OSErrorCodes.EISDIR);
		else
		{
			if ((openMessage.getFlags() & 3) != LinuxOSConstants.O_RDONLY)
			{
				replyWithError(message, -OSErrorCodes.EACCES);
			}
			else
			{
				FUSEOutMessage reply = new FUSEOutMessage(message);
				FUSEOpenOutMessage opReply = new FUSEOpenOutMessage();
				FUSEFileHandle fileHandle = getHandleManager().getNewFileHandle();
				opReply.setFileHandle(fileHandle.getHandleNum());
				reply.setOpReply(opReply);
				dispatch.sendReply(reply);
			}
		}
	}

	public void opendir(FUSEInMessage message, FUSEOpenInMessage openMessage) 
	{
		FUSEOutMessage reply = new FUSEOutMessage(message);
		FUSEOpenOutMessage opReply = new FUSEOpenOutMessage();
		FUSEDirHandle dirHandle = getHandleManager().getNewDirHandle();
		opReply.setFileHandle(dirHandle.getHandleNum());
		reply.setOpReply(opReply);
		
		dispatch.sendReply(reply);
	}

	public void poll(FUSEInMessage message) {
		// TODO Auto-generated method stub

	}

	public void read(FUSEInMessage message, FUSEReadInMessage readMessage) 
	{
		FUSEOutMessage reply = new FUSEOutMessage(message);
		byte[] helloBytes = kHelloString.getBytes();
		if (readMessage.getOffset() < helloBytes.length)
		{
			FUSEDataOutMessage data = new FUSEDataOutMessage(helloBytes, (int)readMessage.getOffset(), (int)(helloBytes.length - readMessage.getOffset()));
			reply.setOpReply(data);
		}
		dispatch.sendReply(reply);
	}

	public void readdir(FUSEInMessage message, FUSEReadInMessage readMessage) 
	{
		long nodeID = message.getNodeID();
		FUSEOutMessage reply = new FUSEOutMessage(message);
		if (nodeID == 1)
		{
			FUSEReaddirOutMessage opReply = new FUSEReaddirOutMessage();
			int offset = 0;
			FUSEDirEntry dotEntry = new FUSEDirEntry(1, 0, FUSEDirEntry.DT_DIR, ".");
			offset += dotEntry.getLength();
			dotEntry.setOffset(offset);// nodeID 1 == root
				
			FUSEDirEntry dotdotEntry = new FUSEDirEntry(1, 1, FUSEDirEntry.DT_DIR, "..");
			offset += dotdotEntry.getLength();
			dotdotEntry.setOffset(offset);// root .. entry points to itself
			
			FUSEDirEntry helloEntry = new FUSEDirEntry(2, 2, FUSEDirEntry.DT_DIR, kHelloName);
			offset += helloEntry.getLength();
			helloEntry.setOffset(offset);
			
			if ( readMessage.getOffset() < offset)
			{
				opReply.addDirEntry(dotEntry);
				opReply.addDirEntry(dotdotEntry);	
				opReply.addDirEntry(helloEntry);
				reply.setOpReply(opReply);
			}
			else
			{
				
			}

		}
		else
		{
			reply.setError(-OSErrorCodes.ENOTDIR);
		}
		
		dispatch.sendReply(reply);
	}

	public void readlink(FUSEInMessage message) {
		// TODO Auto-generated method stub

	}

	public void removexattr(FUSEInMessage message) {
		// TODO Auto-generated method stub

	}

	public void rename(FUSEInMessage message) {
		// TODO Auto-generated method stub

	}

	public void rmdir(FUSEInMessage message) {
		// TODO Auto-generated method stub

	}

	public void setattr(FUSEInMessage message, FUSESetAttrInMessage setAttrMessage) 
	{
		// TODO Auto-generated method stub

	}

	public void setlk(FUSEInMessage message) {
		// TODO Auto-generated method stub

	}

	public void setlkw(FUSEInMessage message) {
		// TODO Auto-generated method stub

	}

	public void setxattr(FUSEInMessage message) {
		// TODO Auto-generated method stub

	}

	public void statfs(FUSEInMessage message) {
		// TODO Auto-generated method stub

	}

	public void symlink(FUSEInMessage message) {
		// TODO Auto-generated method stub

	}

	public void unlink(FUSEInMessage message) {
		// TODO Auto-generated method stub

	}

	public void write(FUSEInMessage message, LinuxFUSEWriteInMessage writeMessage) 
	{
		// TODO Auto-generated method stub

	}

	public void write(FUSEInMessage message, OSXFUSEWriteInMessage writeMessage) 
	{
		// TODO Auto-generated method stub

	}

	public void getxattr(FUSEInMessage message,
			FUSEGetXattrInMessage getXAttrMessage)
	{
		// TODO Auto-generated method stub
		
	}

	public void listxattr(FUSEInMessage message,
			FUSEGetXattrInMessage getXAttrMessage)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public FUSEHandleManager getHandleManager() 
	{
		return handleManager;
	}

}
