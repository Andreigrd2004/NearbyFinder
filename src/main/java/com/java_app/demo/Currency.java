package com.java_app.demo;

import jakarta.persistence.*;

@Entity
@Table(name = "Currency")
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

    public Currency() {
    }

    public Currency(Integer id, String name, Integer amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAmount() {
        return amount;
    }


}
