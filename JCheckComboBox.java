/* https://stackoverflow.com/questions/1573159/java-check-boxes-in-a-jcombobox */

import javax.swing.*;
import java.awt.*;

/**
 * Class that is used to create a combo box that can select multiple items from the list.
 * <p>
 * Selected items will appear as gray.
 * 
 * @author Jacob DuBois
 */
public class JCheckComboBox extends JComboBox<String> {
    private static final long serialVersionUID = 1L;
    private ColoredCellRenderer crenderer;

    public JCheckComboBox() {
        crenderer = new ColoredCellRenderer(this.getRenderer());
        this.setRenderer(crenderer);
    }

    /**
     * Calls setCheckedSize() in the ColoredCellRenderer class.
     * 
     * @see ColoredCellRenderer#setCheckedSize
     */
    void setCheckedSize() {
        crenderer.setCheckedSize(this.getItemCount());
    }

    /**
     * Calls setChecked() in the ColoredCellRenderer class.
     * 
     * @see ColoredCellRenderer#setChecked
     */
    void setChecked(int index) {
        crenderer.setChecked(index);
    }

    /**
     * Iterates through the combo box and returns each item that is checked.
     * 
     * @return a string array that contains each item that is is currently checked
     */
    String[] getChecked() {
        String checked[] = new String[this.numChecked()];
        int index = 0;

        /* Iterates through each item in the combo list. */
        for (int i = 0; i < this.getItemCount(); i++) {
            /* If the item is checked, adds it to the string array. */
            if (crenderer.getChecked(i)) {
                checked[index] = (String) this.getItemAt(i);
                index++;
            }
        }

        return checked;
    }

    /**
     * Counts how many items are checked in the combo box.
     * 
     * @return an int representing the number of checked items
     */
    int numChecked() {
        int count = 0;
         /* Iterates through each item in the combo box. */
        for (int i = 0; i < this.getItemCount(); i++) {
            /* If the item is checked, then raises the count. */
            if (crenderer.getChecked(i)) {
                count++;
            }
        }

        return count;
    }
}

/**
 * Class that is used to create a cell that has a background color within the combo box list.
 * <p>
 * Modified code from <a href="http://www.java2s.com/Tutorials/Java/Swing_How_to/JComboBox/Change_the_colour_of_JComboBox_s_selected_Item.htm">here</a>.
 * 
 * @author Jacob DuBois
 * @author javas2s.com
 */
class ColoredCellRenderer extends DefaultListCellRenderer {
    private ListCellRenderer defaultRenderer; // sets default render settings
    boolean[] isChecked; // each combo box item has a corresponding boolean within this list, to indicate if checked or not

    public ColoredCellRenderer(ListCellRenderer defaultRenderer) {
        this.defaultRenderer = defaultRenderer;
    }

    /**
     * Sets the size of the boolean array, isChecked, which corresponds to the number of items in the combo box.
     * 
     * @param size
     */
    public void setCheckedSize(int size) {
        isChecked = new boolean[size];
    }

    /**
     * Sets the boolean value checked of the combo box item at the given index.
     * 
     * @param index
     */
    public void setChecked(int index) {
        isChecked[index] = !isChecked[index];
    }

    /**
     * Returns the boolean value for whether or not an item is checked at the specified index.
     * 
     * @param index
     * @return a boolean representing that item's checked status, where true means the item is checked
     */
    public boolean getChecked(int index) {
        return isChecked[index];
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component c = defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (c instanceof JLabel && index >= 0) {
            if (isChecked[index]) {
                c.setBackground(Color.gray);
            }
        }
        return c;
    }
}