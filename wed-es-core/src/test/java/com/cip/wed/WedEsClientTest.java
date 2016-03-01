package com.cip.wed;

import com.google.common.collect.Lists;
import dto.Internal;
import dto.Wrapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huachao on 2/29/16.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/spring/appcontext-*.xml"})
public class WedEsClientTest {

    @Resource
    private WedEsClientIndex wedEsClientIndex;
    @Resource
    private WedEsClientGet wedEsClientGet;
    @Resource
    private WedEsClientDelete wedEsClientDelete;
    @Resource
    private WedEsClientUpdate wedEsClientUpdate;
    @Resource
    private WedEsClientBulk wedEsClientBulk;
    @Resource
    private WedEsClientSearch wedEsClientSearch;

    @Test
    public void testIndexOneAndGetOne() {
        Wrapper wrapper = oneWrapper();
        String id = wrapper.getNo()+"";
        boolean indexed = wedEsClientIndex.index("indexname-1", "type-1", wrapper.getNo()+"", wrapper, -1);
        Assert.assertEquals(true, indexed);
        Wrapper wrapper1 = wedEsClientGet.get("indexname-1", "type-1", wrapper.getNo() + "", Wrapper.class);
        Assert.assertEquals(wrapper.getDate(), wrapper1.getDate());
    }

    @Test
    public void testUpdate() {
        Wrapper wrapper = oneWrapper();
        String id = wrapper.getNo()+"";
        boolean indexed = wedEsClientIndex.index("indexname-1", "type-1", wrapper.getNo()+"", wrapper, -1);
        Assert.assertEquals(true, indexed);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "testUpdate");
        boolean updated = wedEsClientUpdate.update("indexname-1", "type-1", wrapper.getNo()+"", map);
        Assert.assertEquals(true, updated);

        Wrapper get = wedEsClientGet.get("indexname-1", "type-1", wrapper.getNo() + "", Wrapper.class);
        Assert.assertEquals(get.getName(), "testUpdate");

        boolean deleted = wedEsClientDelete.delete("indexname-1", "type-1", wrapper.getNo() + "");
        Assert.assertEquals(true, deleted);
    }

    @Test
    public void testBulk(){
        String oldNo = "345";
        Wrapper wrapper = oneWrapper();
        List<String> ids = Lists.newArrayList(wrapper.getNo()+"","345");
        List<Object> objects = Lists.newArrayList((Object) wrapper, (Object) new HashMap<String, Object>() {{
            put("name", "testUpdate2");
        }});
        List<Boolean> ret = wedEsClientBulk.bulk("indexname-1", "type-1", ids, WedEsClientRequest.OP.INDEX, objects);
        Assert.assertTrue(ret != null && ret.size() == 2);

        Wrapper get = wedEsClientGet.get("indexname-1", "type-1", "345", Wrapper.class);
        Assert.assertEquals(get.getName(), "testUpdate2");
    }

    @Test
    public void testSearch_matchall() {
        String query = "{\n" +
                "  \"match_all\" : { }\n" +
                "}";
        SearchResponse searchResponse = wedEsClientSearch.search("bank", "account", query, null, null, 0, 10);
        Assert.assertTrue(searchResponse != null);

    }

    private Internal oneInternal() {
        Internal internal = new Internal();
        internal.setaDouble(RandomUtils.nextDouble(0.0d, 10000.0d));
        internal.setName(RandomStringUtils.random(5));
        internal.setStrArray(new String[]{RandomStringUtils.random(RandomUtils.nextInt(1, 10)),
                RandomStringUtils.random(RandomUtils.nextInt(1, 10)), RandomStringUtils.random(RandomUtils.nextInt(1, 10)),
                RandomStringUtils.random(RandomUtils.nextInt(1, 10)), RandomStringUtils.random(RandomUtils.nextInt(1, 10))});
        return internal;
    }

    private Wrapper oneWrapper() {
        Wrapper wrapper = new Wrapper();
        wrapper.setName(RandomStringUtils.random(5));
        wrapper.setDate(new Date());
        wrapper.setNo(RandomUtils.nextInt(0, 10000));
        wrapper.setIntList(new int[]{});
        Internal[] internals = new Internal[3];
        for (int i = 0;i<3;++i) {
            internals[i] = oneInternal();
        }
        wrapper.setInternals(internals);
        return wrapper;
    }

}
