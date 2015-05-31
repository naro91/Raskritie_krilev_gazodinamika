package gui;

import function.GeneralFunctions;
import plotter.Plotter;
import util.GeneralAlgorithms;
import util.MeasureSystem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
    private MeasureSystem measureSystem = new MeasureSystem(); // объект содержащий методы для получения коэффициента
                                                                // перевода из одной системы единиц в другую
    private JLabel countLabel;   // текстовое поле для отображения информации
    private JButton startСalculating;  // кнопка для начала вычислений
    private JButton addChart;  // кнопка для добавления выбранного графика
    private JButton visableChart;  // кнопка для отображения выбранного графика
    private JButton saveAllChart, testButton1, testButton2; // кнопка для сохранения всех графиков
    private String selectesItem;//= "Temperature";
    private String selectesMeasureUnit;//= "единица измерения";
    private JComboBox comboBox, measureSystemsComBox; //= new JComboBox(items);
    public GraphicInterface() {
        super("ПРОГРАММА ДЛЯ РАСЧЕТА РАСКРЫТИЯ КРЫЛЬЕВ");
        generalAlgorithms = new GeneralAlgorithms(); // создание объекта
        setDefaultCloseOperation(EXIT_ON_CLOSE);  // устанавливаем действие при нажатии крестика
        /* Подготавливаем компоненты объекта  */
        addComponents();
        selectesItem = null;
     /* Подготавливаем временные компоненты  */
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        JPanel panel = new JPanel(new FlowLayout());

     /* Расставляем компоненты по местам  */
        //add(countLabel, BorderLayout.NORTH);
        //buttonsPanel.setBackground(Color.GRAY);
        buttonsPanel.add(saveAllChart);
        buttonsPanel.add(addChart);
        buttonsPanel.add(visableChart);
        buttonsPanel.add(comboBox);
        buttonsPanel.add(measureSystemsComBox);
        //panel.setBackground(Color.GRAY);
        panel.add(countLabel);
        panel.add(startСalculating);
        //panel.add(testButton2);
        add(buttonsPanel, BorderLayout.SOUTH);
        add(panel, BorderLayout.NORTH);
        initListeners(); // инициируем слушателей событий формы
    }

    private void initListeners() {
        startСalculating.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generalAlgorithms.startCalculating();
                comboBox.setModel(new DefaultComboBoxModel<String>(getItems())); // устанавливаем список вычисленных параметров
                if (selectesItem != null) { // если из списка выбран параметр то оставляем его
                    comboBox.setSelectedItem(selectesItem);
                } else { // если не выбран параметр то получаем выбранный параметр из comBox и запоминаем значение
                    selectesItem = (String) comboBox.getSelectedItem();
                }
                measureSystemsComBox.setModel(new DefaultComboBoxModel<String>(measureSystem.getArrayMeasureUnitByName(selectesItem)));
                if (selectesMeasureUnit != null) {
                    measureSystemsComBox.setSelectedItem(selectesMeasureUnit);
                } else {
                    selectesMeasureUnit = (String) measureSystemsComBox.getSelectedItem();
                }
                countLabel.setText("Расчет завершен !!!");
            }
        });
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox box = (JComboBox)e.getSource();
                selectesItem = (String)box.getSelectedItem();
                measureSystemsComBox.setModel(new DefaultComboBoxModel<String>(measureSystem.getArrayMeasureUnitByName(selectesItem)));
                selectesMeasureUnit = (String) measureSystemsComBox.getSelectedItem();
            }
        });
        measureSystemsComBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox box = (JComboBox)e.getSource();
                selectesMeasureUnit = (String)box.getSelectedItem();
            }
        });
        addChart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (generalAlgorithms.getResultIntegration() != null) {
                    double coeffX = measureSystem.getCoefficientConversionByName(selectesMeasureUnit);
                    double coeffY = 1;
                    try {
                        Plotter.plot(selectesItem, generalAlgorithms.getResultIntegration(), new MainGUI(), true, coeffX, coeffY);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else countLabel.setText("Необходимо сначала провести расчет !!!");
            }
        });
        visableChart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(generalAlgorithms.getResultIntegration() == null)) {
                    double coeffX = measureSystem.getCoefficientConversionByName(selectesMeasureUnit);
                    double coeffY = 1;
                    try {
                        Plotter.plot(selectesItem, generalAlgorithms.getResultIntegration(), new MainGUI(), false, coeffX, coeffY);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else countLabel.setText("Необходимо сначала провести расчет !!!");
            }
        });
        saveAllChart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (generalAlgorithms.getResultIntegration() != null) {
                    Thread threadSaving = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            countLabel.setText("Идет процесс сохранения графиков !!!");
                            Plotter.saveAllChart(generalAlgorithms.getResultIntegration(), measureSystem);
                            countLabel.setText("Все графики сохранены с расширением png в директории graphicsImage !!!");
                        }
                    });
                    threadSaving.start();
                } else countLabel.setText("Необходимо сначала провести расчет !!!");
            }
        });
    }

    public void begin() {
        GraphicInterface appFrame = new GraphicInterface();
        //appFrame.setSize(1000, 600);
        appFrame.pack(); /* Эта команда подбирает оптимальный размер в зависимости от содержимого окна  */
        appFrame.setLocationRelativeTo(null);
        appFrame.setVisible(true);
    }

    private void addComponents () {
        /* Подготавливаем компоненты объекта  */
        countLabel = new JLabel("Добро пожаловать в программу для расчета раскрытия крыльев !!!");
        startСalculating = new JButton("Начать расчет");
        addChart = new JButton("Добавить график");
        visableChart = new JButton("Построить график");
        saveAllChart = new JButton("Сохранить все графики");
        // список для выбора графика
        Font font = new Font("Verdana", Font.PLAIN, 18);
        comboBox = new JComboBox(getItems());
        comboBox.setFont(font);
        comboBox.setAlignmentX(LEFT_ALIGNMENT);
        // список для выбора единицы измерения
        measureSystemsComBox = new JComboBox(measureSystem.getArrayMeasureUnit());
        measureSystemsComBox.setFont(font);
        measureSystemsComBox.setAlignmentX(LEFT_ALIGNMENT);
    }

    private String[] getItems() {
        String[] temp = {"Список результатов"};
        return generalAlgorithms.getResultIntegration() == null ? temp : generalAlgorithms.getResultIntegration().getListResultsNames();
    }
}