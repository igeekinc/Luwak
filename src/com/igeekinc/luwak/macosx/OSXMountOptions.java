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

import java.io.IOException;
import java.io.Serializable;

import com.igeekinc.luwak.FUSEChannel;
import com.igeekinc.luwak.FUSEMountOptions;
import com.igeekinc.luwak.LibC;
import com.sun.jna.Memory;
import com.sun.jna.Native;

public class OSXMountOptions extends FUSEMountOptions implements Serializable
{
	private static final long serialVersionUID = 6079235770592760885L;
	
	String volumeName;
										// FUSE_MOPT_ALLOW_OTHER - inheirited
	boolean allowRecursion = false;		// FUSE_MOPT_ALLOW_RECURSION
										// FUSE_MOPT_ALLOW_ROOT - inheirited
	boolean autoXAttr = false;		 	// FUSE_MOPT_AUTO_XATTR
										// FUSE_MOPT_BLOCKSIZE - inheirited
	Integer daemonTimeout;				// FUSE_MOPT_DAEMON_TIMEOUT
	boolean debug = false;				// FUSE_MOPT_DEBUG
	boolean defaultPermissions = false;	// FUSE_MOPT_DEFAULT_PERMISSIONS
	boolean deferPermissions = false;	// FUSE_MOPT_DEFER_PERMISSIONS
	boolean directIO = false;			// FUSE_MOPT_DIRECT_IO
	boolean extendedSecurity = false;	// FUSE_MOPT_EXTENDED_SECURITY
	Integer fsID = 0;					// FUSE_MOPT_FSID
										// FUSE_MOPT_FSNAME - inheirited
										// FUSE_MOPT_FSSUBTYPE - inheirited
	String fsTypeName = null;			// FUSE_MOPT_FSTYPENAME
	Integer initTimeout = null;			// FUSE_MOPT_INIT_TIMEOUT
	Integer ioSize = null;				// FUSE_MOPT_IOSIZE
	boolean jailSymlinks = false;		// FUSE_MOPT_JAIL_SYMLINKS
	boolean killOnUnmount = false;		// FUSE_MOPT_KILL_ON_UNMOUNT
	boolean localVol = false;			// FUSE_MOPT_LOCALVOL
	boolean negativeVNCache = false;	// FUSE_MOPT_NEGATIVE_VNCACHE
	boolean noAlerts = false;			// FUSE_MOPT_NO_ALERTS
	boolean noAppleDouble = false;		// FUSE_MOPT_NO_APPLEDOUBLE
	boolean noAppleXAttr = false;		// FUSE_MOPT_NO_APPLEXATTR
	boolean noAttrCache = false;		// FUSE_MOPT_NO_ATTRCACHE
	boolean noBrowse = false;			// FUSE_MOPT_NO_BROWSE
	boolean noLocalCaches = false;		// FUSE_MOPT_NO_LOCALCACHES
	boolean noReadAhead = false;		// FUSE_MOPT_NO_READAHEAD
	boolean noSyncOnClose = false;		// FUSE_MOPT_NO_SYNCONCLOSE
	boolean noSyncWrites = false;		// FUSE_MOPT_NO_SYNCWRITES
	boolean noUBC = false;				// FUSE_MOPT_NO_UBC
	boolean noVNCache = false;			// FUSE_MOPT_NO_VNCACHE
	
	Integer rdev;						// dev_t for the /dev/osxfuseN to use
	Integer random;						// Random number read from device we will use
	
	public OSXMountOptions()
	{
		setBlockSize(OSXFUSENativeMountArgs.FUSE_DEFAULT_BLOCKSIZE);
		setInitTimeout(OSXFUSENativeMountArgs.FUSE_DEFAULT_INIT_TIMEOUT);
	}
	
	
	public boolean isAllowRecursion()
	{
		return allowRecursion;
	}


	public void setAllowRecursion(boolean allowRecursion)
	{
		this.allowRecursion = allowRecursion;
	}


	public boolean isAutoXAttr()
	{
		return autoXAttr;
	}


	public void setAutoXAttr(boolean autoXAttr)
	{
		this.autoXAttr = autoXAttr;
	}


	/**
	 * How long the kernel should wait for the daemon - default is FUSE_DEFAULT_DAEMON_TIMEOUT (60 seconds)
	 * @return
	 */
	public Integer getDaemonTimeout()
	{
		return daemonTimeout;
	}

	/**
	 * How long the kernel should wait for the daemon - default is FUSE_DEFAULT_DAEMON_TIMEOUT (60 seconds)
	 * @return
	 */
	public void setDaemonTimeout(int daemonTimeout)
	{
		this.daemonTimeout = daemonTimeout;
	}

	public boolean isDebug()
	{
		return debug;
	}


	public void setDebug(boolean debug)
	{
		this.debug = debug;
	}


	public boolean isDefaultPermissions()
	{
		return defaultPermissions;
	}


	public void setDefaultPermissions(boolean defaultPermissions)
	{
		this.defaultPermissions = defaultPermissions;
	}


	public boolean isDeferPermissions()
	{
		return deferPermissions;
	}


	public void setDeferPermissions(boolean deferPermissions)
	{
		this.deferPermissions = deferPermissions;
	}


	public boolean isDirectIO()
	{
		return directIO;
	}


	public void setDirectIO(boolean directIO)
	{
		this.directIO = directIO;
	}


	public boolean isExtendedSecurity()
	{
		return extendedSecurity;
	}


	public void setExtendedSecurity(boolean extendedSecurity)
	{
		this.extendedSecurity = extendedSecurity;
	}

	public Integer getFSID()
	{
		return fsID;
	}
	
	public void setFSID(Integer fsID)
	{
		this.fsID = fsID;
	}
	
	public String getFSTypeName()
	{
		return fsTypeName;
	}


	public void setFSTypeName(String fsTypeName)
	{
		this.fsTypeName = fsTypeName;
	}


	public Integer getInitTimeout()
	{
		return initTimeout;
	}
	
	public void setInitTimeout(Integer initTimeout)
	{
		this.initTimeout = initTimeout;
	}

	
	public Integer getIOSize()
	{
		return ioSize;
	}
	
	public void setIOSize(Integer ioSize)
	{
		this.ioSize = ioSize;
	}
	
	
	public boolean isJailSymlinks()
	{
		return jailSymlinks;
	}


	public void setJailSymlinks(boolean jailSymlinks)
	{
		this.jailSymlinks = jailSymlinks;
	}


	public boolean isKillOnUnmount()
	{
		return killOnUnmount;
	}


	public void setKillOnUnmount(boolean killOnUnmount)
	{
		this.killOnUnmount = killOnUnmount;
	}


	public boolean isLocalVol()
	{
		return localVol;
	}


	public void setLocalVol(boolean localVol)
	{
		this.localVol = localVol;
	}


	public boolean isNegativeVNCache()
	{
		return negativeVNCache;
	}


	public void setNegativeVNCache(boolean negativeVNCache)
	{
		this.negativeVNCache = negativeVNCache;
	}


	public boolean isNoAlerts()
	{
		return noAlerts;
	}


	public void setNoAlerts(boolean noAlerts)
	{
		this.noAlerts = noAlerts;
	}


	public boolean isNoAppleDouble()
	{
		return noAppleDouble;
	}


	public void setNoAppleDouble(boolean noAppleDouble)
	{
		this.noAppleDouble = noAppleDouble;
	}


	public boolean isNoAppleXAttr()
	{
		return noAppleXAttr;
	}


	public void setNoAppleXAttr(boolean noAppleXAttr)
	{
		this.noAppleXAttr = noAppleXAttr;
	}


	public boolean isNoAttrCache()
	{
		return noAttrCache;
	}


	public void setNoAttrCache(boolean noAttrCache)
	{
		this.noAttrCache = noAttrCache;
	}


	public boolean isNoBrowse()
	{
		return noBrowse;
	}


	public void setNoBrowse(boolean noBrowse)
	{
		this.noBrowse = noBrowse;
	}


	public boolean isNoLocalCaches()
	{
		return noLocalCaches;
	}


	public void setNoLocalCaches(boolean noLocalCaches)
	{
		this.noLocalCaches = noLocalCaches;
	}


	public boolean isNoReadAhead()
	{
		return noReadAhead;
	}


	public void setNoReadAhead(boolean noReadAhead)
	{
		this.noReadAhead = noReadAhead;
	}


	public boolean isNoSyncOnClose()
	{
		return noSyncOnClose;
	}


	public void setNoSyncOnClose(boolean noSyncOnClose)
	{
		this.noSyncOnClose = noSyncOnClose;
	}


	public boolean isNoSyncWrites()
	{
		return noSyncWrites;
	}


	public void setNoSyncWrites(boolean noSyncWrites)
	{
		this.noSyncWrites = noSyncWrites;
	}


	public boolean isNoUBC()
	{
		return noUBC;
	}


	public void setNoUBC(boolean noUBC)
	{
		this.noUBC = noUBC;
	}


	public boolean isNoVNCache()
	{
		return noVNCache;
	}


	public void setNoVNCache(boolean noVNCache)
	{
		this.noVNCache = noVNCache;
	}


	public String getVolumeName()
	{
		return volumeName;
	}
	
	public void setVolumeName(String volumeName)
	{
		this.volumeName = volumeName;
	}
	
	
	public Integer getRDev()
	{
		return rdev;
	}


	public void setRDev(Integer rdev)
	{
		this.rdev = rdev;
	}
	
	/**
	 * Gets the RDev from a channel
	 * @param channel
	 * @throws IOException
	 */
	public void setRDev(FUSEChannel channel) throws IOException
	{
		setRDev((int)((OSXFUSEChannel)channel).getRDev());
	}

	public Integer getRandom()
	{
		return random;
	}


	public void setRandom(Integer random)
	{
		this.random = random;
	}

	/**
	 * Get the random handshake number from a channel
	 * @param channel
	 * @throws IOException
	 */
	public void setRandom(FUSEChannel channel) throws IOException
	{
	    Memory drandomMem = new Memory(4);
	    int drandomGetStatus = LibC.ioctl(channel.getFDNum(), OSXFUSENativeMountArgs.FUSEDEVIOCGETRANDOM, drandomMem);
	    if (drandomGetStatus != 0)
	        throw new IOException("Could not get Random from FUSE device - errno = "+Native.getLastError());
	    setRandom(drandomMem.getInt(0));
	}
	/**
	 * Returns a native mount args structure.  If validate is set, all required fields must be present
	 * in the OSXMountOptions object or an IllegalArgumentException will be thrown.
	 * @param validate
	 * @return
	 */
	public OSXFUSENativeMountArgs getNativeMountArgs(boolean validate)
	{
		OSXFUSENativeMountArgs returnArgs = new OSXFUSENativeMountArgs();
		long altFlags = 0;
		altFlags = setFlags(altFlags, OSXFUSENativeMountArgs.FUSE_MOPT_ALLOW_OTHER, isAllowOther());
		if (validate && isAllowOther() && isAllowRoot())
			throw new IllegalArgumentException("Cannot set both allowOthers and allowRoot");
		altFlags = setFlags(altFlags, OSXFUSENativeMountArgs.FUSE_MOPT_ALLOW_RECURSION, isAllowRecursion());
		altFlags = setFlags(altFlags, OSXFUSENativeMountArgs.FUSE_MOPT_ALLOW_ROOT, isAllowRoot());
		altFlags = setFlags(altFlags, OSXFUSENativeMountArgs.FUSE_MOPT_AUTO_XATTR, isAutoXAttr());
		if (getBlockSize() != null)
		{
			returnArgs.blocksize = getBlockSize();
			altFlags = setFlags(altFlags, OSXFUSENativeMountArgs.FUSE_MOPT_BLOCKSIZE, true);	// block size in the structure is valid
		}
		if (getDaemonTimeout() != null)
		{
			returnArgs.daemon_timeout = getDaemonTimeout();
			altFlags = setFlags(altFlags, OSXFUSENativeMountArgs.FUSE_MOPT_DAEMON_TIMEOUT, true);	// Daemon timeout is valid
		}
		altFlags = setFlags(altFlags, OSXFUSENativeMountArgs.FUSE_MOPT_DEBUG, isDebug());
		altFlags = setFlags(altFlags, OSXFUSENativeMountArgs.FUSE_MOPT_DEFAULT_PERMISSIONS, isDefaultPermissions());
		altFlags = setFlags(altFlags, OSXFUSENativeMountArgs.FUSE_MOPT_DEFER_PERMISSIONS, isDeferPermissions());
		altFlags = setFlags(altFlags, OSXFUSENativeMountArgs.FUSE_MOPT_DIRECT_IO, isDirectIO());
		altFlags = setFlags(altFlags, OSXFUSENativeMountArgs.FUSE_MOPT_EXTENDED_SECURITY, isExtendedSecurity());
		if (getFSID() != null)
		{
			returnArgs.fsid = getFSID();
			altFlags = setFlags(altFlags, OSXFUSENativeMountArgs.FUSE_MOPT_FSID, true);
		}
		
		if (validate && getFSName() == null)
			throw new IllegalArgumentException("Must set fsName");
		if (getFSName() != null)
		{
			returnArgs.setFSName(getFSName());
			altFlags = setFlags(altFlags, OSXFUSENativeMountArgs.FUSE_MOPT_FSNAME, true);
		}
		if (getFSSubType() != null)
		{
			returnArgs.fssubtype = getFSSubType();
			altFlags = setFlags(altFlags, OSXFUSENativeMountArgs.FUSE_MOPT_FSSUBTYPE, true);
		}
		
		if (getFSTypeName() != null)
		{
			returnArgs.setFSTypeName(getFSTypeName());
			altFlags = setFlags(altFlags, OSXFUSENativeMountArgs.FUSE_MOPT_FSTYPENAME, true);
		}
		if (getInitTimeout() != null)
		{
			returnArgs.init_timeout = getInitTimeout();
			altFlags = setFlags(altFlags, OSXFUSENativeMountArgs.FUSE_MOPT_INIT_TIMEOUT, true);
		}
		
		if (getIOSize() != null)
		{
			returnArgs.iosize = getIOSize();
			altFlags = setFlags(altFlags, OSXFUSENativeMountArgs.FUSE_MOPT_IOSIZE, true);
		}
		altFlags = setFlags(altFlags, OSXFUSENativeMountArgs.FUSE_MOPT_JAIL_SYMLINKS, isJailSymlinks());
		altFlags = setFlags(altFlags, OSXFUSENativeMountArgs.FUSE_MOPT_KILL_ON_UNMOUNT, isKillOnUnmount());
		altFlags = setFlags(altFlags, OSXFUSENativeMountArgs.FUSE_MOPT_LOCALVOL, isLocalVol());
		altFlags = setFlags(altFlags, OSXFUSENativeMountArgs.FUSE_MOPT_NEGATIVE_VNCACHE, isNegativeVNCache());
		altFlags = setFlags(altFlags, OSXFUSENativeMountArgs.FUSE_MOPT_NO_ALERTS, isNoAlerts());
		altFlags = setFlags(altFlags, OSXFUSENativeMountArgs.FUSE_MOPT_NO_APPLEDOUBLE, isNoAppleDouble());
		altFlags = setFlags(altFlags, OSXFUSENativeMountArgs.FUSE_MOPT_NO_APPLEXATTR, isNoAppleXAttr());
		altFlags = setFlags(altFlags, OSXFUSENativeMountArgs.FUSE_MOPT_NO_ATTRCACHE, isNoAttrCache());
		altFlags = setFlags(altFlags, OSXFUSENativeMountArgs.FUSE_MOPT_NO_BROWSE, isNoBrowse());
		altFlags = setFlags(altFlags, OSXFUSENativeMountArgs.FUSE_MOPT_NO_LOCALCACHES, isNoLocalCaches());
		altFlags = setFlags(altFlags, OSXFUSENativeMountArgs.FUSE_MOPT_NO_READAHEAD, isNoReadAhead());
		altFlags = setFlags(altFlags, OSXFUSENativeMountArgs.FUSE_MOPT_NO_SYNCONCLOSE, isNoSyncOnClose());
		altFlags = setFlags(altFlags, OSXFUSENativeMountArgs.FUSE_MOPT_NO_SYNCWRITES, isNoSyncWrites());
		altFlags = setFlags(altFlags, OSXFUSENativeMountArgs.FUSE_MOPT_NO_UBC, isNoUBC());
		altFlags = setFlags(altFlags, OSXFUSENativeMountArgs.FUSE_MOPT_NO_VNCACHE, isNoVNCache());
		
		if (validate && getMountPath() == null || getMountPath().length() == 0)
			throw new IllegalArgumentException("MountPath not set");
		returnArgs.setMntPath(getMountPath());


		if (validate && getVolumeName() == null || getVolumeName().length() == 0)
			throw new IllegalArgumentException("Volume Name not set");

		returnArgs.setVolName(getVolumeName());
		
		if (validate && getRDev() == null)
			throw new IllegalArgumentException("RDev not set");
		
		if (getRDev() != null)
			returnArgs.rdev = getRDev();
	    
		if (validate && getRandom() == null)
			throw new IllegalArgumentException("Random not set");
		
		if (getRandom() != null)
			returnArgs.random = getRandom();
		
		returnArgs.altflags = altFlags;
		returnArgs.write();	// Flush to the native structure
		return returnArgs;
	}
	
	private long setFlags(long curFlags, long flagBit, boolean set)
	{
		if (set)
			return curFlags |= flagBit;
		return curFlags & ~flagBit;
	}
}
