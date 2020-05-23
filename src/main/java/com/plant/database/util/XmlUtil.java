package com.plant.database.util;

import com.plant.database.common.Config;
import com.plant.database.model.bean.Citation;
import com.plant.database.model.bean.NoteForm;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * XmlUtil
 *  将xml数据直接转化成bean对象，方便后续存储到数据库和solr
 * @author chenjingyu
 * @date 2020/5/14
 */
@SuppressWarnings("all")
public class XmlUtil {

    private static final SAXReader SAX_READER = new SAXReader();
    private static final String TITLE = "title";
    private static final String ID = "ID";
    public static final String NOTE_TYPE = "NoteType";

    private XmlUtil(){}


    /**
     *
     * @param path
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> convertToJavaBean(String path, Class<T> clazz) {

        List<T> result = new ArrayList<>();
        try {
            Document document = SAX_READER.read(new File(path));
            Element rootElement = document.getRootElement();
            String text = rootElement.getText();
            Field[] fields = clazz.getDeclaredFields();
            String clazzName = clazz.getSimpleName();
            //获取数据节点
            List<Element> childElements = rootElement.elements(clazzName);

            childElements.forEach(x -> {
               List<Element> temp;
               //存储从xml中获取的属性值
                Map<String,String> xmlValues= new HashMap<>(8);
                //如果节点有text数据
                if (x.getText() != null && x.getText().trim() != "") {
                        xmlValues.put("title", x.getText().trim());
                }
               //如果存在子节点
               if ((temp = x.elements()).size() > 0 ) {

                   temp.forEach( element ->  {
                       //拿到子节点nameSpace,首字母小写
                       String elementName = element.getName();
                       String name = elementName.substring(0,1).toLowerCase() + elementName.substring(1);
                       //将子节点内所有数据直接存储为字符串xml, title和Id结尾的标签直接取text
                       String tempElememtValue;
                       if (name.equals(TITLE) || name.substring(name.length() - 2).toUpperCase().equals(ID)) {
                           tempElememtValue = element.getText();
                       }else {
                           String xml = element.asXML();
                           tempElememtValue = xml.substring(xml.indexOf(elementName) + elementName.length() + 1,xml.lastIndexOf(elementName) - 2);
                       }
                       xmlValues.put(name, tempElememtValue);
                   });

               }

                List<Attribute> attributes;
               //如果节点存在属性
                if ((attributes = x.attributes()).size() > 0) {
                    attributes.forEach( attr -> {
                        String name = attr.getName();
                        //如果首字母又是他妈的大写（垃圾数据文件！！！！）
                        if (name.charAt(0) < 97){
                            name = name.substring(0,1).toLowerCase() + name.substring(1);
                        }
                        String value = attr.getValue();
                        //如果同一个xml里出现重复的属性，报错手动解决
                       if (xmlValues.get(name) != null) {
                           System.err.println("出现重复属性：" + name);
                       } else {
                         xmlValues.put(name, value);
                       }
                    });
                }

                //将xmlValues中的属性赋值到实例化的对象中
                if (xmlValues.size() > 0) {
                    try {
                        //获取对象实例
                        T t = clazz.newInstance();
                        //遍历对象属性
                        Arrays.asList(fields).forEach(field -> {
                            field.setAccessible(true);
                            String fieldName = field.getName();
                            String value = xmlValues.get(fieldName);
                            if (value != null ) {
                                //赋值
                                try {
                                    Class<?> type = field.getType();
                                    if (type == String.class) {
                                        field.set(t, value.trim());
                                    } else if (type == Integer.class) {
                                        field.set(t, Integer.valueOf(value.trim()));
                                    } else if (type == Date.class) {
                                        field.set(t,DateUtil.defaultParse(value));
                                    }
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        //添加到结果集
                        result.add(t);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            });

            return result;
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     *
     * @param config 配置信息
     * @param <T>
     * @return
     */
    public static <T> Map<Class, List<T>> convertToJavaBean(Config config) throws ClassNotFoundException {
        List<String> names = config.getNames();
        String path = config.getPath();
        Map<Class,List<T>> result = new HashMap<>(16);
        for (String name : names) {
            String tempPath = path + name + "List.xml";
            String className = "com.plant.database.model.bean." + name;
            Class clazz = Class.forName(className);
            result.put(clazz, convertToJavaBean(tempPath, clazz));
        }

        return result;
    }

}
