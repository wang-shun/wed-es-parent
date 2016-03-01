package com.cip.wed.search;

import com.dianping.core.type.PageModel;

/**
 * Created by huachao on 3/1/16.
 */
public interface WedEsSearch {

    /**
     * 搜索
     * @param indexName 索引名,全部小写
     * @param type type名,全部小写
     * @param query query的json字符串
     * @param postFilter filter的json字符串
     * @param sort sort的json字符串
     * @param from 分页起始数
     * @param size 分页大小
     * @return
     */
    public PageModel search(String indexName, String type, String query, String postFilter, String sort, int from, int size);
}
