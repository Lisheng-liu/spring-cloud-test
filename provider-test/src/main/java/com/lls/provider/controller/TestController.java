package com.lls.provider.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logs")
@Api(value = "获取日志")
public class TestController {

    @Value("${server.id}")
    private String serverId;

    @GetMapping
    @ApiOperation(value = "获取日志",notes="获取日志")
    public String getLog(@ApiParam(name = "id",required = true) @RequestParam(name = "id" , required=true) Integer id ){
        return serverId+"_log_"+id;
    }

}
