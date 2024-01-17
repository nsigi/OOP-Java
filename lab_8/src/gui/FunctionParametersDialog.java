package gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FunctionParametersDialog extends JDialog {
    private final int OK = 1;
    private final int CANCEL = -1;
    private JPanel mainPanel;
    private JTextField leftBorderField;
    private JTextField rightBorderField;
    private JSpinner pointCountSpinner;
    private JButton buttonCancel;
    private JButton buttonOK;

    private int status;
    private double leftBorder;
    private double rightBorder;
    private int pointCount;

    public FunctionParametersDialog() throws NumberFormatException {
        setContentPane(mainPanel);
        setTitle("Function parameters");
        setModal(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        SpinnerModel spinnerModel = new SpinnerNumberModel(2, 2, 100, 1);
        pointCountSpinner.setModel(spinnerModel);

        buttonOK.addActionListener(ev -> {
            String leftBorderStr = leftBorderField.getText();
            String rightBorderStr = rightBorderField.getText();

            if (leftBorderStr.isEmpty()) {
                JOptionPane.showMessageDialog(new JPanel(), "Поле левой границы пустое", "Ошибка ввода данных!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (rightBorderStr.isEmpty()) {
                JOptionPane.showMessageDialog(new JPanel(), "Поле правой границы пустое", "Ошибка ввода данных!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                double left = Double.parseDouble(leftBorderStr);
                double right = Double.parseDouble(rightBorderStr);
                if (right <= left) {
                    JOptionPane.showMessageDialog(new JPanel(), "Левая граница должна быть меньше правой", "Ошибка ввода данных!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                pointCount = (int) pointCountSpinner.getValue();
                status = OK;
                leftBorder = left;
                rightBorder = right;
                setVisible(false);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(new JPanel(), "Вводимые данные должны быть числами", "Ошибка ввода данных!", JOptionPane.ERROR_MESSAGE);
                e.getStackTrace();
            }
        });

        buttonCancel.addActionListener(ev -> {
            status = CANCEL;
            setVisible(false);
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                status = CANCEL;
            }
        });
    }

    public int showDialog() {
        pack();
        setVisible(true);
        return status;
    }

    public double getLeftDomainBorder() {
        return leftBorder;
    }

    public double getRightDomainBorder() {
        return rightBorder;
    }

    public int getPointsCount() {
        return pointCount;
    }
}
