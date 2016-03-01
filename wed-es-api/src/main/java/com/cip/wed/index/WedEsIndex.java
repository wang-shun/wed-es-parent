package com.cip.wed.index;

import java.util.List;

/**
 * Created by huachao on 2/29/16.
 */
public interface WedEsIndex {

    /**
     * 批量索引
     * @param indexName 索引名,必须全部小写
     * @param type type名,必须全部小写
     * @param ids 每个document的id
     * @param objs dtos
     * @param <?> DTO的class
     * @return [isFail, isFail, ...]
     */
    List<Boolean> bulkIndex(String indexName, String type, List<String> ids, List<?> objs);

}
