package com.apoem.mmxx.eventtracking.admin.trial;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestServiceImplTest {

    @Autowired
    private TestService testService;

    @Test
    public void staTestTask() {
        testService.staTestService();
    }

    @Test
    public void staTestDatabase() {
    }

    @Test
    public void staTestService() {
    }

    @Test
    public void acqTestService() {
    }
}