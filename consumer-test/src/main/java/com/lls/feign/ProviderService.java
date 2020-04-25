package com.lls.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="provider-server")
public interface ProviderService {

    @RequestMapping(value = "/logs",method = RequestMethod.GET)
    String getId(@RequestParam(name = "id")String id);

}
