package com.wll.queue.consumer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ConsumerImpl implements IConsumer {
	
	ConnectionFactory cf;
	Connection conn;
	Session session;
	MessageConsumer mc;
	
	public void init() {
		cf=new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,ActiveMQConnectionFactory.DEFAULT_PASSWORD,ActiveMQConnectionFactory.DEFAULT_BROKER_URL);
		try {
			conn=cf.createConnection(ActiveMQConnectionFactory.DEFAULT_USER,ActiveMQConnectionFactory.DEFAULT_PASSWORD);
			conn.start();
			session=conn.createSession(false, Session.CLIENT_ACKNOWLEDGE);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String consumerDo(String name) {
		Queue queue;
		try {
			queue = session.createQueue(name);
			mc=session.createConsumer(queue);
			while(true){
				TextMessage tm=(TextMessage) mc.receive();
				if(tm!=null){
					System.out.println(tm.getText());
					tm.acknowledge();
				}else{
					break;
				}
			}
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
