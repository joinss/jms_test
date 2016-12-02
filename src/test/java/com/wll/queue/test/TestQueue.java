package com.wll.queue.test;

import org.junit.Test;

import com.wll.queue.consumer.ConsumerImpl1;
import com.wll.queue.consumer.IConsumer1;
import com.wll.queue.provider.IProvider1;
import com.wll.queue.provider.ProviderImpl1;

public class TestQueue {

	@Test
	public void queueP(){
		IProvider1 provider=new ProviderImpl1();
		provider.init();
		provider.providerDo("queue1");
	}
	@Test
	public void queueC(){
		IConsumer1 consumer=new ConsumerImpl1();
		consumer.init();
		consumer.consumerDo("queue1");
	}
}
