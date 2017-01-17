package cz.cvut.fit.zatlodan.GUI.pages.components;

import cz.cvut.fit.zatlodan.GUI.FormComponent;
import cz.cvut.fit.zatlodan.datamanip.models.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jack on 24/12/16.
 */
public abstract class CreateForm extends FormComponent {

    protected JButton createButton;
    protected JButton returnButton;
    protected Class previous;

    public CreateForm(FormComponent previous) {
        this.previous = previous.getClass();
        init();
    }


    @Override
    public void init() {
        createButton = new JButton("Create");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                create();
            }
        });
        returnButton = new JButton("return");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    parent.init((FormComponent) previous.newInstance());
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                }
            }
        });
        addComponents();
    }

    @Override
    public void init(FormComponent f) {
        parent.init(f);
    }

    protected abstract void addComponents();

    protected abstract void create();
}
