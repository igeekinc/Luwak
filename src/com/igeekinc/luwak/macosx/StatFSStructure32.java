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

import com.igeekinc.luwak.StatFS;

public class StatFSStructure32 extends StatFS
{
	public static final int MFSTYPENAMELEN = 15;
	public static final int MNAMELEN = 90;
	
	public short f_otype;
	public short f_oflags;
	public int f_bsize;
	public int f_iosize;
	public int f_blocks;
	public int f_bfree;
	public int f_bavail;
	public int f_files;
	public int f_ffree;
	public long f_fsid;
	public int f_owner;
	public short f_reserved1;
	public short f_type;
	public short f_flags;
	public long f_reserved2;
	public byte [] f_fstypename = new byte[MFSTYPENAMELEN];
	public byte [] f_mntonname = new byte[MNAMELEN];
	public byte [] f_mntfromname = new byte[MNAMELEN];
	public byte f_reserved3;        /* reserved for future use */
    public int [] f_reserved4 = new int[4];     /* reserved for future use */

	/**
	 * @return Returns the f_bavail.
	 */
	public long get_f_bavail()
	{
		return f_bavail;
	}

	/**
	 * @return Returns the f_bfree.
	 */
	public long get_f_bfree()
	{
		return f_bfree;
	}

	/**
	 * @return Returns the f_blocks.
	 */
	public long get_f_blocks()
	{
		return f_blocks;
	}

	/**
	 * @return Returns the f_bsize.
	 */
	public int get_f_bsize()
	{
		return f_bsize;
	}

	/**
	 * @return Returns the f_ffree.
	 */
	public long get_f_ffree()
	{
		return f_ffree;
	}

	/**
	 * @return Returns the f_files.
	 */
	public long get_f_files()
	{
		return f_files;
	}

	/**
	 * @return Returns the f_flags.
	 */
	public int get_f_flags()
	{
		return f_flags;
	}

	/**
	 * @return Returns the f_fsid.
	 */
	public long get_f_fsid()
	{
		return f_fsid;
	}

	/**
	 * @return Returns the f_fstypename.
	 */
	public byte [] get_f_fstypename()
	{
		return f_fstypename;
	}

	/**
	 * @return Returns the f_iosize.
	 */
	public long get_f_iosize()
	{
		return f_iosize;
	}

	/**
	 * @return Returns the f_mntfromname.
	 */
	public byte [] get_f_mntfromname()
	{
		return f_mntfromname;
	}

	/**
	 * @return Returns the f_mntonname.
	 */
	public byte [] get_f_mntonname()
	{
		return f_mntonname;
	}

	/**
	 * @return Returns the f_oflags.
	 */
	public short get_f_oflags()
	{
		return f_oflags;
	}

	/**
	 * @return Returns the f_otype.
	 */
	public short get_f_otype()
	{
		return f_otype;
	}

	/**
	 * @return Returns the f_owner.
	 */
	public int get_f_owner()
	{
		return f_owner;
	}

	/**
	 * @return Returns the f_type.
	 */
	public int get_f_type()
	{
		return f_type;
	}
}
