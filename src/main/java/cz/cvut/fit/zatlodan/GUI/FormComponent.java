package cz.cvut.fit.zatlodan.GUI;

import javax.swing.*;

/**
 * Created by jack on 10/12/16.
 */
public abstract class FormComponent {

    protected FormComponent parent;
    protected JPanel panel;
    protected FormComponent active;

    public abstract void init();
    public abstract void init(FormComponent f);

    public void setParent(FormComponent p){
        parent = p;
    }

    public void setActive(FormComponent c){
        active = c;
    }

    public JPanel getPanel(){
        return panel;
    }

    public FormComponent getActive(){
        return active;
    }

}
