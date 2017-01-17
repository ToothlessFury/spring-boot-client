package cz.cvut.fit.zatlodan.GUI.pages.components;

import cz.cvut.fit.zatlodan.GUI.FormComponent;
import cz.cvut.fit.zatlodan.GUI.pages.CreateCustomer;
import cz.cvut.fit.zatlodan.GUI.pages.IndexForm;
import cz.cvut.fit.zatlodan.GUI.utils.PanelTableFocusListener;
import cz.cvut.fit.zatlodan.datamanip.models.Model;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by jack on 24/12/16.
 */
public abstract class TablePage extends FormComponent {

    public TablePage() {
        init();
    }

    public TablePage(long id) {
        selectedId = id;
        init();
    }

    protected long selectedId = -1;

    protected JTable table;
    protected JButton deleteButton;
    protected JButton refreshButton;
    protected JButton confirmButton;
    protected JButton createButton;
    protected JButton indexButton;
    protected TablePage self;

    protected HashMap<Long, Model> stagedObjects;

    protected int TABLE_WIDTH = 1024;
    protected int TABLE_HEIGH = 600;
    protected int TABLE_ROWHEIGHT = 30;

    @Override
    public void init() {
        setSelf(this);
        setStagedObjects(new HashMap<>(2));
        panel = new JPanel(new GridBagLayout());
        setTable(new JTable(getTableModel(null)));
        getTable().putClientProperty("terminateEditOnFocusLost", true);
        //getTable().setFont(new Font("Arial", Font.PLAIN, 20));
        getTable().setRowHeight(getTABLE_ROWHEIGHT());
        getTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        setIndexButton(new JButton("Return to Index"));
        getIndexButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.init(new IndexForm());
            }
        });

        setCreateButton(new JButton("create"));
        getCreateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAction();
            }
        });

        setDeleteButton(new JButton("Delete"));
        getDeleteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteModelAction(e);
            }
        });

        setRefreshButton(new JButton("Refresh"));
        getRefreshButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshAction();
            }
        });

        setConfirmButton(new JButton("Save"));
        getConfirmButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commitStaged();
            }
        });
        getConfirmButton().setEnabled(false);

        JScrollPane scrollpane = new JScrollPane(getTable());
        scrollpane.setPreferredSize(new Dimension(getTABLE_WIDTH(), getTABLE_HEIGH()));
        scrollpane.setMinimumSize(new Dimension(getTABLE_WIDTH() / 2, (int) Math.round(getTABLE_HEIGH() / 1.5)));

        GridBagConstraints c = new GridBagConstraints();
        panel.setBackground(Color.GRAY);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(scrollpane, c);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.insets = new Insets(20, 20, 20, 20);
        panel.add(getDeleteButton(), c);
        c.gridx = 1;
        panel.add(getRefreshButton(), c);
        c.gridx = 2;
        panel.add(getConfirmButton(), c);
        c.gridx = 0;
        c.gridy = 4;
        panel.add(getCreateButton(), c);
        c.gridx = 1;
        panel.add(getIndexButton(), c);

        addAdditionalElements(c);

        reloadTable(getTable());
        //setupTable(table);
        panel.addMouseListener(new PanelTableFocusListener(getTable()));
    }

    protected abstract void createAction();

    private void setupTable(JTable table) {
        setJTableColumnsWidth(table, getTABLE_WIDTH(), 10, 20, 20, 25, 25);
    }

    private void setJTableColumnsWidth(JTable table, int tablePreferredWidth, double... percentages) {
        double total = 0;
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            total += percentages[i];
        }

        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth((int) (tablePreferredWidth * (percentages[i] / total)));
        }
    }

    @Override
    public void init(FormComponent f) {
        parent.init(f);
    }

    private void commitStaged() {
        System.out.println("commiting");
        getConfirmButton().setEnabled(false);
        Iterator it = getStagedObjects().entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry) it.next();
            updateModel(pair);
            System.out.println("Object id: " + pair.getKey() + " has been updated.");
            it.remove();
        }
        reloadTable(getTable());
        getStagedObjects().clear();
    }

    protected abstract void updateModel(HashMap.Entry pair);

    private void refreshAction() {
        reloadTable(getTable());
    }

    private void deleteModelAction(ActionEvent e) {
        int row = getTable().getSelectedRow();
        Long id = Long.parseLong(getTable().getValueAt(row, 0).toString());
        try {
            deleteModel(id);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        reloadTable(getTable());
    }

    protected abstract void deleteModel(Long id);

    private void reloadTable(JTable table) {
        Object[][] oarr = getModels();
        table.setModel(getTableModel(oarr));
        addTableListener(table);
    }

    protected abstract Object[][] getModels();

    protected abstract DefaultTableModel getTableModel(Object[][] data);

    private void addTableListener(JTable table) {

        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.addTableModelListener(tme -> {
            try {
                if (tme.getType() == TableModelEvent.UPDATE) {
                    System.out.println("Cell " + tme.getFirstRow() + ", "
                            + tme.getColumn() + " changed. The new value: "
                            + tableModel.getValueAt(tme.getFirstRow(),
                                    tme.getColumn()));

                    int row = tme.getFirstRow();
                    Long id = Long.parseLong((String) table.getValueAt(row, 0));
                    getStagedObjects().put(id, loadModelFromTable(row, tme, table));
                    getConfirmButton().setEnabled(true);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid table value: " + e.getMessage());
            }
        });

    }

    protected abstract Model loadModelFromTable(int row, TableModelEvent tme, JTable table);

    /**
     * @return the table
     */
    public JTable getTable() {
        return table;
    }

    /**
     * @param table the table to set
     */
    public void setTable(JTable table) {
        this.table = table;
    }

    /**
     * @return the deleteButton
     */
    public JButton getDeleteButton() {
        return deleteButton;
    }

    /**
     * @param deleteButton the deleteButton to set
     */
    public void setDeleteButton(JButton deleteButton) {
        this.deleteButton = deleteButton;
    }

    /**
     * @return the refreshButton
     */
    public JButton getRefreshButton() {
        return refreshButton;
    }

    /**
     * @param refreshButton the refreshButton to set
     */
    public void setRefreshButton(JButton refreshButton) {
        this.refreshButton = refreshButton;
    }

    /**
     * @return the confirmButton
     */
    public JButton getConfirmButton() {
        return confirmButton;
    }

    /**
     * @param confirmButton the confirmButton to set
     */
    public void setConfirmButton(JButton confirmButton) {
        this.confirmButton = confirmButton;
    }

    /**
     * @return the createButton
     */
    public JButton getCreateButton() {
        return createButton;
    }

    /**
     * @param createButton the createButton to set
     */
    public void setCreateButton(JButton createButton) {
        this.createButton = createButton;
    }

    /**
     * @return the indexButton
     */
    public JButton getIndexButton() {
        return indexButton;
    }

    /**
     * @param indexButton the indexButton to set
     */
    public void setIndexButton(JButton indexButton) {
        this.indexButton = indexButton;
    }

    /**
     * @return the self
     */
    public TablePage getSelf() {
        return self;
    }

    /**
     * @param self the self to set
     */
    public void setSelf(TablePage self) {
        this.self = self;
    }

    /**
     * @return the stagedObjects
     */
    public HashMap<Long, Model> getStagedObjects() {
        return stagedObjects;
    }

    /**
     * @param stagedObjects the stagedObjects to set
     */
    public void setStagedObjects(HashMap<Long, Model> stagedObjects) {
        this.stagedObjects = stagedObjects;
    }

    /**
     * @return the TABLE_WIDTH
     */
    public int getTABLE_WIDTH() {
        return TABLE_WIDTH;
    }

    /**
     * @param TABLE_WIDTH the TABLE_WIDTH to set
     */
    public void setTABLE_WIDTH(int TABLE_WIDTH) {
        this.TABLE_WIDTH = TABLE_WIDTH;
    }

    /**
     * @return the TABLE_HEIGH
     */
    public int getTABLE_HEIGH() {
        return TABLE_HEIGH;
    }

    /**
     * @param TABLE_HEIGH the TABLE_HEIGH to set
     */
    public void setTABLE_HEIGH(int TABLE_HEIGH) {
        this.TABLE_HEIGH = TABLE_HEIGH;
    }

    /**
     * @return the TABLE_ROWHEIGHT
     */
    public int getTABLE_ROWHEIGHT() {
        return TABLE_ROWHEIGHT;
    }

    /**
     * @param TABLE_ROWHEIGHT the TABLE_ROWHEIGHT to set
     */
    public void setTABLE_ROWHEIGHT(int TABLE_ROWHEIGHT) {
        this.TABLE_ROWHEIGHT = TABLE_ROWHEIGHT;
    }

    protected abstract void addAdditionalElements(GridBagConstraints c);
}
