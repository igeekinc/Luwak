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

public abstract class FUSEHandle
{
	private long handleNum;
	
	FUSEHandle(long handleNum)
	{
		this.handleNum = handleNum;
	}

	public long getHandleNum()
	{
		return handleNum;
	}

	/**
	 * release will be called when the file handle is released by release() or releasedir()
	 * This is the place to close file descriptors, etc.
	 */
	public void release()
	{
		
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (handleNum ^ (handleNum >>> 32));
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
		FUSEHandle other = (FUSEHandle) obj;
		if (handleNum != other.handleNum)
			return false;
		return true;
	}
}
