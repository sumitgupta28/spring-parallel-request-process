Welcome to the spring-parallel-request-process wiki!

This Repo includes different Types of implementation to achieve parallel processing. 

1. Using @Async ([main Branch](https://github.com/sumitgupta28/spring-parallel-request-process))
2. Using CompletableFuture with RestTemplate ([main Branch](https://github.com/sumitgupta28/spring-parallel-request-process))
3. Using Flux and Mono with Spring webflux. ([netty-flux Branch](https://github.com/sumitgupta28/spring-parallel-request-process/tree/netty-flux))

Also Check Other Repo - [spring-boot-async-example](https://github.com/sumitgupta28/spring-boot-async-example) and [wiki page](https://github.com/sumitgupta28/spring-boot-async-example/wiki) for more details on `@Async` implementation.

### fetchData

```bash
curl http://localhost:8080/api/fetchData
```

### parallel

```bash
curl http://localhost:8080/api/parallel
```

### parallelWithExceptionIOneTask

```bash
curl http://localhost:8080/api/parallelWithExceptionIOneTask
```

### parallelWithGlobalTimeout

```bash
curl http://localhost:8080/api/parallelWithGlobalTimeout
```

```bash
curl http://localhost:8080/api/parallelWithIndividualTaskTimeout
```

