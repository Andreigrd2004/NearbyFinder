package com.java_app.demo.location;

import com.java_app.demo.user.CustomUser;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "country", schema = "nearby_finder")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Country {
  @Id
  @SequenceGenerator(name = "country_seq", sequenceName = "country_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "country_seq")
  private Integer id;

  private String name;

  private String currency;

  @ManyToMany(mappedBy = "associatedCountries")
  Set<CustomUser> associatedCustomUsers = new HashSet<>();

  private String country_code;

  @Override
  public String toString() {
    return "Country{" + "id=" + id + ", name='" + name + '\'' + '}';
  }
}
