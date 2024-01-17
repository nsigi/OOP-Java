package gui;

import functions.*;

import javax.swing.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import static java.lang.System.exit;

public class TabulatedFunctionFrame extends JFrame {
    private JPanel contentPane;
    private JTable tableTabFunc;
    private JLabel labelX;
    private JLabel labelY;
    private JTextField fieldX;
    private JTextField fieldY;
    private JButton addPointButton;
    private JButton deletePointButton;

    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu = new JMenu("File");
    private JMenu tabulateMenu = new JMenu("Tabulate");

    private JMenuItem newMenuItem = new JMenuItem("New");
    private JMenuItem openMenuItem = new JMenuItem("Open");
    private JMenuItem saveMenuItem = new JMenuItem("Save");
    private JMenuItem saveAsMenuItem = new JMenuItem("Save As");
    private JMenuItem exitMenuItem = new JMenuItem("Exit");

    private JMenuItem tabulateMenuItem = new JMenuItem("Tabulate function");

    private FunctionParametersDialog fParamsDialog;
    private DocumentTabFunc function;
    private JFileChooser fileChooser;
    private FunctionClassLoader fClassLoader;

    public TabulatedFunctionFrame() {
        setTitle("Tabulated function");
        setVisible(true);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        fParamsDialog = new FunctionParametersDialog();
        function = new DocumentTabFunc();
        fileChooser = new JFileChooser();
        fClassLoader = new FunctionClassLoader();

        function.newFunction(1, 10, 5);
        FunctionTableModel tableModel = new FunctionTableModel(function, this);
        tableTabFunc.setModel(tableModel);

        setContentPane(contentPane);

        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.add(exitMenuItem);
        tabulateMenu.add(tabulateMenuItem);
        menuBar.add(fileMenu);
        menuBar.add(tabulateMenu);
        setJMenuBar(menuBar);

        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onNew();
            }
        });

        saveAsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSaveAs();
            }
        });

        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    onSave();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(new JPanel(), e.getMessage(), "Ошибка сохранения функции в файл!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    onOpen();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(new JPanel(), e.getMessage(), "Ошибка сохранения функции в файл!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onExit();
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onExit();
            }
        });

        deletePointButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onDelete();
            }
        });

        addPointButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onAdd();
            }
        });

        tabulateMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onTabulate();
            }
        });
    }

    private void onNew() {
        if (fParamsDialog.showDialog() == 1) {
            function.newFunction(fParamsDialog.getLeftDomainBorder(), fParamsDialog.getRightDomainBorder(), fParamsDialog.getPointsCount());
            tableTabFunc.revalidate();
            tableTabFunc.repaint();
        }
    }

    private void onSaveAs() {
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                String fileName = fileChooser.getSelectedFile().getAbsolutePath();
                function.saveFunctionAs(fileName);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(new JPanel(), "Возможно файл не выбран или функцию не удалось сохранить", "Ошибка!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onSave() throws IOException {
        if (function.isFileNameAssigned())
            function.saveFunction();
        else
            onSaveAs();
    }

    private void onOpen() throws Exception {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                String fileName = fileChooser.getSelectedFile().getAbsolutePath();
                function.loadFunction(fileName);
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(new JPanel(), "Не удалось открыть файл", "Ошибка открытия файла!", JOptionPane.ERROR_MESSAGE);
            }
            tableTabFunc.revalidate();
            tableTabFunc.repaint();
        }
    }

    private void onExit() {
        if (!function.isModified()) {
            int confirm = JOptionPane.showConfirmDialog(new JPanel(), "Функция не сохранена.\n Вы действительно хотите выйти?",
                    "Подтверждение выхода", JOptionPane.YES_NO_OPTION);
            if (confirm == 0) {
                dispose();
                exit(0);
            }
            else if (confirm == 1)
                onSaveAs();
        } else
            dispose();
    }

    private void onDelete() {
        try {
            function.deletePoint(tableTabFunc.getSelectedRow());
            tableTabFunc.revalidate();
            tableTabFunc.repaint();
        } catch (FunctionPointIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Ошибка удаления точки!", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this, "Невозможно удалить точки, когда их меньше 2", "Ошибка удаления точки!!", JOptionPane.ERROR_MESSAGE);
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(this, "Выбрали точку с неверным индексом", "Ошибка удаления точки!!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onAdd() {
        try {
            function.addPoint(new FunctionPoint(Double.parseDouble(fieldX.getText()), Double.parseDouble(fieldY.getText())));
            tableTabFunc.revalidate();
            tableTabFunc.repaint();
        } catch (InappropriateFunctionPointException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Точка с такими координатами не может быть добавлена в данную табулированную функцию.", "Ошибка добавления точки!", JOptionPane.ERROR_MESSAGE);
        } catch (FunctionPointIndexOutOfBoundsException e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    private void onTabulate() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                if (fParamsDialog.showDialog() == 1) {
                    Class functionClass = fClassLoader.loadClassFromFile(fileChooser.getSelectedFile().getAbsolutePath());
                    Function func = (Function) functionClass.newInstance();
                    function.tabulateFunction(func, fParamsDialog.getLeftDomainBorder(), fParamsDialog.getRightDomainBorder(), fParamsDialog.getPointsCount());
                    tableTabFunc.revalidate();
                    tableTabFunc.repaint();
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Ошибка ввода", "Ошибка табулирования!", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalAccessException e) {
                JOptionPane.showMessageDialog(this, "Доступ к закрытому члену запрещён", "Ошибка табулирования!", JOptionPane.ERROR_MESSAGE);
            } catch (InstantiationException e) {
                JOptionPane.showMessageDialog(this, "Не получилось создать объект данного абстрактного  класса", "Ошибка табулирования!", JOptionPane.ERROR_MESSAGE);
            } catch (ClassFormatError e) {
                JOptionPane.showMessageDialog(this, "Файл не является байт-кодом класса", "Ошибка табулирования!", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showFrame() {
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        TabulatedFunctionFrame frame = new TabulatedFunctionFrame();
        frame.showFrame();
    }
}
