package com.wll.queue.provider;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ProviderImpl implements IProvider {

	ConnectionFactory cf;
	Connection conn;
	Session session;
	MessageProducer mp;
	
	public void init() {
		cf=new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,ActiveMQConnectionFactory.DEFAULT_PASSWORD,ActiveMQConnectionFactory.DEFAULT_BROKER_URL);
		try {
			conn=cf.createConnection();
			conn.start();
			session=conn.createSession(true,Session.SESSION_TRANSACTED);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public String providerDo(String name, String message) {
		try {
			Queue queue=session.createQueue(name);
			mp=session.createProducer(queue);
			TextMessage tm=session.createTextMessage(message);
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
		return "ok";
	}

}
