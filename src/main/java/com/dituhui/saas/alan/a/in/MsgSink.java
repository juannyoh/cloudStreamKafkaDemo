package com.dituhui.saas.alan.a.in;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.dituhui.saas.websocket.LogCacheUtils;

@EnableBinding(CustomSink.class)
public class MsgSink {

	@StreamListener(CustomSink.INPUT)
	public void messageSink1(Object payload) {
		LogCacheUtils.put(payload.toString());
		System.out.println("Received1: " + payload);
	}
	
//	@StreamListener(CustomSink.INPUT2)
//	public void messageSink2(Object payload) {
//		LogCacheUtils.put(payload.toString());
//		System.out.println("Received2: " + payload);
//		
//	}
}
