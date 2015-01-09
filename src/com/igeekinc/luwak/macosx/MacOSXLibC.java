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
 
package com.igeekinc.luwak.macosx;

import com.igeekinc.luwak.Stat;
import com.igeekinc.luwak.msghdr;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public interface MacOSXLibC extends Library 
{
	MacOSXLibC INSTANCE = (MacOSXLibC)Native.loadLibrary("c", MacOSXLibC.class);
    public int recvmsg(int sockfd, msghdr msgHeader, int flags);
    public int mount(String fsType, String target, long mountFlags, Pointer data);
    public int lstat$INODE64(String path, Stat statBuf);
    public int ioctl(int fileDescriptor, int command, Pointer args);
    public int fstat(int fileDescriptor, MacOSXStatStructure32 statStructure);
    public int fstat$INODE64(int fileDescriptor, MacOSXStatStructure64 statStructure);
    public int statfs(String path, StatFSStructure32 statFSStructure);
    public int statfs$INODE64(String path, StatFSStructure64 statFSStructure);
    public int chmod(String path, int mode);
    public int chown(String path, int owner, int group);
    public static class timeval extends Structure
    {
    	public static class ByReference extends timeval implements Structure.ByReference{}
    	
    	public NativeLong tv_sec;
    	public NativeLong tv_usec;
    }
	public int utimes(String path, timeval.ByReference times);   
}
