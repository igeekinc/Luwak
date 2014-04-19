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

import com.igeekinc.util.BufferStructure;

public class FUSEStatFS extends BufferStructure
{
    /*
    __u64   blocks;
    __u64   bfree;
    __u64   bavail;
    __u64   files;
    __u64   ffree;
    __u32   bsize;
    __u32   namelen;
    __u32   frsize;
    __u32   padding;
    __u32   spare[6];
    */
    public static final int kBlocksOffset = 0;
    public static final int kBFreeOffset = kBlocksOffset + 8;
    public static final int kBAvailOffset = kBFreeOffset + 8;
    public static final int kFilesOffset = kBAvailOffset + 8;
    public static final int kFFreeOffset = kFilesOffset + 8;
    public static final int kBSizeOffset = kFFreeOffset + 8;
    public static final int kNameLenOffset =kBSizeOffset + 4;
    public static final int kFRSizeOffset = kNameLenOffset + 4;
    public static final int kPaddingOffset = kFRSizeOffset + 4;
    public static final int kSpareOffset = kPaddingOffset + 4;
    public static final int kFUSEStatFSLength = kSpareOffset + (4 * 6);
    
    public FUSEStatFS()
    {
        super(new byte[kFUSEStatFSLength]);
    }
    
    public FUSEStatFS(byte [] buffer, int offset, int length)
    {
        super(buffer, offset, length);
    }
    
    public long getBlocks()
    {
        return getLongAtOffset(kBlocksOffset);
    }
    
    public void setBlocks(long blocks)
    {
        setLongAtOffset(blocks, kBlocksOffset);
    }
    
    public long getBFree()
    {
    	return getLongAtOffset(kBFreeOffset);
    }
    
    public void setBFree(long blocksFree)
    {
    	setLongAtOffset(blocksFree, kBFreeOffset);
    }
    
    public long getBAvail()
    {
        return getLongAtOffset(kBAvailOffset);
    }
    
    public void setBAvail(long bavail)
    {
        setLongAtOffset(bavail, kBAvailOffset);
    }
    
    public long getFiles()
    {
        return getLongAtOffset(kFilesOffset);
    }
    
    public void setFiles(long files)
    {
        setLongAtOffset(files, kFilesOffset);
    }
    
    public long getFFree()
    {
        return getLongAtOffset(kFFreeOffset);
    }
    
    public void setFFree(long ffree)
    {
        setLongAtOffset(ffree, kFFreeOffset);
    }
    
    public int getBSize()
    {
        return getIntAtOffset(kBSizeOffset);
    }
    
    public void setBSize(int bsize)
    {
        setIntAtOffset(bsize, kBSizeOffset);
    }
    
    public int getNameLen()
    {
        return getIntAtOffset(kNameLenOffset);
    }
    
    public void setNameLen(int nameLen)
    {
        setIntAtOffset(nameLen, kNameLenOffset);
    }
    
    public int getFRSize()
    {
        return getIntAtOffset(kFRSizeOffset);
    }
    
    public void setFRSize(int frSize)
    {
        setIntAtOffset(frSize, kFRSizeOffset);
    }

    public ByteBuffer getBuffer()
    {
        return super.getBuffer();
    }
}
