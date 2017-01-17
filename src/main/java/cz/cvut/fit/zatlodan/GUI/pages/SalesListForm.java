package cz.cvut.fit.zatlodan.GUI.pages;

import cz.cvut.fit.zatlodan.GUI.pages.components.TablePage;
import cz.cvut.fit.zatlodan.datamanip.models.Model;
import cz.cvut.fit.zatlodan.datamanip.models.Sale;
import cz.cvut.fit.zatlodan.weblogic.WebInterface;
import java.awt.GridBagConstraints;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Map;

/**
 * Created by jack on 25/12/16.
 */
public class SalesListForm extends TablePage {

    public SalesListForm() {
    }

    public SalesListForm(long customerId) {
        super(customerId);
    }

    @Override
    protected void createAction() {
        parent.init(new CreateSale(this));
    }

    @Override
    protected void updateModel(Map.Entry pair) {
        Sale s = (Sale) pair.getValue();
        try {
            WebInterface.updateSale(
                    (Long) pair.getKey(),
                    s.getInfo(),
                    s.getDate(),
                    s.getDone(),
                    s.getCustomersId()
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    @Override
    protected void deleteModel(Long id) {
        WebInterface.deleteSale(id);
    }

    @Override
    protected Object[][] getModels() {
        java.util.List<Sale> sales;
        if (selectedId != -1) {
            sales = WebInterface.getAllCustomerSales(selectedId);
        } else {
            sales = WebInterface.getAllSales();
        }
        Object[][] oarr = new String[sales.size()][5];
        try {
            for (int i = 0; i < sales.size(); i++) {
                Sale s = (Sale) sales.get(i);
                oarr[i][0] = Long.toString(s.getId());
                oarr[i][1] = s.getInfo();
                Date d = new Date(Long.parseLong(s.getDate()));
                oarr[i][2] = new Timestamp(d.getTime()).toString();
                oarr[i][3] = Short.toString(s.getDone());
                oarr[i][4] = Long.toString(s.getCustomersId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return oarr;
    }

    @Override
    protected DefaultTableModel getTableModel(Object[][] data) {
        String[] columnNames = {
            "ID",
            "Info",
            "Date",
            "Status",
            "Customer ID",};
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
        String info = (String) table.getValueAt(row, 1);
        String date = (String) table.getValueAt(row, 2);
        short status = Short.parseShort((String) table.getValueAt(row, 3));
        Long customerId = Long.parseLong((String) table.getValueAt(row, 4));
        return new Sale(id, info, date, status, customerId);
    }

    @Override
    protected void addAdditionalElements(GridBagConstraints c) {
        
    }
}
