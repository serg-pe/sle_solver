package my.sle.gui;

public class ResultData {
    private double[][] result;
    private String[] methods;

    public ResultData(double[][] result, String[] methods) {
        this.result = result;
        this.methods = methods;
    }

    public double[][] getResult() {
        return result;
    }

    public String[] getMethods() {
        return methods;
    }
}
