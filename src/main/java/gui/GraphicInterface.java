package gui;

import function.GeneralFunctions;
import plotter.Plotter;
import util.GeneralAlgorithms;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
/**
 * Created by Abovyan Narek on 09.03.15.
 */
import javax.swing.JFrame;

/**
 * Этот класс предназначен для выполнения задач связанных
 * с графическим взаимодействием с пользователем и отображением
 * графической информации
 */


public class GraphicInterface extends JFrame {
    private GeneralAlgorithms generalAlgorithms; // ссылка на объект который инкапсулирует в себе общий алгоритм задачи
    private JLabel countLabel;   // текстовое поле для отображения информации
    private JButton startСalculating;  // кнопка для начала вычислений
    private JButton closeAllWindow;  // кнопка для закрытия всех окон содержащих графики
    private JButton visableAndSaveImageAllGraph;  // кнопка для отображения всех графиков и сохранения их в виде картинок
    private String selectesItem = "P_ks";
    private JComboBox comboBox; //= new JComboBox(items);
    public GraphicInterface() {
        super("Crow calculator");
        generalAlgorithms = new GeneralAlgorithms(); // создание объекта
        setDefaultCloseOperation(EXIT_ON_CLOSE);  // устанавливаем действие при нажатии крестика
        /* Подготавливаем компоненты объекта  */
        addComponents();

     /* Подготавливаем временные компоненты  */
        JPanel buttonsPanel = new JPanel(new FlowLayout());
     /* Расставляем компоненты по местам  */
        add(countLabel, BorderLayout.NORTH);
        buttonsPanel.add(startСalculating);
        buttonsPanel.add(closeAllWindow);
        buttonsPanel.add(visableAndSaveImageAllGraph);
        buttonsPanel.add(comboBox);
        add(buttonsPanel, BorderLayout.SOUTH);
        initListeners(); // иницируем слушателей событий формы
    }

    private void initListeners() {
        startСalculating.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generalAlgorithms.startCalculating();
                countLabel.setText("Расчет завершен !!!");
                //Plotter.plot(0, 0.01, "P_ks", generalAlgorithms.getResultIntegration(), true);
            }
        });
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox box = (JComboBox)e.getSource();
                selectesItem = (String)box.getSelectedItem();
            }
        });
        closeAllWindow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Plotter.closeAllWindow();  // закрываем все окна с графиками
                //GeneralFunctions.instance().getResultIntegration().clearIndexAndArray();  // очищаем resultIntegretion singleton класса GeneralFunctions
            }
        });
        visableAndSaveImageAllGraph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(generalAlgorithms.getResultIntegration() == null)) {
                    Plotter.plot(GeneralAlgorithms.theBeginningOfTheInterval, GeneralAlgorithms.endOfTheInterval, selectesItem, generalAlgorithms.getResultIntegration(), true, GraphicInterface.this);
                } else countLabel.setText("Необходимо сначала провести расчет !!!");
            }
        });
    }

    public void begin() {
        GraphicInterface appFrame = new GraphicInterface();
        //appFrame.setSize(900, 700);
        appFrame.pack(); /* Эта команда подбирает оптимальный размер в зависимости от содержимого окна  */
        appFrame.setLocationRelativeTo(null);
        appFrame.setVisible(true);
    }

    private void addComponents () {
        /* Подготавливаем компоненты объекта  */
        countLabel = new JLabel("Добро пожаловать в программу для расчета раскрытия крыльев !!!");
        startСalculating = new JButton("Начать расчет");
        closeAllWindow = new JButton("Закрыть все окна");
        visableAndSaveImageAllGraph = new JButton("Построить график");
        // список для выбора графика
        Font font = new Font("Verdana", Font.PLAIN, 18);
        comboBox = new JComboBox(getItems());
        comboBox.setFont(font);
        comboBox.setAlignmentX(LEFT_ALIGNMENT);
    }

    private String[] getItems() {
        return GeneralFunctions.getListFunctionsNames();
    }
}