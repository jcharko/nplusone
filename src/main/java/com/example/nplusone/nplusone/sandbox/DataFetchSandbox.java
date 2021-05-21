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
        List<A> all = aRepository.findAll();
        fakeInitializationWhichCouldFireAdditionalSelects(all);
        return all;
    }

    private void fakeInitializationWhichCouldFireAdditionalSelects(List<A> all) {
        for (A a : all) {
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
        List<A> listOfA = aRepository.findWithB();
        List<B> listOfB = bRepository.findByA(listOfA);
        cRepository.findWithD(listOfB);
        fakeInitializationWhichCouldFireAdditionalSelects(listOfA);
        return listOfA;

    }
}
