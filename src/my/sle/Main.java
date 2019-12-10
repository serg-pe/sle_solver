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
                    {1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1}
            };
            double[][] arr3 = {
                    {73, 7, 6, 8},
                    {75, 11, 2, 18},
                    {3, 1, 40, 4},
                    {4, 0, 9, 15},
            };
            var m1 = new MatrixD(arr1);
            var m2 = new MatrixD(arr2);
            var m3 = new MatrixD(arr3);

            System.out.println(m3.det());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
