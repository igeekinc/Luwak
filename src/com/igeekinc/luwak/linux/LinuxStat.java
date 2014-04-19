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

import com.igeekinc.luwak.Stat;

public class LinuxStat extends Stat
{
    public long st_dev;
    public short pad1;
    public int st_ino;
    public int st_mode;
    public int st_nlink;
    public int st_uid;
    public int st_gid;
    public long st_rdev;
    public int pad2;
    public int st_size;
    public int st_blksize;
    public int st_blocks;
    public int st_atime;
    public int st_atimensec;
    public int st_mtime;
    public int st_mtimensec;
    public int st_ctime;
    public int st_ctimensec;
    public long st_ino64;
    
    public long get_st_dev()
    {
        return st_dev;
    }

    public long get_st_ino()
    {
        return st_ino;
    }

    public int get_st_mode()
    {
        return st_mode;
    }

    public int get_st_nlink()
    {
        return st_nlink;
    }

    public int get_st_uid()
    {
        return st_uid;
    }

    public int get_st_gid()
    {
        return st_gid;
    }

    public long get_st_rdev()
    {
        return st_rdev;
    }

    public long get_st_atime()
    {
        return st_atime;
    }

    public long get_st_atimensec()
    {
        return st_atimensec;
    }

    public long get_st_mtime()
    {
        return st_mtime;
    }

    public long get_st_mtimensec()
    {
        return st_mtimensec;
    }

    public long get_st_ctime()
    {
        return st_ctime;
    }

    public long get_st_ctimensec()
    {
        return st_ctimensec;
    }

    public long get_st_size()
    {
        return st_size;
    }

    public long get_st_blocks()
    {
        return st_blocks;
    }

    public int get_st_blksize()
    {
        return st_blksize;
    }
}
