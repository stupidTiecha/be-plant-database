package com.plant.database.mapper;

import com.plant.database.model.bean.NoteImage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 18044703
 */
@Component
public interface NoteImageMapper extends Mapper<NoteImage> {

    /**
     * get image path by imageIds
     *
     * @param imageIds
     * @return
     */
    @Select("<script>\n" +
            "\tselect\n" +
            "\t*\n" +
            "\tfrom note_image\n" +
            "\twhere note_image_id\n" +
            "\tin\n" +
            "\t<foreach collection=\"imageIds\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\">\n" +
            "\t    #{item}\n" +
            "\t</foreach>\n" +
            "</script>")
    List<NoteImage> selectByNoteImageIds(@Param("imageIds") List<String> imageIds);
}
