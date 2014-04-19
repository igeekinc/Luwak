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

/**
 * The Linux create message defines one more additional parameter (umask) than the OSX create message.  This is the abstract
 * base class for both.  Filesystems that want to use the umask parameter should check to see if the type is a LinuxFUSECreateInMessage
 * and then cast appropriately.
 * @author David L. Smith-Uchida
 */
public abstract class FUSECreateInMessage extends FUSEOpInMessage
{
	public FUSECreateInMessage(FUSEInMessage parent)
	{
		super(parent);
	}
	
	public abstract int getFlags();
	public abstract int getMode();
	public abstract String getName();
}
