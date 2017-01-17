package cz.cvut.fit.zatlodan.GUI.pages;

import cz.cvut.fit.zatlodan.GUI.FormComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jack on 10/12/16.
 */
public class IndexForm extends FormComponent {
    private JButton showAllCustomersButton;
    private JButton showAllSalesButton;
    private JLabel heading;

    public IndexForm() {
        init();
    }

    @Override
    public void init() {
        panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.LIGHT_GRAY);
        heading = new JLabel("Available actions:");

        showAllCustomersButton = new JButton("Customers");
        showAllSalesButton = new JButton("Sales");

        GridBagConstraints c = new GridBagConstraints();
        showAllCustomersButton.setBackground(Color.white);
        showAllSalesButton.setBackground(Color.white);
        showAllCustomersButton.setPreferredSize(new Dimension(400, 60));
        showAllSalesButton.setPreferredSize(new Dimension(400, 60));
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 0;
        c.gridy = 0;
        panel.add(heading,c);
        c.gridx = 0;
        c.gridy = 1;
        panel.add(showAllCustomersButton, c);
        c.gridx = 0;
        c.gridy = 2;
        panel.add(showAllSalesButton, c);

        showAllCustomersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    parent.init(new CustomersListForm());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
        showAllSalesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    parent.init(new SalesListForm());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
    }

    @Override
    public void init(FormComponent f) {
        try {
            parent.init(f);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}
