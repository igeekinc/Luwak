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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

import com.igeekinc.luwak.linux.LinuxFUSEDispatch;
import com.igeekinc.luwak.linux.LinuxMountOptions;
import com.igeekinc.luwak.lowlevel.FUSELowLevel;
import com.igeekinc.luwak.macosx.OSXFUSEChannel;
import com.igeekinc.luwak.macosx.OSXFUSEDispatch;
import com.igeekinc.luwak.macosx.OSXFUSENativeMountArgs;
import com.igeekinc.luwak.macosx.OSXMountOptions;
import com.sun.jna.Memory;
import com.sun.jna.Native;

public class LuwakMain
{
	FUSELowLevel lowLevelFS;
	FUSEChannel channel;
	FUSEDispatch dispatch;
	public LuwakMain(FUSELowLevel lowLevelFS)
	{
		this.lowLevelFS = lowLevelFS;
	}
	
	public void setupChannel() throws FileNotFoundException
	{
	       switch(LibC.getSystemType())
	        {
	        case kLinux:
	            channel = new FUSEChannel(new File("/dev/fuse"));
	            break;
	        case kMacOSX:
	            channel = new OSXFUSEChannel("/dev/osxfuse");
	            break;
	        default:
	            throw new InternalError("Unrecognized OS");
	        }
	}
	
	public void setupMount(FUSEMountOptions mountOptions) throws IOException, InterruptedException
	{	
		switch(LibC.getSystemType())
		{
		case kLinux:
		    setupLinuxMount((LinuxMountOptions)mountOptions);
		    break;
		case kMacOSX:
		    setupOSXMount((OSXMountOptions)mountOptions);
		    break;
		default:
		    throw new InternalError("Unrecognized OS");
		}
	}
	
	void setupLinuxMount(LinuxMountOptions mountOptions) throws IOException, InterruptedException
	{
	    String opts = "fd="+channel.getFDNum()+",rootmode=40000,user_id=0,group_id=0";
	    byte [] optsBytes = opts.getBytes(Charset.forName("UTF-8"));
	    Memory optsBuf = new Memory(optsBytes.length + 1);
	    optsBuf.write(0, optsBytes, 0, optsBytes.length);
	    optsBuf.setByte(optsBytes.length, (byte) 0);    // NULL-terminate!
	    if (LibC.mount("fuse", mountOptions.getMountPath(), "fuse", 6, optsBuf) != 0)
	    {
	        throw new IOException("mount failed with error code = "+Native.getLastError());
	    }
	}
	
	void setupOSXMount(OSXMountOptions mountOptions) throws IOException, InterruptedException
	{
		mountOptions.setRDev(channel);
		mountOptions.setRandom(channel);
		if (mountOptions.getFSName() == null)
			mountOptions.setFSName("/dev/"+channel.getDevName());
		mountOptions.setVolumeName("Loopback ");
		OSXFUSENativeMountArgs mountArgs = mountOptions.getNativeMountArgs(true);
		
	    /*
	    OSXFUSENativeMountArgs mountArgs = new OSXFUSENativeMountArgs();
	    Memory drandomMem = new Memory(4);
	    int drandomGetStatus = LibC.ioctl(channel.getFDNum(), OSXFUSENativeMountArgs.FUSEDEVIOCGETRANDOM, drandomMem);
	    if (drandomGetStatus != 0)
	        throw new IOException("Could not get Random from FUSE device - errno = "+Native.getLastError());

	    mountArgs.random = drandomMem.getInt(0);
	    mountArgs.setFSName("luwak@"+channel.getDevName());
	    mountArgs.setMntPath(mountPoint);
	    mountArgs.blocksize = OSXFUSENativeMountArgs.FUSE_DEFAULT_BLOCKSIZE;
	    mountArgs.daemon_timeout = OSXFUSENativeMountArgs.FUSE_DEFAULT_DAEMON_TIMEOUT;
	    mountArgs.fsid = 0;
	    mountArgs.init_timeout = OSXFUSENativeMountArgs.FUSE_DEFAULT_INIT_TIMEOUT;
	    mountArgs.setVolName("Luwak Volume "+channel.getDevName());
	    mountArgs.rdev = (int)((OSXFUSEChannel)channel).getRDev();
	    mountArgs.iosize = OSXFUSENativeMountArgs.FUSE_DEFAULT_IOSIZE;
	    mountArgs.altflags = OSXFUSENativeMountArgs.FUSE_MOPT_ALLOW_OTHER;
	    mountArgs.write();
	    */
	    if (LibC.mount("unused", mountOptions.getMountPath(), "osxfusefs", OSXFUSENativeMountArgs.FUSE_MOPT_ALLOW_ROOT | OSXFUSENativeMountArgs.FUSE_MOPT_LOCALVOL, mountArgs.getPointer()) != 0)
	    {
	        throw new IOException("mount failed with error code = "+Native.getLastError());
	    }
	}
	public void startLoop()
	{
		switch(LibC.getSystemType())
		{
		case kLinux:
		    dispatch = new LinuxFUSEDispatch(channel, lowLevelFS);
		    break;
		case kMacOSX:
			dispatch = new OSXFUSEDispatch(channel, lowLevelFS);
		    break;
		default:
		    throw new InternalError("Unrecognized OS");
		}
		dispatch.start();
	}

	public void waitForLoopFinish() throws InterruptedException
	{
		dispatch.waitForLoopFinish();
	}
}
