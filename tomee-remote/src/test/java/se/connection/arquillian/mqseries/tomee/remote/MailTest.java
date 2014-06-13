package se.connection.arquillian.mqseries.tomee.remote;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import junit.framework.Assert;

@RunWith(Arquillian.class)
public class MailTest {

	@Deployment
	public static Archive<?> createDeploymentPackage() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@Test
	public void mail() {
		Connection qc = null;
		Session qs = null;
		MessageProducer sender = null;
		
		try {
			InitialContext ctx = new InitialContext();
			ConnectionFactory qcf = (ConnectionFactory) ctx.lookup("java:openejb/Resource/qcf");
			Queue queue = (Queue) ctx.lookup("java:openejb/Resource/queue");
			
			qc = qcf.createConnection();
			qs = qc.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			sender = qs.createProducer(queue);
			
			BytesMessage message = qs.createBytesMessage();
			message.writeBytes(new String("Hello").getBytes());
			
			sender.send(message);
		} catch (NamingException e) {
			Assert.fail();
		} catch (JMSException e) {
			Assert.fail();
		}
		finally {
			if (sender != null) {
				try {
					sender.close();
				} catch (JMSException e) {
					Assert.fail();
				}
			}
			if (qs != null) {
				try {
					qs.close();
				} catch (JMSException e) {
					Assert.fail();
				}
			}
			if (qc != null) {
				try {
					qc.close();
				} catch (JMSException e) {
					Assert.fail();
				}
			}
		}
	}
}
