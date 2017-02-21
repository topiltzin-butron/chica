package xyz.cafeconleche.web.chica.entity;

import java.io.Serializable;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table(value = "user_roles")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PrimaryKeyColumn(name = "username", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
	private String username;

	@Column(value = "password")
	private String password;
	
	@Column(value = "sign_in_provider")
	private String signInProvider;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSignInProvider() {
		return signInProvider;
	}

	public void setSignInProvider(String signInProvider) {
		this.signInProvider = signInProvider;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(super.toString());
		sb.append("username: ").append(username).append(", ");
		sb.append("password: ").append("[PROTECTED]").append(", ");
		sb.append("signInProvider: ").append(signInProvider);
		return sb.toString();
	}

}
