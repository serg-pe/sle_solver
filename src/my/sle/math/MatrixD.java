package my.sle.math;

import java.io.Serializable;

public class MatrixD implements Serializable {
    private static final long serialVersionUID = 1L;

    private double[][] matrix;

//    Создание квадратной матрицы по size
    public MatrixD(int size) {
        matrix = new double[size][size];
    }

//    Создание матрицы по количеству строк и столбцов
    public MatrixD(int rows, int cols) {
        matrix = new double[rows][cols];
    }

//    Копирование матрицы в новый объект по значению. (без ссылок на прошлую - matrix)
    public MatrixD(MatrixD matrix) {
        this.matrix = matrix.getMatrix().clone();
        for (int i = 0; i < matrix.getRows(); i++)
            this.matrix[i] = matrix.getMatrix()[i].clone();
    }

//    Создание ьфтрицы по двумерному массиву
    public MatrixD(double[][] array) {
        matrix = array.clone();
        for (int i = 0; i < matrix.length; i++)
            matrix[i] = array[i].clone();
    }

//    Рекурсивный поиск определителя
    private double detRec(MatrixD matrix) {
        MatrixD temp;
        double det = 0;

        if (matrix.getRows() == 1)
            return matrix.matrix[0][0];

        if (matrix.getRows() == 2)
            return matrix.matrix[0][0] * matrix.matrix[1][1] - matrix.matrix[1][0] * matrix.matrix[0][1];

        for (int i = 0; i < matrix.getCols(); i++) {
            temp = new MatrixD(matrix.getRows() - 1, matrix.getCols() - 1);

            for (int j = 1; j < matrix.getRows(); j++) {
                for (int k = 0; k < matrix.getCols(); k++)
                    if (k < i)
                        temp.matrix[j - 1][k] = matrix.matrix[j][k];
                    else if (k > i)
                        temp.matrix[j - 1][k - 1] = matrix.matrix[j][k];
            }
            det += matrix.matrix[0][i] * Math.pow(-1, i) * detRec(temp);
        }

        return det;
    }

//    Срез матрицы по столбцам [colStart; colEnd).
//    colStart  Индекс первого столбца
//     colEnd    Индекс последнего столбца (не включается)
    public MatrixD slice(int colStart, int colEnd) {
        MatrixD result = new MatrixD(this.getRows(), colEnd - colStart);

        for (int i = 0; i < result.getRows(); i++)
            for (int j = colStart; j < colEnd; j++)
                result.matrix[i][j - colStart] = this.getMatrix()[i][j];

        return result;
    }

//    Перестановка строк в матрице
    public MatrixD exchangeRows(int rowIndex1, int rowIndex2) {
        MatrixD result = new MatrixD(this);

        for (int col = 0; col < result.getCols(); col++) {
            result.getMatrix()[rowIndex1][col] = this.getMatrix()[rowIndex2][col];
            result.getMatrix()[rowIndex2][col] = this.getMatrix()[rowIndex1][col];
        }

        return result;
    }

//    Умножение строки матрицы на число
    public MatrixD multiplyRow(int rowIndex, double factor) {
        MatrixD result = new MatrixD(this);

        for (int col = 0; col < result.getCols(); col++)
            result.getMatrix()[rowIndex][col] *= factor;

        return result;
    }

//    Деление строки матрицы на число
    public MatrixD divideRow(int rowIndex, double divizor) {
        MatrixD result = new MatrixD(this);

        for (int col = 0; col < result.getCols(); col++)
            result.getMatrix()[rowIndex][col] /= divizor;

        return result;
    }

//    Поэлементное сложение строк в матрице
    MatrixD sumRows(int rowIndex, int addedRowIndex) {
        MatrixD result = new MatrixD(this);

        for (int col = 0; col < getCols(); col++)
            result.getMatrix()[addedRowIndex][col] += this.getMatrix()[rowIndex][col];

        return result;
    }

//    Деление всех элементов матрицы
    public MatrixD divide(double divizor) {
        MatrixD result = new MatrixD(this);

        for (int row = 0; row < getRows(); row++)
            for (int col = 0; col < getCols(); col++)
                result.getMatrix()[row][col] /= divizor;

        return result;
    }

//    Поиск определителя. Обёртка над вызовом рекурсивного поиска
    public double det() throws ShapesNotAlignedException {
        if (getRows() != getCols())
            throw new ShapesNotAlignedException(String.format("Нельзя найти определитель для прямоугольной матрицы (%d, %d)", getCols(), getRows()));

        return detRec(this);
    }

//    Транспонирование матрицы
    public MatrixD transpose() {
        MatrixD result = new MatrixD(getCols(), getRows());

        for (int i = 0; i < this.getRows(); i++)
            for (int j = 0; j < this.getCols(); j++)
                result.matrix[j][i] = this.matrix[i][j];

        return result;
    }

//    Умножение матриц
    public MatrixD multiply(MatrixD factor) throws ShapesNotAlignedException {
        if (this.getCols() != factor.getRows())
            throw new ShapesNotAlignedException(String.format("Матрицы форм (%d, %d) и (%d, %d) несовместимы", this.getRows(), this.getCols(), factor.getRows(), factor.getCols()));

        MatrixD result = new MatrixD(this.getRows(), factor.getCols());

        for (int i = 0; i < this.getRows(); i++)
            for (int j = 0; j < factor.getCols(); j++)
                for (int k = 0; k < factor.getRows(); k++)
                    result.matrix[i][j] += this.matrix[i][k] * factor.matrix[k][j];

        return result;
    }

//    Получение минора матрицы относительно позиции элемента
    public MatrixD extractMinor(int row, int col) {
        MatrixD minor = new MatrixD(getRows() - 1, getCols() - 1);

        for (int i = 0; i < getRows(); i++) {
            if (i == row)
                continue;
            for (int j = 0; j < getCols(); j++) {
                if (j == col)
                    continue;
                if (i < row && j < col)
                    minor.matrix[i][j] = this.matrix[i][j];
                else if (i < row && j > col)
                    minor.matrix[i][j - 1] = this.matrix[i][j];
                else if (i > row && j < col)
                    minor.matrix[i - 1][j] = this.matrix[i][j];
                else
                    minor.matrix[i - 1][j - 1] = this.matrix[i][j];
            }
        }

        return minor;
    }

//    Высчитывание союзной матрицы
    public MatrixD makeAdjunct() throws ShapesNotAlignedException {
        if (getCols() != getRows())
            throw new ShapesNotAlignedException(String.format("Нельзя найти определитель для прямоугольной матрицы (%d, %d)", getCols(), getRows()));

        MatrixD adjunct = new MatrixD(getRows(), getCols());

        for (int i = 0; i < getRows(); i++)
            for (int j = 0; j < getCols(); j++) {
                adjunct.matrix[i][j] = extractMinor(i, j).det() * Math.pow(-1, i + j);
            }

        return adjunct;
    }

//    Высчитывание обратной матрицы
    public MatrixD makeInvertable() throws ShapesNotAlignedException, DeterminantIsZeroException {
        if (getCols() != getRows())
            throw new ShapesNotAlignedException(String.format("Нельзя найти определитель для прямоугольной матрицы (%d, %d)", getCols(), getRows()));

        double det = det();
        if (det == 0)
            throw new DeterminantIsZeroException("Нельзя найти обратну матрицу. Определитель = 0.");

        return makeAdjunct().transpose().divide(det);
    }

//    Количество строк в матрице
    public int getRows() {
        return getMatrix().length;
    }

//    Количество столбцов в матрице
    public int getCols() {
        return getMatrix()[0].length;
    }

//    Получение матрицы
    public double[][] getMatrix() {
        return matrix;
    }

//    Преобразование матрицы в строку
    @Override
    public String toString() {
        String repr = "";
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                repr += Double.toString(matrix[i][j]);
                if (j != getCols() - 1)
                    repr += " ";
            }
            if (i != getRows() - 1)
                repr += "\n";
        }
        return repr;
    }
}
