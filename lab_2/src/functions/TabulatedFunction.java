package functions;

public class TabulatedFunction {
    private FunctionPoint[] arr;
    private int addElements = 3;

    public TabulatedFunction(double leftX, double rightX, int pointsCount) {
        if (leftX != rightX && pointsCount > 1) {
            arr = new FunctionPoint[pointsCount + addElements];
            double step = (rightX - leftX) / (pointsCount - 1);
            for (int i = 0; leftX <= rightX; ++i, leftX += step) { //заполнение массива
                arr[i] = new FunctionPoint(leftX, 0);
            }
        } else
            System.out.println("Некорректный ввод границ или кол-во точек");
    }

    public TabulatedFunction(double leftX, double rightX, double[] values) {
        if (leftX != rightX && values.length > 1) {
            arr = new FunctionPoint[values.length + addElements];
            double step = (rightX - leftX) / (values.length - 1);
            for (int i = 0; leftX <= rightX; ++i, leftX += step) { //заполнение массива
                arr[i] = new FunctionPoint(leftX, values[i]);
            }
        } else
            System.out.println("Некорректный ввод границ или кол-во точек");
    }

    public double getLeftDomainBorder() {
        if (arr.length != addElements) //проверка на то, что массив не пустой
            return arr[0].get_X();
        return Double.NaN;
    }

    public double getRightDomainBorder() {
        if (arr.length != addElements) //проверка на то, что массив не пустой
            return arr[arr.length - addElements - 1].get_X();
        return Double.NaN;
    }

    public double getFunctionValue(double x) {
        if (x < this.getLeftDomainBorder() || x > this.getRightDomainBorder())//проверка на выход за границы х
            return Double.NaN;
        else {
            if (x != arr[0].get_X()) {//случай, когда x равен первому эл-ту
                int i = 0;
                for (; x > arr[i].get_X(); ++i); //поиск эл-та с заданным значением
                return ((x - arr[i - 1].get_X()) * (arr[i].get_Y() - arr[i - 1].get_Y())) / (arr[i].get_X() - arr[i - 1].get_X()) + arr[i - 1].get_Y();
            } else
                return arr[0].get_Y();
        }
    }

    public int getPointsCount() {
        return arr.length - addElements;
    }

    public FunctionPoint getPoint(int index) {
        if (index > arr.length - addElements - 1 || index < 0)
            throw new IndexOutOfBoundsException("Выход за границы массива");
        return arr[index];
    }

    public void setPoint(int index, FunctionPoint point) {
        if (index > arr.length - addElements - 1 || index < 0)
            System.out.println("Выход за границы массива");
        else {
            if (arr.length - addElements > 2 && index != 0 && index != arr.length - addElements - 1) {//проверка на то, что кол-во элементов > 3 и заменяемый элемент не крайнийй
                if (arr[index + 1].get_X() > point.get_X() && arr[index - 1].get_X() < point.get_X()) //х принадлежит интервалу между соседними точками
                    arr[index] = new FunctionPoint(point.get_X(), point.get_Y());
                else
                    System.out.println("x не принадлежит интервалу между соседними точками");
            } else {
                switch (arr.length - addElements) {//смотрим на кол-во эл-ов в массиве
                    case 0: {
                        System.out.println("Массив пустой");
                        break;
                    }
                    case 1: {
                        if (index == 0)
                            arr[index] = new FunctionPoint(point.get_X(), point.get_Y());
                        else
                            System.out.println("Выход за границы массива");
                        break;
                    }
                    case 2: {
                        if (index == 0 && point.get_X() < this.arr[1].get_X() || index == arr.length - addElements - 1 && point.get_X() > this.arr[index - 1].get_X())
                            arr[index] = new FunctionPoint(point.get_X(), point.get_Y());
                        else
                            System.out.println("Не соответствует условию для 2 граничных точек");
                        break;
                    }
                    default: {
                        if (index == 0 && point.get_X() < this.arr[1].get_X() || index == arr.length - addElements - 1 && point.get_X() > this.arr[index - 1].get_X())
                            arr[index] = new FunctionPoint(point.get_X(), point.get_Y());
                        else
                            System.out.println("Не соответствует условию граничной точки");
                    }
                }
            }
        }
    }

    public double getPointX(int index) {
        if (index < arr.length - addElements && index > -1)
            return arr[index].get_X();
        else
            throw new IndexOutOfBoundsException("Выход за границы массива");
    }

    public void setPointX(int index, double x) {
        if (index > arr.length - addElements - 1 || index < 0) //проверка индекса
            System.out.println("Выход за границы массива");
        else {
            if (arr.length - addElements > 2 && index != 0 && index != arr.length - addElements - 1) {//если массив из трех элемнтов и они не крайние
                if (arr[index + 1].get_X() > x && arr[index - 1].get_X() < x) //если х лежит между точками
                    this.arr[index].set_X(x);
                else
                    System.out.println("x не принадлежит интервалу между соседними точками");
            } else {
                switch (arr.length - addElements) {//если длина массива не 0
                    case 0: {
                        System.out.println("Массив пустой");
                        break;
                    }
                    case 1: {
                        if (index == 0)
                            this.arr[index].set_X(x);
                        else
                            System.out.println("Выход за границы массива");
                        break;
                    }
                    case 2: {
                        if (index == 0 && x < this.arr[1].get_X() || index == arr.length - addElements - 1 && x > this.arr[index - 1].get_X()) //включается 2 ситуации, когда 2 эл-та или 1
                            this.arr[index].set_X(x);
                        else
                            System.out.println("Не соответствует условию для 2 точек");
                        break;
                    }
                    default: {
                        if (index == 0 && x < this.arr[1].get_X() || index == arr.length - addElements - 1 && x > this.arr[index - 1].get_X())
                            this.arr[index].set_X(x);
                        else
                            System.out.println("Не соответствует условию граничной точки");
                    }
                }
            }
        }
    }

    public double getPointY(int index) {
        if (index < arr.length - addElements && index > -1)
            return arr[index].get_Y();
        else
            throw new IndexOutOfBoundsException("Выход за границы массива");
    }

    public void setPointY(int index, double y) {
        if (index < arr.length - addElements && index > -1)
            this.arr[index].set_Y(y);
        else
            System.out.println("Выход за границы массива");
    }

    public void deletePoint(int index) {
        if (arr.length != addElements) {
            if (index < arr.length - addElements && index > -1) {
                System.arraycopy(arr, index + 1, arr, index, arr.length - (index + 1) - addElements);//копируем массив arr в массив arr, пропуская элемент arr[index]
                arr[arr.length - addElements - 1] = null;//удаляем последний эл-т
                ++addElements;//увеличиваем число добавочных эл-ов, чтобы не изменилась длина массива в целом
            } else
                System.out.println("Выход за границы");
        } else {
            System.out.println("Массив пуст, удалять нечего");
        }
    }

    public void addPoint(FunctionPoint point) {
        if (addElements == 0) { //выделяем дополнительно память, если массив уже заполнен
            addElements = 3;
            FunctionPoint[] exArr = new FunctionPoint[arr.length + addElements]; //создаем новый увеличенный массив
            System.arraycopy(arr, 0, exArr, 0, arr.length); //копируем массив arr в exArr, при этом остается 3 доб. эл-та
            arr = exArr;
        }
        int i = 0;
        for (; i < arr.length - addElements && point.get_X() > arr[i].get_X(); ++i);
        System.arraycopy(arr, i, arr, i + 1, arr.length - i - addElements);//справа от i-ого эл-та оставляем место для нового эл-та
        arr[i] = new FunctionPoint(point); //на i-ое место ставим новый point
        --addElements;//уменьшаем число добавочных эл-ов, чтобы не изменилась длина массива в целом
    }

    public void printArr() {
        if(arr.length - addElements != 0)
        for (int i = 0; i < arr.length - addElements; ++i)
            System.out.print("(" + arr[i].get_X() + ", " + arr[i].get_Y() + "); ");
        else
            System.out.println("Массив пуст, выводить нечего");
    }
}