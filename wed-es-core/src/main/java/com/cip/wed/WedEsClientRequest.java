package com.cip.wed;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huachao on 2/29/16.
 */
public class WedEsClientRequest {
    public enum OP {
        INDEX, GET, DELETE, UPDATE, BULK
    }
    private String indexName;
    private String type;
    private String id;
    private OP op;
    private Object obj;

    public WedEsClientRequest(String indexName, String type, String id, OP op, Object obj){
        this.indexName = indexName;
        this.type = type;
        this.id = id;
        this.op = op;
        this.obj = obj;
    }

    public static List<WedEsClientRequest> buildRequestList(String indexNames,
                                                            String types,
                                                            List<String> ids,
                                                            OP ops,
                                                            List<?> objs){
        List<WedEsClientRequest> requests = null;
        if (ids.size() == objs.size()){
            requests = new ArrayList<WedEsClientRequest>();
            for (int i=0; i<ids.size(); ++i){
                WedEsClientRequest wedEsClientRequest = new WedEsClientRequest( indexNames,
                                                                                types,
                                                                                ids.get(i),
                                                                                ops,
                                                                                objs.get(i));
                requests.add(wedEsClientRequest);
            }
        }
        return requests;
    }

    public String getIndexName() {
        return indexName;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public OP getOp() {
        return op;
    }

    public Object getObj() {
        return obj;
    }
}
