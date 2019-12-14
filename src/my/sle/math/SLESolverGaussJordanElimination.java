package my.sle.math;

public class SLESolverGaussJordanElimination implements SLESolver {
    private static final String methodName = "Метод Гаусса-Жордана";

//    Высчитывание нижней прямоугольной матрицы
    private MatrixD straightForward(MatrixD sle) {
        MatrixD result = new MatrixD(sle);

        for (int rowsTransforms = 1; rowsTransforms < result.getRows(); rowsTransforms++) {
            int elementToZeroIndex = rowsTransforms - 1;
            int currentRowIndex = rowsTransforms - 1;

            for (int transformedRowIndex = rowsTransforms; transformedRowIndex < result.getRows(); transformedRowIndex++) {
                if (result.getMatrix()[transformedRowIndex][elementToZeroIndex] != 0) {
                    double factor = -(result.getMatrix()[currentRowIndex][elementToZeroIndex] / result.getMatrix()[transformedRowIndex][elementToZeroIndex]);
                    result = result.multiplyRow(transformedRowIndex, factor).sumRows(currentRowIndex, transformedRowIndex);
                }
            }
        }

        return result;
    }

//    Высчитывание верхней прямоугольной матрицы
    private MatrixD backForward(MatrixD sle) {
        MatrixD result = new MatrixD(sle);

        for (int rowsTransforms = result.getRows() - 1; rowsTransforms > -1; rowsTransforms--) {
            int elementToZeroIndex = rowsTransforms;
            int currentRowIndex = rowsTransforms;

            for (int transformedRowIndex = rowsTransforms; transformedRowIndex > -1; transformedRowIndex--) {
                if (result.getMatrix()[transformedRowIndex][elementToZeroIndex] != 0) {
                    double factor = -(result.getMatrix()[currentRowIndex][elementToZeroIndex] / result.getMatrix()[transformedRowIndex][elementToZeroIndex]);
                    result = result.multiplyRow(transformedRowIndex, factor).sumRows(currentRowIndex, transformedRowIndex);
                }
            }
        }

        return result;
    }

//    Получение ответа - свободные члены
    private double[] getAnswer(MatrixD matrix) {
        double[] answer = new double[matrix.getRows()];

        for (int row = 0; row < answer.length; row++)
            answer[row] = matrix.getMatrix()[row][matrix.getCols() - 1];

        return answer;
    }

    @Override
    public double[] solve(MatrixD sle) throws DeterminantIsZeroException, ShapesNotAlignedException, InconsistentSLEException {
        MatrixD result = new MatrixD(sle);

//        Если матрица единичная
        if (result.getRows() == 1 && result.getCols() == 2) {
            result = result.divide(result.getMatrix()[0][0]);

            if (isInconsistent(result))
                throw new InconsistentSLEException("Несовместная СЛАУ");

            return getAnswer(result);
        }

//        Если элемент в 0-ой строке и 0-ом столбце = 0, 
//        поиск строки, в которой элемент в 0-ом столбце != 0 и перестановка строк
        if (result.getMatrix()[0][0] == 0)
            for (int row = 1; row < sle.getRows(); row++)
                if (result.getMatrix()[row][0] != 0) {
                    result = result.exchangeRows(0, row);
                    break;
                }

        result = straightForward(result);
        result = backForward(result);

        for (int row = 0; row < result.getRows(); row++)
            result = result.divideRow(row, result.getMatrix()[row][row]);

        if (isInconsistent(result))
            throw new InconsistentSLEException("Несовместная СЛАУ");

        return getAnswer(result);
    }

    @Override
    public String getMethodName() {
        return methodName;
    }

    @Override
    public boolean isInconsistent(MatrixD sle) {
        for (int row = 0; row < sle.getRows(); row++) {
            int rowSum = 0;

            for (int col = 0; col < sle.getCols() - 1; col++)
                rowSum += sle.getMatrix()[row][col];

            if (rowSum == 0 && sle.getMatrix()[row][sle.getCols() - 1] != 0) {
                return true;
            }
        }

        return false;
    }
}
