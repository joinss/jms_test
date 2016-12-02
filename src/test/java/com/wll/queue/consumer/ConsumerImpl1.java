package com.wll.queue.consumer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ConsumerImpl1 implements IConsumer1 {
	
	private ConnectionFactory cf;
	private Connection conn;
	private Session session;
	private MessageConsumer mc;
	private Destination queue;
	
	public static final String url="tcp://123.57.207.171:61616";
	public static final String username=ActiveMQConnectionFactory.DEFAULT_USER;
	public static final String pass=ActiveMQConnectionFactory.DEFAULT_PASSWORD;

	public void init() {
		cf=new ActiveMQConnectionFactory(username,pass,url);
		try{
			conn=cf.createConnection();
			session=conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		}catch(JMSException e){
			e.printStackTrace();
		}
	}

	public String consumerDo(String name) {
		String buf=null;
		try {
			queue=session.createQueue(name);
			mc=session.createConsumer(queue);
			
			while(true){
				TextMessage tm=(TextMessage) mc.receive();
				if(tm!=null){
					System.out.println(tm.getText());
					buf=tm.getText();
				}else{
					break;
				}
			}
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buf;
	}

}
