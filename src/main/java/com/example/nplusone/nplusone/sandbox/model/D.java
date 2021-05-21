package com.example.nplusone.nplusone.sandbox.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class D {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    // default fetch type is lazy
    // do not set cascade remove, can remove whole database
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "c_d",
            joinColumns = @JoinColumn(name = "c_id"),
            inverseJoinColumns = @JoinColumn(name = "d_id")
    )
    private Set<C> listOfC = new HashSet<>();

    public void addC(C c) {
        listOfC.add(c);
    }

    public int getId() {
        return id;
    }

    public Set<C> getListOfC() {
        return listOfC;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setListOfC(Set<C> listOfC) {
        this.listOfC = listOfC;
    }
}
