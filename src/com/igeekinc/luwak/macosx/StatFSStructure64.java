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


public class StatFSStructure64 extends StatFS
{

	public static final int MFSTYPENAMELEN = 16;
	public static final int MAXPATHLEN = 1024;
	public static final int MNAMELEN = MAXPATHLEN;
	
	public static final int  f_bsizeOffset = 0;
	public static final int  f_iosizeOffset = 4;
	public static final int  f_blocksOffset = 8;
	public static final int  f_bfreeOffset = 16;
	public static final int  f_bavailOffset = 24;
	public static final int  f_filesOffset = 32;
	public static final int  f_ffreeOffset = 40;
	public static final int  f_fsidOffset = 48;
	public static final int  f_ownerOffset = 56;
	public static final int  f_typeOffset = 60; 
	public static final int  f_flagsOffset = 64;
	public static final int  f_fssubtypeOffset = 68;
	public static final int  f_fstypenameOffset = 72; //[MFSNAMELEN]; /* fs type name */
	public static final int  f_mntonnameOffset = 88; //[MNAMELEN];    /* directory on which mounted */
	public static final int  f_mntfromnameOffset = 1112; //[MNAMELEN];  /* mounted file system */
	public static final int  f_reserved3 = 2136; //;        /* reserved for future use */

	public static final int fsstatBufSize = 2168;
	
	public int f_bsize;
	public int f_iosize;
	public long f_blocks;
	public long f_bfree;
	public long f_bavail;
	public long f_files;
	public long f_ffree;
	public long f_fsid;
	public int f_owner;
	public int f_type;
	public int f_flags;
	public int f_fssubtype;
	public byte [] f_fstypename = new byte[MFSTYPENAMELEN];
	public byte [] f_mntonname = new byte[MAXPATHLEN];
	public byte [] f_mntfromname = new byte[MAXPATHLEN];
	public int [] f_reserved = new int[8];
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

	public int get_f_fssubtype()
	{
		return f_fssubtype;
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
