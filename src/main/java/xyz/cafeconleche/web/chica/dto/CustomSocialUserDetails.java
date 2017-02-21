package xyz.cafeconleche.web.chica.dto;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUser;

import xyz.cafeconleche.web.chica.enums.Role;
import xyz.cafeconleche.web.chica.enums.SocialMediaService;

public class CustomSocialUserDetails extends SocialUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
    private SocialMediaService socialMediaService;
    
    public CustomSocialUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public SocialMediaService getSocialMediaService() {
		return socialMediaService;
	}

	public void setSocialMediaService(SocialMediaService socialMediaService) {
		this.socialMediaService = socialMediaService;
	}
    
	public static class Builder {

		private long id;
        private String username;
        private String firstName;
        private String lastName;
        private String password;
        private Role role;
        private SocialMediaService socialSignInProvider;
        private Set<GrantedAuthority> authorities;

        public Builder() {
            this.authorities = new HashSet<>();
        }
        
        public Builder id(long id) {
        	this.id = id;
        	return this;
        }
        
        public Builder username(String username) {
            this.username = username;
            return this;
        }


        
        public Builder password(String password) {
            if (password == null) {
                password = "SocialUser";
            }
            this.password = password;
            return this;
        }
        
        public Builder role(Role role) {
            this.role = role;
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.toString());
            this.authorities.add(authority);
            return this;
        }
        
        public Builder socialSignInProvider(SocialMediaService socialSignInProvider) {
            this.socialSignInProvider = socialSignInProvider;
            return this;
        }
        
        public CustomSocialUserDetails build() {
        	CustomSocialUserDetails user = new CustomSocialUserDetails(username, password, authorities);
        	user.id = id;
            user.socialMediaService = socialSignInProvider;
            return user;
        }
        
	}

}
