package com.dubbo.server.service.impl;
import com.alibaba.dubbo.rpc.RpcContext;
import com.dubbo.server.service.ServerService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerServiceImpl implements ServerService {

	public String sayHello(String name) {
		System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
		return "Hello " + name + ", response form provider: " + RpcContext.getContext().getLocalAddress();
	}

}