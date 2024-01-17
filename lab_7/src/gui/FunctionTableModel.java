package gui;

import functions.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FunctionTableModel extends DefaultTableModel {
    TabulatedFunction function;
    Component parent;

    public FunctionTableModel(TabulatedFunction func, Component par) {
        function = func;
        parent = par;
    }

    @Override
    public int getRowCount() {
        return (function != null) ? function.getPointsCount() : 0;
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int index) {
        //return (index == 0)? "X" : "Y";
        if (index == 0) return "X";
        if (index == 1) return "Y";
        return "Столбца с таким индексом не существует";
    }

    @Override
    public Class getColumnClass(int index) {
        return Double.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return (rowIndex > -1 && rowIndex < getRowCount() && columnIndex > -1 && columnIndex < 2);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (isCellEditable(rowIndex, columnIndex)) {
            switch (columnIndex) {
                case 0:
                    return function.getPointX(rowIndex);
                case 1:
                    return function.getPointY(rowIndex);
            }
        }
        return null;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        try {
            switch (columnIndex) {
                case 0:
                    function.setPointX(rowIndex, (double) value);
                    break;
                case 1:
                    function.setPointY(rowIndex, (double) value);
                    break;
                default:
                    throw new InappropriateFunctionPointException("Точка не подходит для данной функции");
            }
        } catch (InappropriateFunctionPointException | FunctionPointIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(parent, e.getMessage());
        }
    }
}
