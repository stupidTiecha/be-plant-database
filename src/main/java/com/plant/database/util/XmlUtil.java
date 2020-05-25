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
 *  transform xml data to javaBean
 * @author 18044703
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
            //get data nodes
            List<Element> childElements = rootElement.elements(clazzName);

            childElements.forEach(x -> {
               List<Element> temp;
               //store values get in xml
                Map<String,String> xmlValues= new HashMap<>(8);
                //if node has text value
                if (x.getText() != null && x.getText().trim() != "") {
                        xmlValues.put("title", x.getText().trim());
                }
               //if has child node
               if ((temp = x.elements()).size() > 0 ) {

                   temp.forEach( element ->  {
                       //get cholidNode nameSpace,Lowercase
                       String elementName = element.getName();
                       String name = elementName.substring(0,1).toLowerCase() + elementName.substring(1);
                       //store childNode value as xmlString, if node tag is end by title or Id,store text value.
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
               //if node has attrs
                if ((attributes = x.attributes()).size() > 0) {
                    attributes.forEach( attr -> {
                        String name = attr.getName();
                        //if Capitalized ,Lowercase.
                        if (name.charAt(0) < 97){
                            name = name.substring(0,1).toLowerCase() + name.substring(1);
                        }
                        String value = attr.getValue();
                        //find duplicate values, error
                       if (xmlValues.get(name) != null) {
                           System.err.println("出现重复属性：" + name);
                       } else {
                         xmlValues.put(name, value);
                       }
                    });
                }

                //transform xml values to java bean
                if (xmlValues.size() > 0) {
                    try {
                        //get instance
                        T t = clazz.newInstance();
                        //traversing object properties
                        Arrays.asList(fields).forEach(field -> {
                            field.setAccessible(true);
                            String fieldName = field.getName();
                            String value = xmlValues.get(fieldName);
                            if (value != null ) {
                                //assignment
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
                        //add to result
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
     * @param config config
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
