package my.sle.math;

public interface SLESolver {
    /**
     * Интерфейс, для решения СЛАУ
     * @param sle   Матрица с коэффициентами перед неизвестными и последним столбцом свободных членов
     * @return Матрица значений неизвестных X
     */
    double[] solve(MatrixD sle) throws CantSolveException, ShapesNotAlignedException;
}
