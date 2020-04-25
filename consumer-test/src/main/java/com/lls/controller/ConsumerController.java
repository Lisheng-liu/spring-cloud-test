package com.lls.controller;

import com.lls.feign.ProviderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer")
@Api(value = "consumer")
public class ConsumerController {

    @Autowired
    private ProviderService provicerService;

    @GetMapping
    @ApiOperation(value = "consumer",notes="consumer")
    public String consumer(@ApiParam(name = "id",required = true) @RequestParam(name = "id" , required=true) Integer id ){

        String res = provicerService.getId(""+id);
        System.out.println(res);
        return res;
    }
}
