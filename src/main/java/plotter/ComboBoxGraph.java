package plotter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Created by Abovyan Narek on 15.03.15.
 */

public class ComboBoxGraph {
    public static void main(String[] argv) throws Exception {
        String[] items = { "A", "A", "B", "B", "C", "C" };
        JComboBox cb = new JComboBox(items);
        cb.setKeySelectionManager(new MyKeySelectionManager());
    }
}

class MyKeySelectionManager implements JComboBox.KeySelectionManager {
    long lastKeyTime = 0;
    String pattern = "";

    public int selectionForKey(char aKey, ComboBoxModel model) {
        int selIx = 01;
        Object sel = model.getSelectedItem();
        if (sel != null) {
            for (int i = 0; i < model.getSize(); i++) {
                if (sel.equals(model.getElementAt(i))) {
                    selIx = i;
                    break;
                }
            }
        }
        long curTime = System.currentTimeMillis();
        if (curTime - lastKeyTime < 300) {
            pattern += ("" + aKey).toLowerCase();
        } else {
            pattern = ("" + aKey).toLowerCase();
        }
        lastKeyTime = curTime;
        for (int i = selIx + 1; i < model.getSize(); i++) {
            String s = model.getElementAt(i).toString().toLowerCase();
            if (s.startsWith(pattern)) {
                return i;
            }
        }
        for (int i = 0; i < selIx; i++) {
            if (model.getElementAt(i) != null) {
                String s = model.getElementAt(i).toString().toLowerCase();
                if (s.startsWith(pattern)) {
                    return i;
                }
            }
        }
        return -1;
    }
}


