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

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class msghdr extends Structure
{
    public String msg_name;             /* Address to send to/receive from.  */
    public int msg_namelen;      /* Length of address data.  */

    public Pointer msg_iov;      /* Vector of data to send/receive into.  */
    public int msg_iovlen;          /* Number of elements in the vector.  */

    public Pointer msg_control;          /* Ancillary data (eg BSD filedesc passing). */
    public int msg_controllen;      /* Ancillary data buffer length.
                                   !! The type should be socklen_t but the
                                   definition of the kernel is incompatible
                                   with this.  */

    public int msg_flags;              /* Flags on received message.  */
}

