package plotter;
import java.awt.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.event.*;
/**
 * Created by Abovyan Narek on 21.03.15.
 */

public class ListPanel extends JPanel {

    private JList list;

    public ListPanel(String[] data) {
        super(new BorderLayout());
        list = new JList(data);
        list.addListSelectionListener(new SelectionHandler());
        JScrollPane jsp = new JScrollPane(list);
        this.add(jsp, BorderLayout.CENTER);
        this.setSize(30,30);
    }

    private class SelectionHandler implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                System.out.println(Arrays.toString(list.getSelectedValues()));
            }
        }
    }

    private void display() {
        JFrame f = new JFrame("ListPanel");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(this);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        /*EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                String[] data = {"Math", "Computer", "Physics", "Chemistry"};
                new ListPanel(data).display();
            }
        });*/
        String[] data = {"Math", "Computer", "Physics", "Chemistry"};
        new ListPanel(data).display();
    }
}

