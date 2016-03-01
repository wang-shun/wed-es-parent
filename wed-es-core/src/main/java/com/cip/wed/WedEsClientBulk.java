package com.cip.wed;

import org.elasticsearch.action.ActionRequestBuilder;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by huachao on 2/26/16.
 */
public class WedEsClientBulk extends AbstractWedEsBaseClient {

    private static final Logger logger = LoggerFactory.getLogger(WedEsClientBulk.class);

    @Resource
    WedEsClientIndex wedEsClientIndex;
    @Resource
    WedEsClientUpdate wedEsClientUpdate;
    @Resource
    WedEsClientDelete wedEsClientDelete;

    public List<Boolean> bulk(String indexName,
                                  String type,
                                  List<String> ids,
                                  WedEsClientRequest.OP op,
                                  List<?> objs){
        List<WedEsClientRequest> requests = WedEsClientRequest.buildRequestList(indexName, type, ids, op, objs);
        return bulk(requests, -1);
    }

    public List<Boolean> bulk(List<WedEsClientRequest> requests, long timeoutMS) {
        //准备
        BulkRequestBuilder bulkRequestBuilder = client().prepareBulk();
        //填充
        fill(bulkRequestBuilder, requests);
        //请求
        BulkResponse bulkResponse = (BulkResponse) response(bulkRequestBuilder, timeoutMS);
        //返回
        List<Boolean> ret = new ArrayList<Boolean>();
        if (bulkResponse!=null) {
            Iterator<BulkItemResponse> it = bulkResponse.iterator();
            while (it.hasNext()) {
                ret.add(it.next().isFailed());
            }
        }
        return ret;
    }


    @Override
    protected <T> void fill(ActionRequestBuilder requestBuilder, T obj) {
        if (requestBuilder instanceof BulkRequestBuilder) {
            BulkRequestBuilder bulkRequestBuilder = (BulkRequestBuilder) requestBuilder;
            List<WedEsClientRequest> requests = (List<WedEsClientRequest>) obj;
            for (WedEsClientRequest request : requests) {
                ActionRequestBuilder actionRequestBuilder = prepare(request.getIndexName(),
                                                                    request.getType(),
                                                                    request.getId(),
                                                                    request.getOp());
                if (actionRequestBuilder instanceof UpdateRequestBuilder) {
                    wedEsClientUpdate.fill(actionRequestBuilder, request.getObj());;
                    bulkRequestBuilder.add((UpdateRequestBuilder) actionRequestBuilder);
                } else if (actionRequestBuilder instanceof DeleteRequestBuilder){
                    wedEsClientDelete.fill(actionRequestBuilder, request.getObj());
                    bulkRequestBuilder.add((DeleteRequestBuilder) actionRequestBuilder);
                } else if (actionRequestBuilder instanceof IndexRequestBuilder) {
                    wedEsClientIndex.fill(actionRequestBuilder, request.getObj());
                    bulkRequestBuilder.add((IndexRequestBuilder) actionRequestBuilder);
                }
            }
        }
    }
}
