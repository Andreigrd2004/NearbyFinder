package com.java_app.demo;

import jakarta.persistence.*;


@Entity(name="API_key")
@Table(name = "API_key")
public class API_key {

    @Id
    @SequenceGenerator(
            name = "API_sequence",
            sequenceName = "API_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "API_sequence"
    )
    private String value;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private CustomUser customUser;

    public API_key() {

    }

    @Override
    public String toString() {
        return "API_key{" +
                "value='" + value + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public API_key(String value, String name) {
        this.value = value;
        this.name = name;
    }
}
