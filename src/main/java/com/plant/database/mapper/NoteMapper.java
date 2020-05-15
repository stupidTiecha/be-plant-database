package com.plant.database.mapper;

import com.plant.database.model.bean.Note;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author jingyu chen
 */
@Component
public interface NoteMapper extends Mapper<Note> {
}
