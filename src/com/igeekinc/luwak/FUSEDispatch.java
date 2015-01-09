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
 
package com.igeekinc.luwak;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

import com.igeekinc.luwak.lowlevel.FUSELowLevel;
import com.igeekinc.luwak.messages.FUSEFlushInMessage;
import com.igeekinc.luwak.messages.FUSEInMessage;
import com.igeekinc.luwak.messages.FUSEInitInMessage;
import com.igeekinc.luwak.messages.FUSELookupInMessage;
import com.igeekinc.luwak.messages.FUSEMkdirInMessage;
import com.igeekinc.luwak.messages.FUSEOpenInMessage;
import com.igeekinc.luwak.messages.FUSEOutMessage;
import com.igeekinc.luwak.messages.FUSEReadInMessage;
import com.igeekinc.luwak.messages.FUSEReleaseInMessage;
import com.igeekinc.luwak.messages.FUSESetAttrInMessage;

class ChannelPoller implements Runnable
{
	FUSEDispatch dispatch;
	public ChannelPoller(FUSEDispatch dispatch)
	{
		this.dispatch = dispatch;
	}
	
	public void run() 
	{
		dispatch.pollLoop();
	}
}

class WorkerRunner implements Runnable
{
	FUSEDispatch dispatch;
	public WorkerRunner(FUSEDispatch dispatch)
	{
		this.dispatch = dispatch;
	}
	
	public void run()
	{
		dispatch.workerLoop();
	}
}

/**
 * FUSEDispatch reads messages from the FUSE device and dispatches them on some number of dispatch threads.  The received
 * messages are passed to a FUSELowLevel which processes them and returns appropriate messages.
 * @author David L. Smith-Uchida
 *
 */
public abstract class FUSEDispatch 
{
	protected Thread reader;
	protected Thread [] workerThreads;
	protected FUSEChannel channel;
	protected FUSELowLevel receiver;
	protected LinkedBlockingQueue<FUSEInMessage> requestQueue = new LinkedBlockingQueue<FUSEInMessage>();
	
	public static int kMaxThreadsToRun = 2;
	
	public FUSEDispatch(FUSEChannel channel, FUSELowLevel receiver)
	{
		this.channel = channel;
		this.receiver = receiver;
		receiver.setDispatch(this);
	}
	
	public void start()
	{
		reader = new Thread(new ChannelPoller(this), "FUSE Channel Poller");
		reader.setDaemon(true);
		reader.start();
		
		int numWorkerThreads = receiver.maxThreadsToRun();
		if (numWorkerThreads == 0 || numWorkerThreads > kMaxThreadsToRun)
			numWorkerThreads = kMaxThreadsToRun;
		
		workerThreads = new Thread[numWorkerThreads];
		for (int curWorkerThreadNum = 0; curWorkerThreadNum < numWorkerThreads; curWorkerThreadNum ++)
		{
			workerThreads[curWorkerThreadNum] = new Thread(new WorkerRunner(this), "FUSE Worker "+curWorkerThreadNum);
			workerThreads[curWorkerThreadNum].setDaemon(true);
			workerThreads[curWorkerThreadNum].start();
		}
	}
	public void pollLoop()
	{
		int numErrors = 0;
		Throwable exitThrowable = null;
		while(numErrors < 5)
		{
			try 
			{
				FUSEInMessage inMessage = channel.receiveMessage();
				requestQueue.add(inMessage);
				numErrors = 0;
			} catch (IOException e) {
				numErrors++;
				exitThrowable = e;
			}
		}
		exitThrowable.printStackTrace();
	}

	public void workerLoop()
	{
		while(true)
		{
			try
			{
				FUSEInMessage inMessage = requestQueue.take();
				dispatchMessage(inMessage);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	private void dispatchMessage(FUSEInMessage inMessage)
	{
		try
		{
			switch (inMessage.getOpcode())
			{

			case FUSEInMessage.FUSE_LOOKUP:
				lookup(inMessage);
				break;
			case FUSEInMessage.FUSE_FORGET:
				forget(inMessage);
				break;
			case FUSEInMessage.FUSE_GETATTR:
				receiver.getattr(inMessage);
				break;
			case FUSEInMessage.FUSE_SETATTR:
				setattr(inMessage);
				break;
			case FUSEInMessage.FUSE_READLINK:
				receiver.readlink(inMessage);
				break;
			case FUSEInMessage.FUSE_SYMLINK:
				symlink(inMessage);
				break;
			case FUSEInMessage.FUSE_MKNOD:
				mknod(inMessage);
				break;
			case FUSEInMessage.FUSE_MKDIR:
				mkdir(inMessage);
				break;
			case FUSEInMessage.FUSE_UNLINK:
				unlink(inMessage);
				break;
			case FUSEInMessage.FUSE_RMDIR:
				rmdir(inMessage);
				break;
			case FUSEInMessage.FUSE_RENAME:
				rename(inMessage);
				break;
			case FUSEInMessage.FUSE_LINK:
				link(inMessage);
				break;
			case FUSEInMessage.FUSE_OPEN:
				open(inMessage);
				break;
			case FUSEInMessage.FUSE_READ:
				read(inMessage);
				break;
			case FUSEInMessage.FUSE_WRITE:
				write(inMessage);
				break;
			case FUSEInMessage.FUSE_STATFS:
				statfs(inMessage);
				break;
			case FUSEInMessage.FUSE_RELEASE:
				release(inMessage);
				break;
			case FUSEInMessage.FUSE_FSYNC:
				fsync(inMessage);
				break;
			case FUSEInMessage.FUSE_SETXATTR:
				setxattr(inMessage);
				break;
			case FUSEInMessage.FUSE_GETXATTR:
				getxattr(inMessage);
				break;
			case FUSEInMessage.FUSE_LISTXATTR:
				listxattr(inMessage);
				break;
			case FUSEInMessage.FUSE_REMOVEXATTR:
				removexattr(inMessage);
				break;
			case FUSEInMessage.FUSE_FLUSH:
				flush(inMessage);
				break;
			case FUSEInMessage.FUSE_INIT:
				init(inMessage);
				break;
			case FUSEInMessage.FUSE_OPENDIR:
				opendir(inMessage);
				break;
			case FUSEInMessage.FUSE_READDIR:
				readdir(inMessage);
				break;
			case FUSEInMessage.FUSE_RELEASEDIR:
				releasedir(inMessage);
				break;
			case FUSEInMessage.FUSE_FSYNCDIR:
				fsyncdir(inMessage);
				break;
			case FUSEInMessage.FUSE_GETLK:
				getlk(inMessage);
				break;
			case FUSEInMessage.FUSE_SETLK:
				setlk(inMessage);
				break;
			case FUSEInMessage.FUSE_SETLKW:
				setlkw(inMessage);
				break;
			case FUSEInMessage.FUSE_ACCESS:
				access(inMessage);
				break;
			case FUSEInMessage.FUSE_CREATE:
				create(inMessage);
				break;
			case FUSEInMessage.FUSE_INTERRUPT:
				interrupt(inMessage);
				break;
			case FUSEInMessage.FUSE_BMAP:
				bmap(inMessage);
				break;
			case FUSEInMessage.FUSE_DESTROY:
				destroy(inMessage);
				break;
			case FUSEInMessage.FUSE_IOCTL:
				ioctl(inMessage);
				break;
			case FUSEInMessage.FUSE_POLL:
				poll(inMessage);
				break;
			case FUSEInMessage.FUSE_NOTIFY_REPLY:
				notifyReply(inMessage);
				break;
			case FUSEInMessage.FUSE_BATCH_FORGET:
				batchForget(inMessage);
				break;
			default:
				System.out.println("Received unknown opcode "
						+ inMessage.getOpcode());
			}
		}
		catch (Throwable t)
		{
			t.printStackTrace();
			System.err.println("Continuing");
			FUSEOutMessage errReply = new FUSEOutMessage(inMessage);
			errReply.setError(-OSErrorCodes.EIO);
			sendReply(errReply);
		}
	}

	public void batchForget(FUSEInMessage inMessage) {
		receiver.batchForget(inMessage);
	}

	public void notifyReply(FUSEInMessage inMessage) {
		receiver.notifyReply(inMessage);
	}

	public void poll(FUSEInMessage inMessage) {
		receiver.poll(inMessage);
	}

	public void ioctl(FUSEInMessage inMessage) {
		receiver.ioctl(inMessage);
	}

	public void destroy(FUSEInMessage inMessage) {
		receiver.destroy(inMessage);
	}

	public void bmap(FUSEInMessage inMessage) {
		receiver.bmap(inMessage);
	}

	public void interrupt(FUSEInMessage inMessage) {
		receiver.interrupt(inMessage);
	}

	public abstract void create(FUSEInMessage inMessage);

	public void access(FUSEInMessage inMessage) {
		receiver.access(inMessage);
	}

	public void setlkw(FUSEInMessage inMessage) {
		receiver.setlkw(inMessage);
	}

	public void setlk(FUSEInMessage inMessage) {
		receiver.setlk(inMessage);
	}

	public void getlk(FUSEInMessage inMessage) {
		receiver.getlk(inMessage);
	}

	public void fsyncdir(FUSEInMessage inMessage) {
		receiver.fsyncdir(inMessage);
	}

	public void releasedir(FUSEInMessage inMessage) {
		FUSEReleaseInMessage releaseDirMessage = new FUSEReleaseInMessage(inMessage);
		receiver.releasedir(inMessage, releaseDirMessage);
	}

	public void readdir(FUSEInMessage inMessage) {
		FUSEReadInMessage readDirMessage = new FUSEReadInMessage(inMessage);
		receiver.readdir(inMessage, readDirMessage);
	}

	public void opendir(FUSEInMessage inMessage) {
		FUSEOpenInMessage openDirMessage = new FUSEOpenInMessage(inMessage);
		receiver.opendir(inMessage, openDirMessage);
	}

	public void init(FUSEInMessage inMessage) {
		FUSEInitInMessage initMessage = new FUSEInitInMessage(inMessage);
		receiver.init(inMessage, initMessage);
	}

	public void flush(FUSEInMessage inMessage) {
		FUSEFlushInMessage flushMessage = new FUSEFlushInMessage(inMessage);
		receiver.flush(inMessage, flushMessage);
	}

	public void removexattr(FUSEInMessage inMessage) {
		receiver.removexattr(inMessage);
	}

	public abstract void listxattr(FUSEInMessage inMessage);

	public abstract void getxattr(FUSEInMessage inMessage);

	public void setxattr(FUSEInMessage inMessage) {
		receiver.setxattr(inMessage);
	}

	public void fsync(FUSEInMessage inMessage) {
		receiver.fsync(inMessage);
	}

	public void release(FUSEInMessage inMessage) {
		FUSEReleaseInMessage releaseMessage = new FUSEReleaseInMessage(inMessage);
		receiver.release(inMessage, releaseMessage);
	}

	public void statfs(FUSEInMessage inMessage) {
		receiver.statfs(inMessage);
	}

	public abstract void write(FUSEInMessage inMessage);

	public void read(FUSEInMessage inMessage) {
		FUSEReadInMessage readMessage = new FUSEReadInMessage(inMessage);
		receiver.read(inMessage, readMessage);
	}

	public void open(FUSEInMessage inMessage) {
		FUSEOpenInMessage openMessage = new FUSEOpenInMessage(inMessage);
		receiver.open(inMessage, openMessage);
	}

	public void link(FUSEInMessage inMessage) {
		receiver.link(inMessage);
	}

	public void rename(FUSEInMessage inMessage) {
		receiver.rename(inMessage);
	}

	public void rmdir(FUSEInMessage inMessage) {
		receiver.rmdir(inMessage);
	}

	public void unlink(FUSEInMessage inMessage) {
		receiver.unlink(inMessage);
	}

	public void mkdir(FUSEInMessage inMessage) {
		FUSEMkdirInMessage mkdirMessage = new FUSEMkdirInMessage(inMessage);
		receiver.mkdir(inMessage, mkdirMessage);
	}

	public void mknod(FUSEInMessage inMessage) {
		receiver.mknod(inMessage);
	}

	public void symlink(FUSEInMessage inMessage) {
		receiver.symlink(inMessage);
	}

	public void setattr(FUSEInMessage inMessage) {
		FUSESetAttrInMessage setAttrMessage = new FUSESetAttrInMessage(inMessage);
		receiver.setattr(inMessage, setAttrMessage);
	}

	public void forget(FUSEInMessage inMessage) {
		receiver.forget(inMessage);
	}

	public void lookup(FUSEInMessage inMessage) {
		FUSELookupInMessage lookupMessage = new FUSELookupInMessage(inMessage);
		receiver.lookup(inMessage, lookupMessage);
	}
	
	public void sendReply(FUSEOutMessage reply)
	{
		try {
			channel.sendMessage(reply);
		} catch (IOException e) 
		{
			e.printStackTrace();
			try
			{
				FUSEOutMessage errReply = new FUSEOutMessage(reply.getUnique(), -OSErrorCodes.EIO);
				channel.sendMessage(errReply);
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
	}

	public void waitForLoopFinish() throws InterruptedException
	{
		if (reader != null)
			reader.join();
	}
}
