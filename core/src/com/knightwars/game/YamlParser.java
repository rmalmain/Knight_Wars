package com.knightwars.game;

import com.knightwars.game.environment.Building;
import com.knightwars.game.environment.InvalidUpgradeException;
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

    public static void yamlValidity(Map<String, Object> javaMap, Class<?> superClass, String objPackage) throws InvalidYamlFormat{
        for (String classesHierarchy : javaMap.keySet()) {
            try {
                if (Class.forName(objPackage + "." + classesHierarchy).getSuperclass() != superClass) {
                    throw new InvalidYamlFormat("Invalid YAML format : not extending the right superclass...");
                }
            } catch (ClassNotFoundException e) {
                throw new InvalidYamlFormat("Invalid YAML format : Some classes from the YAML file were not found");
            }
        }
    }
}
