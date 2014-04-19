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

public class OSErrorCodes
{
  static public final int 	EPERM	;		/* Operation not permitted */
  static public final int 	ENOENT	;		/* No such file or directory */
  static public final int 	ESRCH	;		/* No such process */
  static public final int 	EINTR	;		/* Interrupted system call */
  static public final int 	EIO	;		/* Input/output error */
  static public final int 	ENXIO	;		/* Device not configured */
  static public final int 	E2BIG	;		/* Argument list too long */
  static public final int 	ENOEXEC	;		/* Exec format error */
  static public final int 	EBADF	;		/* Bad file descriptor */
  static public final int 	ECHILD	;		/* No child processes */
  static public final int 	EDEADLK	;		/* Resource deadlock avoided */
                                          /* 11 was EAGAIN */
  static public final int 	ENOMEM	;		/* Cannot allocate memory */
  static public final int 	EACCES	;		/* Permission denied */
  static public final int 	EFAULT	;		/* Bad address */
  static public final int 	ENOTBLK	;		/* Block device required */
  static public final int 	EBUSY	;		/* Device busy */
  static public final int 	EEXIST	;		/* File exists */
  static public final int 	EXDEV	;		/* Cross-device link */
  static public final int 	ENODEV	;		/* Operation not supported by device */
  static public final int 	ENOTDIR	;		/* Not a directory */
  static public final int 	EISDIR	;		/* Is a directory */
  static public final int 	EINVAL	;		/* Invalid argument */
  static public final int 	ENFILE	;		/* Too many open files in system */
  static public final int 	EMFILE	;		/* Too many open files */
  static public final int 	ENOTTY	;		/* Inappropriate ioctl for device */
  static public final int 	ETXTBSY	;		/* Text file busy */
  static public final int 	EFBIG	;		/* File too large */
  static public final int 	ENOSPC	;		/* No space left on device */
  static public final int 	ESPIPE	;		/* Illegal seek */
  static public final int 	EROFS	;		/* Read-only file system */
  static public final int 	EMLINK	;		/* Too many links */
  static public final int 	EPIPE	;		/* Broken pipe */

  /* math software */
  static public final int 	EDOM	;		/* Numerical argument out of domain */
  static public final int 	ERANGE	;		/* Result too large */

  /* non-blocking and interrupt i/o */
  static public final int 	EAGAIN	;		/* Resource temporarily unavailable */
  static public final int 	EWOULDBLOCK;		/* Operation would block */

  /* ipc/network software -- argument errors */
  static public final int 	ENOTSOCK	;		/* Socket operation on non-socket */
  static public final int 	EDESTADDRREQ	;		/* Destination address required */
  static public final int 	EMSGSIZE	;		/* Message too long */
  static public final int 	EPROTOTYPE	;		/* Protocol wrong type for socket */
  static public final int 	ENOPROTOOPT	;		/* Protocol not available */
  static public final int 	EPROTONOSUPPORT	;		/* Protocol not supported */
  static public final int 	ESOCKTNOSUPPORT	;		/* Socket type not supported */
  static public final int 	EPFNOSUPPORT	;		/* Protocol family not supported */
  static public final int 	EAFNOSUPPORT	;		/* Address family not supported by protocol family */
  static public final int 	EADDRINUSE	;		/* Address already in use */
  static public final int 	EADDRNOTAVAIL	;		/* Can't assign requested address */

  /* ipc/network software -- operational errors */
  static public final int 	ENETDOWN	;		/* Network is down */

  static public final int 	ELOOP		;		/* Too many levels of symbolic links */
  static public final int 	ENAMETOOLONG	;		/* File name too long */

  /* should be rearranged */

  static public final int 	ENOTEMPTY;		/* Directory not empty */

  /* quotas & mush */

  static public final int 	EUSERS;		/* Too many users */

  /* Network File System */

  static public final int 	EREMOTE;		/* Too many levels of remote in path */

  static public final int 	ENOLCK;		/* No locks available */
  static public final int 	ENOSYS;		/* Function not implemented */

  static public final int 	EOVERFLOW;		/* Value too large to be stored in data type */
  
  static public final int	ENOATTR;		/* Extended attribute does not exist in file */
  static
  {
      switch (LibC.getSystemType())
      {
      case kMacOSX:
         EPERM   =   1;      /* Operation not permitted */
         ENOENT  =   2;      /* No such file or directory */
         ESRCH   =   3;      /* No such process */
         EINTR   =   4;      /* Interrupted system call */
         EIO =   5;      /* Input/output error */
         ENXIO   =   6;      /* Device not configured */
         E2BIG   =   7;      /* Argument list too long */
         ENOEXEC =   8;      /* Exec format error */
         EBADF   =   9;      /* Bad file descriptor */
         ECHILD  =   10;     /* No child processes */
         EDEADLK =   11;     /* Resource deadlock avoided */
                                              /* 11 was EAGAIN */
         ENOMEM  =   12;     /* Cannot allocate memory */
         EACCES  =   13;     /* Permission denied */
         EFAULT  =   14;     /* Bad address */
         ENOTBLK =   15;     /* Block device required */
         EBUSY   =   16;     /* Device busy */
         EEXIST  =   17;     /* File exists */
         EXDEV   =   18;     /* Cross-device link */
         ENODEV  =   19;     /* Operation not supported by device */
         ENOTDIR =   20;     /* Not a directory */
         EISDIR  =   21;     /* Is a directory */
         EINVAL  =   22;     /* Invalid argument */
         ENFILE  =   23;     /* Too many open files in system */
         EMFILE  =   24;     /* Too many open files */
         ENOTTY  =   25;     /* Inappropriate ioctl for device */
         ETXTBSY =   26;     /* Text file busy */
         EFBIG   =   27;     /* File too large */
         ENOSPC  =   28;     /* No space left on device */
         ESPIPE  =   29;     /* Illegal seek */
         EROFS   =   30;     /* Read-only file system */
         EMLINK  =   31;     /* Too many links */
         EPIPE   =   32;     /* Broken pipe */

      /* math software */
         EDOM    =   33;     /* Numerical argument out of domain */
         ERANGE  =   34;     /* Result too large */

      /* non-blocking and interrupt i/o */
         EAGAIN  =   35;     /* Resource temporarily unavailable */
         EWOULDBLOCK =   EAGAIN;     /* Operation would block */

      /* ipc/network software -- argument errors */
         ENOTSOCK    =38;        /* Socket operation on non-socket */
         EDESTADDRREQ    =39;        /* Destination address required */
         EMSGSIZE    =40;        /* Message too long */
         EPROTOTYPE  =41;        /* Protocol wrong type for socket */
         ENOPROTOOPT =42;        /* Protocol not available */
         EPROTONOSUPPORT =43;        /* Protocol not supported */
         ESOCKTNOSUPPORT =44;        /* Socket type not supported */

         EPFNOSUPPORT    =46;        /* Protocol family not supported */
         EAFNOSUPPORT    =47;        /* Address family not supported by protocol family */
         EADDRINUSE  =48;        /* Address already in use */
         EADDRNOTAVAIL   =49;        /* Can't assign requested address */

      /* ipc/network software -- operational errors */
         ENETDOWN    =50;        /* Network is down */

         ELOOP       =62;        /* Too many levels of symbolic links */
         ENAMETOOLONG    =63;        /* File name too long */

      /* should be rearranged */

         ENOTEMPTY   =66;        /* Directory not empty */

      /* quotas & mush */


         EUSERS      =68;        /* Too many users */


      /* Network File System */
         EREMOTE     =71;        /* Too many levels of remote in path */

         ENOLCK      =77;        /* No locks available */
         ENOSYS      =78;        /* Function not implemented */



         EOVERFLOW   =84;        /* Value too large to be stored in data type */
         ENOATTR	 =93;		/* Extended attribute does not exist in file */
         break;
      case kLinux:
      
             EPERM   =   1;      /* Operation not permitted */
             ENOENT  =   2;      /* No such file or directory */
             ESRCH   =   3;      /* No such process */
             EINTR   =   4;      /* Interrupted system call */
             EIO     =   5;      /* Input/output error */
             ENXIO   =   6;      /* Device not configured */
             E2BIG   =   7;      /* Argument list too long */
             ENOEXEC =   8;      /* Exec format error */
             EBADF   =   9;      /* Bad file descriptor */
             ECHILD  =   10;     /* No child processes */
             EAGAIN  =   11;      /* Try again */
             ENOMEM  =   12;     /* Cannot allocate memory */
             EACCES  =   13;     /* Permission denied */
             EFAULT  =   14;     /* Bad address */
             ENOTBLK =   15;     /* Block device required */
             EBUSY   =   16;     /* Device busy */
             EEXIST  =   17;     /* File exists */
             EXDEV   =   18;     /* Cross-device link */
             ENODEV  =   19;     /* Operation not supported by device */
             ENOTDIR =   20;     /* Not a directory */
             EISDIR  =   21;     /* Is a directory */
             EINVAL  =   22;     /* Invalid argument */
             ENFILE  =   23;     /* Too many open files in system */
             EMFILE  =   24;     /* Too many open files */
             ENOTTY  =   25;     /* Inappropriate ioctl for device */
             ETXTBSY =   26;     /* Text file busy */
             EFBIG   =   27;     /* File too large */
             ENOSPC  =   28;     /* No space left on device */
             ESPIPE  =   29;     /* Illegal seek */
             EROFS   =   30;     /* Read-only file system */
             EMLINK  =   31;     /* Too many links */
             EPIPE   =   32;     /* Broken pipe */

          /* math software */
             EDOM    =   33;     /* Numerical argument out of domain */
             ERANGE  =   34;     /* Result too large */

             EDEADLK =   35; /* Resource deadlock would occur */
             ENAMETOOLONG =  36; /* File name too long */
             ENOLCK =    37; /* No record locks available */
             ENOSYS =    38; /* Function not implemented */
             ENOTEMPTY = 39; /* Directory not empty */
             ELOOP    =  40; /* Too many symbolic links encountered */
             EWOULDBLOCK =   EAGAIN; /* Operation would block */
             
             EREMOTE  =  66; /* Object is remote */
             
             EOVERFLOW = 75; /* Value too large for defined data type */

             EUSERS   =  87; /* Too many users */
             ENOTSOCK =  88; /* Socket operation on non-socket */
             EDESTADDRREQ =  89; /* Destination address required */
             EMSGSIZE =  90; /* Message too long */
             EPROTOTYPE  =   91; /* Protocol wrong type for socket */
             ENOPROTOOPT =   92; /* Protocol not available */
             EPROTONOSUPPORT =   93; /* Protocol not supported */
             ESOCKTNOSUPPORT =   94; /* Socket type not supported */

             EPFNOSUPPORT =  96; /* Protocol family not supported */
             EAFNOSUPPORT =  97; /* Address family not supported by protocol */
             EADDRINUSE =    98; /* Address already in use */
             EADDRNOTAVAIL = 99; /* Cannot assign requested address */
             ENETDOWN =  100;    /* Network is down */
             
             ENOATTR = ENOENT;	/* Extended attribute does not exist in file */
             break;
      default:
          throw new InternalError("Unrecognized OS");
      }
  }
  public OSErrorCodes()
  {
  }
}