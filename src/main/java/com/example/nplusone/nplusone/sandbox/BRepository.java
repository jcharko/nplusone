package com.example.nplusone.nplusone.sandbox;

import com.example.nplusone.nplusone.sandbox.model.A;
import com.example.nplusone.nplusone.sandbox.model.B;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BRepository extends JpaRepository<B, Integer> {

    @Query("select distinct entity from B entity join fetch entity.listOfC where entity.parentA in (:listOfA)")
    List<B> findByA(List<A> listOfA);
}
