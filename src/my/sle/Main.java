package my.sle;

import my.sle.math.MatrixD;
import my.sle.math.ShapesNotAlignedException;

public class Main {

    public static void main(String[] args) {
        try {
            double[][] arr1 = {
                    {1, 1, 1},
                    {1, 1, 1},
                    {1, 1, 1},
            };
            double[][] arr2 = {
                    {1, 1, 1, 1},
                    {1, 1, 1, 1},
                    {1, 1, 1, 1}
            };
            var m1 = new MatrixD(arr1);
            var m2 = new MatrixD(arr2);

            System.out.println(m1.multiply(m2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
