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

import java.nio.charset.Charset;

import com.sun.jna.Structure;

public class OSXFUSENativeMountArgs extends Structure
{
    public static final int MAXPATHLEN = 1024;
    public static final int MFSTYPENAMELEN = 16;    // Includes terminating nL

    public static final long FUSE_MOPT_IGNORE                 = 0x0000000000000000L;
    public static final long FUSE_MOPT_ALLOW_OTHER            = 0x0000000000000001L;
    public static final long FUSE_MOPT_ALLOW_RECURSION        = 0x0000000000000002L;
    public static final long FUSE_MOPT_ALLOW_ROOT             = 0x0000000000000004L;
    public static final long FUSE_MOPT_AUTO_XATTR             = 0x0000000000000008L;
    public static final long FUSE_MOPT_BLOCKSIZE              = 0x0000000000000010L;
    public static final long FUSE_MOPT_DAEMON_TIMEOUT         = 0x0000000000000020L;
    public static final long FUSE_MOPT_DEBUG                  = 0x0000000000000040L;
    public static final long FUSE_MOPT_DEFAULT_PERMISSIONS    = 0x0000000000000080L;
    public static final long FUSE_MOPT_DEFER_PERMISSIONS      = 0x0000000000000100L;
    public static final long FUSE_MOPT_DIRECT_IO              = 0x0000000000000200L;
    public static final long FUSE_MOPT_EXTENDED_SECURITY      = 0x0000000000000400L;
    public static final long FUSE_MOPT_FSID                   = 0x0000000000000800L;
    public static final long FUSE_MOPT_FSNAME                 = 0x0000000000001000L;
    public static final long FUSE_MOPT_FSSUBTYPE              = 0x0000000000002000L;
    public static final long FUSE_MOPT_FSTYPENAME             = 0x0000000000004000L;
    public static final long FUSE_MOPT_INIT_TIMEOUT           = 0x0000000000008000L;
    public static final long FUSE_MOPT_IOSIZE                 = 0x0000000000010000L;
    public static final long FUSE_MOPT_JAIL_SYMLINKS          = 0x0000000000020000L;
    public static final long FUSE_MOPT_KILL_ON_UNMOUNT        = 0x0000000000040000L;
    public static final long FUSE_MOPT_LOCALVOL               = 0x0000000000080000L;
    public static final long FUSE_MOPT_NEGATIVE_VNCACHE       = 0x0000000000100000L;
    public static final long FUSE_MOPT_NO_ALERTS              = 0x0000000000200000L;
    public static final long FUSE_MOPT_NO_APPLEDOUBLE         = 0x0000000000400000L;
    public static final long FUSE_MOPT_NO_APPLEXATTR          = 0x0000000000800000L;
    public static final long FUSE_MOPT_NO_ATTRCACHE           = 0x0000000001000000L;
    public static final long FUSE_MOPT_NO_BROWSE              = 0x0000000002000000L;
    public static final long FUSE_MOPT_NO_LOCALCACHES         = 0x0000000004000000L;
    public static final long FUSE_MOPT_NO_READAHEAD           = 0x0000000008000000L;
    public static final long FUSE_MOPT_NO_SYNCONCLOSE         = 0x0000000010000000L;
    public static final long FUSE_MOPT_NO_SYNCWRITES          = 0x0000000020000000L;
    public static final long FUSE_MOPT_NO_UBC                 = 0x0000000040000000L;
    public static final long FUSE_MOPT_NO_VNCACHE             = 0x0000000080000000L;
    
    /* Get mounter's pid. */
    public static final int FUSEDEVGETMOUNTERPID           = _IOR('F', 1,  4);

    /* Check if FUSE_INIT kernel-user handshake is complete. */
    public static final int FUSEDEVIOCGETHANDSHAKECOMPLETE = _IOR('F', 2,  4);

    /* Mark the daemon as dead. */
    public static final int FUSEDEVIOCSETDAEMONDEAD        = _IOW('F', 3,  4);

    /* Tell the kernel which operations the daemon implements. */
    public static final int FUSEDEVIOCSETIMPLEMENTEDBITS   = _IOW('F', 4,  8);

    /* Get device's random "secret". */
    public static final int FUSEDEVIOCGETRANDOM            = _IOR('F', 5, 4);
    
    public static final int  IOCPARM_MASK    = 0x1fff;          /* parameter length, at most 13 bits */
    public static final int IOCPARM_MAX     = (IOCPARM_MASK + 1);      /* max size of ioctl args */
    /* no parameters */
    public static final int IOC_VOID        = 0x20000000;
    /* copy parameters out */
    public static final int IOC_OUT         = 0x40000000;
    /* copy parameters in */
    public static final int IOC_IN          = 0x80000000;
    /* copy paramters in and out */
    public static final int IOC_INOUT       = (IOC_IN|IOC_OUT);
    /* mask for IN/OUT/VOID */
    public static final int IOC_DIRMASK     = 0xe0000000;

    static int _IOC(int inout, int group, int num, int len)
    {
        return (inout | ((len & IOCPARM_MASK) << 16) | ((group) << 8) | (num));
    }
    
    static int _IO(int group, int num)
    {
        return _IOC(IOC_VOID,  group, num, 0);
    }
    
    static int _IOR(int group, int command, int size)
    {
        return _IOC(IOC_OUT,   group, command, size);
    }
    
    static  int _IOW(int g, int n, int size)
    {
        return _IOC(IOC_IN,    g, n, size);
    }

    public static final int FUSE_DEFAULT_BLOCKSIZE             = 4096;
    public static final int FUSE_DEFAULT_DAEMON_TIMEOUT        = 60;
    public static final int FUSE_DEFAULT_INIT_TIMEOUT          = 10;
    public static final int FUSE_DEFAULT_IOSIZE                = (16 * 4096);
    
    public byte [] mntpath = new byte[MAXPATHLEN]; // path to the mount point
    public byte [] fsname = new byte[MAXPATHLEN];  // file system description string
    public byte [] fstypename = new byte[MFSTYPENAMELEN]; // file system type name
    public byte [] volname = new byte[MAXPATHLEN]; // volume name
    public long altflags;            // see mount-time flags below
    public int blocksize;           // fictitious block size of our "storage"
    public int daemon_timeout;      // timeout in seconds for upcalls to daemon
    public int fsid;                // optional custom value for part of fsid[0]
    public int fssubtype;           // file system sub type id (type is "osxfusefs")
    public int init_timeout;        // timeout in seconds for daemon handshake
    public int iosize;              // maximum size for reading or writing
    public int random;              // random "secret" from device
    public int rdev;                // dev_t for the /dev/osxfuseN in question
    
    public void setMntPath(String mountPath)
    {
        stringToArray(mountPath, mntpath);
    }
    
    public void setFSName(String fsName)
    {
        stringToArray(fsName, fsname);
    }
    
    public void setFSTypeName(String fsTypeName)
    {
        stringToArray(fsTypeName, fstypename);
    }
    
    public void setVolName(String volName)
    {
        stringToArray(volName, volname);
    }
    
    protected void stringToArray(String sourceString, byte [] setArray)
    {
        byte [] mountPathBytes = sourceString.getBytes(Charset.forName("UTF-8"));
        if (mountPathBytes.length > setArray.length - 1)
            throw new IllegalArgumentException(mountPathBytes.length + " is greater than "+(setArray.length - 1));
        for (int curByteNum = 0; curByteNum < mntpath.length; curByteNum++)
            mntpath[curByteNum] = 0;    // Zero it out for good luck
        System.arraycopy(mountPathBytes, 0, setArray, 0, mountPathBytes.length);
    }

}
