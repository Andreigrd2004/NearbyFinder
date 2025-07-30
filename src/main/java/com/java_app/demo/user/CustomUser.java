package com.java_app.demo.user;

import com.java_app.demo.apikey.model.ApiKey;
import com.java_app.demo.country.Country;
import jakarta.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity()
@Table(name = "custom_user", schema = "nearby_finder")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomUser implements UserDetails {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Integer id;

  private String email;

  private String displayName;

  private String password;

  private String username;

  private Boolean enabled;

  private Boolean accountNonExpired;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(
      name = "user_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      schema = "nearby_finder")
  @Column(name = "role_name")
  private Set<String> roles = new HashSet<>();

  @OneToMany(mappedBy = "customUser", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<ApiKey> api_keySet = new HashSet<>();

  @ManyToMany
  @JoinTable(
      name = "user_country",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "country_id"))
  private Set<Country> associatedCountries = new HashSet<>();

  private Boolean accountNonLocked;

  private boolean credentialsNonExpired;

  public void eraseCredentials() {
    this.password = null;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles.stream()
        .map(
            roleString ->
                new SimpleGrantedAuthority("ROLE_" + roleString))
        .collect(Collectors.toList());
  }
}
