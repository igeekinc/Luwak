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
 
package com.igeekinc.luwak.messages.linux;

import com.igeekinc.luwak.messages.FUSEGetXattrInMessage;
import com.igeekinc.luwak.messages.FUSEInMessage;


public class LinuxFUSEGetXAttrInMessage extends FUSEGetXattrInMessage
{
	public static final int kSizeOffset = 0;
	public static final int kPaddingOffset = 4;
	
	public static final int kGetXAttrMessageLength = 8;
			
	public LinuxFUSEGetXAttrInMessage(FUSEInMessage parent)
	{
		super(parent);
	}

	public int getSize()
	{
		return getIntAtOffset(kSizeOffset);
	}
	
	public String getXAttrName()
	{
		return getUTF8StringAtOffset(kGetXAttrMessageLength, getSize()-kGetXAttrMessageLength);
	}
	
	public String toString()
	{
		return "getxattr name = "+getXAttrName()+", size = "+getSize();
	}
}