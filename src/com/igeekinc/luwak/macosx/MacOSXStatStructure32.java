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
 
package com.igeekinc.luwak.macosx;

import com.igeekinc.luwak.Stat;

public class MacOSXStatStructure32 extends Stat
{
	public int st_dev;
	public int st_ino;
	public short st_mode;
	public short st_nlink;
	public int st_uid;
	public int st_gid;
	public int st_rdev;
	public int st_atimetime;
	public int st_atimensecs;
	public int st_mtimetime;
	public int st_mtimensecs;
	public int st_ctimetime;
	public int st_ctimensecs;
	public long st_size;
	public long st_blocks;
	public int st_blksize;
	public int st_flags;
	public int st_gen;
	public int st_lspare;
	public int st_qspare;
	
	public MacOSXStatStructure32()
	{
	    
	}
	
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
		return st_atimetime;
	}

	public long get_st_atimensec()
	{
		return st_atimensecs;
	}

	public long get_st_mtime()
	{
		return st_mtimetime;
	}

	public long get_st_mtimensec()
	{
		return st_mtimensecs;
	}

	public long get_st_ctime()
	{
		return st_ctimetime;
	}

	public long get_st_ctimensec()
	{
		return st_ctimensecs;
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

	public int get_st_flags()
	{
		return st_flags;
	}

	public int get_st_gen()
	{
		return st_gen;
	}
}
