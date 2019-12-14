package my.sle.gui;

// Формат данных для хранения информации о решении СЛАУ
// Используется в Контроллере (Controller) и Представлении (View)
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
