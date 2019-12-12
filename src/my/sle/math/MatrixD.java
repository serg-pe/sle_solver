package my.sle.math;

public class MatrixD {
    private int rows;
    private int cols;

    private double[][] matrix;

    public MatrixD(int size) {
        rows = cols = size;
        matrix = new double[rows][cols];
    }

    public MatrixD(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        matrix = new double[rows][cols];
    }

    public MatrixD(MatrixD matrix) {
        rows = matrix.rows;
        cols = matrix.cols;
        this.matrix = matrix.getMatrix().clone();
        for (int i = 0; i < matrix.getRows(); i++)
            this.matrix[i] = matrix.getMatrix()[i].clone();
    }

    public MatrixD(double[][] array) {
        rows = array.length;
        cols = array[0].length;
        matrix = array.clone();
        for (int i = 0; i < rows; i++)
            matrix[i] = array[i].clone();
    }

    private double[] getRow(int number) {
        return matrix[number].clone();
    }

    private double detRec(MatrixD matrix) {
        MatrixD temp;
        double det = 0;

        if (matrix.rows == 1)
            return matrix.matrix[0][0];

        if (matrix.rows == 2)
            return matrix.matrix[0][0] * matrix.matrix[1][1] - matrix.matrix[1][0] * matrix.matrix[0][1];

        for (int i = 0; i < matrix.cols; i++) {
            temp = new MatrixD(matrix.rows - 1, matrix.cols - 1);

            for (int j = 1; j < matrix.rows; j++) {
                for (int k = 0; k < matrix.cols; k++)
                    if (k < i)
                        temp.matrix[j - 1][k] = matrix.matrix[j][k];
                    else if (k > i)
                        temp.matrix[j - 1][k - 1] = matrix.matrix[j][k];
            }
            det += matrix.matrix[0][i] * Math.pow(-1, i) * detRec(temp);
        }

        return det;
    }

    /**
     * Срез матрицы.
     * @param colStart  Индекс первого столбца
     * @param colEnd    Индекс последнего столбца
     * @return  Матрица с исходным количеством строк и столбцами [@param colStart не включая @param colEnd)
     */
    public MatrixD slice(int colStart, int colEnd) {
        var result = new MatrixD(this.getRows(), colEnd - colStart);

        for (int i = 0; i < result.getRows(); i++)
            for (int j = colStart; j < colEnd; j++)
                result.matrix[i][j - colStart] = this.getMatrix()[i][j];

        return result;
    }

    public MatrixD exchangeRows(int rowIndex1, int rowIndex2) {
        MatrixD result = new MatrixD(this);

        for (int col = 0; col < result.getCols(); col++) {
            result.getMatrix()[rowIndex1][col] = this.getMatrix()[rowIndex2][col];
            result.getMatrix()[rowIndex2][col] = this.getMatrix()[rowIndex1][col];
        }

        return result;
    }

    public MatrixD multiplyRow(int rowIndex, double factor) {
        MatrixD result = new MatrixD(this);

        for (int col = 0; col < result.getCols(); col++)
            result.getMatrix()[rowIndex][col] *= factor;

        return result;
    }

    public MatrixD divideRow(int rowIndex, double divizor) {
        MatrixD result = new MatrixD(this);

        for (int col = 0; col < result.getCols(); col++)
            result.getMatrix()[rowIndex][col] /= divizor;

        return result;
    }

    MatrixD sumRows(int rowIndex, int addedRowIndex) {
        MatrixD result = new MatrixD(this);

        for (int col = 0; col < getCols(); col++)
            result.getMatrix()[addedRowIndex][col] += this.getMatrix()[rowIndex][col];

        return result;
    }

    public MatrixD divide(double divizor) {
        MatrixD result = new MatrixD(this);

        for (int row = 0; row < getRows(); row++)
            for (int col = 0; col < getCols(); col++)
                result.getMatrix()[row][col] /= divizor;

        return result;
    }

    public double det() throws ShapesNotAlignedException {
        if (cols != rows)
            throw new ShapesNotAlignedException(String.format("No determinant for non-square matrix (%d, %d)", cols, rows));

        return detRec(this);
    }

    public MatrixD transpose() {
        MatrixD result = new MatrixD(cols, rows);

        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < this.cols; j++)
                result.matrix[j][i] = this.matrix[i][j];

        return result;
    }

    public MatrixD multiply(MatrixD factor) throws ShapesNotAlignedException {
        if (this.cols != factor.rows)
            throw new ShapesNotAlignedException(String.format("Shapes not aligned for (%d, %d) & (%d, %d)", this.rows, this.cols, factor.rows, factor.cols));

        MatrixD result = new MatrixD(this.rows, factor.cols);

        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < factor.cols; j++)
                for (int k = 0; k < factor.rows; k++)
                    result.matrix[i][j] += this.matrix[i][k] * factor.matrix[k][j];

        return result;
    }

    public MatrixD extractMinor(int row, int col) {
        MatrixD minor = new MatrixD(rows - 1, cols - 1);

        for (int i = 0; i < rows; i++) {
            if (i == row)
                continue;
            for (int j = 0; j < cols; j++) {
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

    public MatrixD makeAdjunct() throws ShapesNotAlignedException {
        /*
        *Союзная матрица
         */
        if (cols != rows)
            throw new ShapesNotAlignedException(String.format("No determinant for non-square matrix (%d, %d)", cols, rows));

        var adjunct = new MatrixD(rows, cols);

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++) {
                adjunct.matrix[i][j] = extractMinor(i, j).det() * Math.pow(-1, i + j);
            }

        return adjunct;
    }

    public MatrixD makeInvertable() throws ShapesNotAlignedException, CantSolveException {
        if (cols != rows)
            throw new ShapesNotAlignedException(String.format("No determinant for non-square matrix (%d, %d)", cols, rows));

        double det = det();
        if (det == 0)
            throw new CantSolveException("Can nott find invertable Matrix. Determinant = 0.");

        return makeAdjunct().transpose().divide(det);
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    @Override
    public String toString() {
        String repr = "";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                repr += Double.toString(matrix[i][j]);
                if (j != cols - 1)
                    repr += " ";
            }
            if (i != rows - 1)
                repr += "\n";
        }
        return repr;
    }
}
