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

    private MatrixD addStr() {
        return null;
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
