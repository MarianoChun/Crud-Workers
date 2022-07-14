package com.example.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "workers")
@EqualsAndHashCode
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

}
