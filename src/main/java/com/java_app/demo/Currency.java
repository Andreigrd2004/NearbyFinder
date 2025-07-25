package com.java_app.demo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "currency", schema = "nearby_finder")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Currency {
    @Id
    @SequenceGenerator(
            name = "currency_seq",
            sequenceName = "currency_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "currency_seq")
    private Integer id;

    private String name;

    private Integer amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;


}
