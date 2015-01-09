package com.igeekinc.luwak.examples.loopback;

import com.igeekinc.luwak.util.FUSEHandleManager;

class LoopbackHandleManager extends FUSEHandleManager<LoopbackFileHandle, LoopbackDirHandle>
{

	@Override
	protected LoopbackDirHandle allocateDirHandle(long handleNum)
	{
		return new LoopbackDirHandle(handleNum);
	}

	@Override
	protected LoopbackFileHandle allocateFileHandle(long handleNum)
	{
		return new LoopbackFileHandle(handleNum);
	}

}