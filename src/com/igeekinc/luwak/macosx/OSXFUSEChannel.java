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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.igeekinc.luwak.FUSEChannel;
import com.igeekinc.luwak.LibC;
import com.igeekinc.luwak.Stat;
import com.igeekinc.util.logging.ErrorLogMessage;
import com.sun.jna.Native;

public class OSXFUSEChannel extends FUSEChannel
{
    public static final int kOSXFUSE_NDEVICES = 24;
    
    public OSXFUSEChannel(String baseName) throws FileNotFoundException
    {
        // For OSX, there are multiple device names.  We bang along them until one opens or we run out of devices
        
        for (int curDevNum = 0; curDevNum < kOSXFUSE_NDEVICES; curDevNum++)
        {
            File curFile = new File(baseName+curDevNum);
            if (curFile.exists())
            {
                try
                {
                    openFile(curFile);
                    return; // Found one
                } catch (FileNotFoundException e)
                {
                    Logger.getLogger(getClass()).error(new ErrorLogMessage("Caught exception"), e);
                } catch (InternalError e)
                {
                    Logger.getLogger(getClass()).error(new ErrorLogMessage("Caught exception"), e);
                }
            }
        }
        throw new FileNotFoundException("Could not find suitable device with basename of "+baseName);
    }
    
    public long getRDev() throws IOException
    {
        Stat statStructure = LibC.newStat();
        int fstatStatus = LibC.fstat(getFDNum(), statStructure);
        if (fstatStatus != 0)
        {
            throw new IOException("Could not fstat device errno = "+Native.getLastError());
        }
        return statStructure.get_st_rdev();
    }
}
