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

import java.nio.ByteBuffer;

import com.igeekinc.luwak.inode.FUSEReqInfo;
import com.igeekinc.luwak.inode.exceptions.InodeException;

public abstract class FUSEFileHandle extends FUSEHandle
{
	protected FUSEFileHandle(long handleNum)
	{
		super(handleNum);
	}
	
	public abstract int read(FUSEReqInfo reqInfo, long offset, byte[] returnBuffer, int flags, int readFlags) throws InodeException;

	public abstract int write(FUSEReqInfo reqInfo, long offset, ByteBuffer writeBytes, int writeFlags) throws InodeException;
	public abstract void flush(FUSEReqInfo reqInfo, long lockOwnerFlags) throws InodeException;
}
