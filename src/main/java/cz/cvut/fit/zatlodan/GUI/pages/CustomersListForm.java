package cz.cvut.fit.zatlodan.GUI.pages;

import cz.cvut.fit.zatlodan.GUI.pages.components.TablePage;
import cz.cvut.fit.zatlodan.datamanip.models.Customer;
import cz.cvut.fit.zatlodan.datamanip.models.Model;
import cz.cvut.fit.zatlodan.weblogic.WebInterface;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.util.Map;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Created by jack on 10/12/16.
 */
public class CustomersListForm extends TablePage {

    private JButton showSales;

    @Override
    protected void createAction() {
        parent.init(new CreateCustomer(this));
    }

    @Override
    protected void updateModel(Map.Entry pair) {
        Customer c = (Customer) pair.getValue();
        try {
            WebInterface.updateCustomer(
                    (Long) pair.getKey(),
                    c.getName(),
                    c.getEmailAddress(),
                    c.getAddress(),
                    c.getContact()
            );
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    @Override
    protected void deleteModel(Long id) {
        WebInterface.deleteCustomer(id);
    }

    @Override
    protected Object[][] getModels() {
        java.util.List<Customer> customers;
        customers = WebInterface.getAllCustomers();
        Object[][] oarr = new String[customers.size()][5];
        try {
            for (int i = 0; i < customers.size(); i++) {
                Customer c = (Customer) customers.get(i);
                oarr[i][0] = Long.toString(c.getId());
                oarr[i][1] = c.getName();
                oarr[i][2] = c.getEmailAddress();
                oarr[i][3] = c.getAddress();
                oarr[i][4] = c.getContact();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return oarr;
    }

    @Override
    protected DefaultTableModel getTableModel(Object[][] data) {
        String[] columnNames = {
            "ID",
            "Name",
            "Email addressField",
            "Contact",
            "Address",};
        return new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };
    }

    @Override
    protected Model loadModelFromTable(int row, TableModelEvent tme, JTable table) {
        Long id = Long.parseLong((String) table.getValueAt(row, 0));
        String name = (String) table.getValueAt(row, 1);
        String emailAddress = (String) table.getValueAt(row, 2);
        String address = (String) table.getValueAt(row, 3);
        String contact = (String) table.getValueAt(row, 4);
        return new Customer(id, name, emailAddress, address, contact);
    }

    @Override
    protected void addAdditionalElements(GridBagConstraints c) {
        showSales = new JButton("Show sales");
        c.gridy = 4;
        c.gridx = 2;
        showSales.setBackground(Color.white);
        showSales.setEnabled(false);
        panel.add(showSales, c);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                if (lsm.isSelectionEmpty()) {
                    showSales.setEnabled(false);
                    selectedId = -1;
                } else {
                    int index = lsm.getLeadSelectionIndex();
                    selectedId = Long.parseLong((String) table.getValueAt(index, 0));
                    showSales.setEnabled(true);
                }
            }
        });

        showSales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.init(new SalesListForm(selectedId));
            }
        });
    }

}
