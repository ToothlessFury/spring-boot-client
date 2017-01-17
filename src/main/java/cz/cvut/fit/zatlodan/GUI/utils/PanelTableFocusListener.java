package cz.cvut.fit.zatlodan.GUI.utils;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by jack on 23/12/16.
 */
public class PanelTableFocusListener implements MouseListener {

    private JTable table;

    public PanelTableFocusListener(JTable table) {
        this.table = table;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (table != null && table.getCellEditor() != null) {
            table.getCellEditor().stopCellEditing();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
