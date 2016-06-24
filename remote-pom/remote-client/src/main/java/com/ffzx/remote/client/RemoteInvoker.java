package com.ffzx.remote.client;

public interface RemoteInvoker {



	public <T> T send(InternalRequest request);
}
