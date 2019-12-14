package my.sle;

import my.sle.gui.Controller;
import my.sle.gui.Model;
import my.sle.gui.View;

public class Main {

    public static void main(String[] args) {
        try {
//            Model (Модель), View (Представление), Controller (контроллер) [шаблон проектирования MVC]
//            Используется для разделения кода при разработки интерфейса
//            Такой подход позволяет разделить функционал по выводу внешнего вида окна программы,
//            обработки событий (например, нажатие на кнопку) и каких-то важных данных,
//            от которых зависитвнешний вид окна, в разные классы и => файлы.
//            Разделение на несколько классов позволяет улучшить читаемость (так как всё это могло быть в одном очень большом файле)
//            и поддержку кода.
//            СОВЕТУЮ ОЧЕНЬ ХОРОШО ИЗУЧИТЬ ЭТИ 3 КЛАССА И САМ ШАБЛОН MVC (последнее по возможности, первое гораздо важнее)!!!!!!
            Model model = new Model();
            View view = new View("Решение СЛАУ");
            Controller controller = new Controller(model, view);
            controller.init();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
