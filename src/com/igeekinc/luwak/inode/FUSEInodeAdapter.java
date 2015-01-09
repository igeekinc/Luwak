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

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;

import com.igeekinc.luwak.FUSEAttr;
import com.igeekinc.luwak.FUSEStatFS;
import com.igeekinc.luwak.OSErrorCodes;
import com.igeekinc.luwak.inode.exceptions.InodeException;
import com.igeekinc.luwak.inode.exceptions.NoEntryException;
import com.igeekinc.luwak.inode.exceptions.OutOfRangeException;
import com.igeekinc.luwak.lowlevel.FUSELowLevelBase;
import com.igeekinc.luwak.messages.FUSEAttrOutMessage;
import com.igeekinc.luwak.messages.FUSECreateInMessage;
import com.igeekinc.luwak.messages.FUSECreateOutMessage;
import com.igeekinc.luwak.messages.FUSEDataOutMessage;
import com.igeekinc.luwak.messages.FUSEEntryOutMessage;
import com.igeekinc.luwak.messages.FUSEFlushInMessage;
import com.igeekinc.luwak.messages.FUSEGetXattrInMessage;
import com.igeekinc.luwak.messages.FUSEGetXattrOutMessage;
import com.igeekinc.luwak.messages.FUSEInMessage;
import com.igeekinc.luwak.messages.FUSELookupInMessage;
import com.igeekinc.luwak.messages.FUSEMkdirInMessage;
import com.igeekinc.luwak.messages.FUSEOpOutMessage;
import com.igeekinc.luwak.messages.FUSEOpenInMessage;
import com.igeekinc.luwak.messages.FUSEOpenOutMessage;
import com.igeekinc.luwak.messages.FUSEReadInMessage;
import com.igeekinc.luwak.messages.FUSEReaddirOutMessage;
import com.igeekinc.luwak.messages.FUSESetAttrInMessage;
import com.igeekinc.luwak.messages.FUSEStatFSOutMessage;
import com.igeekinc.luwak.messages.FUSEWriteOutMessage;
import com.igeekinc.luwak.messages.linux.LinuxFUSEWriteInMessage;
import com.igeekinc.luwak.messages.macosx.OSXFUSEGetXAttrInMessage;
import com.igeekinc.luwak.messages.macosx.OSXFUSEWriteInMessage;
import com.igeekinc.luwak.util.FUSEDirHandle;
import com.igeekinc.luwak.util.FUSEFileHandle;
import com.igeekinc.luwak.util.FUSEHandleManager;

/**
 * FUSEInodeAdapter takes the FUSELowLevel interface and makes it more straightforward to use.
 * To implement a file system with the inode level interfaces, implement the FUSEVolumeInterface interface, FUSEInode, FUSEFileHandle, FUSEDIRHandle and FUSEHandleManager.
 * A FUSEInodeAdapter will wrap the FUSEVolumeInterface plugin and translate between the low level
 * interface and the inode interface
 *
 */

public class FUSEInodeAdapter<I extends FUSEInode, M extends FUSEInodeManager<I>, F extends FUSEFileHandle, D extends FUSEDirHandle, H extends FUSEHandleManager<F, D>> extends FUSELowLevelBase<F, D, H>
{
	FUSEVolume<I, F, D, M, H> volume;
	
	public FUSEInodeAdapter(FUSEVolume<I, F, D, M, H> plugin)
	{
		this.volume = plugin;
		plugin.setInodeAdapter(this);
	}
	
	public void access(FUSEInMessage message)
	{
		replyWithError(message, -OSErrorCodes.ENOSYS);
	}

	public void bmap(FUSEInMessage message)
	{
		replyWithError(message, -OSErrorCodes.ENOSYS);
	}

	public void create(FUSEInMessage message, FUSECreateInMessage createMessage)
	{
		FUSEMessageInfo<I> msgInfo = initReqInfo(message);
		try
		{
			I inode = msgInfo.getInode();
			CreateInfo<F, I> createInfo = (CreateInfo<F, I>) inode.create(msgInfo.getReqInfo(), createMessage.getName(), createMessage.getFlags(), createMessage.getMode());
			FUSECreateOutMessage opReply = new FUSECreateOutMessage();
			FUSEOpenOutMessage openReply = new FUSEOpenOutMessage();
			openReply.setFileHandle(createInfo.getFileHandle().getHandleNum());
			
			FUSEInode newInode = createInfo.getInode();
			FUSEEntryOutMessage entryReply = new FUSEEntryOutMessage(newInode.getAttr());
			entryReply.setNodeID(newInode.getInodeNum());
			entryReply.setAttrValid(1);
			entryReply.setEntryValid(1);
			
			opReply.setOpenReply(openReply);
			opReply.setEntryReply(entryReply);
			
			replyOK(message, opReply);
		}
		catch (InodeException e)
		{
			replyWithError(message, e);
		}
	}

	public void destroy(FUSEInMessage message)
	{
		replyWithError(message, -OSErrorCodes.ENOSYS);
	}

	public void flush(FUSEInMessage message, FUSEFlushInMessage flushMessage)
	{
		FUSEMessageInfo<I> reqInfo = initReqInfo(message);
		try
		{
			F flushFileHandle = retrieveFileHandle(flushMessage.getFileHandleNum());
			flushFileHandle.flush(reqInfo.getReqInfo(), flushMessage.getLockOwnerFlags());
			replyOK(message);
		}
		catch (InodeException e)
		{
			replyWithError(message, e);
		}
	}

	public void forget(FUSEInMessage message)
	{
		//replyOK(message);
	}

	public void fsync(FUSEInMessage message)
	{
		replyWithError(message, -OSErrorCodes.ENOSYS);
	}

	public void fsyncdir(FUSEInMessage message)
	{
		replyWithError(message, -OSErrorCodes.ENOSYS);
	}

	public void getattr(FUSEInMessage message)
	{
		FUSEMessageInfo<I> messageInfo = initReqInfo(message);
		try
		{
			I inode = messageInfo.getInode();
			FUSEAttr returnAttr = inode.getAttr(messageInfo.getReqInfo());
			FUSEAttrOutMessage opReply = new FUSEAttrOutMessage(returnAttr);
			replyOK(message, opReply);
		}
		catch (InodeException e)
		{
			replyWithError(message, e);
		}
	}

	public void getlk(FUSEInMessage message)
	{
		replyWithError(message, -OSErrorCodes.ENOSYS);
	}

	public void getxattr(FUSEInMessage message, FUSEGetXattrInMessage xattrMessage)
	{
		FUSEMessageInfo<I> messageInfo = initReqInfo(message);
		try
		{
			I inode = messageInfo.getInode();
			int position = 0;
			byte [] returnBuffer = new byte[xattrMessage.getSize()];
			if (xattrMessage instanceof OSXFUSEGetXAttrInMessage)
				position = ((OSXFUSEGetXAttrInMessage)xattrMessage).getPosition();
			int bytesRead = inode.getXAttr(messageInfo.getReqInfo(), xattrMessage.getXAttrName(), position, returnBuffer);
			FUSEOpOutMessage opReply;
			/*
			if (xattrMessage.getSize() >= bytesRead)
			{
				opReply = new FUSEDataOutMessage(returnBuffer, 0, bytesRead);
			}
			else*/
			{
				opReply = new FUSEGetXattrOutMessage(bytesRead);
			}
			replyOK(message, opReply);
		}
		catch (InodeException e)
		{
			replyWithError(message, e);
		}
	}

	public void interrupt(FUSEInMessage message)
	{
		replyWithError(message, -OSErrorCodes.ENOSYS);
	}

	public void ioctl(FUSEInMessage message)
	{
		replyWithError(message, -OSErrorCodes.ENOSYS);
	}

	public void link(FUSEInMessage message)
	{
		replyWithError(message, -OSErrorCodes.ENOSYS);
	}

	final static Charset utf8 = Charset.forName("UTF-8");
	public void listxattr(FUSEInMessage message, FUSEGetXattrInMessage xattrMessage)
	{
		FUSEMessageInfo<I> messageInfo = initReqInfo(message);
		try
		{
			I inode = messageInfo.getInode();
			String [] attrNames = inode.listXAttr(messageInfo.getReqInfo());
			int totalSize = 0;
			ArrayList<byte []>attrNameBytesList = new ArrayList<byte []>();
			if (attrNames != null)
			{
				for (String curAttrName:attrNames)
				{
					byte [] attrNameBytes = curAttrName.getBytes(utf8);
					attrNameBytesList.add(attrNameBytes);
					totalSize += attrNameBytes.length + 1;	// Space for the terminating null
				}
			}
			FUSEOpOutMessage opReply;
			if (xattrMessage.getSize() > 0 && totalSize <= xattrMessage.getSize())
			{
				byte [] returnBuffer = new byte[xattrMessage.getSize()];
				int bufOffset = 0;
				for (byte [] curNameBytes:attrNameBytesList)
				{
					System.arraycopy(curNameBytes, 0, returnBuffer, bufOffset, curNameBytes.length);
					bufOffset += curNameBytes.length;
					returnBuffer[bufOffset] = 0;
					bufOffset++;
				}
				opReply = new FUSEDataOutMessage(returnBuffer, 0, totalSize);
			}
			else
			{
				if (xattrMessage.getSize() == 0)
				{
					opReply = new FUSEGetXattrOutMessage(totalSize);
				}
				else
				{
					// The buffer was too small
					throw new OutOfRangeException();
				}
			}
			replyOK(message, opReply);
		}
		catch (InodeException e)
		{
			replyWithError(message, e);
		}
	}

	public void lookup(FUSEInMessage message, FUSELookupInMessage lookupMessage)
	{
		FUSEMessageInfo<I> messageInfo = initReqInfo(message);
		try
		{
			I inode = messageInfo.getInode();
			I returnInode = (I) inode.lookup(messageInfo.getReqInfo(), lookupMessage.getName());
			if (returnInode != null)
			{
				FUSEEntryOutMessage opReply = new FUSEEntryOutMessage(returnInode.getAttr());
				opReply.setNodeID(returnInode.getInodeNum());
				opReply.setAttrValid(1);
				opReply.setEntryValid(1);
				replyOK(message, opReply);
			}
			else
			{
				replyWithError(message, -OSErrorCodes.ENOENT);
			}
		}
		catch (NoEntryException e)
		{
			replyWithError(message, -OSErrorCodes.ENOENT);
		}
		catch (InodeException e)
		{
			replyWithError(message, e);
		}
	}

	public void mkdir(FUSEInMessage message, FUSEMkdirInMessage mkdirMessage)
	{
		FUSEMessageInfo<I> messageInfo = initReqInfo(message);
		try
		{
			I inode = messageInfo.getInode();
			I returnInode = (I) inode.mkdir(messageInfo.getReqInfo(), mkdirMessage.getName(), mkdirMessage.getMode(), mkdirMessage.getUMask());
			FUSEEntryOutMessage opReply = new FUSEEntryOutMessage(returnInode.getAttr());
			opReply.setNodeID(returnInode.getInodeNum());
			opReply.setAttrValid(1);
			opReply.setEntryValid(1);
			replyOK(message, opReply);
		}
		catch (InodeException e)
		{
			replyWithError(message, e);
		}
	}

	public void mknod(FUSEInMessage message)
	{
		replyWithError(message, -OSErrorCodes.ENOSYS);
	}

	public void open(FUSEInMessage message, FUSEOpenInMessage openMessage)
	{
		FUSEMessageInfo<I> messageInfo = initReqInfo(message);
		try
		{
			I inode = messageInfo.getInode();
			FUSEFileHandle fileHandle = inode.open(messageInfo.getReqInfo(), openMessage.getFlags());
			FUSEOpenOutMessage opReply = new FUSEOpenOutMessage();
			opReply.setFileHandle(fileHandle.getHandleNum());
			replyOK(message, opReply);
		}
		catch (InodeException e)
		{
			replyWithError(message, e);
		}
	}

	public void opendir(FUSEInMessage message, FUSEOpenInMessage openMessage)
	{
		FUSEMessageInfo<I> messageInfo = initReqInfo(message);
		try
		{
			I inode = messageInfo.getInode();
			FUSEDirHandle fileHandle = inode.opendir(messageInfo.getReqInfo(), openMessage.getFlags());
			FUSEOpenOutMessage opReply = new FUSEOpenOutMessage();
			opReply.setFileHandle(fileHandle.getHandleNum());
			replyOK(message, opReply);
		}
		catch (InodeException e)
		{
			replyWithError(message, e);
		}
	}

	public void poll(FUSEInMessage message)
	{
		replyWithError(message, -OSErrorCodes.ENOSYS);
	}

	public void read(FUSEInMessage message, FUSEReadInMessage readMessage)
	{
		FUSEMessageInfo<I> messageInfo = initReqInfo(message);
		byte [] returnBuffer = new byte[readMessage.getSize()];
		try
		{
			F readFileHandle = retrieveFileHandle(readMessage.getFileHandleNum());
			int bytesRead = readFileHandle.read(messageInfo.getReqInfo(), readMessage.getOffset(), returnBuffer, readMessage.getFlags(),
					readMessage.getReadFlags());
			FUSEDataOutMessage opReply = new FUSEDataOutMessage(returnBuffer, 0, bytesRead);
			replyOK(message, opReply);
		}
		catch (InodeException e)
		{
			replyWithError(message, e);
		}
	}

	public void readdir(FUSEInMessage message, FUSEReadInMessage readMessage)
	{
		FUSEMessageInfo<I> messageInfo = initReqInfo(message);
		try
		{
			D readDirHandle = retrieveDirHandle(readMessage.getFileHandleNum());
			DirectoryEntryBuffer returnBuffer = readDirHandle.readdir(messageInfo.getReqInfo(), readMessage.getOffset(), readMessage.getSize(),
					readMessage.getFlags(), readMessage.getReadFlags());

			FUSEReaddirOutMessage opReply = returnBuffer.getOutMessage();
			replyOK(message, opReply);
		}
		catch(InodeException e)
		{
			replyWithError(message, e);
		}
	}

	public void readlink(FUSEInMessage message)
	{
		replyWithError(message, -OSErrorCodes.ENOSYS);
	}

	public void removexattr(FUSEInMessage message)
	{
		replyWithError(message, -OSErrorCodes.ENOSYS);
	}

	public void rename(FUSEInMessage message)
	{
		replyWithError(message, -OSErrorCodes.ENOSYS);
	}

	public void rmdir(FUSEInMessage message)
	{
		replyWithError(message, -OSErrorCodes.ENOSYS);
	}

	public void setattr(FUSEInMessage message, FUSESetAttrInMessage setAttrMessage)
	{
		FUSEMessageInfo<I> messageInfo = initReqInfo(message);
		try
		{
			I inode = messageInfo.getInode();
			FUSEAttr returnAttr = inode.setAttr(messageInfo.getReqInfo(), new SetAttrs(setAttrMessage));
			FUSEAttrOutMessage opReply = new FUSEAttrOutMessage(returnAttr);
			replyOK(message, opReply);
		} catch (InodeException e)
		{
			replyWithError(message, e);
		}
	}

	public void setlk(FUSEInMessage message)
	{
		replyWithError(message, -OSErrorCodes.ENOSYS);
	}

	public void setlkw(FUSEInMessage message)
	{
		replyWithError(message, -OSErrorCodes.ENOSYS);
	}

	public void setxattr(FUSEInMessage message)
	{
		replyWithError(message, -OSErrorCodes.ENOSYS);
	}

	public void statfs(FUSEInMessage message)
	{
        FUSEMessageInfo<I> messageInfo = initReqInfo(message);
        try
        {
            FUSEStatFS returnStatFS = volume.getStatFS(messageInfo.getReqInfo());
            FUSEStatFSOutMessage opReply = new FUSEStatFSOutMessage(returnStatFS);
            replyOK(message, opReply);
        }
        catch (InodeException e)
        {
            replyWithError(message, e);
        }
	}

	public void symlink(FUSEInMessage message)
	{
		replyWithError(message, -OSErrorCodes.ENOSYS);
	}

	public void unlink(FUSEInMessage message)
	{
		replyWithError(message, -OSErrorCodes.ENOSYS);
	}

	public void write(FUSEInMessage message, LinuxFUSEWriteInMessage writeMessage)
	{
		F writeFileHandle = retrieveFileHandle(writeMessage.getFileHandleNum());
		long offset = writeMessage.getOffset();
		ByteBuffer writeBytes = writeMessage.getWriteBytes();
		int writeFlags = writeMessage.getWriteFlags();
		writeCommon(message, writeFileHandle, offset, writeBytes, writeFlags);
	}

	public void write(FUSEInMessage message, OSXFUSEWriteInMessage writeMessage)
	{
		F writeFileHandle = retrieveFileHandle(writeMessage.getFileHandleNum());
		long offset = writeMessage.getOffset();
		ByteBuffer writeBytes = writeMessage.getWriteBytes().slice();
		int writeFlags = writeMessage.getWriteFlags();
		writeCommon(message, writeFileHandle, offset, writeBytes, writeFlags);
	}
	
	private void writeCommon(FUSEInMessage message, F writeFileHandle,
			long offset, ByteBuffer writeBytes, int writeFlags) {
		FUSEMessageInfo<I> messageInfo = initReqInfo(message);
		try
		{
			int bytesWritten = writeFileHandle.write(messageInfo.getReqInfo(), offset, writeBytes.slice(), writeFlags);
			FUSEWriteOutMessage opReply = new FUSEWriteOutMessage();
			opReply.setSize(bytesWritten);
			replyOK(message, opReply);
		}
		catch (InodeException e)
		{
			replyWithError(message, e);
		}
	}

	public synchronized D allocateDirHandle()
	{
		return getHandleManager().getNewDirHandle();
	}
	
	public synchronized F allocateFileHandle()
	{
		return getHandleManager().getNewFileHandle();
	}
	
	public synchronized D retrieveDirHandle(long handleNum)
	{
		return getHandleManager().retrieveDirHandle(handleNum);
	}
	
	public synchronized F retrieveFileHandle(long handleNum)
	{
		return getHandleManager().retrieveFileHandle(handleNum);
	}
	
	public synchronized void releaseFileHandle(F releaseHandle)
	{
		getHandleManager().releaseFileHandle(releaseHandle);
	}
	
	public synchronized void releaseDirHandle(D releaseHandle)
	{
		getHandleManager().releaseDirHandle(releaseHandle);
	}

	public M getInodeManager()
	{
		return volume.getInodeManager();
	}
	
	
	@Override
	public H getHandleManager() 
	{
		return volume.getHandleManager();
	}

	/**
	 * Initializes a request info object.  This will also retrieve the inode if possible
	 * @param message
	 * @return
	 */
	public FUSEMessageInfo<I> initReqInfo(FUSEInMessage message)
	{
		FUSEReqInfo reqInfo = new FUSEReqInfo(message);
		long inodeNum = reqInfo.getInodeNum();
		I inode = getInodeManager().retrieveInode(inodeNum);
		FUSEMessageInfo<I> returnInfo = new FUSEMessageInfo<I>(inode, inodeNum, reqInfo);
		return returnInfo;
	}
}
