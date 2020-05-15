package com.plant.database;

import com.plant.database.api.PlantDataApi;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = BePlantDatabaseApplication.class)
@RunWith(SpringRunner.class)
class BePlantDatabaseApplicationTests {

    @Autowired
    private PlantDataApi plantDataApi;


    @Test
    void contextLoads() {
    }

    @Test
    void test1 () {
       plantDataApi.transformData();
    }
}
