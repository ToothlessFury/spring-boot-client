package cz.cvut.fit.zatlodan.GUI;

import cz.cvut.fit.zatlodan.GUI.pages.ConnectionForm;
import cz.cvut.fit.zatlodan.GUI.pages.IndexForm;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jack on 10/12/16.
 */
public class Gui extends FormComponent {

    private JFrame frame;
    private JPanel panel;
    private JScrollPane scrollPane;
    private int WINDOW_WIDTH = 800;
    private int WINDOW_HEIGHT = 600;


    public void init() {
        try {
            frame = new JFrame("TJV Term Work Client");
            frame.setVisible(true);
            //frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            panel = new JPanel(new BorderLayout());
            scrollPane = new JScrollPane(panel);
            //scrollPane.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
            frame.add(scrollPane);

            init(new ConnectionForm());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    @Override
    public void init(FormComponent f) {
        try {
            panel.removeAll();
            panel.add(f.getPanel(), BorderLayout.CENTER);
            panel.revalidate();
            f.setParent(this);
            frame.pack();
            frame.setLocationRelativeTo(null);
            setActive(f);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

}
