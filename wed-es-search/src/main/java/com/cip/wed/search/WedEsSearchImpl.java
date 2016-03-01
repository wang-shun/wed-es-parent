package com.cip.wed.search;

import com.cip.wed.WedEsClientSearch;
import com.dianping.core.type.PageModel;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huachao on 3/1/16.
 */
public class WedEsSearchImpl implements WedEsSearch {

    @Resource
    private WedEsClientSearch wedEsClientSearch;

    /**
     * 搜索
     *
     * @param indexName  索引名,全部小写
     * @param type       type名,全部小写
     * @param query      query的json字符串
     * @param postFilter filter的json字符串
     * @param sort       sort的json字符串
     * @param pageNo     起始页
     * @param pageSize   分页大小
     * @return
     */
    @Override
    public PageModel search(String indexName, String type, String query, String postFilter, String sort, int pageNo, int pageSize) {
        SearchResponse searchResponse = wedEsClientSearch.search(indexName, type, query, postFilter, sort, pageNo*pageSize, pageSize);
        PageModel pageModel = new PageModel();
        if (searchResponse != null && searchResponse.getHits() != null) {
            pageModel.setPage(pageNo);
            pageModel.setPageSize(pageSize);
            pageModel.setRecordCount((int) searchResponse.getHits().getTotalHits());
            List<Map<String, Object>> records = new ArrayList<Map<String, Object>>();
            for(SearchHit hit : searchResponse.getHits().hits()){
                records.add(hit.sourceAsMap());
            }
            pageModel.setRecords(records);
        }
        return pageModel;
    }
}
