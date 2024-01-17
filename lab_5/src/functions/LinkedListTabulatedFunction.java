package functions;
import java.io.*;

public class LinkedListTabulatedFunction implements TabulatedFunction {
    class FunctionNode implements Serializable, Cloneable {
        private FunctionPoint point;
        private FunctionNode next = null;
        private FunctionNode prev = null;

        public FunctionNode() {
            point = new FunctionPoint();
        }

        public FunctionNode(FunctionPoint p) {
            point = p;
        }

        public FunctionNode(FunctionNode node) {
            point = node.point;
            next = node.next;
            prev = node.prev;
        }
    }

    private FunctionNode head;
    private int lengthList;
    private FunctionNode lastUsed;
    private int indexLastUsed;

    private boolean chSort(FunctionPoint[] m) {
        for (int i = 0; i < m.length - 1; ++i)
            if (m[i].get_X() > m[i + 1].get_X())
                return false;
        return true;
    }

    public LinkedListTabulatedFunction() {
        head = lastUsed = null;
        lengthList = 0;
        indexLastUsed = -1;
    }

    public LinkedListTabulatedFunction(FunctionPoint[] m) throws IllegalArgumentException {
        if (m.length > 1 && chSort(m)) {
            head = new FunctionNode();
            head.next = head.prev = head;
            lengthList = 0;
            lastUsed = null;
            indexLastUsed = -1;

            for (int i = 0; i < m.length; ++i) {
                FunctionPoint newEl = addNodeToTail().point;
                newEl.set_X(m[i].get_X());
                newEl.set_Y(m[i].get_Y());
            }
        } else
            throw new IllegalArgumentException("Массив не упорядочен или число точек менее двух");
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount) throws IllegalArgumentException {
        if (leftX < rightX && pointsCount > 1) {
            head = new FunctionNode();
            head.next = head.prev = head;
            lengthList = 0;
            lastUsed = null;
            indexLastUsed = -1;

            double step = (rightX - leftX) / (pointsCount - 1);
            for (int i = 0; leftX <= rightX; ++i, leftX += step) { //заполнение массива
                addNodeToTail().point.set_X(leftX);
            }
        } else
            throw new IllegalArgumentException("Некорректный ввод границ или кол-во точек");
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, double[] values) throws IllegalArgumentException {
        if (leftX < rightX && values.length > 1) {
            head = new FunctionNode();
            head.next = head.prev = head;
            lengthList = 0;
            lastUsed = null;
            indexLastUsed = -1;

            double step = (rightX - leftX) / (values.length - 1);
            for (int i = 0; leftX <= rightX; ++i, leftX += step) { //заполнение массива
                FunctionPoint newEl = addNodeToTail().point;
                newEl.set_X(leftX);
                newEl.set_Y(values[i]);
            }
        } else
            throw new IllegalArgumentException("Некорректный ввод границ или кол-во точек");
    }

    FunctionNode addNodeToTail() {
        indexLastUsed = lengthList++;
        FunctionNode nElem = new FunctionNode();
        nElem.next = head;
        nElem.prev = head.prev;
        if (lengthList == 1)
            head.next = nElem;
        else
            head.prev.next = nElem;
        head.prev = nElem;

        lastUsed = nElem;
        return nElem;
    }

    FunctionNode getNodeByIndex(int index) {
        FunctionNode buf;
        if (indexLastUsed != index) {
            if (indexLastUsed < index) {
                if ((index - indexLastUsed) < (lengthList - index)) {
                    buf = lastUsed;
                    for (int i = indexLastUsed; i < index; ++i, buf = buf.next) ;
                } else {
                    buf = head;
                    for (int i = lengthList; i > index; --i, buf = buf.prev) ;
                }
            } else {
                if ((indexLastUsed - index - 1) < index) {
                    buf = lastUsed;
                    for (int i = indexLastUsed; i > index; --i, buf = buf.prev) ;
                } else {
                    buf = head;
                    for (int i = -1; i < index; ++i, buf = buf.next) ;
                }
            }

            lastUsed = buf;
            indexLastUsed = index;
        }
        return lastUsed;
    }

    FunctionNode addNodeByIndex(int index) {
        ++lengthList;
        FunctionNode findElem = getNodeByIndex(index);
        FunctionNode newElem = new FunctionNode();
        newElem.prev = findElem.prev;
        newElem.next = findElem;
        findElem.prev = findElem.prev.next = newElem;

        lastUsed = newElem;
        indexLastUsed = index;
        return newElem;
    }

    FunctionNode deleteNodeByIndex(int index) {
        --lengthList;
        FunctionNode deleteElem = getNodeByIndex(index);
        lastUsed = deleteElem.prev;
        indexLastUsed = index + 1;
        deleteElem.prev.next = deleteElem.next;
        deleteElem.next.prev = deleteElem.prev;
        deleteElem.next = deleteElem.prev = null;

        return deleteElem;
    }

    public double getRightDomainBorder() {
        return head.prev.point.get_X();
    }

    public double getLeftDomainBorder() {
        return head.next.point.get_X();
    }

    public double getFunctionValue(double x) {
        if (x < getLeftDomainBorder() || x > getRightDomainBorder())//проверка на выход за границы х
            return Double.NaN;
        else {
            if (x != getNodeByIndex(0).point.get_X()) {//случай, когда x равен первому эл-ту
                int i = 0;
                for (; x > getNodeByIndex(i).point.get_X(); ++i) ; //поиск эл-та с заданным значением
                return ((x - getNodeByIndex(i - 1).point.get_X()) * (getNodeByIndex(i).point.get_Y() - getNodeByIndex(i - 1).point.get_Y())) / (getNodeByIndex(i).point.get_X() - getNodeByIndex(i - 1).point.get_X()) + getNodeByIndex(i - 1).point.get_Y();
            } else
                return getNodeByIndex(0).point.get_Y();
        }
    }

    public int getPointsCount() {
        return lengthList;
    }

    public FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException {
        if (index > lengthList - 1 || index < 0)
            throw new FunctionPointIndexOutOfBoundsException("Выход за границы списка");
        return getNodeByIndex(index).point;
    }

    public void setPoint(int index, FunctionPoint point) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {
        if (index > lengthList - 1 || index < 0)
            throw new FunctionPointIndexOutOfBoundsException("Выход за границы списка");
        else {
            if (lengthList > 2 && index != 0 && index != lengthList - 1) {//проверка на то, что кол-во элементов > 3 и заменяемый элемент не крайнийй
                if (getNodeByIndex(index + 1).point.get_X() > point.get_X() && getNodeByIndex(index - 1).point.get_X() < point.get_X()) //х принадлежит интервалу между соседними точками
                    getNodeByIndex(index).point = new FunctionPoint(point.get_X(), point.get_Y());
                else
                    throw new InappropriateFunctionPointException("x не принадлежит интервалу между соседними точками");
            } else {
                switch (lengthList) {//смотрим на кол-во эл-ов в массиве
                    case 0: {
                        System.out.println("Список пустой");
                        break;
                    }
                    case 1: {
                        if (index == 0)
                            getNodeByIndex(index).point = new FunctionPoint(point.get_X(), point.get_Y());
                        else
                            throw new FunctionPointIndexOutOfBoundsException("Выход за границы списка");
                        break;
                    }
                    case 2: {
                        if (index == 0 && point.get_X() < getNodeByIndex(1).point.get_X() || index == lengthList - 1 && point.get_X() > getNodeByIndex(index - 1).point.get_X())
                            getNodeByIndex(index).point = new FunctionPoint(point.get_X(), point.get_Y());
                        else
                            throw new InappropriateFunctionPointException("Не соответствует условию граничных точек");
                        break;
                    }
                    default: {
                        if (index == 0 && point.get_X() < getNodeByIndex(1).point.get_X() || index == lengthList - 1 && point.get_X() > getNodeByIndex(index - 1).point.get_X())
                            getNodeByIndex(index).point = new FunctionPoint(point.get_X(), point.get_Y());
                        else
                            throw new InappropriateFunctionPointException("Не соответствует условию граничной точки");
                    }
                }
            }
        }
    }

    public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException {
        if (index < lengthList && index > -1)
            return getNodeByIndex(index).point.get_X();
        else
            throw new FunctionPointIndexOutOfBoundsException("Выход за границы списка");
    }

    public void setPointX(int index, double x) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {
        if (index > lengthList - 1 || index < 0) //проверка индекса
            throw new FunctionPointIndexOutOfBoundsException("Выход за границы списка");
        else {
            if (lengthList > 2 && index != 0 && index != lengthList - 1) {//если массив из трех элементов и они не крайние
                if (getNodeByIndex(index + 1).point.get_X() > x && getNodeByIndex(index - 1).point.get_X() < x) //если х лежит между точками
                    getNodeByIndex(index).point.set_X(x);
                else
                    throw new InappropriateFunctionPointException("x не принадлежит интервалу между соседними точками");
            } else {
                switch (lengthList) {//если длина массива не 0
                    case 0:
                    case 1: {
                        System.out.println("Длина списка должна быть больше 1");
                        break;
                    }
                    case 2: {
                        if (index == 0 && x < getNodeByIndex(1).point.get_X() || index == lengthList - 1 && x > getNodeByIndex(index - 1).point.get_X()) //включается 2 ситуации, когда 2 эл-та или 1
                            getNodeByIndex(index).point.set_X(x);
                        else
                            throw new InappropriateFunctionPointException("Не соответствует условию для 2 точек");
                        break;
                    }
                    default: {
                        if (index == 0 && x < getNodeByIndex(1).point.get_X() || index == lengthList - 1 && x > getNodeByIndex(index - 1).point.get_X())
                            getNodeByIndex(index).point.set_X(x);
                        else
                            throw new InappropriateFunctionPointException("Не соответствует условию граничной точки");
                    }
                }
            }
        }
    }

    public double getPointY(int index) throws FunctionPointIndexOutOfBoundsException {
        if (index < lengthList && index > -1)
            return getNodeByIndex(index).point.get_Y();
        else
            throw new FunctionPointIndexOutOfBoundsException("Выход за границы списка");
    }

    public void setPointY(int index, double y) throws FunctionPointIndexOutOfBoundsException {
        if (index < lengthList && index > -1)
            getNodeByIndex(index).point.set_Y(y);
        else
            throw new FunctionPointIndexOutOfBoundsException("Выход за границы списка");
    }

    public void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException, IllegalStateException {
        if (index < lengthList && index > -1) {
            if (lengthList > 2) {
                deleteNodeByIndex(index);
            } else {
                throw new IllegalStateException("Нельзя удалить точку,так как их осталось 2");
            }
        } else {
            throw new FunctionPointIndexOutOfBoundsException("Выход за границы списка");
        }
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        int i = 0;
        for (; i < lengthList && point.get_X() > getNodeByIndex(i).point.get_X(); ++i) ;

        if (i < lengthList && getNodeByIndex(i).point.get_X() == point.get_X())
            throw new InappropriateFunctionPointException("Элемент с таким x уже существует");
        else {
            FunctionNode addEl = (i == lengthList) ? addNodeToTail() : addNodeByIndex(i);
            addEl.point.set_X(point.get_X());
            addEl.point.set_Y(point.get_Y());
        }
    }

    public void printCont() {
        if (lengthList != 0)
            for (int i = 0; i < lengthList; ++i)
                System.out.print("(" + getNodeByIndex(i).point.get_X() + ", " + getNodeByIndex(i).point.get_Y() + "); ");
        else
            System.out.println("Список пуст, выводить нечего");
        System.out.println();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(lengthList);
        FunctionPoint[] points = new FunctionPoint[lengthList];
        for (int i = 0; i < lengthList; ++i) {
            points[i] = getPoint(i);
        }
        out.writeObject(points);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int size = (int) in.readObject();
        FunctionPoint[] points = (FunctionPoint[]) in.readObject();
        head = new FunctionNode();
        FunctionNode firstEl = new FunctionNode(points[0]);
        FunctionNode secondEl = new FunctionNode(points[1]);
        head.next = secondEl.prev = firstEl;
        firstEl.next = head.prev = secondEl;
        secondEl.next = firstEl.prev = head;
        lastUsed = secondEl;
        indexLastUsed = 1;
        lengthList = 2;

        for (int i = 2; i < size; ++i) {
            try {
                addPoint(points[i]);
            } catch (InappropriateFunctionPointException e) {
                e.getMessage();
            }
        }
    }

    @Override
    public String toString() {
        String str = "{";
        for (FunctionNode p = head.next; p != head; p = p.next) {
            str += "(" + p.point.get_X() + "; " + p.point.get_Y() + ")";
            str += (p.next != head) ? ", " : "}";
        }
        return str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof TabulatedFunction))
            return false;
        if (o instanceof LinkedListTabulatedFunction) {
            if (((LinkedListTabulatedFunction) o).lengthList == lengthList) {
                FunctionNode p, pOb;
                for (p = head.next, pOb = ((LinkedListTabulatedFunction) o).head.next; p != head; p = p.next, pOb = pOb.next)
                    if (!p.point.equals(pOb.point))
                        return false;
                return true;
            } else
                return false;
        } else {
            if (((TabulatedFunction) o).getPointsCount() == getPointsCount()) {
                int i = 0;
                for (FunctionNode p = head.next; p != head; p = p.next)
                    if (!p.point.equals(((TabulatedFunction) o).getPoint(i++)))
                        return false;
                return true;
            } else
                return false;
        }
    }

    @Override
    public int hashCode() {
        int result = lengthList * 11;
        for (FunctionNode p = head.next; p != head; p = p.next) {
            result += p.point.hashCode() * 5;
        }
        return result;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        FunctionPoint[] arrList = new FunctionPoint[lengthList];
        for (int i = 0; i < lengthList; ++i)
            arrList[i] = new FunctionPoint(getPoint(i));
        return (Object) new LinkedListTabulatedFunction(arrList);
    }
}