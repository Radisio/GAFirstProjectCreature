package GeneticAlgorithm.Configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class SelectionConfig {
    private String method;
    @JsonProperty("mp")
    private Map<String, String> map;

    public SelectionConfig(){}


    public SelectionConfig(String method, Map<String, String> map) {
        this.method = method;
        this.map = map;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }



    @Override
    public String toString() {
        return "SelectionConfig{" +
                "method='" + method + '\'' +
                ", map=" + map +
                '}';
    }
}
