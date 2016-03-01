package com.cip.wed;

import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.cip.wed.WedEsClientRequest.OP.DELETE;

/**
 * Created by huachao on 2/26/16.
 */
public class WedEsClientDelete extends DefaultWedEsBaseClient {

    private static final Logger logger = LoggerFactory.getLogger(WedEsClientDelete.class);

    public <T> boolean delete(String indexName, String type, String id){
        return delete(indexName, type, id, -1);
    }

    public <T> boolean delete(String indexName, String type, String id, long timeoutMS) {
        //准备
        DeleteRequestBuilder deleteRequestBuilder = (DeleteRequestBuilder) prepare(indexName, type, id, DELETE);
        //请求
        DeleteResponse deleteResponse = (DeleteResponse) response(deleteRequestBuilder, timeoutMS);
        //返回
        if (deleteResponse!=null) {
            return deleteResponse.isFound();
        }
        return false;
    }

}
