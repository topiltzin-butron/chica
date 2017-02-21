package xyz.cafeconleche.web.chica.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

import com.datastax.driver.core.utils.UUIDs;

@Table(value="greetings")
public class Greeting implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PrimaryKeyColumn(name="id", ordinal=1, type=PrimaryKeyType.PARTITIONED) // TODO check why
	private UUID id = UUIDs.timeBased();
	
	@PrimaryKeyColumn(name="user", ordinal=0, type=PrimaryKeyType.CLUSTERED)
	private String user;
	
	@Column(value="greet")
	private String greet;
	
	@Column(value = "creation_date")
	private Date creationDate;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getGreet() {
		return greet;
	}

	public void setGreet(String greet) {
		this.greet = greet;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(super.toString());
		sb.append("id: ").append(id);
		sb.append(", user: ").append(user);
		sb.append(", greet: ").append(greet);
		sb.append(", creationDate: ").append(creationDate);
		return  sb.toString();
	}
	

}
