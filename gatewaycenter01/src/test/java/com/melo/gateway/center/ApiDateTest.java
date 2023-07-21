package com.melo.gateway.center;

import com.melo.gateway.center.api.IApiService;
import com.melo.gateway.center.domain.model.ApiData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ApiDateTest {

    @Resource
    public IApiService apiService;

    @Test
    public void testList() {
        List<ApiData> httpStatementList = apiService.getHttpStatementList();
        System.out.println(httpStatementList);
    }
}
