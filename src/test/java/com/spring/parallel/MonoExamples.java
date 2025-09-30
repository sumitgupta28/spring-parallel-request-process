package com.spring.parallel;

import reactor.core.publisher.Mono;

import java.util.function.Supplier;

public class MonoExamples {


    public static void main(String[] args) {

        MonoExamples fluxAndMonoServices
                = new MonoExamples();

        fluxAndMonoServices.fruitMono()
                .subscribe(s -> {
                    System.out.println("Mono -> s = " + s);
                });


        fluxAndMonoServices.fruitsMonoZipWith()
                .subscribe(s -> {
                    System.out.println("Mono -> s = " + s);
                });
/*
        fluxAndMonoServices.fruitMonoWithError()
                .subscribe(s -> {
                    System.out.println("Mono -> s = " + s);
                });*/


        Supplier<String> dataSupplier = () -> {
            System.out.println("Fetching data...");
            return "Lazy Data";
        };
        Mono<String> lazyMono = Mono.fromSupplier(dataSupplier);
        lazyMono.subscribe(
                data -> System.out.println("fruitCount: " + data),
                error -> System.err.println("Error: " + error),
                () -> System.out.println("data.........")
        );
    }

    public Mono<String> fruitMono() {
        return Mono.just("Mango").log();
    }

    public Mono<String> fruitMonoWithError() {
        return Mono.just("Mango").log()
                .then(Mono.error(new RuntimeException("Error Occurred while publishing data")));
    }

    public Mono<String> fruitsMonoZipWith() {
        var fruits = Mono.just("Mango");
        var veggies = Mono.just("Tomato");

        return veggies.zipWith(fruits,
                (first, second) -> first + "," + second).log();
    }
}
