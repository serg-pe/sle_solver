package my.sle.gui;

import my.sle.math.*;

// Модель. Предназначена для хранения данных, определяющих параметры внешнего вида окна
// Используется в Контроллере (Controller)
// Роль - предоставление данных контроллеру о том, как сгенерировать Представление
public class Model {
    private int matrixSize = 3;

    public int getMatrixSize() {
        return matrixSize;
    }

    public void setMatrixSize(int matrixSize) {
        if (matrixSize < 1)
            this.matrixSize = 3;
        else
            this.matrixSize = matrixSize;
    }
}
