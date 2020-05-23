package com.plant.database.mapper;

import com.plant.database.model.bean.User;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author jingyu chen
 */
@Component
public interface UserMapper extends Mapper<User> {
}
