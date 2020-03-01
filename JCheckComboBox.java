/* https://stackoverflow.com/questions/1573159/java-check-boxes-in-a-jcombobox */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.util.*;
import java.awt.color.*;

// public class JCheckComboBox extends JComboBox {
//   public JCheckComboBox() { addStuff(); }
//   public JCheckComboBox(JCheckBox[] items) { super(items); addStuff(); }
//   public JCheckComboBox(Vector items) { super(items); addStuff(); }
//   public JCheckComboBox(ComboBoxModel aModel) { super(aModel); addStuff(); }

//   private void addStuff() {
//     setRenderer(new ComboBoxRenderer());
//     addActionListener(new ActionListener() {
//       public void actionPerformed(ActionEvent ae) { itemSelected(); }
//     });
//   }
//   private void itemSelected() {
//     if (getSelectedItem() instanceof JCheckBox) {
//       JCheckBox jcb = (JCheckBox)getSelectedItem();
//       jcb.setSelected(!jcb.isSelected());
//     }
//   }
//     class ComboBoxRenderer implements ListCellRenderer {
//         private JLabel defaultLabel;
//         public ComboBoxRenderer() { setOpaque(true); }
//         public Component getListCellRendererComponent(JList list, Object value, int index,
//                     boolean isSelected, boolean cellHasFocus) {
//             if (value instanceof Component) {
//                 Component c = (Component)value;
//                 if (isSelected) {
//                 c.setBackground(new Color(0,0,0));
//                 c.setForeground(new Color(255,0,0));
//                 } else {
//                 c.setBackground(new Color(0,0,0));
//                 c.setForeground(new Color(255,0,0));
//                 }
//                 return c;
//             } else {
//                 if (defaultLabel==null) defaultLabel = new JLabel(value.toString());
//                 else defaultLabel.setText(value.toString());
//                 return defaultLabel;
//             }
//         }
//     }
// }

public class JCheckComboBox extends JComboBox {
    private static final long serialVersionUID = 1L;
    private ColoredCellRenderer crenderer;

    public JCheckComboBox() {
        crenderer = new ColoredCellRenderer(this.getRenderer());
        this.setRenderer(crenderer);
    }

    void setCheckedSize() {
        crenderer.setCheckedSize(this.getItemCount());
    }

    void setChecked(int index) {
        crenderer.setChecked(index);
    }

    boolean getChecked(int index) {
        return crenderer.getChecked(index);
    }
}

/* http://www.java2s.com/Tutorials/Java/Swing_How_to/JComboBox/Change_the_colour_of_JComboBox_s_selected_Item.htm */
class ColoredCellRenderer extends DefaultListCellRenderer {
    private ListCellRenderer defaultRenderer;
    boolean[] isChecked;

    public ColoredCellRenderer(ListCellRenderer defaultRenderer) {
        this.defaultRenderer = defaultRenderer;
    }

    public void setCheckedSize(int size) {
        isChecked = new boolean[size];
    }

    public void setChecked(int index) {
        isChecked[index] = !isChecked[index];
    }

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