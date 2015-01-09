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

import com.igeekinc.luwak.FUSEAttr;
import com.igeekinc.luwak.inode.exceptions.InodeException;
import com.igeekinc.luwak.util.FUSEDirHandle;
import com.igeekinc.luwak.util.FUSEFileHandle;

public abstract class FUSEInode<V extends FUSEVolume<?, ?, ?, ?, ?>>
{
	long inodeNum, generation;
	FUSEAttr attr;
	protected V volume;
	
	/**
	 * Create an inode using the attributes and overriding the inode number
	 * @param inodeNum
	 * @param generation
	 * @param attr
	 */
	public FUSEInode(V volume, long inodeNum, long generation, FUSEAttr attr)
	{
		this.volume = volume;
		this.inodeNum = inodeNum;
		this.generation = generation;
		this.attr = attr;
		attr.setInode(inodeNum);	// Override the one in attr
	}
	
	public FUSEInode(V volume, long inodeNum, long generation)
	{
		this.volume = volume;
		this.inodeNum = inodeNum;
		this.generation = generation;
	}
	
	public FUSEInode(V volume, long generation, FUSEAttr attr)
	{
		this.volume = volume;
		this.inodeNum = attr.getInode();
		this.generation = generation;
		this.attr = attr;
	}
	public long getInodeNum()
	{
		return inodeNum;
	}
	
	public long getGeneration()
	{
		return generation;
	}
	
	public FUSEAttr getAttr() throws InodeException
	{
		return attr;
	}
	
	public void setAttr(FUSEAttr attr)
	{
		if (attr.getInode() != inodeNum)
			throw new IllegalArgumentException("attr.inodeNum "+attr.getInode()+" != inodeNum "+inodeNum);
		this.attr = attr;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (generation ^ (generation >>> 32));
		result = prime * result + (int) (inodeNum ^ (inodeNum >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FUSEInode other = (FUSEInode) obj;
		if (generation != other.generation)
			return false;
		if (inodeNum != other.inodeNum)
			return false;
		return true;
	}

	public abstract FUSEInode<V> lookup(FUSEReqInfo reqInfo, String name) throws InodeException;
	
	/**
	 * Handles open of a file.  Return the fileHandle  if the open
	 * was successful
	 * @param reqInfo
	 * @param flags
	 * @return
	 */
	public abstract FUSEFileHandle open(FUSEReqInfo reqInfo, int flags) throws InodeException;
	
	/**
	 * Handles open of a directory.  Return the fileHandle if the open
	 * was successful
	 * @param reqInfo
	 * @param flags
	 * @return
	 */
	public abstract FUSEDirHandle opendir(FUSEReqInfo reqInfo, int flags) throws InodeException;
	
	public abstract CreateInfo<? extends FUSEFileHandle, ? extends FUSEInode> create(FUSEReqInfo reqInfo, String name, int flags,
			int mode) throws InodeException;
	
	public abstract FUSEInode<V> mkdir(FUSEReqInfo reqInfo, String name, int mode, int uMask) throws InodeException;
	
	public abstract FUSEAttr getAttr(FUSEReqInfo reqInfo) throws InodeException;
	
	public abstract FUSEAttr setAttr(FUSEReqInfo reqInfo, SetAttrs newAttr) throws InodeException;
	
	/**
	 * listXAttr should always just return the list of names of all of the attributes.  The inode adapter
	 * handles the case where the size of the required buffer should be returned.
	 * @param reqInfo
	 * @return
	 * @throws InodeException
	 */
	public abstract String [] listXAttr(FUSEReqInfo reqInfo) throws InodeException;
	
	/**
	 * Returns content from an extended attribute.  If buffer is zero length, return the total size
	 * of the attribute, regardles of the offset
	 * @param reqInfo
	 * @param xattrName 
	 * @param offset Offset from the beginning of the extended attribute
	 * @param buffer Returned data
	 * @return number of bytes copied OR the size of the extended attribute if the buffer is zero length
	 * @throws InodeException
	 */
	public abstract int getXAttr(FUSEReqInfo reqInfo, String xattrName, int offset, byte [] buffer) throws InodeException;

	public V getVolume() {
		return volume;
	}
}
