package com.cip.wed.index;

import com.cip.wed.WedEsClientBulk;
import com.cip.wed.WedEsClientRequest;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by huachao on 2/29/16.
 */
public class WedEsIndexImpl implements WedEsIndex{

    @Resource
    private WedEsClientBulk wedEsClientBulk;

    @Override
    public List<Boolean> bulkIndex(String indexName, String type, List<String> ids, List<?> objs) {
        return wedEsClientBulk.bulk(indexName, type, ids, WedEsClientRequest.OP.INDEX, objs);
    }
}
