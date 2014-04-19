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

import com.igeekinc.luwak.OSErrorCodes;

public class MacOSXErrorCodes extends OSErrorCodes
{
    static public final int EINPROGRESS =   36;     /* Operation now in progress */
    static public final int EALREADY =  37;     /* Operation already in progress */
    
    static public final int ENOTSUP = 45;    /* Operation not supported */
    static public final int EOPNOTSUPP   =ENOTSUP;      /* Operation not supported */
    
    static public final int ENETUNREACH =51;        /* Network is unreachable */
    static public final int ENETRESET   =52;        /* Network dropped connection on reset */
    static public final int ECONNABORTED    =53;        /* Software caused connection abort */
    static public final int ECONNRESET  =54;        /* Connection reset by peer */
    static public final int ENOBUFS     =55;        /* No buffer space available */
    static public final int EISCONN     =56;        /* Socket is already connected */
    static public final int ENOTCONN    =57;        /* Socket is not connected */
    static public final int ESHUTDOWN   =58;        /* Can't send after socket shutdown */
    static public final int ETOOMANYREFS    =59;        /* Too many references: can't splice */
    static public final int ETIMEDOUT   =60;        /* Operation timed out */
    static public final int ECONNREFUSED    =61;        /* Connection refused */
    
    static public final int EHOSTDOWN   =64;        /* Host is down */
    static public final int EHOSTUNREACH    =65;        /* No route to host */

    static public final int EPROCLIM    =67;        /* Too many processes */
    static public final int EDQUOT      =69;        /* Disc quota exceeded */
    

    static public final int ESTALE      =70;        /* Stale NFS file handle */
    
    static public final int EBADRPC     =72;        /* RPC struct is bad */
    static public final int ERPCMISMATCH    =73;        /* RPC version wrong */
    static public final int EPROGUNAVAIL    =74;        /* RPC prog. not avail */
    static public final int EPROGMISMATCH   =75;        /* Program version wrong */
    static public final int EPROCUNAVAIL    =76;        /* Bad procedure for program */
    
    static public final int EFTYPE      =79;        /* Inappropriate file type or format */
    static public final int EAUTH       =80;        /* Authentication error */
    static public final int ENEEDAUTH   =81;        /* Need authenticator */

 /* Intelligent device errors */
    static public final int EPWROFF     =82;    /* Device power is off */
    static public final int EDEVERR     =83;    /* Device error, e.g. paper out */
    
    /* Program loading errors */
    static public final int EBADEXEC =85;    /* Bad executable */
    static public final int EBADARCH =86;    /* Bad CPU type in executable */
    static public final int ESHLIBVERS   =87;    /* Shared library version mismatch */
    static public final int EBADMACHO    =88;    /* Malformed Macho file */

    static public final int ECANCELED   =89;        /* Operation canceled */
}
