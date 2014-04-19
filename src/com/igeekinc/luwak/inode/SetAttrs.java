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

import com.igeekinc.luwak.messages.FUSESetAttrInMessage;
import com.igeekinc.util.unix.UnixDate;

public class SetAttrs
{
	private int valid;
	private long size, lockOwner;
	private UnixDate accessTime, modifyTime;
	private int mode, uid, gid;
	
	public SetAttrs(FUSESetAttrInMessage inMessage)
	{
		this.valid = inMessage.getValid();
		this.size = inMessage.getSize();
		this.lockOwner = inMessage.getLockOwner();
		this.accessTime = inMessage.getAccessTime();
		this.modifyTime = inMessage.getModifyTime();
		this.mode = inMessage.getMode();
		this.uid = inMessage.getUID();
		this.gid = inMessage.getGID();
	}

	public int getValid()
	{
		return valid;
	}

	public long getSize()
	{
		if (!isSizeValid())
			throw new IllegalArgumentException("size is not valid");
		return size;
	}

	public boolean isSizeValid()
	{
		return (valid & FUSESetAttrInMessage.FATTR_SIZE) != 0;
	}

	public long getLockOwner()
	{
		if (!isLockOwnerValid())
			throw new IllegalArgumentException("lockOwer is not valid");
		return lockOwner;
	}

	public boolean isLockOwnerValid()
	{
		return (valid & FUSESetAttrInMessage.FATTR_LOCKOWNER) != 0;
	}

	public UnixDate getAccessTime()
	{
		if (!isAccessTimeValid())
			throw new IllegalArgumentException("accessTime is not valid");
		return accessTime;
	}

	public boolean isAccessTimeValid()
	{
		return (valid & FUSESetAttrInMessage.FATTR_ATIME) != 0;
	}

	public UnixDate getModifyTime()
	{
		if (!isModifyTimeValid())
			throw new IllegalArgumentException("modifyTime is not valid");
		return modifyTime;
	}

	public boolean isModifyTimeValid()
	{
		return (valid & FUSESetAttrInMessage.FATTR_MTIME) != 0;
	}

	public int getMode()
	{
		if (!isModeValid())
			throw new IllegalArgumentException("mode is not valid");
		return mode;
	}

	public boolean isModeValid()
	{
		return (valid & FUSESetAttrInMessage.FATTR_MODE) != 0;
	}

	public int getUID()
	{
		if (!isUIDValid())
			throw new IllegalArgumentException("uid is not valid");
		return uid;
	}

	public boolean isUIDValid()
	{
		return (valid & FUSESetAttrInMessage.FATTR_UID) != 0;
	}

	public int getGID()
	{
		if (!isGIDValid())
			throw new IllegalArgumentException("gid is not valid");
		return gid;
	}

	public boolean isGIDValid()
	{
		return (valid & FUSESetAttrInMessage.FATTR_GID) != 0;
	}
}
