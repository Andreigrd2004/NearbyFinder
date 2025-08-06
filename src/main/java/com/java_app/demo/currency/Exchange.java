package com.java_app.demo.currency;

import jakarta.persistence.*;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "exchange", schema = "nearby_finder")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Exchange {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String source;

  private String target;

  private double amount;

  private Date ExpirationDate;

  public Exchange(String source, String target, Double amount, Date expirationTime) {
    this.source = source;
    this.target = target;
    this.amount = amount;
    this.ExpirationDate = expirationTime;
  }
}
