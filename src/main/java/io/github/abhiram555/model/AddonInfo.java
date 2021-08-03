package io.github.abhiram555.model;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;

public class AddonInfo {
    private final HashMap<?,?> yamlmap;
    private String mainclasspath;
    private String name;
    private String version;
    private String author;

    public AddonInfo(InputStream stream){
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(dumperOptions);
        this.yamlmap = yaml.load(stream);

        this.mainclasspath = (String) yamlmap.get("main");
        this.name = (String) yamlmap.get("name");
        this.version = (String) yamlmap.get("version");
        this.author = (String) yamlmap.get("author");
    }

    public String getClassPath(){
        return this.mainclasspath;
    }

    public String getName(){
        return this.name;
    }

    public String getVersion(){
        return this.version;
    }

    public String getAuthor(){
        return this.author;
    }
}
