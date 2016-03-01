package com.cip.wed.search;

import com.dianping.core.type.PageModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by huachao on 2/29/16.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:config/spring/appcontext-*.xml"})
public class WedEsSearchTest {

    @Resource
    private WedEsSearch wedEsSearch;

    @Test
    public void testSearch(){
        String query = "{\n" +
                "  \"match_all\" : { }\n" +
                "}";
        PageModel pageModel = wedEsSearch.search("bank", "account", query, null, null, 0, 10);
        Assert.assertTrue(pageModel!=null);
    }

}
