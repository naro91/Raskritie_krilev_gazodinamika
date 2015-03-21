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
    public GraphicInterface() {
        super("Crow calculator");
        generalAlgorithms = new GeneralAlgorithms(); // создание объекта
        setDefaultCloseOperation(EXIT_ON_CLOSE);  // устанавливаем действие при нажатии крестика
     /* Подготавливаем компоненты объекта  */
        countLabel = new JLabel("Welcome to the program to calculate the spread wings !!!");
        startСalculating = new JButton("Start Calculating");
        closeAllWindow = new JButton("Close All Window");
        visableAndSaveImageAllGraph = new JButton("Visible and save all graph");
     /* Подготавливаем временные компоненты  */
        JPanel buttonsPanel = new JPanel(new FlowLayout());
     /* Расставляем компоненты по местам  */
        add(countLabel, BorderLayout.NORTH);
        buttonsPanel.add(startСalculating);
        buttonsPanel.add(closeAllWindow);
        buttonsPanel.add(visableAndSaveImageAllGraph);
        add(buttonsPanel, BorderLayout.SOUTH);
        initListeners(); // иницируем слушателей событий формы
    }
    private void initListeners() {
        startСalculating.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generalAlgorithms.startCalculating();
                countLabel.setText("Finish calculating !!!");
                //Plotter.plot(0, 0.01, "P_ks", generalAlgorithms.getResultIntegration(), true);
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
                    Plotter.plotAllGraphics(GeneralAlgorithms.theBeginningOfTheInterval, GeneralAlgorithms.endOfTheInterval, generalAlgorithms.getResultIntegration(), true);
                } else countLabel.setText("you first need to calculate !!!");
            }
        });
    }

    public void begin() {
        GraphicInterface appFrame = new GraphicInterface();
        appFrame.setLocationRelativeTo(null);
        appFrame.setVisible(true);
        appFrame.pack(); /* Эта команда подбирает оптимальный размер в зависимости от содержимого окна  */
    }
}