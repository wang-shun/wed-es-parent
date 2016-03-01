package com.cip.wed.index;

import com.google.common.collect.Lists;
import dto.Internal;
import dto.Wrapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by huachao on 2/29/16.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:config/spring/appcontext-*.xml"})
public class WedEsIndexTest {

    @Resource
    private WedEsIndex wedEsIndex;

    @Test
    public void testBulkIndex(){
        Wrapper wrapper1 = oneWrapper();
        Wrapper wrapper2 = oneWrapper();
        List<String> ids = Lists.newArrayList(wrapper1.getNo() + "", wrapper2.getNo() + "");
        List<Object> objects = Lists.newArrayList((Object) wrapper1, (Object) wrapper2);
        List<Boolean> ret = wedEsIndex.bulkIndex("indexname-1", "type-1", ids, objects);
        Assert.assertTrue(ret!= null && ret.size() == 2);
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
