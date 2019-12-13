package my.sle.gui;

public class SLEResultData {
    private double[] result;
    private String method;
    private String errorMessage;

    public SLEResultData(double[] result, String method, String errorMessage) {
        this.result = result;
        this.method = method;
        this.errorMessage = errorMessage;
    }

    public double[] getResult() {
        return result;
    }

    public String getMethod() {
        return method;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
