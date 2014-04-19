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

import com.igeekinc.luwak.FUSEDispatch;
import com.igeekinc.luwak.messages.FUSECreateInMessage;
import com.igeekinc.luwak.messages.FUSEFlushInMessage;
import com.igeekinc.luwak.messages.FUSEGetXattrInMessage;
import com.igeekinc.luwak.messages.FUSEInMessage;
import com.igeekinc.luwak.messages.FUSEInitInMessage;
import com.igeekinc.luwak.messages.FUSELookupInMessage;
import com.igeekinc.luwak.messages.FUSEMkdirInMessage;
import com.igeekinc.luwak.messages.FUSEOpenInMessage;
import com.igeekinc.luwak.messages.FUSEReadInMessage;
import com.igeekinc.luwak.messages.FUSEReleaseInMessage;
import com.igeekinc.luwak.messages.FUSESetAttrInMessage;
import com.igeekinc.luwak.messages.linux.LinuxFUSEWriteInMessage;
import com.igeekinc.luwak.messages.macosx.OSXFUSEWriteInMessage;

public interface FUSELowLevel 
{
	public void setDispatch(FUSEDispatch dispatch);
	
	/**
	 * Return 0 for an unlimited number of threads, 1 to ensure single threadedness and any number
	 * in-between to set an upper limit.  The number of threads being run is decided by FUSEDispatch
	 * @return Maximum number of threads to run
	 */
	public int maxThreadsToRun();

	public void lookup(FUSEInMessage message, FUSELookupInMessage lookupMessage);
	public void forget(FUSEInMessage message);
	/**
	 * Retrieves attributes for the file identified by message.nodeID
	 * Reply with a FUSEGetAttrOutMessage as the opReply
	 * @param message
	 */
	public void getattr(FUSEInMessage message);
	public void setattr(FUSEInMessage message, FUSESetAttrInMessage setAttrMessage);
	public void readlink(FUSEInMessage message);
	public void symlink(FUSEInMessage message);
	public void mknod(FUSEInMessage message);
	public void mkdir(FUSEInMessage message, FUSEMkdirInMessage mkdirMessage);
	public void unlink(FUSEInMessage message);
	public void rmdir(FUSEInMessage message);
	public void rename(FUSEInMessage message);
	public void link(FUSEInMessage message);
	public void open(FUSEInMessage message, FUSEOpenInMessage openMessage);
	public void read(FUSEInMessage message, FUSEReadInMessage readMessage);
	public void write(FUSEInMessage message, LinuxFUSEWriteInMessage writeMessage);
	public void write(FUSEInMessage message, OSXFUSEWriteInMessage writeMessage);
	public void statfs(FUSEInMessage message);
	public void release(FUSEInMessage message, FUSEReleaseInMessage releaseMessage);
	public void fsync(FUSEInMessage message);
	public void setxattr(FUSEInMessage message);
	
	/**
	 * Returns the bytes in the extended attribute or the size of the extended attribute
	 * in a FUSEGetXAttrOutMessage if the buffer size passed is 0.  If the buffer size passed is 0 and the size of the
	 * attribute is 0, do not return a FUSEGetXAttrOutMessage, instead return a zero length read buffer
	 * @param message
	 * @param getXAttrMessage
	 */
	public void getxattr(FUSEInMessage message, FUSEGetXattrInMessage getXAttrMessage);
	
	/**
	 * listxattr shares the FUSEGetXAttrInMessage with getxattr.  The only valid parameter
	 * is the size, which indicates the size of the buffer available
	 * @param message
	 * @param getXAttrMessage
	 */
	public void listxattr(FUSEInMessage message, FUSEGetXattrInMessage getXAttrMessage);
	public void removexattr(FUSEInMessage message);
	public void flush(FUSEInMessage message, FUSEFlushInMessage flushMessage);
	public void init(FUSEInMessage message, FUSEInitInMessage initMessage);
	public void opendir(FUSEInMessage message, FUSEOpenInMessage openMessage);
	public void readdir(FUSEInMessage message, FUSEReadInMessage readMessage);
	public void releasedir(FUSEInMessage message, FUSEReleaseInMessage releaseDirMessage);
	public void fsyncdir(FUSEInMessage message);
	public void getlk(FUSEInMessage message);
	public void setlk(FUSEInMessage message);
	public void setlkw(FUSEInMessage message);
	public void access(FUSEInMessage message);
	public void create(FUSEInMessage message, FUSECreateInMessage createMessage);
	public void interrupt(FUSEInMessage message);
	public void bmap(FUSEInMessage message);
	public void destroy(FUSEInMessage message);
	public void ioctl(FUSEInMessage message);
	public void poll(FUSEInMessage message);
	public void notifyReply(FUSEInMessage inMessage);
	public void batchForget(FUSEInMessage inMessage);
}
