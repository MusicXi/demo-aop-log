package com.myron.ims.filter;

import com.myron.ims.constants.MDCConstants;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.UUID;

/**
 * Created by liuguanqing on 16/12/28.
 * 在logback日志输出中增加MDC参数选项
 * 注意，此Filter尽可能的放在其他Filter之前
 *
 * 默认情况下，将会把“requestId”、“requestSeq”、“localIp”、“timestamp”、“uri”添加到MDC上下文中。
 * 1）其中requestId，requestSeq为调用链跟踪使用，开发者不需要手动修改他们。
 * 2）localIp为当前web容器的宿主机器的本地IP，为内网IP。
 * 3）timestamp为请求开始被servlet处理的时间戳，设计上为被此Filter执行的开始时间，可以使用此值来判断内部程序执行的效率。
 * 4）uri为当前request的uri参数值。
 *
 * 我们可以在logback.xml文件的layout部分，通过%X{key}的方式使用MDC中的变量
 */
public class HttpRequestMDCFilter implements Filter {

    /**
     *  是否开启cookies映射，如果开启，那么将可以在logback中使用
     *  %X{_C_:<name>}来打印此cookie，比如：%X{_C_:user};
     *  如果开启此选项，还可以使用如下格式打印所有cookies列表:
     *  格式为：key:value,key:value
     *  %X{requestCookies}
     */

    private boolean mappedCookies;
    /**
     * 是否开启headers映射，如果开启，将可以在logback中使用
     * %X{_H_:<header>}来打印此header,比如：%X{_H_:X-Forwarded-For}
     * 如果开启此参数，还可以使用如下格式打印所有的headers列表:
     * 格式为：key:value,key:value
     * %X{requestHeaders}
     */
    private boolean mappedHeaders;

    /**
     * 是否开启parameters映射，此parameters可以为Get的查询字符串，可以为post的Form Entries
     * %X{_P_:<parameter>}来答应此参数值，比如：%X{_P_:page}
     * 如果开启此参数，还可以使用如下格式打印所有的headers列表:
     * 格式为：key:value,key:value
     * %X{requestParameters}
     */
    private boolean mappedParameters;

    private String localIp;//本机IP


    //all headers,content as key:value,key:value
    private static final String HEADERS_CONTENT = "requestHeaders";

    //all cookies
    private static final String COOKIES_CONTENT = "requestCookies";

    //all parameters
    private static final String PARAMETERS_CONTENT = "requestParameters";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        mappedCookies = Boolean.valueOf(filterConfig.getInitParameter("mappedCookies"));
        mappedHeaders = Boolean.valueOf(filterConfig.getInitParameter("mappedHeaders"));
        mappedParameters = Boolean.valueOf(filterConfig.getInitParameter("mappedParameters"));
        //getLocalIp
        localIp = getLocalIp();
    }

    private String getLocalIp() {
        try {
            //一个主机有多个网络接口
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = netInterfaces.nextElement();
                //每个网络接口,都会有多个"网络地址",比如一定会有loopback地址,会有siteLocal地址等.以及IPV4或者IPV6    .
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    //get only :172.*,192.*,10.*
                    if (address.isSiteLocalAddress() && !address.isLoopbackAddress()) {
                        return address.getHostAddress();
                    }
                }
            }
        }catch (Exception e) {
            //
        }
        return null;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        try {
            mdc(request, response);
        } catch (Exception e) {
            //
        }

        try {
            chain.doFilter(request,response);
        } finally {
            MDC.clear();//must be,threadLocal
        }

    }

    private void mdc(HttpServletRequest hsr, HttpServletResponse response) {
        MDC.put(MDCConstants.LOCAL_IP_MDC_KEY,localIp);
        String requestId = StringUtils.isEmpty(hsr.getHeader(MDCConstants.REQUEST_ID_HEADER))? UUID.randomUUID().toString():hsr.getHeader(MDCConstants.REQUEST_ID_HEADER);
        MDC.put(MDCConstants.REQUEST_ID_MDC_KEY, requestId);
        // 设置响应requestId
        if (!StringUtils.isEmpty(requestId)) {
            response.setHeader(MDCConstants.REQUEST_ID_MDC_KEY, requestId);
        }

        String requestSeq = hsr.getHeader(MDCConstants.REQUEST_SEQ_HEADER);
        if(requestSeq != null) {
            String nextSeq = requestSeq + "0";//seq will be like:000,real seq is the number of "0"
            MDC.put(MDCConstants.NEXT_REQUEST_SEQ_MDC_KEY,nextSeq);
        }else {
            MDC.put(MDCConstants.NEXT_REQUEST_SEQ_MDC_KEY,"0");
        }
        MDC.put(MDCConstants.REQUEST_SEQ_MDC_KEY,requestSeq);
        MDC.put(MDCConstants.TIMESTAMP,"" + System.currentTimeMillis());
        MDC.put(MDCConstants.URI_MDC_KEY,hsr.getRequestURI());

        if(mappedHeaders) {
            Enumeration<String> e = hsr.getHeaderNames();
            if(e != null) {
                //
                StringBuffer stringBuffer = new StringBuffer();
                while (e.hasMoreElements()) {
                    String header = e.nextElement();
                    String value = hsr.getHeader(header);
                    MDC.put(MDCConstants.HEADER_KEY_PREFIX + header, value);
                    stringBuffer.append(header).append(":").append(value).append(",");
                    //
                }
               MDC.put(HEADERS_CONTENT, stringBuffer.toString());
            }
        }

        if(mappedCookies) {
            Cookie[] cookies = hsr.getCookies();
            if(cookies != null && cookies.length > 0) {
                //
                for(Cookie  cookie : cookies) {
                    String name = cookie.getName();
                    String value = cookie.getValue();
                    MDC.put(MDCConstants.COOKIE_KEY_PREFIX + name,value);
                    //
                }
               
            }
        }

        if(mappedParameters) {
            Enumeration<String> e = hsr.getParameterNames();
            if(e != null) {
                //
                while (e.hasMoreElements()) {
                    String key = e.nextElement();
                    String value = hsr.getParameter(key);
                    MDC.put(MDCConstants.PARAMETER_KEY_PREFIX + key,value);
                    //
                }
                
            }
        }
    }

    @Override
    public void destroy() {

    }
}
