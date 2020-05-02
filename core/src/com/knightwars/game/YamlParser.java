package com.knightwars.game;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public class YamlParser {

    /** Convert a YAML file to a java map
     * @param path The path of the YAML file to convert
     * @return the map from the YAML file
     * @throws FileNotFoundException thrown exception if the path is wrong
     */
    public static Map<String, Object> yamlToJavaMap(String path) throws FileNotFoundException {
        Yaml yaml = new Yaml();
        InputStream inputStream = new FileInputStream(new File(path));
        return yaml.load(inputStream);
    }

    /** Verify the right construction of the yaml file for building upgrades
     * @param javaMap The java map returned by the yamlToJavaMap method
     * @param superClass The super class of the buildings
     * @param objPackage The package containing the buildings
     * @throws InvalidYamlFormatException thrown exception if there is a construction error in the yaml file
     */
    public static void yamlValidity(Map<String, Object> javaMap, Class<?> superClass, String objPackage) throws InvalidYamlFormatException {
        for (String classesHierarchy : javaMap.keySet()) {
            try {
                Class<?> c = Class.forName(objPackage + "." + classesHierarchy);
                while (c != null) {
                    if (c == superClass) {
                        return;
                    } else {
                        c = c.getSuperclass();
                    }
                }
                throw new InvalidYamlFormatException("Invalid YAML format : not extending the right superclass...");
            } catch (ClassNotFoundException e) {
                throw new InvalidYamlFormatException("Invalid YAML format : Some classes from the YAML file were not found");
            }
        }
    }
}
