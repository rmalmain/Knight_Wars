package com.knightwars.game;

import com.knightwars.game.environment.Building;
import org.reflections.Reflections;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

public class YamlParser {
    public static Map<String, Object> yamlToJavaMap(String path) throws FileNotFoundException {
        Yaml yaml = new Yaml();
        InputStream inputStream = new FileInputStream(new File(path));
        return yaml.load(inputStream);
    }

    public static boolean yamlValidity(Map<String, Object> javaMap, Class<?> superClass, String objPackage) {
            for (String classesHierarchy : javaMap.keySet()) {
                try {
                    if (Class.forName(objPackage + "." + classesHierarchy).getSuperclass() != superClass) {
                        System.out.println("Not the right superclass...");
                        return false;
                    }
                } catch (ClassNotFoundException e) {
                    System.out.println(classesHierarchy);
                    System.out.println("Class not found. YAML file may be invalid.");
                    return false;
                }
            }
            return true;
    }
}
