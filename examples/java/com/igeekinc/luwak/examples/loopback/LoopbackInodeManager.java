/*
 * Copyright 2002-2014 iGeek, Inc.
 * All Rights Reserved
 * @@OpenSource@@
 */
package com.igeekinc.luwak.examples.loopback;

import java.util.HashMap;

import com.igeekinc.luwak.inode.FUSEInodeManager;

class LoopbackInodeManager extends FUSEInodeManager<LoopbackInode>
{
	HashMap<String, LoopbackInode>pathMap = new HashMap<String, LoopbackInode>();
	public LoopbackInodeManager(LoopbackVolume parent)
	{
		super(parent);
	}
	@Override
	public boolean addInode(LoopbackInode addInode)
	{
		boolean returnValue;
		if (super.addInode(addInode))
		{
			synchronized(pathMap)
			{
				LoopbackInode addLoopbackInode = (LoopbackInode)addInode;
				String path = addLoopbackInode.getBaseFile().getAbsolutePath();
				if (pathMap.containsKey(path))
				{
					returnValue = false;
				}
				else
				{
					pathMap.put(path, addLoopbackInode);
					returnValue = true;
				}
			}
			if (!returnValue)
				super.removeInode(addInode);
		}
		else
		{
			returnValue = false;
		}
		return returnValue;
	}
	@Override
	public void removeInode(LoopbackInode removeInode)
	{
		synchronized(pathMap)
		{
			LoopbackInode removeLoopbackInode = (LoopbackInode)removeInode;
			pathMap.remove(removeLoopbackInode.getBaseFile().getAbsolutePath());
		}
		super.removeInode(removeInode);
	}

	public LoopbackInode retrieveInode(String path)
	{
		synchronized(pathMap)
		{
			return pathMap.get(path);
		}
	}
}