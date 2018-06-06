package com.hj.aptmanage;

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
import java.util.HashMap;
import java.util.Optional;

@Component
@RestController
@SpringBootApplication
@Slf4j
public class AptManageApplication {

    private final String APT_LIST_URI = "http://apis.data.go.kr/1611000/AptListService/getRoadnameAptList";

//    @Value("${public.aptList.apiKey}")
//    private String APT_LIST_KEY;

    @Autowired
    ApiProperty apiProperty;

    WebClient webClient = WebClient.create();

    public static void main(String[] args) {
        SpringApplication.run(AptManageApplication.class, args);
    }

    @GetMapping("/apt/{loadCode}")
    public Mono<HashMap> getAptList(@PathVariable String loadCode){

        //System.out.println(apiProperty.getApikey());
        log.info("getApiKey {}", apiProperty.getAptListKey());
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
}
