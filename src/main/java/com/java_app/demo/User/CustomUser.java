package com.java_app.demo.User;

import com.java_app.demo.ApiKey.ApiKey;
import com.java_app.demo.Country.Country;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity()
@Table(name = "custom_user", schema = "nearby_finder")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomUser implements UserDetails {
    
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @Id
    private Integer id;
        
    private Collection<? extends GrantedAuthority> authorities;

    private String email;

    private String displayName;

    private String password;

    private String username;

    private Boolean enabled;

    private Boolean accountNonExpired;

    @OneToMany(mappedBy = "customUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ApiKey> api_keySet = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_country",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    private Set<Country> associatedCountries = new HashSet<>();

    private Boolean accountNonLocked;

    private boolean credentialsNonExpired;

    public CustomUser(String email, String displayName, String username, String password, Boolean enabled, Collection<? extends GrantedAuthority> authorities) {
        this.email = email;
        this.displayName = displayName;
        this.enabled=enabled;
        this.username=username;
        this.password=password;
        this.accountNonExpired=true;
        this.accountNonLocked=true;
        this.credentialsNonExpired=true;
        this.authorities=authorities;
    }

    public void eraseCredentials(){
        this.password=null;
    }

}
