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

public class FUSEMessageInfo<I extends FUSEInode>
{
	private I inode;
	private long inodeNum;
	private FUSEReqInfo reqInfo;
	
	public FUSEMessageInfo(I inode, long inodeNum, FUSEReqInfo reqInfo)
	{
		this.inode = inode;
		this.inodeNum = inodeNum;
		this.reqInfo = reqInfo;
	}

	public I getInode()
	{
		return inode;
	}

	public long getInodeNum()
	{
		return inodeNum;
	}

	public FUSEReqInfo getReqInfo()
	{
		return reqInfo;
	}
}
