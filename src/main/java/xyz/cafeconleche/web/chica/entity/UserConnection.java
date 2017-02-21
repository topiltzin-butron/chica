package xyz.cafeconleche.web.chica.entity;

import java.io.Serializable;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table(value = "user_connection")
public class UserConnection implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@PrimaryKeyColumn(name = "username", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private String username;
	
	@PrimaryKeyColumn(name = "providerId", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
	private String providerId;
	
	@PrimaryKeyColumn(name = "providerUserId", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
	private String providerUserId;

	@Column(value = "rank")
	private int rank;
	
	@Column(value = "displayName")
	private String displayName;
	
	@Column(value = "profileUrl")
	private String profileUrl;
	
	@Column(value = "imageUrl")
	private String imageUrl;
	
	@Column(value = "accessToken")
	private String accessToken;
	
	@Column(value = "secret")
	private String secret;
	
	@Column(value = "refreshToken")
	private String refreshToken;
	
	@Column(value = "expireTime")
	private long expireTime;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getProviderUserId() {
		return providerUserId;
	}

	public void setProviderUserId(String providerUserId) {
		this.providerUserId = providerUserId;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}
	
}
