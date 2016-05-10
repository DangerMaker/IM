package com.ez08.im.net;

/**
 * User: lyjq(1752095474)
 * Date: 2016-05-09
 */
public class HttpRequestFactory {

    RestApi restApi = new RestApiImpl();

    private volatile static HttpRequestFactory instance;

    public static HttpRequestFactory getInstance() {
        if (instance == null) {
            synchronized (HttpRequestFactory.class) {
                if (instance == null)
                    instance = new HttpRequestFactory();
            }
        }
        return instance;
    }

    public RestApi getHttpImpl(){
        return restApi;
    }

}
