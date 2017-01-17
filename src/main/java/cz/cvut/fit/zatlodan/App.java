package cz.cvut.fit.zatlodan;

import cz.cvut.fit.zatlodan.GUI.Gui;
import cz.cvut.fit.zatlodan.datamanip.models.Customer;
import cz.cvut.fit.zatlodan.weblogic.WebInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;

/**
 * Created by jack on 21.12.16.
 */
public class App {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Gui g = new Gui();
        g.init();
    }

}
