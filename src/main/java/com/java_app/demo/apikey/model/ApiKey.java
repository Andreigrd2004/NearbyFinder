package com.java_app.demo.apikey.model;

import com.java_app.demo.user.CustomUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "api_key", schema = "nearby_finder")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiKey {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String value;

  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private CustomUser customUser;

  @Override
  public String toString() {
    return "API_key{" + "value='" + value + '\'' + ", name='" + name + '\'' + '}';
  }

}
