package com.lls.scheduler;

import com.lls.util.IpUtil;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Profile({"dev","test","uat","prod"})
@Component
public class SchedulerTask {

    @Autowired
    private SpringClientFactory factory;
    @Value("${spring.application.name}")
    private String appName;

    @Scheduled(cron="${task.cron}")
//    @Scheduled(initialDelay = 10*1000,fixedDelay = 24*60*60*1000)
    public void work(){
        String address = IpUtil.getHostIpv4();
        ILoadBalancer loadBlander = factory.getLoadBalancer(appName);
        List<Server> allServers = loadBlander.getAllServers();
        List<Server> sortedServers = allServers.stream().sorted(Comparator.comparing(Server::getHost)).collect(Collectors.toList());
        if(StringUtils.isNotBlank(address) && address.equals(sortedServers.get(0).getHost())){
            // work
            System.out.println("work ... ");
        }
    }
}
