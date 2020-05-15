package com.plant.database.api;

import com.plant.database.model.bean.Response;
import com.plant.database.service.PlantDataService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * PlantDataApi
 *
 * @author chenjingyu
 * @date 2020/5/15
 */
@Controller("plantData")
public class PlantDataApi {

    private final PlantDataService plantDataService;

    public PlantDataApi(PlantDataService plantDataService) {
        this.plantDataService = plantDataService;
    }

    @GetMapping(value = "transformData")
    public Response transformData () {

        if(plantDataService.transformData()) {
            //成功
            return new Response();
        }
        //失败
        return new Response();
    }
}
