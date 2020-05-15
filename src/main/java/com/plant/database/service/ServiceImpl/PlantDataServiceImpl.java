package com.plant.database.service.ServiceImpl;

import com.plant.database.common.Config;
import com.plant.database.service.PlantDataService;
import com.plant.database.util.XmlUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * PlantDataServiceImpl
 *
 * @author chenjingyu
 * @date 2020/5/15
 */
@Service("plantDataService")
public class PlantDataServiceImpl implements PlantDataService {

    private final Config config;

    private final ApplicationContext context;

    public PlantDataServiceImpl(Config config, ApplicationContext context) {
        this.config = config;
        this.context = context;
    }

    @Override
    @SuppressWarnings("all")
    public boolean transformData() {

        try {
            //从xml中解析获取所有数据
            Map<Class, List<Object>> stringListMap = XmlUtil.convertToJavaBean(config);

            stringListMap.forEach((key , value) -> {
                //插入数据库
                String simpleName = key.getSimpleName();
                String mapperName = simpleName + "Mapper";
                try {
                    Class<?> aClass = Class.forName("com.plant.database.mapper." + mapperName);
                    Object bean = context.getBean(aClass);
                    if (bean instanceof Mapper) {
                        value.parallelStream().forEach(((Mapper) bean)::insert);

                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                //插入solr
            });

            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
