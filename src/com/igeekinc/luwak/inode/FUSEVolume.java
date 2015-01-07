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

import com.igeekinc.luwak.FUSEStatFS;
import com.igeekinc.luwak.inode.exceptions.InodeException;
import com.igeekinc.luwak.util.FUSEDirHandle;
import com.igeekinc.luwak.util.FUSEFileHandle;
import com.igeekinc.luwak.util.FUSEHandleManager;

/**
 * The FUSEVolume takes care of the volume level operations such as getRoot
 * @author David L. Smith-Uchida
 *
 * @param <I> The Inode class
 * @param <F> The File Handle class
 * @param <D> The Directory Handle Class
 * @param <M> The Inode Manager class
 * @param <H> The Handle Manager Class
 */
public interface FUSEVolume<I extends FUSEInode, F extends FUSEFileHandle, D extends FUSEDirHandle, M extends FUSEInodeManager<I>, H extends FUSEHandleManager<F, D>>
{
	public H allocateHandleManager();
	
	public FUSEInodeManager<I> allocateInodeManager();
	
	public FUSEInodeAdapter<I, ? extends FUSEInodeManager<I>, ?, ?, ?> getAdapter();
	
	/**
	 * This routine will be called when the FUSEInodeAdapter is being initialized
	 * and gives the adapter to the plugin
	 * @param adapter
	 */
	public void setInodeAdapter(FUSEInodeAdapter<I, ? extends FUSEInodeManager<I>, ?, ?, ?> adapter);

	
	public I getRoot();
	public I retrieveInode(long inodeNum);	

	//public FUSEAttr getXAttr(FUSEReqInfo reqInfo) throws InodeException;

	public FUSEStatFS getStatFS(FUSEReqInfo reqInfo) throws InodeException;

	public H getHandleManager();
	public M getInodeManager();
}
