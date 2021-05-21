package com.example.nplusone.nplusone.sandbox;

import com.example.nplusone.nplusone.sandbox.model.A;
import com.example.nplusone.nplusone.sandbox.model.B;
import com.example.nplusone.nplusone.sandbox.model.C;
import com.example.nplusone.nplusone.sandbox.model.D;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DataFetchSandbox {

    private final ARepository aRepository;
    private final BRepository bRepository;
    private final CRepository cRepository;

    public DataFetchSandbox(ARepository aRepository, BRepository bRepository, CRepository cRepository) {
        this.aRepository = aRepository;
        this.bRepository = bRepository;
        this.cRepository = cRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<A> fetchDataNPlusOneProblem() {
        List<A> listOfA = aRepository.findAll();

        // No related data in L1 cache so additional selects will be fired
        fakeInitializationWhichCouldFireAdditionalSelects(listOfA);

        return listOfA;
    }

    // can be for example mapping entity to dto
    private void fakeInitializationWhichCouldFireAdditionalSelects(List<A> listOfA) {
        for (A a : listOfA) {
            for (B b : a.getListOfB()) {
                for (C c : b.getListOfC()) {
                    for (D d : c.getListOfD()) {
                        d.getId();
                    }
                }
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<A> fetchDataWithoutNPlusOneProblem() {
        // Fetching main object list
        List<A> listOfA = aRepository.findWithB();

        // Fetching relation list with previously fetched data in where in clause
        List<B> listOfB = bRepository.findByA(listOfA);

        // Fetching relation list with previously fetched data in where in clause
        cRepository.findWithD(listOfB);

        // All data is in L1 cache so operation above will NOT fire additional selects
        fakeInitializationWhichCouldFireAdditionalSelects(listOfA);
        return listOfA;

    }
}
