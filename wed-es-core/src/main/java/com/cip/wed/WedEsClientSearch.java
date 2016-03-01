package com.cip.wed;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WrapperQueryBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;

import java.util.List;

/**
 * Created by huachao on 3/1/16.
 */
public class WedEsClientSearch extends DefaultWedEsBaseClient {

    public SearchResponse search(String indexName, String type, String query, String postFilter, String sortField, int from, int size){
        WrapperQueryBuilder queryWrapper = null;
        if (StringUtils.isNotBlank(query)) {
            queryWrapper = QueryBuilders.wrapperQuery(query);
        }
        WrapperQueryBuilder postFilterWrapper = null;
        if (StringUtils.isNotBlank(postFilter)) {
            postFilterWrapper = QueryBuilders.wrapperQuery(postFilter);
        }
        SortBuilder sortBuilder = null;
        if (StringUtils.isNotBlank(sortField)) {
            sortBuilder = SortBuilders.fieldSort(sortField);
        }
        return search(indexName, type, queryWrapper, postFilterWrapper, sortBuilder , from, size);
    }

    public SearchResponse search(String indexName, String type, QueryBuilder query, QueryBuilder postFilter, SortBuilder sortBuilder, int from, int size){
        List<String> indexNames = Lists.newArrayList(indexName);
        List<String> types = Lists.newArrayList(type);
        return search(indexNames, types, query, postFilter, sortBuilder, from, size, -1);
    }

    public SearchResponse search(List<String> indexNames, List<String> types, QueryBuilder query, QueryBuilder postFilter, SortBuilder sortBuilder, int from, int size, long timeoutMS) {
        //准备
        SearchRequestBuilder searchRequestBuilder = client().prepareSearch();
        for (String indexName : indexNames) {
            searchRequestBuilder.setIndices(indexName);
        }
        for (String type : types) {
            searchRequestBuilder.setTypes(type);
        }
        if (query != null) {
            searchRequestBuilder.setQuery(query);
        }
        if (postFilter != null) {
            searchRequestBuilder.setPostFilter(postFilter);
        }
        if (sortBuilder != null) {
            searchRequestBuilder.addSort(sortBuilder);
        }
        searchRequestBuilder.setFrom(from);
        searchRequestBuilder.setSize(size);
        //搜索
        SearchResponse searchResponse = null;
        if (timeoutMS > 0) {
            searchResponse = searchRequestBuilder.execute().actionGet(new TimeValue(timeoutMS));
        } else {
            searchResponse = searchRequestBuilder.execute().actionGet();
        }
        //返回
        return searchResponse;
    }
}
