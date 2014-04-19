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

import java.nio.charset.Charset;

public class FUSELookupInMessage extends FUSEOpInMessage
{
	public static final int kNameOffset = 0;
	
	static final Charset utf8 = Charset.forName("UTF-8");
	
	public FUSELookupInMessage(FUSEInMessage parent)
	{
		super(parent);
	}
	
	public String getName()
	{
		int stringLength = length;
		if (buffer[baseOffset + stringLength] == 0)
			stringLength --;	// We don't need to stinking null termination - more than one null is just a mess so don't worry about it
		String returnString = new String(buffer, baseOffset, stringLength, utf8);
		return returnString;
	}
}
