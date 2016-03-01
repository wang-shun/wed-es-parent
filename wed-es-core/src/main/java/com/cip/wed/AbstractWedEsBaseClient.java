package com.cip.wed;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.ActionRequestBuilder;
import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by huachao on 2/29/16.
 */
public abstract  class AbstractWedEsBaseClient {

    private static final Logger logger = LoggerFactory.getLogger(AbstractWedEsBaseClient.class);

    @Resource
    private WedEsTransportClient wedEsTransportClient;

    protected Client client() {
        return wedEsTransportClient.getTransportClient();
    }

    protected ObjectMapper mapper(){
        return wedEsTransportClient.getMapper();
    }

    /**** For Document API ****/

    protected ActionRequestBuilder prepare(String indexName, String type, String id, WedEsClientRequest.OP op){
        switch (op) {
            case INDEX:
                return client().prepareIndex(indexName, type, id);
            case GET:
                return client().prepareGet(indexName, type, id);
            case DELETE:
                return client().prepareDelete(indexName, type, id);
            case UPDATE:
                return client().prepareUpdate(indexName, type, id);
        }
        return null;
    }

    protected abstract <T> void fill(ActionRequestBuilder requestBuilder, T obj);

    protected ActionResponse response(ActionRequestBuilder requestBuilder, long timeoutMS) {
        if (requestBuilder == null)
            return null;
        if (timeoutMS > 0) {
            return requestBuilder.get(new TimeValue(timeoutMS));
        } else {
            return requestBuilder.get();
        }
    }


}
