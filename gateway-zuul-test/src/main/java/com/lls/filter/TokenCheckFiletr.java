package com.lls.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.http.HttpServerConnection;
import org.apache.http.protocol.RequestContent;

import javax.servlet.http.HttpServletRequest;

public class TokenCheckFiletr extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext requestContent = RequestContext.getCurrentContext();
        HttpServletRequest httpServletRequest = requestContent.getRequest();
        System.out.println("url:"+httpServletRequest.getRequestURL());

        String user = httpServletRequest.getParameter("user");
        if(!"test".equals(user)){
            requestContent.setSendZuulResponse(false);
            requestContent.getResponse().setContentType("application/json;charset=UTF-8");
            requestContent.setResponseBody("{\"code\":\"100404\",\"msg\":\"errUser\"");
        }

        return null;
    }
}
