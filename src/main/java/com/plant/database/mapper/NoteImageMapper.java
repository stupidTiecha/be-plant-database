package com.plant.database.mapper;

import com.plant.database.model.bean.NoteImage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author jingyu chen
 */
@Component
public interface NoteImageMapper extends Mapper<NoteImage> {

    /**
     * 通过noteImageId 集合查询 图片地址
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
