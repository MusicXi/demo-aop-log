package com.myron.ims.constants;

public class MDCConstants {

    public static final String REQUEST_ID_HEADER = "X-Request-ID";

    public static final String REQUEST_SEQ_HEADER = "X-Request-Seq";

    public static final String REQUEST_ID_MDC_KEY = "requestId";

    public static final String REQUEST_SEQ_MDC_KEY = "requestSeq";

    //追踪链下发时，使用的seq，由Filter生成，通常开发者不需要修改它。
    public static final String NEXT_REQUEST_SEQ_MDC_KEY = "nextRequestSeq";

    public static final String LOCAL_IP_MDC_KEY = "localIp";

    public static final String URI_MDC_KEY = "uri";

    public static final String TIMESTAMP = "_timestamp_";//进入filter的时间戳

    public static final String COOKIE_KEY_PREFIX = "_C_";

    public static final String HEADER_KEY_PREFIX = "_H_";

    public static final String PARAMETER_KEY_PREFIX = "_P_";
}