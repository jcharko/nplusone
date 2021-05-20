package com.example.nplusone.nplusone.sandbox.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class A {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    //lazy by default
    @OneToMany(mappedBy = "parentA", cascade = CascadeType.ALL)
    private List<B> listOfB;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<B> getListOfB() {
        return listOfB;
    }

    public void setListOfB(List<B> listOfB) {
        this.listOfB = listOfB;
    }
}
