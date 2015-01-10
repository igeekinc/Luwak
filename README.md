Luwak
=====

Luwak is yet another FUSE interface for Java.  Why create yet another FUSE Java interface?  Other projects were largely dead or 
relied too much on native code or didn't give a good Java style interface.

Luwak is almost pure Java.  There is OS specific glue code and a native class that calls libc via JNA.  
Luwak currently supports Linux and OS X.

Getting Started
---------------
To build Luwak you will need the Luwak project and the iGeekCommon project.  These can both be checked out from GitHub.

`cd Luwak`

`ant`

The output will be in output/lib/Luwak.jar

The examples will be in output/lib/LuwakExamples.jar