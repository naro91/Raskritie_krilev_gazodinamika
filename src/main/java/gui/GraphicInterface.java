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
    private JButton addChart;  // кнопка для добавления выбранного графика
    private JButton visableChart;  // кнопка для отображения выбранного графика
    private JButton saveAllChart; // кнопка для сохранения всех графиков
    private String selectesItem;//= "Temperature";
    private JComboBox comboBox; //= new JComboBox(items);
    public GraphicInterface() {
        super("Crow calculator");
        generalAlgorithms = new GeneralAlgorithms(); // создание объекта
        setDefaultCloseOperation(EXIT_ON_CLOSE);  // устанавливаем действие при нажатии крестика
        /* Подготавливаем компоненты объекта  */
        addComponents();
        selectesItem = null;
     /* Подготавливаем временные компоненты  */
        JPanel buttonsPanel = new JPanel(new FlowLayout());
     /* Расставляем компоненты по местам  */
        add(countLabel, BorderLayout.NORTH);
        buttonsPanel.add(startСalculating);
        buttonsPanel.add(saveAllChart);
        buttonsPanel.add(addChart);
        buttonsPanel.add(visableChart);
        buttonsPanel.add(comboBox);
        add(buttonsPanel, BorderLayout.SOUTH);
        initListeners(); // иницируем слушателей событий формы
    }

    private void initListeners() {
        startСalculating.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generalAlgorithms.startCalculating();
                comboBox.setModel(new DefaultComboBoxModel<String>(getItems()));
                if (selectesItem != null) {
                    comboBox.setSelectedItem(selectesItem);
                } else {
                    selectesItem = (String) comboBox.getSelectedItem();
                }
                countLabel.setText("Расчет завершен !!!");
            }
        });
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox box = (JComboBox)e.getSource();
                selectesItem = (String)box.getSelectedItem();
            }
        });
        addChart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (generalAlgorithms.getResultIntegration() != null) {
                    Plotter.plot(selectesItem, generalAlgorithms.getResultIntegration(), GraphicInterface.this, true);
                } else countLabel.setText("Необходимо сначала провести расчет !!!");
            }
        });
        visableChart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(generalAlgorithms.getResultIntegration() == null)) {
                    Plotter.plot(selectesItem, generalAlgorithms.getResultIntegration(), GraphicInterface.this, false);
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
                            Plotter.saveAllChart(generalAlgorithms.getResultIntegration());
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
        //appFrame.setSize(900, 700);
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
    }

    private String[] getItems() {
        String[] temp = {"Список результатов"};
        return generalAlgorithms.getResultIntegration() == null ? temp : generalAlgorithms.getResultIntegration().getListResultsNames();
    }
}