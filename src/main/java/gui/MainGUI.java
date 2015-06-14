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
 * Класс содержит пользовательский интерфейс и методы взаимодействия с пользователем
 */
public class MainGUI {
    private GeneralAlgorithms generalAlgorithms; // ссылка на объект который инкапсулирует в себе общий алгоритм задачи
    private MeasureSystem measureSystem = new MeasureSystem(); // объект содержащий методы для получения коэффицента
    // перевода из одной системы единиц в другую
    private static final String pathInitialFiles = "./initialData/";  // путь к файлам с начальными условиями
    private static JFrame frame;
    private JButton saveAllChart;  // кнопка сохранения всех графиков
    private JButton visableChart;  // кнопка отображения графика
    private JButton addChart;  // кнопка добавления графика
    private JComboBox comboBox;  // список с результатами расчетов
    private JComboBox measureSystemsComBox;  // список единиц измерений
    private JButton startСalculating;  // кнопка старта вычисления
    private JComboBox initialFilesBox;  // список файлов с начальными условиями
    private JLabel countLabel;  //
    private JPanel graphicPanel;  // панель для вывода графиков
    private JPanel panel;  // для вывода информации в текстовом виде
    private JCheckBox calculateForAllFiles;  // checkBox для расчета всех файлов
    private JButton updateFilesList;  // кнопка обновления списка файлов с начальными условиями
    private JLabel picLabel;
    private String selectesItem;  //  выбранный параметр для построения графика
    private String selectesMeasureUnit;  // выбранная единица измерения
    private String selectesInitialFile;  // выбранный файл исходных данных

    public MainGUI () throws IOException {
        generalAlgorithms = new GeneralAlgorithms();
        comboBox.setModel(new DefaultComboBoxModel<String>(getItems()));
        measureSystemsComBox.setModel(new DefaultComboBoxModel<String>(measureSystem.getArrayMeasureUnit()));
        initialFilesBox.setModel(new DefaultComboBoxModel<String>(
                generalAlgorithms.getFilesNamesOfDirectory(pathInitialFiles)));
        startСalculating.setBackground(Color.red);
        startСalculating.setOpaque(true);
        selectesInitialFile = (String) initialFilesBox.getSelectedItem();
        if (selectesInitialFile != null) generalAlgorithms.enterInitialDataFromFile(
                pathInitialFiles.concat(selectesInitialFile));
        BufferedImage myPicture = ImageIO.read(new File("./image/image.jpg"));
        BufferedImage resizedImage = resize(myPicture,600,400);
        picLabel = new JLabel(new ImageIcon(resizedImage));
        graphicPanel.setLayout(new BorderLayout());
        graphicPanel.add(picLabel, BorderLayout.CENTER);
        /* Подготавливаем компоненты объекта  */
        initListeners(); // инициируем слушателей событий формы
    }

    private void initListeners() {
        // слушатель кнопки старта вычисления
        startСalculating.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generalAlgorithms.startCalculating();
                // устанавливаем список вычисленных параметров
                comboBox.setModel(new DefaultComboBoxModel<String>(getItems()));
                // если из списка выбран параметр то оставляем его
                if (selectesItem != null && !selectesItem.equals("Список результатов")) {
                    comboBox.setSelectedItem(selectesItem);
                } else { // если не выбран параметр то получаем выбранный параметр из comBox и запоминаем значение
                    selectesItem = (String) comboBox.getSelectedItem();
                }
                measureSystemsComBox.setModel(new DefaultComboBoxModel<String>(
                        measureSystem.getArrayMeasureUnitByName(selectesItem)));
                if (selectesMeasureUnit != null && !selectesMeasureUnit.equals("единица измерения")) {
                    measureSystemsComBox.setSelectedItem(selectesMeasureUnit);
                } else {
                    selectesMeasureUnit = (String) measureSystemsComBox.getSelectedItem();
                }
                countLabel.setText("Расчет завершен !!!");
                startСalculating.setBackground(Color.GREEN);
            }
        });
        // слушатель списка вычисленных параметров
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox box = (JComboBox)e.getSource();
                selectesItem = (String)box.getSelectedItem();
                measureSystemsComBox.setModel(new DefaultComboBoxModel<String>(
                        measureSystem.getArrayMeasureUnitByName(selectesItem)));
                selectesMeasureUnit = (String) measureSystemsComBox.getSelectedItem();
            }
        });
        // слушатель списка единиц измерения
        measureSystemsComBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox box = (JComboBox)e.getSource();
                selectesMeasureUnit = (String)box.getSelectedItem();
            }
        });
        // слушатель списка файлов с начальными условиями
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
        // слушатель кнопки добавления графика
        addChart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (generalAlgorithms.getResultIntegration() != null) {
                    if (picLabel != null) {
                        graphicPanel.remove(picLabel);
                        picLabel = null;
                    }
                    double coeffY = measureSystem.getCoefficientConversionByName(selectesMeasureUnit);
                    double coeffX = 1;
                    Plotter.plot(selectesItem, generalAlgorithms.getResultIntegration(),
                            MainGUI.this, true, coeffY, coeffX);
                } else countLabel.setText("Необходимо сначала провести расчет !!!");
            }
        });
        // слушатель кнопки отображения графика
        visableChart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(generalAlgorithms.getResultIntegration() == null)) {
                    if (picLabel != null) {
                        graphicPanel.remove(picLabel);
                        picLabel = null;
                    }
                    double coeffY = measureSystem.getCoefficientConversionByName(selectesMeasureUnit);
                    double coeffX = 1;
                    Plotter.plot(selectesItem, generalAlgorithms.getResultIntegration(),
                            MainGUI.this, false, coeffY, coeffX);
                } else countLabel.setText("Необходимо сначала провести расчет !!!");
            }
        });
        // слушатель кнопки сохранения всех графиков
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
        // слушатель кнопки обновления списка файлов с исходными данными
        updateFilesList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initialFilesBox.setModel(new DefaultComboBoxModel<String>(
                        generalAlgorithms.getFilesNamesOfDirectory(pathInitialFiles)));
                selectesInitialFile = (String) initialFilesBox.getSelectedItem();
            }
        });
    }

    // метод стартует работу графического интерфейса
    public static void begin() throws IOException {
        // устанавливаем название программы
        frame = new JFrame("ПРОГРАММА ДЛЯ РАСЧЕТА РАСКРЫТИЯ КРЫЛЬЕВ");
        MainGUI mainGUI = new MainGUI();
        frame.setContentPane(mainGUI.panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // устанавливаем действие при нажатии крестика
        //frame.pack(); /* Эта команда подбирает оптимальный размер в зависимости от содержимого окна  */
        frame.setSize(1000, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // метод возвращает список результатов проведенных вычислений
    private String[] getItems() {
        String[] temp = {"Список результатов"};
        return generalAlgorithms.getResultIntegration() == null ? temp :
                generalAlgorithms.getResultIntegration().getListResultsNames();
    }

    // метод возвращает панель для размещения графика
    public JPanel getPanel() {
        return graphicPanel;
    }

    // метод возвращает Frame
    public static JFrame getFrame() {
        return frame;
    }

    // метод устанавливает Frame
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
