package com.plant.database.common;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Config
 *
 * @author chenjingyu
 * @date 2020/5/15
 */
@Component
@ConfigurationProperties(prefix = "project.xml")
public class Config {

    private String path;

    private String names;


    public List<String> getNames() {
        return Arrays.asList(names.split(","));
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setNames(String names) {
        this.names = names;
    }
}
