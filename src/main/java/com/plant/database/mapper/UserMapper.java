package com.plant.database.mapper;

import com.plant.database.model.bean.User;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author 18044703
 */
@Component
public interface UserMapper extends Mapper<User> {
}
