package com.dituhui.saas.alan.a.in;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface CustomSink {

	String INPUT = "input";

	@Input(INPUT)
	SubscribableChannel input();

	// String INPUT1 = "input1";
	// @Input(INPUT1)
	// SubscribableChannel input1();

	// String INPUT2 = "input2";
	// @Input(INPUT2)
	// SubscribableChannel input2();

}
