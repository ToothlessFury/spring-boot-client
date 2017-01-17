package cz.cvut.fit.zatlodan.GUI.pages;

import cz.cvut.fit.zatlodan.GUI.FormComponent;
import cz.cvut.fit.zatlodan.GUI.pages.components.CreateForm;
import cz.cvut.fit.zatlodan.GUI.pages.components.DateLabelFormatter;
import cz.cvut.fit.zatlodan.datamanip.models.Customer;
import cz.cvut.fit.zatlodan.datamanip.models.Status;
import cz.cvut.fit.zatlodan.weblogic.WebInterface;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by jack on 25/12/16.
 */
public class CreateSale extends CreateForm {

    private JTextField infoInput;
    private JDatePicker dateInput;
    private JList statusInput;
    private JList customerIdInput;

    public CreateSale(FormComponent previous) {
        super(previous);
    }

    @Override
    protected void addComponents() {
        panel = new JPanel(new GridBagLayout());
        setInfoInput(new JTextField("", 20));
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        setDateInput(new JDatePickerImpl(datePanel, new DateLabelFormatter()));

        setStatusInput(new JList(getAvailibleStatuses()));
        getStatusInput().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        getStatusInput().setLayoutOrientation(JList.VERTICAL);
        getStatusInput().setVisibleRowCount(-1);

        setCustomerIdInput(new JList(getAvailibleCustomerIds()));
        getCustomerIdInput().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        getCustomerIdInput().setLayoutOrientation(JList.VERTICAL);
        getCustomerIdInput().setVisibleRowCount(5);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 2;
        panel.add(new JLabel("Info: "), c);
        c.gridx = 1;
        c.gridwidth = 3;
        panel.add(getInfoInput(), c);
        c.gridy = 2;
        c.gridx = 0;
        c.gridwidth = 1;
        panel.add(new JLabel("Date: "), c);
        c.gridx = 1;
        c.gridwidth = 3;
        panel.add(datePanel, c);
        c.gridy = 4;
        c.gridx = 0;
        c.gridwidth = 1;
        panel.add(new JLabel("Status: "), c);
        c.gridx = 1;
        c.gridwidth = 3;
        panel.add(getStatusInput(), c);
        c.gridy = 6;
        c.gridx = 0;
        c.gridwidth = 1;
        panel.add(new JLabel("customerId: "), c);
        c.gridx = 1;
        c.gridwidth = 3;
        c.gridheight = 4;
        panel.add(new JScrollPane(getCustomerIdInput()), c);
        c.gridwidth = 4;
        c.gridx = 0;
        c.gridy = 10;
        c.gridheight = 2;
        panel.add(createButton, c);
        c.gridy = 12;
        panel.add(returnButton, c);
    }

    private Object[] getAvailibleStatuses() {
        Status[] statuses = new Status[3];
        statuses[0] = new Status((short) 0, "DONE");
        statuses[1] = new Status((short) 1, "PENDING");
        statuses[2] = new Status((short) 2, "CANCELLED");
        return statuses;
    }

    @Override
    protected void create() {
        try {
            String info = getInfoInput().getText();
            Date date = (Date) getDateInput().getModel().getValue();
            String timestamp = new java.sql.Timestamp(date.getTime()).toString();
            Status s = (Status) getStatusInput().getSelectedValue();
            String status = Short.toString(s.getCode());
            String custId = Long.toString((Long) (getCustomerIdInput().getSelectedValue()));
            WebInterface.createSale(info, timestamp, status, custId);
            try {
                parent.init((FormComponent) previous.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public Object[] getAvailibleCustomerIds() {
        java.util.List<Customer> custs = WebInterface.getAllCustomers();
        Object[] ids = new Object[custs.size()];
        int i = 0;
        for (Customer c : custs) {
            ids[i] = c.getId();
            i++;
        }
        return ids;
    }

    /**
     * @return the infoInput
     */
    public JTextField getInfoInput() {
        return infoInput;
    }

    /**
     * @param infoInput the infoInput to set
     */
    public void setInfoInput(JTextField infoInput) {
        this.infoInput = infoInput;
    }

    /**
     * @return the dateInput
     */
    public JDatePicker getDateInput() {
        return dateInput;
    }

    /**
     * @param dateInput the dateInput to set
     */
    public void setDateInput(JDatePicker dateInput) {
        this.dateInput = dateInput;
    }

    /**
     * @return the statusInput
     */
    public JList getStatusInput() {
        return statusInput;
    }

    /**
     * @param statusInput the statusInput to set
     */
    public void setStatusInput(JList statusInput) {
        this.statusInput = statusInput;
    }

    /**
     * @return the customerIdInput
     */
    public JList getCustomerIdInput() {
        return customerIdInput;
    }

    /**
     * @param customerIdInput the customerIdInput to set
     */
    public void setCustomerIdInput(JList customerIdInput) {
        this.customerIdInput = customerIdInput;
    }
}
