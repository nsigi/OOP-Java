import functions.*;
import functions.basic.*;
import functions.meta.*;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            ArrayTabulatedFunction array = new ArrayTabulatedFunction(1, 5, new double[]{1, 2, 3, 4, 5});
            System.out.println(array.toString());
            LinkedListTabulatedFunction list = new LinkedListTabulatedFunction(1, 5, new double[]{1, 2, 10, 18, 26});
            System.out.println(list.toString());
            LinkedListTabulatedFunction list1 = new LinkedListTabulatedFunction(1, 5, new double[]{1, 2, 3, 4, 5});
            System.out.println(list1.toString());

            System.out.println(list1.equals(array));
            Object o = new Object();
            o = list.clone();
            System.out.println(o.toString());
           // list.setPoint(1, new FunctionPoint(1.5, 19));
            System.out.println(list.toString());
            System.out.println(o.toString());
            System.out.println(array.hashCode());
            System.out.println(list.hashCode());
            System.out.println(o.hashCode());
        } catch (FunctionPointIndexOutOfBoundsException | IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
