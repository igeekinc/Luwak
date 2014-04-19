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
 
package com.igeekinc.luwak;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import com.igeekinc.util.BufferStructure;

public class FUSEDirEntry extends BufferStructure
{
	public static final int kInodeOffset = 0;
	public static final int kOffsetOffset = kInodeOffset + 8;
	public static final int kNameLenOffset = kOffsetOffset + 8;
	public static final int kTypeOffset = kNameLenOffset + 4;
	public static final int kNameOffset = kTypeOffset + 4;
	
	public static final int kFixedSize = kNameOffset;
	
	public static final int kMaxNameLen = 255;
	
	// Dirent file types
	public static final int DT_UNKNOWN = 0;	// Unknown file type
	public static final int DT_FIFO = 1;	// FIFO file type
	public static final int DR_CHR = 2;		// Character device
	public static final int DT_DIR = 4;		// Directory
	public static final int DT_BLK = 6;		// Block device
	public static final int DT_REG = 8;		// Regular file
	public static final int DT_LNK = 10;	// Symbolic link
	public static final int DT_SOCK = 12;	// Socket
	public static final int DR_WHT = 14;	// Whiteout
	
	static Charset utf8 = Charset.forName("UTF-8");
	public FUSEDirEntry(byte[] buffer)
	{
		super(buffer);
	}

	public FUSEDirEntry(byte[] buffer, int baseOffset, int length)
	{
		super(buffer, baseOffset, length);
	}

	
	static int calculatePaddingLength(int nameBytesTotal)
	{
		int misaligned = nameBytesTotal % 8;
		if (misaligned > 0)
			return 8 - misaligned;
		return 0;
	}
	
	public FUSEDirEntry(long inodeNum, long offset, int type, byte [] nameBytes)
	{
		// We'll assume that we are going to start on a 64-bit aligned boundary.  The fixed portion is 64 bit aligned, so just
		// worry about aligning on the name bytes
		super(new byte[kFixedSize + nameBytes.length + calculatePaddingLength(nameBytes.length)]);
		setInodeNum(inodeNum);
		setOffset(offset);
		setNameBytes(nameBytes);
		setType(type);
	}
	
	public FUSEDirEntry(long inodeNum, long offset, int type, String name)
	{
		this(inodeNum, offset, type, name.getBytes(utf8));
	}

	public void setInodeNum(long inodeNum)
	{
		setLongAtOffset(inodeNum, kInodeOffset);
	}
	
	public long getInodeNum()
	{
		return getLongAtOffset(kInodeOffset);
	}
	
	public void setOffset(long offset)
	{
		setLongAtOffset(offset, kOffsetOffset);
	}

	public long getOffset()
	{
		return getLongAtOffset(kOffsetOffset);
	}
	
	public int getNameLen()
	{
		return getIntAtOffset(kNameLenOffset);
	}
	
	public void setNameBytes(byte [] nameBytes)
	{
		int nameLen = nameBytes.length;
		if (nameLen > kMaxNameLen)
			throw new IllegalArgumentException("Max name length = "+kMaxNameLen);
		if (nameLen < 1)
			throw new IllegalArgumentException("Cannot have name length less than 1");

		setIntAtOffset(nameLen, kNameLenOffset);
		System.arraycopy(nameBytes, 0, buffer, baseOffset + kNameOffset, nameLen);
		// FYI - dirent has null-terminated names.  fuse_dirent does not.
	}
	
	public void setType(int type)
	{
		setIntAtOffset(type, kTypeOffset);
	}
	
	public ByteBuffer getBuffer()
	{
		return super.getBuffer();
	}
	
	public int getLength()
	{
		return length;
	}

	public String getName()
	{
		return new String(buffer, baseOffset + kNameOffset, getNameLen(), utf8);
	}
	
	public String toString()
	{
		return "inodeNum = "+getInodeNum()+" offset = "+getOffset()+" nameLen = "+getNameLen()+" name = "+getName();
	}
}
