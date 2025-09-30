package com.spring.parallel;

import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

public class FluxExampleServices {

    public static void main(String[] args) {

        FluxExampleServices fluxAndMonoServices = new FluxExampleServices();

        fluxAndMonoServices.fruitsFlux()
                .subscribe(s -> {
                    System.out.println("s = " + s);
                });

        fluxAndMonoServices.fruitsFluxMap()
                .subscribe(s -> {
                    System.out.println("s = " + s);
                });
        System.out.println(" --------------- ");
        Flux<String> stringFlux = Flux.zip(fluxAndMonoServices.fruitsFlux(), fluxAndMonoServices.numberFlux(), (fruitName, integer) -> {
            // System.out.println(fruitName+integer);
            return fruitName + integer;
        });

        stringFlux.subscribe(
                fruitCount -> System.out.println("fruitCount: " + fruitCount),
                error -> System.err.println("Error: " + error),
                () -> System.out.println("Custom object zipping complete.")
        );

        Optional<Integer> optional = Optional.of(1)
                .filter(i -> i > 2)
                .map(i -> i * 2);

    }

    public Flux<String> fruitsFlux() {
        return Flux.fromIterable(List.of("Mango", "Orange", "Banana")).log();
    }

    public Flux<Integer> numberFlux() {
        return Flux.fromIterable(List.of(1, 3, 4)).log();
    }

    public Flux<String> fruitsFluxMap() {
        return Flux.fromIterable(List.of("Mango", "Orange", "Banana"))
                .map(String::toUpperCase);
    }
}
