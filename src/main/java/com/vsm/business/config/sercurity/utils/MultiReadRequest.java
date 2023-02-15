//package com.vsm.business.config.sercurity.utíls;
//
//import org.apache.commons.io.IOUtils;
//
//import javax.servlet.ReadListener;
//import javax.servlet.ServletInputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletRequestWrapper;
//import java.io.*;
//import java.nio.charset.Charset;
//import java.util.stream.Collectors;
//
//public class MultiReadRequest extends HttpServletRequestWrapper {
//
////    private String requestBody;
////
////    public MultiReadRequest(HttpServletRequest request) {
////        super(request);
////        try {
////            requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////    }
////
////    @Override
////    public ServletInputStream getInputStream() throws IOException {
////        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(requestBody.getBytes(Charset.forName("UTF-8")));
////        return new ServletInputStream() {
////            @Override
////            public boolean isFinished() {
////                return byteArrayInputStream.available() == 0;
////            }
////
////            @Override
////            public boolean isReady() {
////                return true;
////            }
////
////            @Override
////            public void setReadListener(ReadListener readListener) {
////
////            }
////
////            @Override
////            public int read() throws IOException {
////                return byteArrayInputStream.read();
////            }
////        };
////    }
////
////    @Override
////    public BufferedReader getReader() throws IOException {
////        return new BufferedReader(new InputStreamReader(this.getInputStream(), Charset.forName("UTF-8")));
////    }
//
//    private ByteArrayOutputStream cachedBytes;
//
//    public MultiReadRequest(HttpServletRequest request) {
//        super(request);
//    }
//
//    @Override
//    public ServletInputStream getInputStream() throws IOException {
//        if (cachedBytes == null)
//            cacheInputStream();
//
//        return new CachedServletInputStream(cachedBytes.toByteArray());
//    }
//
//    @Override
//    public BufferedReader getReader() throws IOException{
//        return new BufferedReader(new InputStreamReader(getInputStream()));
//    }
//
//    private void cacheInputStream() throws IOException {
//        /* Cache the inputstream in order to read it multiple times. For
//         * convenience, I use apache.commons IOUtils
//         */
//        cachedBytes = new ByteArrayOutputStream();
//        IOUtils.copy(super.getInputStream(), cachedBytes);
//    }
//
//
//    /* An input stream which reads the cached request body */
//    private static class CachedServletInputStream extends     ServletInputStream {
//
//        private final ByteArrayInputStream buffer;
//
//        public CachedServletInputStream(byte[] contents) {
//            this.buffer = new ByteArrayInputStream(contents);
//        }
//
//        @Override
//        public int read() {
//            return buffer.read();
//        }
//
//        @Override
//        public boolean isFinished() {
//            return buffer.available() == 0;
//        }
//
//        @Override
//        public boolean isReady() {
//            return true;
//        }
//
//        @Override
//        public void setReadListener(ReadListener listener) {
//            throw new RuntimeException("Not implemented");
//        }
//    }
//}
