package functions;

import java.io.Externalizable;
import java.io.Serializable;

public interface TabulatedFunction extends Function, Serializable, Externalizable, Cloneable {
    int getPointsCount();

    FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException;

    void setPoint(int index, FunctionPoint point) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException;

    double getPointX(int index) throws FunctionPointIndexOutOfBoundsException;

    void setPointX(int index, double x) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException;

    double getPointY(int index) throws FunctionPointIndexOutOfBoundsException;

    void setPointY(int index, double y) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException;

    void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException, IllegalStateException;

    void addPoint(FunctionPoint point) throws InappropriateFunctionPointException;

    void printCont();

    String toString();

    boolean equals(Object o);

    int hashCode();

    Object clone() throws CloneNotSupportedException;
}