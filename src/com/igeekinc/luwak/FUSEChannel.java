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

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import com.igeekinc.luwak.messages.FUSEInMessage;
import com.igeekinc.luwak.messages.FUSEOutMessage;

public class FUSEChannel 
{
	public static final int kFUSEChannelBufSize = 132*1024;
	
	/*
	 * We use a FileChannel and a RandomAccessFile because a FileChannel cannot be read from and written to concurrently, which
	 * means a single FileChannel cannot be used in a multi-threaded setup (and we always run with at least two threads, one poll thread and
	 * one worker thread).
	 * However, the FileChannel interface allows for I/O from multiple ByteBuffers which frees us from having to gather the
	 * pieces of the message into a single buffer or making multiple writes.  
	 * 
	 * So, channelRAF is used to read the messages from the kernel and fileChannel is used to send message back
	 */
	protected RandomAccessFile channelRAF;
	protected FileChannel fileChannel;
	
	String devName;
	String devPath;
	int fd;
	public FUSEChannel(File openDevice) throws FileNotFoundException
	{
		openFile(openDevice);
	}

	protected FUSEChannel()
	{
	    
	}
    protected void openFile(File openDevice) throws FileNotFoundException,
            InternalError
    {
        channelRAF = new RandomAccessFile(openDevice, "rw");
		fileChannel = channelRAF.getChannel();
		try {
			Field fdField = FileDescriptor.class.getDeclaredField("fd");
			fdField.setAccessible(true);
			FileDescriptor fdObj = channelRAF.getFD();
			fd = fdField.getInt(fdObj);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new InternalError("Got SecurityException getting file descriptor");
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new InternalError("Got NoSuchFieldException getting file descriptor");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new InternalError("Got IOException getting file descriptor");
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new InternalError("Got IllegalArgumentException getting file descriptor");
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new InternalError("Got IllegalAccessException getting file descriptor");
		}
		devName = openDevice.getName();
		devPath = openDevice.getAbsolutePath();
    }
	
	public int getFDNum()
	{
		return fd;
	}
	
	public FUSEInMessage receiveMessage() throws IOException
	{
		ByteBuffer readBuffer = ByteBuffer.allocate(kFUSEChannelBufSize);
		int bytesRead;
		
		// Only one thread should be doing a read at a time.  We synchronize on "this" so that we can write concurrently
		// with reading
		synchronized(this)
		{
			bytesRead = channelRAF.read(readBuffer.array());
		}
		FUSEInMessage returnMessage = new FUSEInMessage(readBuffer, 0, bytesRead);
		
		return returnMessage;
	}
	
	public void sendMessage(FUSEOutMessage sendMessage) throws IOException
	{
		// Synchronize on fileChannel so that we can write messages while reading them
		synchronized(fileChannel)
		{
			ByteBuffer [] sendBuffers = sendMessage.getByteBuffers();
			// FUSE expects to get all of the data for a message back in one write.  When doing data transfers,
			// it's better to tack the header on as a separate buffer rather than copy and re-align the data into
			// a single buffer so messages take the form of a series of ByteBuffers.  However, the maximum number of
			// buffers in an I/O is capped at 16 somewhere, which causes FUSE to give back an EINVAL because the length
			// of the write doesn't match the header size.
			// Some calls, such as readdir, can get a little carried away with the number of buffers.  Rather than
			// make all of the higher levels aware, just consolidate here whenever we get more than 16.  In general, 
			// this will be for small amounts of data so it won't make any real difference
			if (sendBuffers.length > 16)
			{
				int bufferSize = 0;
				for (ByteBuffer curBuffer:sendBuffers)
				{
					bufferSize += curBuffer.limit();
				}
				byte [] writeBuffer = new byte[bufferSize];
				int offset = 0;
				for (ByteBuffer curBuffer:sendBuffers)
				{
					curBuffer.get(writeBuffer, offset, curBuffer.limit());
					offset += curBuffer.limit();
				}
				fileChannel.write(ByteBuffer.wrap(writeBuffer));
			}
			else
			{
				fileChannel.write(sendBuffers, 0, sendBuffers.length);
			}
		}
	}

    public String getDevName()
    {
        return devName;
    }

    public String getDevPath()
    {
        return devPath;
    }
	
	
}
