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
    public CompletableFuture<Guard> getGuard(ClientResponse clientResponse) {


        Optional<Guard> optional = clientResponse.bodyToMono(HashMap.class)
                .map(x -> x.get("response"))
                .map(x -> ((HashMap<String, Object>) (x)).get("body"))
                .blockOptional()
                .filter(x -> !x.equals(""))
                .map(x -> ((HashMap<String, Object>) (x)).get("item"))
                .map(x -> {

                    Guard guard = new Guard();
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> convert = mapper.convertValue(x, Map.class);

                    try {
                        org.apache.commons.beanutils.BeanUtils.populate(guard, convert);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                    return guard;
                });
        return CompletableFuture.completedFuture(optional.orElse(new Guard()));
    }

    @Async
    public CompletableFuture<Disinfection> getDisinfection(ClientResponse clientResponse) {


        Optional<Disinfection> optional = clientResponse.bodyToMono(HashMap.class)
                .map(x -> x.get("response"))
                .map(x -> ((HashMap<String, Object>) (x)).get("body"))
                .blockOptional()
                .filter(x -> !x.equals(""))
                .map(x -> ((HashMap<String, Object>) (x)).get("item"))
                .map(x -> {

                    Disinfection disinfection = new Disinfection();
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> convert = mapper.convertValue(x, Map.class);

                    try {
                        org.apache.commons.beanutils.BeanUtils.populate(disinfection, convert);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                    return disinfection;
                });
        return CompletableFuture.completedFuture(optional.orElse(new Disinfection()));
    }

    @Async
    public CompletableFuture<Cleaning> getCleaning(ClientResponse clientResponse) {

        Optional<Cleaning> optional = clientResponse.bodyToMono(HashMap.class)
                .map(x -> x.get("response"))
                .map(x -> ((HashMap<String, Object>) (x)).get("body"))
                .blockOptional()
                .filter(x -> !x.equals(""))
                .map(x -> ((HashMap<String, Object>) (x)).get("item"))
                .map(x -> {

                    Cleaning cleaning = new Cleaning();
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> convert = mapper.convertValue(x, Map.class);

                    try {
                        org.apache.commons.beanutils.BeanUtils.populate(cleaning, convert);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                    return cleaning;
                });
        return CompletableFuture.completedFuture(optional.orElse(new Cleaning()));
    }

    @Async
    public CompletableFuture<Etc> getEtc(ClientResponse clientResponse){

        Optional<Etc> optional = clientResponse.bodyToMono(HashMap.class)
                .map(x -> x.get("response"))
                .map(x -> ((HashMap<String, Object>) (x)).get("body"))
                .blockOptional()
                .filter(x -> !x.equals(""))
                .map(x -> ((HashMap<String, Object>) (x)).get("item"))
                .map(x ->  {

                    Etc etc = new Etc();
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> convert = mapper.convertValue(x, Map.class);

                    try {
                        org.apache.commons.beanutils.BeanUtils.populate(etc, convert);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                    return etc;
                });

        return CompletableFuture.completedFuture(optional.orElse(new Etc()));
    }

    @Async
    public CompletableFuture<LaborManage> getLabor(ClientResponse clientResponse){

        Optional<LaborManage> optional = clientResponse.bodyToMono(HashMap.class)
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

        return CompletableFuture.completedFuture(optional.orElse(new LaborManage()));
    }

    @Async
    public CompletableFuture<Vehicle> getVehicle(ClientResponse clientResponse){

        Optional<Vehicle> optional = clientResponse.bodyToMono(HashMap.class)
                .map(x -> x.get("response"))
                .map(x -> ((HashMap<String, Object>) (x)).get("body"))
                .blockOptional()
                .filter(x -> !x.equals(""))
                .map(x -> ((HashMap<String, Object>) (x)).get("item"))
                .map(x ->  {

                    Vehicle vehicle = new Vehicle();
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> convert = mapper.convertValue(x, Map.class);

                    try {
                        org.apache.commons.beanutils.BeanUtils.populate(vehicle, convert);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                    return vehicle;
                });

        return CompletableFuture.completedFuture(optional.orElse(new Vehicle()));
    }

    @Async
    public CompletableFuture<Cloth> getCloth(ClientResponse clientResponse){

        Optional<Cloth> optional = clientResponse.bodyToMono(HashMap.class)
                .map(x -> x.get("response"))
                .map(x -> ((HashMap<String, Object>) (x)).get("body"))
                .blockOptional()
                .filter(x -> !x.equals(""))
                .map(x -> ((HashMap<String, Object>) (x)).get("item"))
                .map(x ->  {

                    Cloth cloth = new Cloth();
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> convert = mapper.convertValue(x, Map.class);

                    try {
                        org.apache.commons.beanutils.BeanUtils.populate(cloth, convert);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                    return cloth;
                });

        return CompletableFuture.completedFuture(optional.orElse(new Cloth()));
    }

    @Async
    public CompletableFuture<Edu> getEdu(ClientResponse clientResponse){

        Optional<Edu> optional = clientResponse.bodyToMono(HashMap.class)
                .map(x -> x.get("response"))
                .map(x -> ((HashMap<String, Object>) (x)).get("body"))
                .blockOptional()
                .filter(x -> !x.equals(""))
                .map(x -> ((HashMap<String, Object>) (x)).get("item"))
                .map(x ->  {

                    Edu edu = new Edu();
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> convert = mapper.convertValue(x, Map.class);

                    try {
                        org.apache.commons.beanutils.BeanUtils.populate(edu, convert);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                    return edu;
                });

        return CompletableFuture.completedFuture(optional.orElse(new Edu() ));
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
