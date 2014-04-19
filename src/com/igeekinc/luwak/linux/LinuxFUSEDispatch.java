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
 
package com.igeekinc.luwak.linux;

import com.igeekinc.luwak.FUSEChannel;
import com.igeekinc.luwak.FUSEDispatch;
import com.igeekinc.luwak.lowlevel.FUSELowLevel;
import com.igeekinc.luwak.messages.FUSEInMessage;
import com.igeekinc.luwak.messages.linux.LinuxFUSECreateInMessage;
import com.igeekinc.luwak.messages.linux.LinuxFUSEGetXAttrInMessage;
import com.igeekinc.luwak.messages.linux.LinuxFUSEWriteInMessage;

public class LinuxFUSEDispatch extends FUSEDispatch 
{

	public LinuxFUSEDispatch(FUSEChannel channel, FUSELowLevel receiver) 
	{
		super(channel, receiver);
	}

	public void create(FUSEInMessage inMessage) 
	{
		LinuxFUSECreateInMessage createMessage = new LinuxFUSECreateInMessage(inMessage);
		receiver.create(inMessage, createMessage);
	}
	
	public void write(FUSEInMessage inMessage) 
	{
		LinuxFUSEWriteInMessage writeMessage = new LinuxFUSEWriteInMessage(inMessage);
		receiver.write(inMessage, writeMessage);
	}

	@Override
	public void getxattr(FUSEInMessage inMessage)
	 {
		LinuxFUSEGetXAttrInMessage getXAttrMessage = new LinuxFUSEGetXAttrInMessage(inMessage);
		receiver.getxattr(inMessage, getXAttrMessage);
	}
	
	@Override
	public void listxattr(FUSEInMessage inMessage)
	{
		LinuxFUSEGetXAttrInMessage listXAttrMessage = new LinuxFUSEGetXAttrInMessage(inMessage);
		receiver.listxattr(inMessage, listXAttrMessage);
	}
}
