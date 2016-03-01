package com.cip.wed;

import org.elasticsearch.action.ActionRequestBuilder;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by huachao on 2/26/16.
 */
public class WedEsClientGet extends DefaultWedEsBaseClient {

    private static final Logger logger = LoggerFactory.getLogger(WedEsClientGet.class);

    public <T> T get(String indexName, String type, String id, Class<T> clazz){
        return get(indexName, type, id, clazz, -1);
    }

    public <T> T get(String indexName, String type, String id, Class<T> clazz, long timeoutMS) {
        //准备
        GetRequestBuilder getRequestBuilder = (GetRequestBuilder) prepare(indexName, type, id, WedEsClientRequest.OP.GET);
        //请求
        GetResponse getResponse = (GetResponse)response(getRequestBuilder, timeoutMS);
        //返回
        T result = null;
        if (getResponse!=null && getResponse.isExists()) {
            byte[] resultBytes = getResponse.getSourceAsBytes();
            try {
                result = mapper().readValue(resultBytes, clazz);
            } catch (Exception e) {
                logger.error("bytes to Object fail", e);
            }
        }
        return result;
    }

}
