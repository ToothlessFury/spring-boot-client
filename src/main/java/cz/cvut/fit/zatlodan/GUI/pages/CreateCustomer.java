package cz.cvut.fit.zatlodan.GUI.pages;

import cz.cvut.fit.zatlodan.GUI.FormComponent;
import cz.cvut.fit.zatlodan.GUI.pages.components.CreateForm;
import cz.cvut.fit.zatlodan.weblogic.WebInterface;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jack on 25/12/16.
 */
public class CreateCustomer extends CreateForm {

    private JTextField nameInput;
    private JTextField emailAddressField;
    private JTextField addressField;
    private JTextField contactField;

    private int FIELD_HEIGHT = 30;

    public CreateCustomer(FormComponent previous) {
        super(previous);
    }

    @Override
    protected void addComponents() {
        panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        setNameInput(new JTextField("", 20));
        setEmailAddressField(new JTextField("", 20));
        setAddressField(new JTextField("", 20));
        setContactField(new JTextField("", 20));

        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 2;
        panel.add(new JLabel("Name: "), c);
        c.gridx = 1;
        c.gridwidth = 3;
        panel.add(getNameInput(), c);
        c.gridy = 2;
        c.gridx = 0;
        c.gridwidth = 1;
        panel.add(new JLabel("Email: "), c);
        c.gridx = 1;
        c.gridwidth = 3;
        panel.add(getEmailAddressField(), c);
        c.gridy = 4;
        c.gridx = 0;
        c.gridwidth = 1;
        panel.add(new JLabel("Address: "), c);
        c.gridx = 1;
        c.gridwidth = 3;
        panel.add(getAddressField(), c);
        c.gridy = 6;
        c.gridx = 0;
        c.gridwidth = 1;
        panel.add(new JLabel("Contact: "), c);
        c.gridx = 1;
        c.gridwidth = 3;
        panel.add(getContactField(), c);
        c.gridwidth = 4;
        c.gridx = 0;
        c.gridy = 8;
        c.gridheight = 2;
        panel.add(createButton, c);
        c.gridx = 0;
        c.gridy = 10;
        panel.add(returnButton, c);
    }
    
    

    @Override
    protected void create() {
        try {
            String name = getNameInput().getText();
            String emailAddress = getEmailAddressField().getText();
            String address = getAddressField().getText();
            String contact = getAddressField().getText();
            WebInterface.createCustomer(name, emailAddress, address, contact);
            try {
                parent.init((FormComponent) previous.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     * @return the nameInput
     */
    public JTextField getNameInput() {
        return nameInput;
    }

    /**
     * @param nameInput the nameInput to set
     */
    public void setNameInput(JTextField nameInput) {
        this.nameInput = nameInput;
    }

    /**
     * @return the emailAddressField
     */
    public JTextField getEmailAddressField() {
        return emailAddressField;
    }

    /**
     * @param emailAddressField the emailAddressField to set
     */
    public void setEmailAddressField(JTextField emailAddressField) {
        this.emailAddressField = emailAddressField;
    }

    /**
     * @return the addressField
     */
    public JTextField getAddressField() {
        return addressField;
    }

    /**
     * @param addressField the addressField to set
     */
    public void setAddressField(JTextField addressField) {
        this.addressField = addressField;
    }

    /**
     * @return the contactField
     */
    public JTextField getContactField() {
        return contactField;
    }

    /**
     * @param contactField the contactField to set
     */
    public void setContactField(JTextField contactField) {
        this.contactField = contactField;
    }
}
