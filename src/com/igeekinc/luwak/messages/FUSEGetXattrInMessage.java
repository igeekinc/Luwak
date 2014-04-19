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
 * The FUSEGetXattrInMessage is share between the getxattr and listxattr functions.
 * When listxattr is called, the xattr name is ignored.
 * 
 * Linux and OSX are slightly different, so this is the abstract base class with the
 * common calls between the two.
 * @author David L. Smith-Uchida
 *
 */
public abstract class FUSEGetXattrInMessage extends FUSEOpInMessage
{

	public FUSEGetXattrInMessage(FUSEInMessage parent)
	{
		super(parent);
	}

	/**
	 * The size of the whole message
	 * @return
	 */
	public abstract int getSize();
	
	/**
	 * The attribute name to retrieve
	 * @return
	 */
	public abstract String getXAttrName();
}
