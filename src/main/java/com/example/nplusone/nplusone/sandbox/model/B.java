package com.example.nplusone.nplusone.sandbox.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class B {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "a_id")
    private A parentA;

    //lazy by default
    @OneToMany(mappedBy = "parentB", cascade = CascadeType.ALL)
    private List<C> listOfC;

    public B() {
    }

    public B(A a) {
        this.parentA = a;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public A getParentA() {
        return parentA;
    }

    public void setParentA(A parentA) {
        this.parentA = parentA;
    }

    public List<C> getListOfC() {
        return listOfC;
    }

    public void setListOfC(List<C> listOfC) {
        this.listOfC = listOfC;
    }
}
