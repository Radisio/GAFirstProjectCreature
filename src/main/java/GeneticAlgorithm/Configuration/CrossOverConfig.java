package GeneticAlgorithm.Configuration;

public class CrossOverConfig {
    private String method;
    private double rate;

    public CrossOverConfig(){}
    public CrossOverConfig(String method, double rate) {
        this.method = method;
        this.rate = rate;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
