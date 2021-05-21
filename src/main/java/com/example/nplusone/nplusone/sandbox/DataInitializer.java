package com.example.nplusone.nplusone.sandbox;

import com.example.nplusone.nplusone.sandbox.model.A;
import com.example.nplusone.nplusone.sandbox.model.B;
import com.example.nplusone.nplusone.sandbox.model.C;
import com.example.nplusone.nplusone.sandbox.model.D;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.rangeClosed;

@Service
public class DataInitializer {

    private final ARepository aRepository;

    public DataInitializer(ARepository aRepository) {
        this.aRepository = aRepository;
    }

    @Transactional
    public List<A> prepareData(int count) {
        return aRepository.saveAll(createAList(count));
    }

    private List<A> createAList(int count) {
        return rangeClosed(1, count)
                .boxed()
                .map(createA(count))
                .collect(toList());
    }

    private Function<Integer, A> createA(int count) {
        return num -> {
            A a = new A();
            List<B> bList = createBList(a, count);
            a.setListOfB(bList);
            return a;
        };
    }

    private List<B> createBList(A a, int count) {
        return rangeClosed(1, count)
                .boxed()
                .map(createB(a, count))
                .collect(toList());
    }

    private Function<Integer, B> createB(A a, int count) {
        return num -> {
            B b = new B(a);
            List<C> cList = createCList(b, count);
            b.setListOfC(cList);
            return b;
        };
    }

    private List<C> createCList(B b, int count) {
        return rangeClosed(1, count)
                .boxed()
                .map(createC(b, count))
                .collect(toList());
    }

    private Function<Integer, C> createC(B b, int count) {
        return num -> {
            List<D> listOfD = createDList(count);
            C c = new C(b);
            listOfD.forEach(c::addD);
            return c;
        };
    }

    private List<D> createDList(int count) {
        return rangeClosed(1, count)
                .boxed()
                .map(d -> new D())
                .collect(toList());
    }
}
