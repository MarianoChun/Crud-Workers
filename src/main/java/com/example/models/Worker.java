package com.example.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "workers")
@EqualsAndHashCode @ToString
public class Worker {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String surname;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private String password;

    @Getter @Setter
    private String occupation;

    public Worker(String name, String surname, String email, String password, String occupation) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.occupation = occupation;
    }

    public Worker(){};
}
