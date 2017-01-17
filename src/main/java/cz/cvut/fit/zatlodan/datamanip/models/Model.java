package cz.cvut.fit.zatlodan.datamanip.models;

import java.io.Serializable;

/**
 * Created by jack on 24/12/16.
 */
public class Model implements Serializable {

    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
