package gui;

import functions.*;

import java.io.*;
import java.util.Objects;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.Iterator;

public class DocumentTabFunc implements TabulatedFunction {

    private TabulatedFunction function;
    private String filename;
    private boolean fileNameAssigned = false;
    private boolean modified;

    public DocumentTabFunc() {
        filename = "";
        fileNameAssigned = false;
        modified = false;
    }

    public DocumentTabFunc(String file) {
        filename = file;
        fileNameAssigned = true;
        modified = false;
    }

    public void newFunction(double leftX, double rightX, int pointsCount) {
        function = new ArrayTabulatedFunction(leftX, rightX, pointsCount);
    }

    public void newFunction(FunctionPoint[] m) throws IllegalArgumentException, Exception{
        function = new ArrayTabulatedFunction(m);
    }

    public void saveFunction() throws FileNotFoundException {
        if (!modified) {
            if (!fileNameAssigned)
                throw new FileNotFoundException();
            try (FileWriter writer = new FileWriter(filename)) {
                JSONArray pointList = new JSONArray();
                for (int i = 0; i < function.getPointsCount(); ++i) {
                    JSONObject point = new JSONObject();
                    point.put("X", function.getPointX(i));
                    point.put("Y", function.getPointY(i));
                    pointList.add(point);
                }
                writer.write(pointList.toJSONString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            modified = true;
        }
    }

    public void saveFunctionAs(String fileName) {
        if (modified && filename.equals(fileName))
            return;
        try {
            filename = fileName;
            fileNameAssigned = true;
            saveFunction();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFunction(String fileName) throws FileNotFoundException, Exception {
        filename = fileName;
        fileNameAssigned = true;
        try (FileReader reader = new FileReader(fileName);) {
            JSONParser jsonParser = new JSONParser();
            Object o = (Object) jsonParser.parse(reader);
            JSONArray pointList = (JSONArray) o;
            FunctionPoint[] arrP = new FunctionPoint[pointList.size()];

            Iterator it = pointList.iterator();
            int i = 0;
            while (it.hasNext()) {
                JSONObject p = (JSONObject) it.next();
                arrP[i] = new FunctionPoint((double) p.get("X"), (double) p.get("Y"));
                ++i;
            }

            newFunction(arrP);

            fileNameAssigned = true;
            modified = false;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void tabulateFunction(Function func, double leftX, double rightX, int pointsCount) throws Exception {
        try {
            function = TabulatedFunctions.tabulate(func, leftX, rightX, pointsCount);
            modified = false;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public boolean isFileNameAssigned() {
        return fileNameAssigned;
    }

    public boolean isModified() {
        return modified;
    }

    @Override
    public double getLeftDomainBorder() {
        return function.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return function.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return function.getFunctionValue(x);
    }

    @Override
    public int getPointsCount() {
        return function.getPointsCount();
    }

    @Override
    public FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException {
        return function.getPoint(index);
    }

    @Override
    public void setPoint(int index, FunctionPoint point) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {
        function.setPoint(index, point);
        modified = false;
    }

    @Override
    public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException {
        return function.getPointX(index);
    }

    @Override
    public void setPointX(int index, double x) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {
        function.setPointX(index, x);
        modified = false;
    }

    @Override
    public double getPointY(int index) throws FunctionPointIndexOutOfBoundsException {
        return (function.getPointY(index));
    }

    @Override
    public void setPointY(int index, double y) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {
        function.setPointY(index, y);
        modified = false;
    }

    @Override
    public void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException, IllegalStateException {
        function.deletePoint(index);
        modified = false;
    }

    @Override
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        function.addPoint(point);
        modified = false;
    }

    @Override
    public void printCont() {
        function.printCont();
    }

    @Override
    public String toString() {
        String str = "{";
        for (int i = 0; i < getPointsCount(); ++i) {
            str += "(" + getPoint(i).get_X() + "; " + getPoint(i).get_Y() + ")";
            str += (i < getPointsCount() - 1) ? ", " : "}";
        }
        return str;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o instanceof TabulatedFunction) {
            if (((TabulatedFunction) o).getPointsCount() == getPointsCount()) {
                for (int i = 0; i < getPointsCount(); ++i)
                    if (!getPoint(i).equals(((TabulatedFunction) o).getPoint(i)))
                        return false;
                return true;
            } else
                return false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(function.hashCode(), filename, fileNameAssigned, modified);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        DocumentTabFunc clone = new DocumentTabFunc();
        clone.function = (TabulatedFunction) function.clone();
        clone.filename = filename;
        clone.modified = modified;
        clone.fileNameAssigned = fileNameAssigned;
        return clone;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    }
}