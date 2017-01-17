package cz.cvut.fit.zatlodan.datamanip.models;

/**
 * Created by jack on 25/12/16.
 */
public class Status {

    private short code;
    private String desc;

    public Status(short code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public short getCode() {
        return code;
    }

    public void setCode(short code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return desc;
    }
}
