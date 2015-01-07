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

import org.apache.log4j.Logger;

import com.igeekinc.luwak.inode.exceptions.AccessException;
import com.igeekinc.luwak.inode.exceptions.InodeException;
import com.igeekinc.luwak.util.FUSEDirHandle;
import com.igeekinc.luwak.util.FUSEFileHandle;
import com.igeekinc.luwak.util.FUSEHandleManager;

/**
 * FUSEVolumeBase provides basic functionality for classes implementing FUSEVolume
 * @author David L. Smith-Uchida
 *
 * @param <I> The Inode class
 * @param <F> The File Handle class
 * @param <D> The Directory Handle Class
 * @param <M> The Inode Manager class
 * @param <H> The Handle Manager Class
 */
public abstract class FUSEVolumeBase<I extends FUSEInode<? extends FUSEVolume<I, F, D, M, H>>, F extends FUSEFileHandle, D extends FUSEDirHandle, M extends FUSEInodeManager<I>, H extends FUSEHandleManager<F, D>> implements FUSEVolume<I, F, D, M, H>
{
	protected FUSEInodeAdapter<I, M, F, D, H> adapter;
	protected Logger logger = Logger.getLogger(getClass());
	private H handleManager;
	private M inodeManager;
	
	public abstract H allocateHandleManager();

	public abstract M allocateInodeManager();

	public FUSEVolumeBase()
	{
		handleManager = allocateHandleManager();
		inodeManager = allocateInodeManager();
	}
	@SuppressWarnings("unchecked")
	public void setInodeAdapter(FUSEInodeAdapter<I, ? extends FUSEInodeManager<I>, ?, ?, ?> adapter)
	{
		this.adapter = (FUSEInodeAdapter<I, M, F, D, H>) adapter;
	}

	public H getHandleManager() 
	{
		return handleManager;
	}

	public M getInodeManager() 
	{
		return inodeManager;
	}
}
