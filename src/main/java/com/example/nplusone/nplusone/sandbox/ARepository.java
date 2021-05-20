package com.example.nplusone.nplusone.sandbox;

import com.example.nplusone.nplusone.sandbox.model.A;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ARepository extends JpaRepository<A, Integer> {

    @Query("select distinct entity from A entity join fetch entity.listOfB")
    List<A> findWithB();

}
