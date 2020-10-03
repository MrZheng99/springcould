package com.zj.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestController {
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("/test1")
    public String test1(){
       return  restTemplate.getForObject("http://product-server/product/findAll",String.class);
    }
    @Autowired
    LoadBalancerClient loadBalancerClient;
    @GetMapping("/test2")
    public String test2(){
        ServiceInstance serviceInstance = loadBalancerClient.choose("product-server");
        String url = String.format("http://%s:%s/%s",serviceInstance.getHost(),serviceInstance.getPort(),"/product/findAll");
        return restTemplate.getForObject(url,String.class);
    }
    @GetMapping("/test3")
    public String test3(){
        return  restTemplate.getForObject("http://product-server/product/findOne",String.class);
    }


}
