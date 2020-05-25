package com.plant.database.mapper;

import com.plant.database.model.bean.NoteFormNoteImage;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 18044703
 */
@Component
public interface NoteFormNoteImageMapper extends Mapper<NoteFormNoteImage> {

    /**
     *  get all related image infos by noteFormId
     * @param noteFormId
     * @return
     */
    @Select("SELECT * FROM note_form_note_image WHERE note_form_id = #{noteFormId} ")
    List<NoteFormNoteImage> selectByNoteFormId(String noteFormId);
}
