package xyz.cafeconleche.web.chica;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class CassandraTest {

	public static void main(String[] args) {
	
//		BasicConfigurator.configure();
		Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build(); 
		System.out.println("\nCluster name: " + cluster.getClusterName()+"\n");
		Session session = cluster.connect();
		
		session.execute("SLECT * FROM GREETINGS");
		
		session.close();
	}
	
}
