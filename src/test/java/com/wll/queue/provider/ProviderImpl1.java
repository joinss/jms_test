package com.wll.queue.provider;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ProviderImpl1 implements IProvider1 {
	
	private ConnectionFactory cf;
	private Connection conn;
	private Session session;
	private Destination queue;
	private MessageProducer mp;
	public static final String url="tcp://123.57.207.171:61616";
	public static final String url1=ActiveMQConnectionFactory.DEFAULT_BROKER_URL;
	public static final String username=ActiveMQConnectionFactory.DEFAULT_USER;
	public static final String pass=ActiveMQConnectionFactory.DEFAULT_PASSWORD;

	public void init() {
		cf=new ActiveMQConnectionFactory(username,pass,url);
		try {
			conn=cf.createConnection();
			conn.start();
			session=conn.createSession(true, Session.SESSION_TRANSACTED);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public String providerDo(String name) {
		try {
			queue=session.createQueue(name);
			mp=session.createProducer(queue);
			TextMessage tm=session.createTextMessage("this is a product message!!!!!");
			mp.send(tm);
			session.commit();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(conn!=null){
				try {
					conn.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("ok");
		return "ok";
	}

}
