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

import com.sun.jna.Structure;

public abstract class StatFS extends Structure
{
    public abstract long get_f_bavail();
    /**
     * @return Returns the f_bfree.
     */
    public abstract long get_f_bfree();
    /**
     * @return Returns the f_blocks.
     */
    public abstract long get_f_blocks();
    /**
     * @return Returns the f_bsize.
     */
    public abstract int get_f_bsize();
    /**
     * @return Returns the f_ffree.
     */
    public abstract long get_f_ffree();
    /**
     * @return Returns the f_files.
     */
    public abstract long get_f_files();
    /**
     * @return Returns the f_flags.
     */
    public abstract int get_f_flags();
    /**
     * @return Returns the f_fsid.
     */
    public abstract long get_f_fsid();
    /**
     * @return Returns the f_fstypename.
     */
    public abstract byte [] get_f_fstypename();
    /**
     * @return Returns the f_iosize.
     */
    public abstract long get_f_iosize();
    /**
     * @return Returns the f_mntfromname.
     */
    public abstract byte [] get_f_mntfromname();
    /**
     * @return Returns the f_mntonname.
     */
    public abstract byte [] get_f_mntonname();
    /**
     * @return Returns the f_owner.
     */
    public abstract int get_f_owner();
    /**
     * @return Returns the f_type.
     */
    public abstract int get_f_type();
}
