package com.java_app.demo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(name="API_key")
@Table(name = "API_key")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiKey {

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



    @Override
    public String toString() {
        return "API_key{" +
                "value='" + value + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
