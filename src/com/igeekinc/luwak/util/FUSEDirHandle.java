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
 
package com.igeekinc.luwak.util;

import com.igeekinc.luwak.inode.DirectoryEntryBuffer;
import com.igeekinc.luwak.inode.FUSEReqInfo;
import com.igeekinc.luwak.inode.exceptions.InodeException;

/**
 * FUSEDirHandle provides a way to keep track of the information for an open directory handle.
 * This is provided for convenience - other mechanisms can be used to handle open directories
 *
 */
public abstract class FUSEDirHandle extends FUSEHandle
{
	protected FUSEDirHandle(long handleNum)
	{
		super(handleNum);
	}
	
	public abstract DirectoryEntryBuffer readdir(FUSEReqInfo reqInfo, long offset, int size, int flags, int readFlags) throws InodeException;
}
