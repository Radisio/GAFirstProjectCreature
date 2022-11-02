package GeneticAlgorithm.Configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class SelectionConfig {
    private String method;
    @JsonProperty("mp")
    private Map<String, String> map;
    private double rate;

    public SelectionConfig(){}


    public SelectionConfig(String method, Map<String, String> map, double rate) {
        this.method = method;
        this.map = map;
        this.rate = rate;
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

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "SelectionConfig{" +
                "method='" + method + '\'' +
                ", map=" + map +
                ", rate=" + rate +
                '}';
    }
}
