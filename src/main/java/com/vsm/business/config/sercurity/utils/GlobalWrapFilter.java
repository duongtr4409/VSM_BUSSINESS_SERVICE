//package com.vsm.business.config.sercurity.utíls;
//
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletRequestWrapper;
//import java.io.BufferedReader;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.nio.charset.Charset;
//import java.util.stream.Collectors;
//
//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
//public class GlobalWrapFilter implements Filter {
//
////    @Override
////    public void init(FilterConfig filterConfig) throws ServletException {
////    }
////
////    @Override
////    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
////        MultiReadRequest wrapper = new MultiReadRequest((HttpServletRequest) request);
////        chain.doFilter(wrapper, response);
////    }
////
////    @Override
////    public void destroy() {
////    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response,
//                         FilterChain chain) throws IOException, ServletException {
//
//        /* wrap the request in order to read the inputstream multiple times */
//        MultiReadRequest multiReadRequest = new MultiReadRequest((HttpServletRequest) request);
//
//        /* here I read the inputstream and do my thing with it; when I pass the
//         * wrapped request through the filter chain, the rest of the filters, and
//         * request handlers may read the cached inputstream
//         */
//        //doMyThing(multiReadRequest.getInputStream());
//        //OR
//        //anotherUsage(multiReadRequest.getReader());
//        chain.doFilter(multiReadRequest, response);
//    }
//}
