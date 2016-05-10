package com.ez08.im.net;

import retrofit.client.Response;

/**
 * User: lyjq(1752095474)
 * Date: 2016-05-09
 */
public interface Callback <T> {

    /** Successful HTTP response. */
    void success(T t, Response response);

    /**
     * Unsuccessful HTTP response due to network failure, non-2XX status code, or unexpected
     * exception.
     */
    void failure(Error error);
}