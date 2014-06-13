package se.connection.arquillian.mqseries.tomee.remote;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class MailTest {

	@Deployment
	public static Archive<?> createDeploymentPackage() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@Test
	public void mail() {
		
	}
}
