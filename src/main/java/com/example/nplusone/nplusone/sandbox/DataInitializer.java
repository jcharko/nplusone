package com.example.nplusone.nplusone.sandbox;

import com.example.nplusone.nplusone.sandbox.model.A;
import com.example.nplusone.nplusone.sandbox.model.B;
import com.example.nplusone.nplusone.sandbox.model.C;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.rangeClosed;

@Service
public class DataInitializer {

    private final ARepository aRepository;

    public DataInitializer(ARepository aRepository) {
        this.aRepository = aRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<A> prepareData(int count) {
        return aRepository.saveAll(createAList(count));
    }

    private List<A> createAList(int count) {
        return rangeClosed(0, count)
                .boxed()
                .map(num -> {
                    A a = new A();
                    List<B> bList = createBList(a, count);
                    a.setListOfB(bList);
                    return a;
                })
                .collect(toList());
    }

    private List<B> createBList(A a, int count) {
        return rangeClosed(0, count)
                .boxed()
                .map(num -> {
                    B b = new B(a);
                    List<C> cList = createCList(b, count);
                    b.setListOfC(cList);
                    return b;
                })
                .collect(toList());
    }

    private List<C> createCList(B b, int count) {
        return rangeClosed(0, count)
                .boxed()
                .map(a -> new C(b))
                .collect(toList());
    }
}
