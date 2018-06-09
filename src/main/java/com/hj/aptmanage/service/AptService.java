package com.hj.aptmanage.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hj.aptmanage.entity.Cmnuse;
import com.hj.aptmanage.entity.LaborManage;
import com.hj.aptmanage.entity.Tax;
import com.hj.aptmanage.exception.FunctionWithException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public CompletableFuture<LaborManage> getLabor(ClientResponse clientResponse){

        Optional<LaborManage> laborManage = clientResponse.bodyToMono(HashMap.class)
                .map(x -> x.get("response"))
                .map(x -> ((HashMap<String, Object>) (x)).get("body"))
                .blockOptional()
                .filter(x -> !x.equals(""))
                .map(x -> ((HashMap<String, Object>) (x)).get("item"))
                .map(x ->  {

                    LaborManage labor = new LaborManage();
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> convert = mapper.convertValue(x, Map.class);

                    try {
                        org.apache.commons.beanutils.BeanUtils.populate(labor, convert);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                    return labor;
                });

        return CompletableFuture.completedFuture(laborManage.orElse(new LaborManage()));
    }

    @Async
    public CompletableFuture<Cmnuse> getCmnuse(ClientResponse clientResponse){

        Optional<Cmnuse> optional = clientResponse.bodyToMono(HashMap.class)
                .map(x -> x.get("response"))
                .map(x -> ((HashMap<String, Object>) (x)).get("body"))
                .blockOptional()
                .filter(x -> !x.equals(""))
                .map(x -> ((HashMap<String, Object>) (x)).get("item"))
                .map(x ->  {

                    Cmnuse cmn = new Cmnuse();
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> convert = mapper.convertValue(x, Map.class);

                    try {
                        org.apache.commons.beanutils.BeanUtils.populate(cmn, convert);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                    return cmn;
                });

        return CompletableFuture.completedFuture(optional.orElse(new Cmnuse()));
    }

    @Async
    public CompletableFuture<Tax> getTax(ClientResponse clientResponse){

        Optional<Tax> optional = clientResponse.bodyToMono(HashMap.class)
                .map(x -> x.get("response"))
                .map(x -> ((HashMap<String, Object>) (x)).get("body"))
                .blockOptional()
                .filter(x -> !x.equals(""))
                .map(x -> ((HashMap<String, Object>) (x)).get("item"))
                .map(x ->  {

                    Tax tax = new Tax();
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> convert = mapper.convertValue(x, Map.class);

                    try {
                        org.apache.commons.beanutils.BeanUtils.populate(tax, convert);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                    return tax;
                });

        return CompletableFuture.completedFuture(optional.orElse(new Tax()));
    }

    public static void main(String[] args){
        LaborManage labor = new LaborManage();

        //Optional<String> test = Optional.<LaborManage>ofNullable(labor).map(x -> x.getBonus());

        //System.out.println(test.orElse("dd"));
    }

}
