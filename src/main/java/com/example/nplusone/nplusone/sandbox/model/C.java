package com.example.nplusone.nplusone.sandbox.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class C {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private B parentB;

    @ManyToMany(mappedBy = "listOfC", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<D> listOfD = new HashSet<>();

    public C() {
    }

    public C(B b) {
        this.parentB = b;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public B getParentB() {
        return parentB;
    }

    public Set<D> getListOfD() {
        return listOfD;
    }

    public void setParentB(B parentB) {
        this.parentB = parentB;
    }

    public void addD(D d) {
        listOfD.add(d);
        d.addC(this);
    }
}
