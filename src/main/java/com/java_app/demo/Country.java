package com.java_app.demo;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Country")
public class Country {
    @Id
    @SequenceGenerator(
            name="country_seq",
            sequenceName = "country_seq",
            allocationSize = 1
    )

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "country_seq")
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Currency> currencies = new HashSet<>();

    @ManyToMany(mappedBy = "associatedCountries")
    Set<CustomUser>  associatedCustomUsers = new HashSet<>();

    public Country() {
    }

    public Country(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
