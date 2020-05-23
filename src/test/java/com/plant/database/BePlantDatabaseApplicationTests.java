package com.plant.database;

import com.plant.database.api.PlantDataApi;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@SpringBootTest(classes = BePlantDatabaseApplication.class)
@RunWith(SpringRunner.class)
class BePlantDatabaseApplicationTests {

    @Autowired
    private PlantDataApi plantDataApi;

    @Autowired
    private SolrClient solrClient;


    @Test
    void contextLoads() {
    }

    @Test
    void test1 () {
//       plantDataApi.transformDataToMysql();
    }

    @Test
    void test2 () {
//       plantDataApi.transformDataToSolr();
    }

    @Test
    void test3 () {
//        SolrInputDocument solrInputFields = new SolrInputDocument();
//        solrInputFields.addField("item_id","955264652132548997");
//        solrInputFields.addField("item_topics","789456");
//        solrInputFields.addField("item_topics","123456789");
//        solrInputFields.addField("item_topics","456789132");
//        solrInputFields.addField("item_topics","564782139");
//        solrInputFields.addField("item_topics","54213456");
//        solrInputFields.addField("item_topics","789545678");
//        try {
//            solrClient.add(solrInputFields);
//            UpdateResponse commit = solrClient.commit();
//            System.out.println(commit.toString());
//        } catch (SolrServerException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            System.err.println(11111);
//            e.printStackTrace();
//        }
    }@Test
    void test4 () {
//        try {
//            solrClient.deleteById("955264652132548997");
//            UpdateResponse commit = solrClient.commit();
//            System.out.println(commit.toString());
//        } catch (SolrServerException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            System.err.println(11111);
//            e.printStackTrace();
//        }
    }
}
