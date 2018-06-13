package com.hj.aptmanage;

import com.hj.aptmanage.entity.*;
import com.hj.aptmanage.service.AptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
@RestController
@SpringBootApplication
@Slf4j
public class AptManageApplication {

    private final String APT_LIST_URI = "http://apis.data.go.kr/1611000/AptListService/getRoadnameAptList";
    private final String PUBLIC_COST_URI = "http://apis.data.go.kr/1611000/AptCmnuseManageCostService/";
    private final String PUBLIC_COST_LABOR_URI = "getHsmpLaborCostInfo";
    private final String PUBLIC_COST_CMNUSE_URI = "getHsmpOfcrkCostInfo";
    private final String PUBLIC_COST_TAX_URI = "getHsmpTaxdueInfo";
    private final String PUBLIC_COST_CLOTH_URI = "getHsmpClothingCostInfo";
    private final String PUBLIC_COST_EDU_URI = "getHsmpEduTraingCostInfo";
    private final String PUBLIC_COST_VEHICLE_URI = "getHsmpVhcleMntncCostInfo";
    private final String PUBLIC_COST_ETC_URI = "getHsmpEtcCostInfo";
    private final String PUBLIC_COST_CLEANING_URI = "getHsmpCleaningCostInfo";
    private final String PUBLIC_COST_GUARD_URI = "getHsmpGuardCostInfo";
    private final String PUBLIC_COST_DISINFECTION_URI = "getHsmpDisinfectionCostInfo";

    @Autowired
    ApiProperty apiProperty;

    @Autowired
    AptService aptService;

    WebClient webClient = WebClient.create();

    public static void main(String[] args) {
        SpringApplication.run(AptManageApplication.class, args);
    }

    private Mono<ClientResponse> getCostMono(String uri, String aptCode, String date){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");

        log.info("getPublicCostKey {}", apiProperty.getPublicCostKey());

        Optional.<String>ofNullable(date)
                .filter(x -> !x.equals(""))
                .orElseGet(() -> formatter.format(YearMonth.now()));

        log.info("year month {}", date);

        return webClient.get()
                .uri(URI.create(PUBLIC_COST_URI + uri + "?serviceKey=" + apiProperty.getPublicCostKey() + "&kaptCode=" + aptCode + "&searchDate=" + date))
                .accept(MediaType.APPLICATION_JSON)
                .exchange();
                //.flatMap(x -> Mono.fromCompletionStage(aptService.costCompletableFuture(x, t)));
    }

    public WebClient.RequestHeadersSpec<?> getCostRequest(String uri, String aptCode, String date){

        try {
            Thread.sleep(1 * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");

        log.info("getPublicCostKey {}", apiProperty.getPublicCostKey());

        Optional.<String>ofNullable(date)
                .filter(x -> !x.equals(""))
                .orElseGet(() -> formatter.format(YearMonth.now()));

        log.info("year month {}", date);

        return webClient.get()
                .uri(URI.create(PUBLIC_COST_URI + uri + "?serviceKey=" + apiProperty.getPublicCostKey() + "&kaptCode=" + aptCode + "&searchDate=" + date))
                .accept(MediaType.APPLICATION_JSON);

        //.flatMap(x -> Mono.fromCompletionStage(aptService.costCompletableFuture(x, t)));
    }

    @GetMapping("/apt/{loadCode}")
    public Mono<HashMap> getAptList(@PathVariable String loadCode){

        //System.out.println(apiProperty.getApikey());
        log.info("getAptListKey {}", apiProperty.getAptListKey());
        Optional.<String>ofNullable(apiProperty.getAptListKey())
                                .filter(x -> !x.equals(""))
                                .orElseThrow(()-> new NullPointerException());

        Mono<HashMap> map = webClient.get()
                .uri(URI.create(APT_LIST_URI + "?loadCode=" + loadCode + "&ServiceKey=" + apiProperty.getAptListKey()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .flatMap(x -> x.bodyToMono(HashMap.class))
                ;
                //.flatMap(x -> webClient.get().uri())

        return map;
    }

    @GetMapping("/apt/cost/disinfection/{aptCode}")
    public Mono<Disinfection> getDisinfectionCost(@PathVariable String aptCode, String date){

        return getCostMono(PUBLIC_COST_DISINFECTION_URI, aptCode, date)
                .flatMap(x -> Mono.fromCompletionStage(aptService.costCompletableFuture(x, new Disinfection())));

    }


    @GetMapping("/apt/cost/guard/{aptCode}")
    public Mono<Guard> getGuardCost(@PathVariable String aptCode, String date){

        return getCostMono(PUBLIC_COST_GUARD_URI, aptCode, date)
                .flatMap(x -> Mono.fromCompletionStage(aptService.costCompletableFuture(x, new Guard())));

    }

    @GetMapping("/apt/cost/cleaning/{aptCode}")
    public Mono<Cleaning> getCleaningCost(@PathVariable String aptCode, String date){

        return getCostMono(PUBLIC_COST_CLEANING_URI, aptCode, date)
                .flatMap(x -> Mono.fromCompletionStage(aptService.costCompletableFuture(x, new Cleaning())));
    }

    @GetMapping("/apt/cost/etc/{aptCode}")
    public Mono<Etc> getEtcCost(@PathVariable String aptCode, String date){

        return getCostMono(PUBLIC_COST_ETC_URI, aptCode, date)
                .flatMap(x -> Mono.fromCompletionStage(aptService.costCompletableFuture(x, new Etc())));
    }

    @GetMapping("/apt/cost/vehicle/{aptCode}")
    public Mono<Vehicle> getVehicleCost(@PathVariable String aptCode, String date){

        return getCostMono(PUBLIC_COST_VEHICLE_URI, aptCode, date)
                .flatMap(x -> Mono.fromCompletionStage(aptService.costCompletableFuture(x, new Vehicle())));
    }

    @GetMapping("/apt/cost/edu/{aptCode}")
    public Mono<Edu> getEduCost(@PathVariable String aptCode, String date){

        return getCostMono(PUBLIC_COST_EDU_URI, aptCode, date)
                .flatMap(x -> Mono.fromCompletionStage(aptService.costCompletableFuture(x, new Edu())));
    }

    @GetMapping("/apt/cost/cloth/{aptCode}")
    public Mono<Cloth> geClothCost(@PathVariable String aptCode, String date){

        return getCostMono(PUBLIC_COST_CLOTH_URI, aptCode, date)
                .flatMap(x -> Mono.fromCompletionStage(aptService.costCompletableFuture(x, new Cloth())));
    }

    @GetMapping("/apt/cost/tax/{aptCode}")
    public Mono<Tax> getTaxCost(@PathVariable String aptCode, String date){

        return getCostMono(PUBLIC_COST_TAX_URI, aptCode, date)
                .flatMap(x -> Mono.fromCompletionStage(aptService.costCompletableFuture(x, new Tax())));
    }

    @GetMapping("/apt/cost/cmnuse/{aptCode}")
    public Mono<Cmnuse> getCmnuseCost(@PathVariable String aptCode, String date){

        return getCostMono(PUBLIC_COST_CMNUSE_URI, aptCode, date)
                .flatMap(x -> Mono.fromCompletionStage(aptService.costCompletableFuture(x, new Cmnuse())));
    }

    @GetMapping("/apt/cost/labor/{aptCode}")
    public Mono<LaborManage> getAptLaborCost(@PathVariable String aptCode, String date){

        return getCostMono(PUBLIC_COST_LABOR_URI, aptCode, date)
             .flatMap(x -> Mono.fromCompletionStage(aptService.costCompletableFuture(x, new LaborManage())));
    }

    @GetMapping("/apt/test/{aptCode}")
    public Mono<Long> getAptPublicCostTest(@PathVariable String aptCode, String date) throws ExecutionException, InterruptedException {

        AtomicLong totalCost = new AtomicLong(0L);

//
        Mono<Long> cost1 = getCostMono(PUBLIC_COST_LABOR_URI, aptCode, date)
                .flatMap(x -> Mono.fromCompletionStage(aptService.costCompletableFuture(x, new LaborManage())))
                .map(x -> x.getCostTotal());

        Mono<Long> cost2 = getCostMono(PUBLIC_COST_DISINFECTION_URI, aptCode, date)
                .flatMap(c -> Mono.fromCompletionStage(aptService.costCompletableFuture(c, new Disinfection())))
                .map(x -> x.getTotal());

        Mono<Long> cost3 = getCostMono(PUBLIC_COST_GUARD_URI, aptCode, date)
                .flatMap(c -> Mono.fromCompletionStage(aptService.costCompletableFuture(c, new Guard())))
                .map(x -> x.getTotal());

        Mono<Long> cost4 = getCostMono(PUBLIC_COST_CLEANING_URI, aptCode, date)
                .flatMap(c -> Mono.fromCompletionStage(aptService.costCompletableFuture(c, new Cleaning())))
                .map(x -> x.getTotal());

        Mono<Long> cost5 = getCostMono(PUBLIC_COST_ETC_URI, aptCode, date)
                .flatMap(c -> Mono.fromCompletionStage(aptService.costCompletableFuture(c, new Etc())))
                .map(x -> x.getTotal());

        Mono<Long> cost6 = getCostMono(PUBLIC_COST_VEHICLE_URI, aptCode, date)
                .flatMap(c -> Mono.fromCompletionStage(aptService.costCompletableFuture(c, new Vehicle())))
                .map(x -> x.getTotal());

        Mono<Long> cost7 = getCostMono(PUBLIC_COST_EDU_URI, aptCode, date)
                .flatMap(c -> Mono.fromCompletionStage(aptService.costCompletableFuture(c, new Edu())))
                .map(x -> x.getTotal());

        Mono<Long> cost8 = getCostMono(PUBLIC_COST_CLOTH_URI, aptCode, date)
                .flatMap(c -> Mono.fromCompletionStage(aptService.costCompletableFuture(c, new Cloth())))
                .map(x -> x.getTotal());

        Mono<Long> cost9 = getCostMono(PUBLIC_COST_TAX_URI, aptCode, date)
                .flatMap(c -> Mono.fromCompletionStage(aptService.costCompletableFuture(c, new Tax())))
                .map(x -> x.getTotal());

        Mono<Long> cost10 = getCostMono(PUBLIC_COST_CMNUSE_URI, aptCode, date)
                .flatMap(c -> Mono.fromCompletionStage(aptService.costCompletableFuture(c, new Cmnuse())))
                .map(x -> x.getTotalCost());
//
//
//
//
        Mono<Long> result2 = cost1
                .flatMap(x -> {
                    totalCost.getAndAdd(x);
                    return cost2;
                })
                .flatMap(x -> {
                    totalCost.getAndAdd(x);
                    return cost3;
                }).flatMap(x ->  {
                    totalCost.getAndAdd(x);
                    return cost4;
                }).flatMap(x ->  {
                    totalCost.getAndAdd(x);
                    return cost5;
                }).flatMap(x ->  {
                    totalCost.getAndAdd(x);
                    return cost6;
                }).flatMap(x ->  {
                    totalCost.getAndAdd(x);
                    return cost7;
                }).flatMap(x ->  {
                    totalCost.getAndAdd(x);
                    return cost8;
                }).flatMap(x ->  {
                    totalCost.getAndAdd(x);
                    return cost9;
                }).flatMap(x ->  {
                    totalCost.getAndAdd(x);
                    return cost10;
                }).map(x -> totalCost.longValue());


                
                
                



//        f = getCostMono(PUBLIC_COST_LABOR_URI, aptCode, date)
//                .flatMap(x -> Mono.fromCompletionStage(aptService.costCompletableFuture(x, new LaborManage())))
//                .flatMap(x -> Flux.from(() -> totalCost += x.getCostTotal()));

//        getCostMono(PUBLIC_COST_CMNUSE_URI, aptCode, date)
//                .map(x -> aptService.costCompletableFuture(x, new Cmnuse())).block()
//                .thenApply(x -> totalCost.updateAndGet(v -> v + x.getTotalCost()));

        //List<CompletableFuture<?>> completableFutures = Arrays.asList(cf1, cf2);

        List<Mono<Long>> monos = Arrays.asList(cost1, cost2, cost3, cost4, cost5, cost6, cost7, cost8, cost9, cost10);

//        monos.stream()
//                .parallel()
//                .
//                .forEach(x -> {
//                    //x.log();
//                    x.subscribe(r ->{
//                        log.info("long r ########## {} ", r);
//                        totalCost.getAndAdd(r);
//                        log.info("totalCost@@@@@@@ {} ", totalCost.longValue());
//                    });
//
//                    x.doOnSuccess(s -> {
//                        log.info("long s ######## {} " , s);
//                        totalCost.getAndAdd(s);
//                    });
//
//                });

//        Mono<Long> result = Mono.fromSupplier(() -> {
//
//            AtomicLong totalCost = new AtomicLong(0L);
//
//            monos.stream().map(x -> {
//                x.subscribe(r -> {
//                    log.info("long r ########## {} ", r);
//                    totalCost.getAndAdd(r);
//                    log.info("totalCost@@@@@@@ {} ", totalCost.longValue());
//                });
//
//                return totalCost.longValue();
//            }).
//
//                    return Mono.just(list);
//
//                }
//        )

        return result2;


    }
}
