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

import java.util.HashMap;

/**
 * FUSEInodeManager provides a basic inode cache.  Misses in the cache are satisfied by calling into the
 * volume
 * @author David L. Smith-Uchida
 *
 * @param <I> Inode class
 */
public class FUSEInodeManager<I extends FUSEInode>
{
	protected HashMap<Long, I>map = new HashMap<Long, I>();
	protected FUSEVolume<I, ?, ?, ?, ?> volume;
	
	public FUSEInodeManager(FUSEVolume<I, ?, ?, ?, ?> volume)
	{
		this.volume = volume;
	}
	
	public I retrieveInode(long inodeNum)
	{
		I returnInode = null;
		synchronized(map)
		{
			returnInode = map.get(inodeNum);
		}
		if (returnInode == null)
		{
			returnInode = volume.retrieveInode(inodeNum);
			if (returnInode != null)
			{
				synchronized(map)
				{
					// retrieveInode may have taken some time, so make sure that no one else
					// slipped in our inode while we were waiting on retrieveInode.  If they did,
					// discard the inode we got from retrieveInod and use the other one instead
					I checkInode = map.get(inodeNum);
					if (checkInode != null)
					{
						returnInode = checkInode;
					}
					else
					{
						map.put(inodeNum, returnInode);
					}
				}
			}
		}
		return returnInode;
	}
	
	public boolean addInode(I addInode)
	{
		synchronized(map)
		{
			if (map.containsKey(addInode.getInodeNum()))
				return false;
			map.put(addInode.getInodeNum(), addInode);
		}
		return true;
	}
	
	public void removeInode(I removeInode)
	{
		synchronized(map)
		{
			map.remove(removeInode.getInodeNum());
		}
	}
}
