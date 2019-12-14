package my.sle.math;

//Интерфейс, для решения СЛАУ
public interface SLESolver {

//    Метод, выполняющий решение СЛАУ
    double[] solve(MatrixD sle) throws DeterminantIsZeroException, ShapesNotAlignedException, InconsistentSLEException;

//    Возвращает название метода решения СЛАУ
    String getMethodName();

//    Проверка матрицы на совместность
    boolean isInconsistent(MatrixD sle);
}
