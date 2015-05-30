package gui;

import plotter.Plotter;
import util.GeneralAlgorithms;
import util.MeasureSystem;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Abovyan on 27.05.15.
 */
public class MainGUI {
    private GeneralAlgorithms generalAlgorithms; // ссылка на объект который инкапсулирует в себе общий алгоритм задачи
    private MeasureSystem measureSystem = new MeasureSystem(); // объект содержащий методы для получения коэффицента
    // перевода из одной системы единиц в другую
    private static final String pathInitialFiles = "./initialData/";
    private static JFrame frame;
    private JButton saveAllChart;
    private JButton visableChart;
    private JButton addChart;
    private JComboBox comboBox;
    private JComboBox measureSystemsComBox;
    private JButton startСalculating;
    private JComboBox initialFilesBox;
    private JLabel countLabel;
    private JPanel graphicPanel;
    private JPanel panel;
    private JCheckBox calculateForAllFiles;
    private JButton updateFilesList;
    private JLabel picLabel;
    private String selectesItem;//= "Temperature";
    private String selectesMeasureUnit;//= "единица измерения";
    private String selectesInitialFile;

    public MainGUI () throws IOException {
        generalAlgorithms = new GeneralAlgorithms(); // создание объекта
        comboBox.setModel(new DefaultComboBoxModel<String>(getItems()));
        measureSystemsComBox.setModel(new DefaultComboBoxModel<String>(measureSystem.getArrayMeasureUnit()));
        initialFilesBox.setModel(new DefaultComboBoxModel<String>(generalAlgorithms.getFilesNamesOfDirectory(pathInitialFiles)));
        startСalculating.setBackground(Color.red);
        startСalculating.setOpaque(true);
        selectesInitialFile = (String) initialFilesBox.getSelectedItem();
        if (selectesInitialFile != null) generalAlgorithms.enterInitialDataFromFile(pathInitialFiles.concat(selectesInitialFile));
        BufferedImage myPicture = ImageIO.read(new File("./image/image.jpg"));
        BufferedImage resizedImage = resize(myPicture,600,400);
        picLabel = new JLabel(new ImageIcon(resizedImage));
        graphicPanel.setLayout(new BorderLayout());
        graphicPanel.add(picLabel, BorderLayout.CENTER);
        /* Подготавливаем компоненты объекта  */
//        selectesItem = null;
//        selectesMeasureUnit = null;
//        selectesInitialFile
        initListeners(); // иницируем слушателей событий формы
    }

    private void initListeners() {
        startСalculating.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generalAlgorithms.startCalculating();
                comboBox.setModel(new DefaultComboBoxModel<String>(getItems())); // устанавливаем список вычисленных параметров
                if (selectesItem != null && !selectesItem.equals("Список результатов")) { // если из списка выбран параметр то оставляем его
                    comboBox.setSelectedItem(selectesItem);
                } else { // если не выбран параметр то получаем выбранный параметр из comBox и запоминаем значение
                    selectesItem = (String) comboBox.getSelectedItem();
                }
                measureSystemsComBox.setModel(new DefaultComboBoxModel<String>(measureSystem.getArrayMeasureUnitByName(selectesItem)));
                if (selectesMeasureUnit != null && !selectesMeasureUnit.equals("единица измерения")) {
                    measureSystemsComBox.setSelectedItem(selectesMeasureUnit);
                } else {
                    selectesMeasureUnit = (String) measureSystemsComBox.getSelectedItem();
                }
                countLabel.setText("Расчет завершен !!!");
                startСalculating.setBackground(Color.GREEN);
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

        initialFilesBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox box = (JComboBox)e.getSource();
                if ( !selectesInitialFile.equals((String)box.getSelectedItem()) ) {
                    selectesInitialFile = (String) box.getSelectedItem();
                    generalAlgorithms.enterInitialDataFromFile(pathInitialFiles.concat(selectesInitialFile));
                    startСalculating.setBackground(Color.red);
                }
            }
        });
        addChart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (generalAlgorithms.getResultIntegration() != null) {
                    if (picLabel != null) {
                        graphicPanel.remove(picLabel);
                        picLabel = null;
                    }
                    double coeffX = measureSystem.getCoefficientConversionByName(selectesMeasureUnit);
                    double coeffY = 1;
                    Plotter.plot(selectesItem, generalAlgorithms.getResultIntegration(), MainGUI.this, true, coeffX, coeffY);
                } else countLabel.setText("Необходимо сначала провести расчет !!!");
            }
        });
        visableChart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(generalAlgorithms.getResultIntegration() == null)) {
                    if (picLabel != null) {
                        graphicPanel.remove(picLabel);
                        picLabel = null;
                    }
                    double coeffX = measureSystem.getCoefficientConversionByName(selectesMeasureUnit);
                    double coeffY = 1;
                    Plotter.plot(selectesItem, generalAlgorithms.getResultIntegration(), MainGUI.this, false, coeffX, coeffY);
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

        updateFilesList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initialFilesBox.setModel(new DefaultComboBoxModel<String>(generalAlgorithms.getFilesNamesOfDirectory(pathInitialFiles)));
                selectesInitialFile = (String) initialFilesBox.getSelectedItem();
            }
        });
    }

    public static void begin() throws IOException {
        frame = new JFrame("ПРОГРАММА ДЛЯ РАСЧЕТА РАСКРЫТИЯ КРЫЛЬЕВ");
        MainGUI mainGUI = new MainGUI();
        frame.setContentPane(mainGUI.panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // устанавливаем действие при нажатии крестика
        //frame.pack(); /* Эта команда подбирает оптимальный размер в зависимости от содержимого окна  */
        frame.setSize(1000, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    private String[] getItems() {
        String[] temp = {"Список результатов"};
        return generalAlgorithms.getResultIntegration() == null ? temp : generalAlgorithms.getResultIntegration().getListResultsNames();
    }

    public JPanel getPanel() {
        return graphicPanel;
    }

    public static JFrame getFrame() {
        return frame;
    }

    public static void setFrame(JFrame frame) {
        MainGUI.frame = frame;
    }

    // метод для изменения масштаба изображения
    public static BufferedImage resize(BufferedImage image, int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
        return bi;
    }

}
