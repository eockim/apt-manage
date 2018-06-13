package com.hj.aptmanage.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hj.aptmanage.entity.*;
import com.hj.aptmanage.exception.FunctionWithException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@Service
@EnableAsync
@Slf4j
public class AptService {

    private  <T> Consumer<T> wrapper(FunctionWithException<T, Exception> fwe){
        return (x) -> {
            try {
                fwe.accept(x);
            }catch(Exception e){
                throw new RuntimeException(e);
            }
        };
    }

    @Async
    public <T> T cost(ClientResponse clientResponse, T t) {

        Optional<T> optional = clientResponse.bodyToMono(HashMap.class)
                .map(x -> x.get("response"))
                .map(x -> ((HashMap<String, Object>) (x)).get("body"))
                .blockOptional()
                .filter(x -> !x.equals(""))
                .map(x -> ((HashMap<String, Object>) (x)).get("item"))
                .map(x -> {

                    //T t = null;
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> convert = mapper.convertValue(x, Map.class);

                    try {
                        org.apache.commons.beanutils.BeanUtils.populate(t, convert);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                    return t;
                });
        return optional.orElse(t);
    }

    @Async
    public <T> CompletableFuture<T> costCompletableFuture(ClientResponse clientResponse, T t) {

        Optional<T> optional = clientResponse.bodyToMono(HashMap.class)
                .map(x -> x.get("response"))
                .map(x -> ((HashMap<String, Object>) (x)).get("body"))
                .blockOptional()
                .filter(x -> !x.equals(""))
                .map(x -> ((HashMap<String, Object>) (x)).get("item"))
                .map(x -> {

                    //T t = null;
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> convert = mapper.convertValue(x, Map.class);

                    try {
                        org.apache.commons.beanutils.BeanUtils.populate(t, convert);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                    return t;
                });
        return CompletableFuture.completedFuture(optional.orElse(t));
    }


    public static void main(String[] args){
        LaborManage labor = new LaborManage();

        //Optional<String> test = Optional.<LaborManage>ofNullable(labor).map(x -> x.getBonus());

        //System.out.println(test.orElse("dd"));
    }

}
