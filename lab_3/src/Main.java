import functions.*;

public class Main {
    public static void main(String[] args) {
        try {
            //LinkedListTabulatedFunction myList = new LinkedListTabulatedFunction(1, 5, new double[]{1, 4, 9, 16, 25});
            LinkedListTabulatedFunction myList = new LinkedListTabulatedFunction(1, 2, 2);

            myList.printCont();
            System.out.println();
          //  myList.addPoint(new FunctionPoint(4.5, 10));
            myList.deletePoint(0);
            myList.printCont();
            //System.out.println();
           // System.out.println(myList.getFunctionValue(1.5));
        } catch (FunctionPointIndexOutOfBoundsException | IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
