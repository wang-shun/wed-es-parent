package com.cip.wed;

import org.elasticsearch.action.ActionRequestBuilder;

/**
 * Created by huachao on 3/1/16.
 */
public class DefaultWedEsBaseClient extends AbstractWedEsBaseClient {
    @Override
    protected <T> void fill(ActionRequestBuilder requestBuilder, T obj) {
        //do nothing
    }
}
