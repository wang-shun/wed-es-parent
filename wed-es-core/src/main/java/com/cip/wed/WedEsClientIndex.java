package com.cip.wed;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.elasticsearch.action.ActionRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by huachao on 2/26/16.
 */
public class WedEsClientIndex extends AbstractWedEsBaseClient {

    private static final Logger logger = LoggerFactory.getLogger(WedEsClientIndex.class);

    public <T> boolean index(String indexName, String type, String id, T obj){
        return index(indexName, type, id, -1);
    }

    public <T> boolean index(String indexName, String type, String id, T obj, long timeoutMS) {
        //准备
        IndexRequestBuilder indexRequestBuilder = (IndexRequestBuilder) prepare(indexName, type, id, WedEsClientRequest.OP.INDEX);
        //填充
        fill(indexRequestBuilder, obj);
        //请求
        IndexResponse response = (IndexResponse) response(indexRequestBuilder, timeoutMS);
        //返回
        if (response!=null) {
            return response.isCreated();
        }
        return false;
    }

    @Override
    protected <T> void fill(ActionRequestBuilder requestBuilder, T obj) {
        if (requestBuilder instanceof IndexRequestBuilder) {
            IndexRequestBuilder indexRequestBuilder = (IndexRequestBuilder) requestBuilder;
            byte[] json = null;
            try {
                json = mapper().writeValueAsBytes((Object) obj);
            } catch (JsonProcessingException e) {
                logger.error("DTO序列化失败"+obj.toString(), e);
            }
            indexRequestBuilder.setSource(json);
        }
    }
}
