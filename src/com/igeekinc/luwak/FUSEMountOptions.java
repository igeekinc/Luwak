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

public class FUSEMountOptions
{
	public FUSEMountOptions()
	{
		
	}
	
	boolean allowOther = false;	// Set if other users are allowed to use the file system
	boolean allowRoot = false;	// Set if root is allowed to use the file system (unless the mounting user is root)
	String mountPath = null;
	String fsName = null;
	
	Integer blockSize = null;
	Integer maxRead = null;
	Integer fsSubType = null;
	
	/**
	 * Set if other users are allowed to use the file system
	 * @return
	 */
	public boolean isAllowOther()
	{
		return allowOther;
	}
	
	/**
	 * Set if other users are allowed to use the file system
	 * @param allowOther
	 */
	public void setAllowOther(boolean allowOther)
	{
		this.allowOther = allowOther;
	}
	
	/**
	 *  Set if root is allowed to use the file system (unless the mounting user is root)
	 * @return
	 */
	public boolean isAllowRoot()
	{
		return allowRoot;
	}
	
	/**
	 *  Set if root is allowed to use the file system (unless the mounting user is root)
	 * @return
	 */
	public void setAllowRoot(boolean allowRoot)
	{
		this.allowRoot = allowRoot;
	}
	
	/**
	 * The path the file system will be mounted on
	 * @return
	 */
	public String getMountPath()
	{
		return mountPath;
	}
	
	/**
	 * The path the file system will be mounted on
	 * @return
	 */
	public void setMountPath(String mountPath)
	{
		this.mountPath = mountPath;
	}
	
	/**
	 * The name of the file system type
	 * @return
	 */
	public String getFSName()
	{
		return fsName;
	}
	
	/**
	 * The name of the file system type
	 * @return
	 */
	public void setFSName(String fsName)
	{
		this.fsName = fsName;
	}
	
	/**
	 * The recommended blocksize for the file system
	 * @return
	 */
	public Integer getBlockSize()
	{
		return blockSize;
	}
	
	/**
	 * The recommended blocksize for the file system
	 * @return
	 */
	public void setBlockSize(int blockSize)
	{
		this.blockSize = blockSize;
	}
	
	/**
	 * The maximum read in one I/O
	 * @return
	 */
	public Integer getMaxRead()
	{
		return maxRead;
	}
	
	/**
	 * The maximum read in one I/O
	 * @return
	 */
	public void setMaxRead(int maxRead)
	{
		this.maxRead = maxRead;
	}

	public Integer getFSSubType()
	{
		return fsSubType;
	}

	public void setFSSubType(Integer fsSubType)
	{
		this.fsSubType = fsSubType;
	}
	
	
}
