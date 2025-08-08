package com.java_app.demo.currency;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Table(name = "exchange", schema = "nearby_finder")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Exchange {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String source;

  private String target;

  private double amount;

  private LocalDateTime expirationDate;
}
