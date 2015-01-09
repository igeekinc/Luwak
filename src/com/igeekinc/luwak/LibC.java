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

import com.igeekinc.luwak.linux.LinuxLibC;
import com.igeekinc.luwak.linux.LinuxStat;
import com.igeekinc.luwak.macosx.MacOSXLibC;
import com.igeekinc.luwak.macosx.MacOSXStatStructure32;
import com.igeekinc.luwak.macosx.MacOSXStatStructure64;
import com.igeekinc.luwak.macosx.StatFSStructure32;
import com.igeekinc.luwak.macosx.StatFSStructure64;
import com.igeekinc.util.BitTwiddle;
import com.igeekinc.util.unix.UnixDate;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;


public class LibC
{
	public enum SystemType
	{
	    kLinux,
	    kMacOSX
	}

    private static SystemType systemType;
    private static boolean is64BitJVM;
    
    static
    {
        String  osName = System.getProperty("os.name"); //$NON-NLS-1$

        if (osName.equals("Mac OS X") || osName.equals("Darwin")) //$NON-NLS-1$
        {
            systemType = SystemType.kMacOSX;
        }

        if (osName.equals("Linux")) //$NON-NLS-1$
        {
            systemType = SystemType.kLinux;
        }
        
        is64BitJVM = is64BitVM();
    }
    
    public static int mount(String source, String target, String fsType, long mountFlags, Pointer data)
    {
        switch(systemType)
        {
        case kMacOSX:
            return MacOSXLibC.INSTANCE.mount(fsType, target, mountFlags, data);
        case kLinux:
            return LinuxLibC.INSTANCE.mount(source, target, fsType, new NativeLong(mountFlags), data);
        default:
            throw new InternalError("Unrecognized OS");    
        }
    }
    
    public static int lstat(String path, Stat statStructure)
    {
        switch(systemType)
        {
        case kMacOSX:
            return MacOSXLibC.INSTANCE.lstat$INODE64(path, statStructure);
        case kLinux:
            return LinuxLibC.INSTANCE.__lxstat(3, path, (LinuxStat)statStructure);
        default:
            throw new InternalError("Unrecognized OS");    
        }
    }
    
    public static int fstat(int fileDescriptor, Stat statStructure)
    {
        switch(systemType)
        {
        case kMacOSX:
            return MacOSXLibC.INSTANCE.fstat$INODE64(fileDescriptor, (MacOSXStatStructure64) statStructure);
        case kLinux:
            //return LinuxLibC.INSTANCE.__lxstat(3, path, (LinuxStat)statStructure);
        default:
            throw new InternalError("Unrecognized OS");    
        }
    }
    
    public static int ioctl(int fileDescriptor, int command, Pointer args)
    {
        switch (systemType)
        {
        case kMacOSX:
            return MacOSXLibC.INSTANCE.ioctl(fileDescriptor, command, args);
        case kLinux:
            return LinuxLibC.INSTANCE.ioctl(fileDescriptor, command, args);
        default:
            throw new InternalError("Unrecognized OS");    
        }
    }
    
    public static int statfs(String path, StatFS statFSStructure)
    {
        switch(systemType)
        {
        case kMacOSX:
            return MacOSXLibC.INSTANCE.statfs$INODE64(path, (StatFSStructure64) statFSStructure);
        case kLinux:
            //return LinuxLibC.INSTANCE.__lxstat(3, path, (LinuxStat)statStructure);
        default:
            throw new InternalError("Unrecognized OS");    
        }
    }
    
    public static int chmod(String path, int mode)
    {
        switch(systemType)
        {
        case kMacOSX:
            return MacOSXLibC.INSTANCE.chmod(path, mode);
        case kLinux:
            return LinuxLibC.INSTANCE.chmod(path, mode);
        default:
            throw new InternalError("Unrecognized OS");    
        }
    }
    
    public static int chown(String path, int uid, int gid)
    {
        switch(systemType)
        {
        case kMacOSX:
            return MacOSXLibC.INSTANCE.chown(path, uid, gid);
        case kLinux:
            return LinuxLibC.INSTANCE.chown(path, uid, gid);
        default:
            throw new InternalError("Unrecognized OS");    
        }
    }
    
    public static int utimes(String path, UnixDate accessTime, UnixDate modifyTime)
    {
        switch(systemType)
        {
        case kMacOSX:
        	MacOSXLibC.timeval.ByReference macTimesRef = new MacOSXLibC.timeval.ByReference();
        	MacOSXLibC.timeval [] macTimes = (MacOSXLibC.timeval [])macTimesRef.toArray(2);
        	macTimes[0].tv_sec = new NativeLong(accessTime.getSecsLong());
        	macTimes[0].tv_usec = new NativeLong(accessTime.getUSecs());
        	macTimes[1].tv_sec = new NativeLong(modifyTime.getSecsLong());
        	macTimes[1].tv_usec = new NativeLong(modifyTime.getUSecs());
            return MacOSXLibC.INSTANCE.utimes(path, macTimesRef);
        case kLinux:
        	LinuxLibC.timeval.ByReference linuxTimesRef = new LinuxLibC.timeval.ByReference();
        	LinuxLibC.timeval [] linuxTimes = (LinuxLibC.timeval [])linuxTimesRef.toArray(2);
        	linuxTimes[0].tv_sec = new NativeLong(accessTime.getSecsLong());
        	linuxTimes[0].tv_usec = new NativeLong(accessTime.getUSecs());
        	linuxTimes[1].tv_sec = new NativeLong(modifyTime.getSecsLong());
        	linuxTimes[1].tv_usec = new NativeLong(modifyTime.getUSecs());
            return LinuxLibC.INSTANCE.utimes(path, linuxTimesRef);
        default:
            throw new InternalError("Unrecognized OS");    
        }
    }
    
    public static Stat newStat()
    {
        switch(systemType)
        {
        case kMacOSX:
            if (is64BitJVM)
                return new MacOSXStatStructure64();
            else
                return new MacOSXStatStructure32();
        case kLinux:
            return new LinuxStat();
        default:
            throw new InternalError("Unrecognized OS");    
        }
    }
    
    public static StatFS newStatFS()
    {
        switch(systemType)
        {
        case kMacOSX:
            if (is64BitJVM)
                return new StatFSStructure64();
            else
                return new StatFSStructure32();
        case kLinux:
            //return new LinuxStat();
        default:
            throw new InternalError("Unrecognized OS");    
        }
    }
    static public boolean is64BitVM()
    {
        boolean is64Bit = false;
        String bits = System.getProperty("sun.arch.data.model", "?");
        if (bits.equals("64"))
        {
            is64Bit = true;
        }
        if (bits.equals("?"))
        {
            // probably sun.arch.data.model isn't available
            // maybe not a Sun JVM?
            // try with the vm.name property
            is64Bit = System.getProperty("java.vm.name").toLowerCase().indexOf("64") >= 0;
        }
        return is64Bit;
    }
    
    public static boolean is32BitVM()
    {
        return !is64BitVM();
    }
    
    public static SystemType getSystemType()
    {
        return systemType;
    }
}
