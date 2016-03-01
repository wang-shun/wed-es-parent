package com.cip.wed.index;

import com.cip.wed.WedEsClientBulk;
import com.cip.wed.WedEsClientRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by huachao on 2/29/16.
 */
public class WedEsIndexImpl implements WedEsIndex{

    private Logger logger = LoggerFactory.getLogger(WedEsIndexImpl.class);

    @Resource
    private WedEsClientBulk wedEsClientBulk;

    @Override
    public List<Boolean> bulkIndex(String indexName, String type, List<String> ids, List<?> objs) {
        logger.warn("bulkIndex");
        return wedEsClientBulk.bulk(indexName, type, ids, WedEsClientRequest.OP.INDEX, objs);
    }
}
