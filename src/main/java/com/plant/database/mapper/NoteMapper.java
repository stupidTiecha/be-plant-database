package com.plant.database.mapper;

import com.plant.database.model.bean.Note;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author 18044703
 */
@Component
public interface NoteMapper extends Mapper<Note> {
}
