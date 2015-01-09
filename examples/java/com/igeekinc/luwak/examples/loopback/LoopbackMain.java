/*
 * Copyright 2002-2014 iGeek, Inc.
 * All Rights Reserved
 * @@OpenSource@@
 */
 
package com.igeekinc.luwak.examples.loopback;

import java.io.File;
import java.io.IOException;

import com.igeekinc.luwak.FUSEMountOptions;
import com.igeekinc.luwak.LibC;
import com.igeekinc.luwak.LibC.SystemType;
import com.igeekinc.luwak.LuwakMain;
import com.igeekinc.luwak.inode.FUSEInodeAdapter;
import com.igeekinc.luwak.inode.exceptions.InodeException;
import com.igeekinc.luwak.linux.LinuxMountOptions;
import com.igeekinc.luwak.lowlevel.FUSELowLevel;
import com.igeekinc.luwak.macosx.OSXMountOptions;
import com.igeekinc.util.SystemTest;

public class LoopbackMain extends LuwakMain
{

	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException, InodeException
	{
		LoopbackVolume loopback = new LoopbackVolume(new File(args[0]));
		FUSEInodeAdapter<LoopbackInode, LoopbackInodeManager, LoopbackFileHandle, LoopbackDirHandle, LoopbackHandleManager> adapter = new FUSEInodeAdapter<LoopbackInode, LoopbackInodeManager, LoopbackFileHandle, LoopbackDirHandle, LoopbackHandleManager>(loopback);
		LoopbackMain main = new LoopbackMain(adapter);
		main.setupChannel();
		if (LibC.getSystemType() == SystemType.kMacOSX)
			main.startLoop();		// OSX FUSE likes to have somebody listening on the device before we do the mount

		
		FUSEMountOptions mountOptions;
		switch(LibC.getSystemType())
        {
        case kLinux:
            mountOptions = new LinuxMountOptions();
            break;
        case kMacOSX:
            mountOptions = new OSXMountOptions();
            break;
        default:
            throw new InternalError("Unrecognized OS");
        }
		mountOptions.setMountPath(args[1]);
		main.setupMount(mountOptions);
		if (LibC.getSystemType() != SystemType.kMacOSX)
			main.startLoop();	// Linux FUSE, on the other hand, throws errors when we try to read from the device prior to mounting
		
		while (true)
		{
			try
			{
				Thread.sleep(60000);
			}
			catch (InterruptedException e)
			{
				
			}
		}
	}

	public LoopbackMain(FUSELowLevel lowLevelFS)
	{
		super(lowLevelFS);
	}
}
