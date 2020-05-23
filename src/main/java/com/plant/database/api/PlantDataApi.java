package com.plant.database.api;

import com.plant.database.common.Consts;
import com.plant.database.model.bean.Response;
import com.plant.database.model.dto.SearchReq;
import com.plant.database.service.PlantDataService;
import org.springframework.web.bind.annotation.*;



/**
 * PlantDataApi
 *
 * @author chenjingyu
 * @date 2020/5/15
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api")
public class PlantDataApi {

    private final PlantDataService plantDataService;

    public PlantDataApi(PlantDataService plantDataService) {
        this.plantDataService = plantDataService;
    }


    @PostMapping(value = "search")
    public Response search(@RequestBody SearchReq params) {

        params.getSearchContent().put(Consts.SEARCH_SCOPE,params.getSearchScope());
        if (params.getSearchType() == Consts.SearchMode.SEARCH_ALL) {
            return plantDataService.searchAll(params.getSearchContent());
        } else if (params.getSearchType() == Consts.SearchMode.SEARCH_BY_TOPIC) {
            return plantDataService.searchByTopic(params.getSearchContent());
        } else if (params.getSearchType() == Consts.SearchMode.SEARCH_BY_FIELD) {
            return plantDataService.searchByField(params.getSearchContent());
        } else {
            return new Response();
        }
    }

    @GetMapping(value = "plant")
    public Response getPlant(@RequestParam("itemId") String itemId) {
        return plantDataService.getPlant(itemId);
    }

    @GetMapping(value = "transformDataToMysql")
    public Response transformDataToMysql() {

        if(plantDataService.transformDataToMysql()) {
            //成功
            return new Response("success");
        }
        //失败
        return new Response("failed");
    }

    @GetMapping(value = "transformDataToSolr")
    public Response transformDataToSolr () {
        if(plantDataService.transformDataToSolr()) {
            //成功
            return new Response("success");
        }
        //失败
        return new Response("failed");
    }
}
