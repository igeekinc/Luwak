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

import java.util.HashMap;

public abstract class FUSEHandleManager<F extends FUSEFileHandle, D extends FUSEDirHandle>
{
	protected HashMap<Long, F>fileHandles = new HashMap<Long, F>();
	protected HashMap<Long, D>dirHandles = new HashMap<Long, D>();
	private long nextHandleNum;
	private boolean rolledOver = false;
	
	public FUSEHandleManager()
	{
		nextHandleNum = 0;
	}
	
	public synchronized D getNewDirHandle()
	{
		long handleNum = getNextHandleNum();
		D returnHandle = allocateDirHandle(handleNum);
		dirHandles.put(returnHandle.getHandleNum(), returnHandle);
		return returnHandle;
	}
	
	public synchronized F getNewFileHandle()
	{
		long handleNum = getNextHandleNum();
		F returnHandle = allocateFileHandle(handleNum);
		fileHandles.put(returnHandle.getHandleNum(), returnHandle);
		return returnHandle;
	}

	
	public synchronized D retrieveDirHandle(long handleNum)
	{
		D returnHandle = dirHandles.get(handleNum);
	
		return returnHandle;
	}
	
	public synchronized F retrieveFileHandle(long handleNum)
	{
		F returnHandle = fileHandles.get(handleNum);

		return returnHandle;
	}
	
	public synchronized void releaseFileHandle(F releaseHandle)
	{
		fileHandles.remove(releaseHandle.getHandleNum());
		releaseHandle.release();
	}
	
	public synchronized void releaseDirHandle(D releaseHandle)
	{
		dirHandles.remove(releaseHandle.getHandleNum());
		releaseHandle.release();
	}
	
	private long getNextHandleNum()
	{
		if (rolledOver)
		{
			while (fileHandles.containsKey(nextHandleNum) || dirHandles.containsKey(nextHandleNum))
			{
				nextHandleNum ++;
				if (nextHandleNum == Integer.MAX_VALUE)
					nextHandleNum = 0;	// We rolled over
			}
		}
		else
		{
			nextHandleNum++;
			if (nextHandleNum == Integer.MAX_VALUE)
			{
				nextHandleNum = 0;	// We rolled over
				rolledOver = true;	// Now, we'll check for in-use filehandles.  We'll probably never get to this point but.
			}
		}
		return nextHandleNum;
	}
	

	protected abstract D allocateDirHandle(long handleNum);
	
	protected abstract F allocateFileHandle(long handleNum);
}
