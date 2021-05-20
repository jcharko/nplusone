package com.example.nplusone.nplusone.sandbox.model;

import javax.persistence.*;

@Entity
public class C {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private B parentB;

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

    public void setParentB(B parentB) {
        this.parentB = parentB;
    }
}
