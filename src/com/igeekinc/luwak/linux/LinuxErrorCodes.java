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

import com.igeekinc.luwak.OSErrorCodes;

/**
 * These error codes are unique to Linux
 * @author David L. Smith-Uchida
 */
public class LinuxErrorCodes extends OSErrorCodes
{
    static public final int ENOMSG   =  42; /* No message of desired type */
    static public final int EIDRM    =  43; /* Identifier removed */
    static public final int ECHRNG   =  44; /* Channel number out of range */
    static public final int EL2NSYNC =  45; /* Level 2 not synchronized */
    static public final int EL3HLT   =  46; /* Level 3 halted */
    static public final int EL3RST   =  47; /* Level 3 reset */
    static public final int ELNRNG   =  48; /* Link number out of range */
    static public final int EUNATCH  =  49; /* Protocol driver not attached */
    static public final int ENOCSI   =  50; /* No CSI structure available */
    static public final int EL2HLT   =  51; /* Level 2 halted */
    static public final int EBADE    =  52; /* Invalid exchange */
    static public final int EBADR    =  53; /* Invalid request descriptor */
    static public final int EXFULL   =  54; /* Exchange full */
    static public final int ENOANO   =  55; /* No anode */
    static public final int EBADRQC  =  56; /* Invalid request code */
    static public final int EBADSLT  =  57; /* Invalid slot */

    static public final int EDEADLOCK = EDEADLK;

    static public final int EBFONT   =  59; /* Bad font file format */
    static public final int ENOSTR   =  60; /* Device not a stream */
    static public final int ENODATA  =  61; /* No data available */
    static public final int ETIME    =  62; /* Timer expired */
    static public final int ENOSR    =  63; /* Out of streams resources */
    static public final int ENONET   =  64; /* Machine is not on the network */
    static public final int ENOPKG   =  65; /* Package not installed */
    
    static public final int ENOLINK  =  67; /* Link has been severed */
    static public final int EADV     =  68; /* Advertise error */
    static public final int ESRMNT   =  69; /* Srmount error */
    static public final int ECOMM    =  70; /* Communication error on send */
    static public final int EPROTO   =  71; /* Protocol error */
    static public final int EMULTIHOP = 72; /* Multihop attempted */
    static public final int EDOTDOT  =  73; /* RFS specific error */
    static public final int EBADMSG  =  74; /* Not a data message */
    static public final int ENOTUNIQ =  76; /* Name not unique on network */
    static public final int EBADFD   =  77; /* File descriptor in bad state */
    static public final int EREMCHG  =  78; /* Remote address changed */
    static public final int ELIBACC  =  79; /* Can not access a needed shared library */
    static public final int ELIBBAD  =  80; /* Accessing a corrupted shared library */
    static public final int ELIBSCN  =  81; /* .lib section in a.out corrupted */
    static public final int ELIBMAX  =  82; /* Attempting to link in too many shared libraries */
    static public final int ELIBEXEC =  83; /* Cannot exec a shared library directly */
    static public final int EILSEQ   =  84; /* Illegal byte sequence */
    static public final int ERESTART =  85; /* Interrupted system call should be restarted */
    static public final int ESTRPIPE =  86; /* Streams pipe error */
    
    static public final int EOPNOTSUPP =    95; /* Operation not supported on transport endpoint */
    
    static public final int   ENETUNREACH =   101;    /* Network is unreachable */
    static public final int   ENETRESET = 102;    /* Network dropped connection because of reset */
    static public final int   ECONNABORTED =  103;    /* Software caused connection abort */
    static public final int   ECONNRESET =    104;    /* Connection reset by peer */
    static public final int   ENOBUFS  =  105;    /* No buffer space available */
    static public final int   EISCONN  =  106;    /* Transport endpoint is already connected */
    static public final int   ENOTCONN =  107;    /* Transport endpoint is not connected */
    static public final int   ESHUTDOWN = 108;    /* Cannot send after transport endpoint shutdown */
    static public final int   ETOOMANYREFS =  109;    /* Too many references: cannot splice */
    static public final int   ETIMEDOUT = 110;    /* Connection timed out */
    static public final int   ECONNREFUSED =  111;    /* Connection refused */
    static public final int   EHOSTDOWN = 112;    /* Host is down */
    static public final int   EHOSTUNREACH =  113;    /* No route to host */
    static public final int   EALREADY =  114;    /* Operation already in progress */
    static public final int   EINPROGRESS =   115;    /* Operation now in progress */
    static public final int   ESTALE   =  116;    /* Stale NFS file handle */
    static public final int   EUCLEAN  =  117;    /* Structure needs cleaning */
    static public final int   ENOTNAM  =  118;    /* Not a XENIX named type file */
    static public final int   ENAVAIL  =  119;    /* No XENIX semaphores available */
    static public final int   EISNAM   =  120;    /* Is a named type file */
    static public final int   EREMOTEIO = 121;    /* Remote I/O error */
    static public final int   EDQUOT   =  122;    /* Quota exceeded */

    static public final int   ENOMEDIUM = 123;    /* No medium found */
    static public final int   EMEDIUMTYPE =   124;    /* Wrong medium type */
    static public final int   ECANCELED = 125;    /* Operation Canceled */
    static public final int   ENOKEY   =  126;    /* Required key not available */
    static public final int   EKEYEXPIRED =   127;    /* Key has expired */
    static public final int   EKEYREVOKED =   128;    /* Key has been revoked */
    static public final int   EKEYREJECTED =  129;    /* Key was rejected by service */

  /* for robust mutexes */
    static public final int   EOWNERDEAD =    130;    /* Owner died */
    static public final int   ENOTRECOVERABLE =   131;    /* State not recoverable */

    static public final int ERFKILL    =  132;    /* Operation not possible due to RF-kill */
}
