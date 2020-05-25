package com.plant.database.mapper;

import com.plant.database.model.dto.PlantDataBean;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 *
 * @author 18044703
 */
@Component
public interface PlantDataBeanMapper extends Mapper<PlantDataBean> {

    /**
     * get item and related info
     *
     * @param itemId
     * @return
     */
    @Select("SELECT\n" +
            "\ta.note_id,\n" +
            "\tb.note_form_id,\n" +
            "\tb.content,\n" +
            "\tc.title,\n" +
            "\t( SELECT title FROM note_class WHERE note_class_id = c.note_class_id ) note_class,\n" +
            "\t(SELECT title FROM item_type WHERE item_type_id = c.item_type_id) item_type,\n" +
            "\tc.display_order \n" +
            "FROM\n" +
            "\tnote a\n" +
            "\tLEFT JOIN note_form b ON a.note_id = b.note_id\n" +
            "\tLEFT JOIN note_type c ON a.note_type_id = c.note_type_id \n" +
            "WHERE\n" +
            "\ta.item_id = #{itemId} \n" +
            "ORDER BY\n" +
            "\tc.display_order ASC;")
    List<PlantDataBean> getPlants(String itemId);
}
