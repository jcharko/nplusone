package com.example.nplusone.nplusone.sandbox;

import com.example.nplusone.nplusone.sandbox.model.B;
import com.example.nplusone.nplusone.sandbox.model.C;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CRepository extends JpaRepository<C, Integer> {

    @Query("select distinct entity " +
           "from C entity " +
           "join fetch entity.listOfD ld " +
           "where entity.parentB in (:listOfB)")
    List<C> findWithD(List<B> listOfB);
}
