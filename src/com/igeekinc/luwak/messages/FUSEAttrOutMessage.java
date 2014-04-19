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
 
package com.igeekinc.luwak.messages;

import java.nio.ByteBuffer;

import com.igeekinc.luwak.FUSEAttr;

public class FUSEAttrOutMessage extends FUSEOpOutMessage 
{
	public static final int kAttrValidOffset = 0;
	public static final int kAttrValidNSecOffset = kAttrValidOffset + 8;
	public static final int kDummyOffset = kAttrValidNSecOffset + 4;
	public static final int kBaseLength = kDummyOffset + 4;
	public static final int kTotalLength = kBaseLength + FUSEAttr.kFUSEAttrLength;
	
	FUSEAttr attr;
	
	public FUSEAttrOutMessage()
	{
		super(kBaseLength);
		attr = new FUSEAttr();
	}
	
	public FUSEAttrOutMessage(FUSEAttr attr)
	{
		super(kBaseLength);
		this.attr = attr;
	}
	
	public int getNumBuffers()
	{
		return 2;
	}
	
	public void getBuffers(ByteBuffer[] bufferList, int bufferListOffset)
	{
		bufferList[bufferListOffset] = getBuffer();
		bufferList[bufferListOffset + 1] = attr.getBuffer();
	}
	
	public FUSEAttr getAttr()
	{
		return attr;
	}
	
	public void setAttrValid(long attrValid)
	{
		setLongAtOffset(attrValid, kAttrValidOffset);
	}
	
	public long getAttrValid()
	{
		return getLongAtOffset(kAttrValidOffset);
	}
	
	public void setAttrValidNSec(int attrValidNSec)
	{
		setIntAtOffset(attrValidNSec, kAttrValidNSecOffset);
	}
	
	public int getAttrValidNSec()
	{
		return getIntAtOffset(kAttrValidNSecOffset);
	}
}
