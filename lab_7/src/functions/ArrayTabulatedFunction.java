package functions;
import java.io.*;

public class ArrayTabulatedFunction implements TabulatedFunction {
    private FunctionPoint[] arr;
    private int addElements = 3;

    private boolean chSort(FunctionPoint[] m) {
        for (int i = 0; i < m.length - 1; ++i)
            if (m[i].get_X() > m[i + 1].get_X())
                return false;
        return true;
    }

    public ArrayTabulatedFunction() {
        arr = null;
        addElements = 3;
    }

    public ArrayTabulatedFunction(FunctionPoint[] m) throws IllegalArgumentException, Exception {
        if (m.length > 1 && chSort(m)) {
            arr = new FunctionPoint[m.length + addElements];
            System.arraycopy(m, 0, arr, 0, m.length);
        } else
            throw new IllegalArgumentException("Массив не упорядочен или число точек менее двух");
    }

    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) throws IllegalArgumentException {
        if (leftX < rightX && pointsCount > 1) {
            arr = new FunctionPoint[pointsCount + addElements];
            double step = (rightX - leftX) / (pointsCount - 1);
            for (int i = 0; leftX <= rightX; ++i, leftX += step) { //заполнение массива
                arr[i]= new FunctionPoint(leftX, 0);
            }
        } else
            throw new IllegalArgumentException("Некорректный ввод границ или кол-во точек");
    }

    public ArrayTabulatedFunction(double leftX, double rightX, double[] values) throws IllegalArgumentException {
        if (leftX < rightX && values.length > 1) {
            arr = new FunctionPoint[values.length + addElements];
            double step = (rightX - leftX) / (values.length - 1);
            for (int i = 0; leftX <= rightX; ++i, leftX += step) { //заполнение массива
                arr[i]= new FunctionPoint(leftX, values[i]);
            }
        } else
            throw new IllegalArgumentException("Некорректный ввод границ или кол-во точек");
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
        if (x < getLeftDomainBorder() || x > getRightDomainBorder())//проверка на выход за границы х
            return Double.NaN;
        else {
            if (x != arr[0].get_X()) {//случай, когда x равен первому эл-ту
                int i = 0;
                for (; x > arr[i].get_X(); ++i) ; //поиск эл-та с заданным значением
                return ((x - arr[i - 1].get_X()) * (arr[i].get_Y() - arr[i - 1].get_Y())) / (arr[i].get_X() - arr[i - 1].get_X()) + arr[i - 1].get_Y();
            } else
                return arr[0].get_Y();
        }
    }

    public int getPointsCount() {
        return arr.length - addElements;
    }

    public FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException {
        if (index > arr.length - addElements - 1 || index < 0)
            throw new FunctionPointIndexOutOfBoundsException("Выход за границы массива");
        return arr[index];
    }

    public void setPoint(int index, FunctionPoint point) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {
        if (index > arr.length - addElements - 1 || index < 0)
            throw new FunctionPointIndexOutOfBoundsException("Выход за границы массива");
        else {
            if (arr.length - addElements > 2 && index != 0 && index != arr.length - addElements - 1) {//проверка на то, что кол-во элементов > 3 и заменяемый элемент не крайнийй
                if (arr[index + 1].get_X() > point.get_X() && arr[index - 1].get_X() < point.get_X()) //х принадлежит интервалу между соседними точками
                    arr[index] = new FunctionPoint(point.get_X(), point.get_Y());
                else
                    throw new InappropriateFunctionPointException("x не принадлежит интервалу между соседними точками");
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
                            throw new FunctionPointIndexOutOfBoundsException("Выход за границы массива");
                        break;
                    }
                    case 2: {
                        if (index == 0 && point.get_X() < arr[1].get_X() || index == arr.length - addElements - 1 && point.get_X() > arr[index - 1].get_X())
                            arr[index] = new FunctionPoint(point.get_X(), point.get_Y());
                        else
                            throw new InappropriateFunctionPointException("Не соответствует условию для 2 граничных точек");
                        break;
                    }
                    default: {
                        if (index == 0 && point.get_X() < arr[1].get_X() || index == arr.length - addElements - 1 && point.get_X() > arr[index - 1].get_X())
                            arr[index] = new FunctionPoint(point.get_X(), point.get_Y());
                        else
                            throw new InappropriateFunctionPointException("Не соответствует условию граничной точки");
                    }
                }
            }
        }
    }

    public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException {
        if (index < arr.length - addElements && index > -1)
            return arr[index].get_X();
        else
            throw new FunctionPointIndexOutOfBoundsException("Выход за границы массива");
    }

    public void setPointX(int index, double x) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {
        if (index > arr.length - addElements - 1 || index < 0) //проверка индекса
            throw new FunctionPointIndexOutOfBoundsException("Выход за границы массива");
        else {
            if (arr.length - addElements > 2 && index != 0 && index != arr.length - addElements - 1) {//если массив из трех элемнтов и они не крайние
                if (arr[index + 1].get_X() > x && arr[index - 1].get_X() < x) //если х лежит между точками
                    arr[index].set_X(x);
                else
                    throw new InappropriateFunctionPointException("x не принадлежит интервалу между соседними точками");
            } else {
                switch (arr.length - addElements) {//если длина массива не 0
                    case 0: {
                        System.out.println("Массив пустой");
                        break;
                    }
                    case 1: {
                        if (index == 0)
                            arr[index].set_X(x);
                        else
                            throw new FunctionPointIndexOutOfBoundsException("Выход за границы массива");
                        break;
                    }
                    case 2: {
                        if (index == 0 && x < arr[1].get_X() || index == arr.length - addElements - 1 && x > arr[index - 1].get_X()) //включается 2 ситуации, когда 2 эл-та или 1
                            arr[index].set_X(x);
                        else
                            throw new InappropriateFunctionPointException("Не соответствует условию для 2 точек");
                        break;
                    }
                    default: {
                        if (index == 0 && x < arr[1].get_X() || index == arr.length - addElements - 1 && x > arr[index - 1].get_X())
                            arr[index].set_X(x);
                        else
                            throw new InappropriateFunctionPointException("Не соответствует условию граничной точки");
                    }
                }
            }
        }
    }

    public double getPointY(int index) throws FunctionPointIndexOutOfBoundsException {
        if (index < arr.length - addElements && index > -1)
            return arr[index].get_Y();
        else
            throw new FunctionPointIndexOutOfBoundsException("Выход за границы массива");
    }

    public void setPointY(int index, double y) throws FunctionPointIndexOutOfBoundsException {
        if (index < arr.length - addElements && index > -1)
            arr[index].set_Y(y);
        else
            throw new FunctionPointIndexOutOfBoundsException("Выход за границы массива");
    }

    public void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException {
        if (arr.length - addElements > 2) {
            if (index < arr.length - addElements && index > -1) {
                System.arraycopy(arr, index + 1, arr, index, arr.length - (index + 1) - addElements);//копируем массив arr в массив arr, пропуская элемент arr[index]
                arr[arr.length - addElements - 1] = null;//удаляем последний эл-т
                ++addElements;//увеличиваем число добавочных эл-ов, чтобы не изменилась длина массива в целом
            } else
                throw new FunctionPointIndexOutOfBoundsException("Выход за границы");
        } else {
            throw new IllegalStateException("В массиве 2 элемента, удалять нечего");
        }
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        if (addElements == 0) { //выделяем дополнительно память, если массив уже заполнен
            addElements = 3;
            FunctionPoint[] exArr = new FunctionPoint[arr.length + addElements]; //создаем новый увеличенный массив
            System.arraycopy(arr, 0, exArr, 0, arr.length); //копируем массив arr в exArr, при этом остается 3 доб. эл-та
            arr = exArr;
        }
        int i = 0;
        for (; i < arr.length - addElements && point.get_X() > arr[i].get_X(); ++i) ;
        if (i == arr.length - addElements){
            arr[i] = new FunctionPoint(point); //на i-ое место ставим новый point
            --addElements;//уменьшаем число добавочных эл-ов, чтобы не изменилась длина массива в целом
            return;
        }
        if (arr[i].get_X() == point.get_X()) {
            throw new InappropriateFunctionPointException("Данный элемент уже существует");
        } else {
            System.arraycopy(arr, i, arr, i + 1, arr.length - i - addElements);//справа от i-ого эл-та оставляем место для нового эл-та
            arr[i] = new FunctionPoint(point); //на i-ое место ставим новый point
            --addElements;//уменьшаем число добавочных эл-ов, чтобы не изменилась длина массива в целом
        }
    }

    public void printCont() {
        if (arr.length - addElements != 0)
            for (int i = 0; i < arr.length - addElements; ++i)
                System.out.print("(" + arr[i].get_X() + ", " + arr[i].get_Y() + "); ");
        else
            System.out.println("Массив пуст, выводить нечего");
        System.out.println();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(arr);
        out.writeObject(addElements);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        arr = (FunctionPoint[]) in.readObject();
        addElements = (int) in.readObject();
    }

    @Override
    public String toString() {
        String str = "{";
        for (int i = 0; i < getPointsCount(); ++i) {
            str += "(" + arr[i].get_X() + "; " + arr[i].get_Y() + ")";
            str += (i < getPointsCount() - 1) ? ", " : "}";
        }
        return str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof TabulatedFunction))
            return false;
        if (o instanceof ArrayTabulatedFunction) {
            if (((ArrayTabulatedFunction) o).getPointsCount() == getPointsCount()) {
                for (int i = 0; i < getPointsCount(); ++i)
                    if (!arr[i].equals(((ArrayTabulatedFunction) o).arr[i]))
                        return false;
                return true;
            } else
                return false;
        } else {
            if (((TabulatedFunction) o).getPointsCount() == getPointsCount()) {
                for (int i = 0; i < getPointsCount(); ++i)
                    if (!arr[i].equals(((TabulatedFunction) o).getPoint(i)))
                        return false;
                return true;
            } else
                return false;
        }
    }

    @Override
    public int hashCode() {
        int result = getPointsCount() * 11;
        for (int i = 0; i < getPointsCount(); ++i) {
            result += arr[i].hashCode() * 5;
        }
        return result;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ArrayTabulatedFunction nArr = (ArrayTabulatedFunction) super.clone();
        nArr.arr = arr.clone();
        return (Object) nArr;
    }
}