package com.hj.aptmanage;

import com.hj.aptmanage.entity.Cloth;
import com.hj.aptmanage.entity.Cmnuse;
import com.hj.aptmanage.entity.LaborManage;
import com.hj.aptmanage.service.AptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@RestController
@SpringBootApplication
@Slf4j
public class AptManageApplication {

    private final String APT_LIST_URI = "http://apis.data.go.kr/1611000/AptListService/getRoadnameAptList";
    private final String PUBLIC_COST_LABOR_URI = "http://apis.data.go.kr/1611000/AptCmnuseManageCostService/getHsmpLaborCostInfo";
    private final String PUBLIC_COST_CMNUSE_URI = "http://apis.data.go.kr/1611000/AptCmnuseManageCostService/getHsmpOfcrkCostInfo";
    private final String PUBLIC_COST_TAX_URI = "http://apis.data.go.kr/1611000/AptCmnuseManageCostService/getHsmpTaxdueInfo";
    private final String PUBLIC_COST_CLOTH_URI = "http://apis.data.go.kr/1611000/AptCmnuseManageCostService/getHsmpClothingCostInfo";
//    @Value("${public.aptList.apiKey}")
//    private String APT_LIST_KEY;

    @Autowired
    ApiProperty apiProperty;

    @Autowired
    AptService aptService;

    WebClient webClient = WebClient.create();

    public static void main(String[] args) {
        SpringApplication.run(AptManageApplication.class, args);
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

    @GetMapping("/apt/cost/cloth/{aptCode}")
    public Mono<Cloth> geClothCost(@PathVariable String aptCode, String date){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");

        log.info("getPublicCostKey {}", apiProperty.getPublicCostKey());

        Optional.<String>ofNullable(date)
                .filter(x -> !x.equals(""))
                .orElseGet(() -> formatter.format(YearMonth.now()));

        log.info("year month {}", date);

        return webClient.get()
                .uri(URI.create(PUBLIC_COST_TAX_URI + "?serviceKey=" + apiProperty.getPublicCostKey() + "&kaptCode=" + aptCode + "&searchDate=" + date))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .flatMap(x -> Mono.fromCompletionStage(aptService.getCloth(x)));

    }

    @GetMapping("/apt/cost/tax/{aptCode}")
    public Mono<Cmnuse> getTaxCost(@PathVariable String aptCode, String date){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");

        log.info("getPublicCostKey {}", apiProperty.getPublicCostKey());

        Optional.<String>ofNullable(date)
                .filter(x -> !x.equals(""))
                .orElseGet(() -> formatter.format(YearMonth.now()));

        log.info("year month {}", date);

        return webClient.get()
                .uri(URI.create(PUBLIC_COST_TAX_URI + "?serviceKey=" + apiProperty.getPublicCostKey() + "&kaptCode=" + aptCode + "&searchDate=" + date))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .flatMap(x -> Mono.fromCompletionStage(aptService.getCmnuse(x)));

    }

    @GetMapping("/apt/cost/cmnuse/{aptCode}")
    public Mono<Cmnuse> getCmnuseCost(@PathVariable String aptCode, String date){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");

        log.info("getPublicCostKey {}", apiProperty.getPublicCostKey());

        Optional.<String>ofNullable(date)
                .filter(x -> !x.equals(""))
                .orElseGet(() -> formatter.format(YearMonth.now()));

        log.info("year month {}", date);

        return webClient.get()
                .uri(URI.create(PUBLIC_COST_CMNUSE_URI + "?serviceKey=" + apiProperty.getPublicCostKey() + "&kaptCode=" + aptCode + "&searchDate=" + date))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .flatMap(x -> Mono.fromCompletionStage(aptService.getCmnuse(x)));

    }

    @GetMapping("/apt/cost/labor/{aptCode}")
    public Mono<LaborManage> getAptLaborCost(@PathVariable String aptCode, String date){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");

        log.info("getPublicCostKey {}", apiProperty.getPublicCostKey());

        Optional.<String>ofNullable(date)
                .filter(x -> !x.equals(""))
                .orElseGet(() -> formatter.format(YearMonth.now()));

        log.info("year month {}", date);

        return webClient.get()
                .uri(URI.create(PUBLIC_COST_LABOR_URI + "?serviceKey=" + apiProperty.getPublicCostKey() + "&kaptCode=" + aptCode + "&searchDate=" + date))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .flatMap(x -> Mono.fromCompletionStage(aptService.getLabor(x)));
    }

    @GetMapping("/apt/test/{aptCode}")
    public Mono<Map> getAptPublicCostTest(@PathVariable String aptCode, String date){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");

        log.info("getPublicCostKey {}", apiProperty.getPublicCostKey());

        Optional.<String>ofNullable(date)
                .filter(x -> !x.equals(""))
                .orElseGet(() -> formatter.format(YearMonth.now()));

        log.info("year month {}", date);

        return webClient.get()
                .uri(URI.create(PUBLIC_COST_LABOR_URI + "?serviceKey=" + apiProperty.getPublicCostKey() + "&kaptCode=" + aptCode + "&searchDate=" + date))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .flatMap(c -> c.bodyToMono(HashMap.class))
                ;
    }
}
