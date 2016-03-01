package com.cip.wed;

import org.elasticsearch.action.ActionRequestBuilder;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * Created by huachao on 2/26/16.
 */
public class WedEsClientUpdate extends AbstractWedEsBaseClient {

    private static final Logger logger = LoggerFactory.getLogger(WedEsClientUpdate.class);

    public <T> boolean update(String indexName, String type, String id, Map<String, Object> map) {
        return update(indexName, type, id, map, -1);
    }

    public <T> boolean update(String indexName, String type, String id, Map<String, Object> map, long timeoutMS) {
        //准备
        UpdateRequestBuilder updateRequestBuilder = (UpdateRequestBuilder) prepare(indexName, type, id, WedEsClientRequest.OP.UPDATE);
        //填充
        fill(updateRequestBuilder, map);
        //请求
        UpdateResponse updateResponse = (UpdateResponse) response(updateRequestBuilder, timeoutMS);
        //返回
        if (updateResponse!=null && !updateResponse.isCreated()) {
            return true;
        }
        return false;
    }

    @Override
    protected <T> void fill(ActionRequestBuilder requestBuilder, T obj) {
        if (requestBuilder instanceof UpdateRequestBuilder) {
            UpdateRequestBuilder updateRequestBuilder = (UpdateRequestBuilder) requestBuilder;
            Map<String, Object> map = (Map<String, Object>) obj;
            XContentBuilder jsonBuilder = null;
            try {
                jsonBuilder = XContentFactory.jsonBuilder();
                jsonBuilder = jsonBuilder.startObject();
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    jsonBuilder.field(entry.getKey(), entry.getValue());
                }
                jsonBuilder.endObject();
            } catch (IOException e) {
                logger.error("jsonBuilder error", e);
            }
            updateRequestBuilder.setDoc(jsonBuilder);
        }
    }
}
