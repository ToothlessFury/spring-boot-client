package cz.cvut.fit.zatlodan.GUI.pages;

import cz.cvut.fit.zatlodan.GUI.FormComponent;
import cz.cvut.fit.zatlodan.weblogic.WebInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jack on 28/12/16.
 */
public class ConnectionForm extends FormComponent {
    
    private JTextField connField;
    private JButton submit;
    private boolean valid;
    
    public ConnectionForm() {
        init();
    }
    
    @Override
    public void init() {
        valid = false;
        panel = new JPanel(new GridBagLayout());
        submit = new JButton("Connect");
        connField = new JTextField(WebInterface.getBaseRestUri(), 30);
        connField.setPreferredSize(new Dimension(200, 40));
        connField.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processConnection();
            }
        });
        
        submit.setBackground(Color.white);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processConnection();
            }
        });
        
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 0;
        c.gridy = 0;
        panel.add(connField, c);
        c.gridy = 1;
        panel.add(submit, c);
    }
    
    @Override
    public void init(FormComponent f) {
        parent.init(f);
    }
    
    private void processConnection() {
        valid = false;
        String address = connField.getText();
        try {
            System.out.println(address);
            WebInterface.setBaseRestUri(address);
            WebInterface.getAllCustomers();
        } catch (Exception e) {
            valid = false;
            JOptionPane.showMessageDialog(null,
                    "Connection to: "
                    + address
                    + " Has failed with message:" + "\n"
                    + e.getMessage());
            return;
        }
        valid = true;
        init(new IndexForm());
    }
}
