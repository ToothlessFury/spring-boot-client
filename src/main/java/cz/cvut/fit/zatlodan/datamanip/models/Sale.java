package cz.cvut.fit.zatlodan.datamanip.models;

/**
 * Created by jack on 21.12.16.
 */
public class Sale extends Model {

    private String info;
    private String date;
    private short done;
    private Long customersId;

    public Sale() {

    }

    public Sale(Long id, String info, String date, short status, Long customersId) {
        this.id = id;
        this.info = info;
        this.date = date;
        this.done = status;
        this.customersId = customersId;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", info='" + info + '\'' +
                ", date='" + date + '\'' +
                ", status=" + done +
                ", customersId=" + customersId +
                '}';
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public short getDone() {
        return done;
    }

    public void setDone(short status) {
        this.done = status;
    }

    public Long getCustomersId() {
        return customersId;
    }

    public void setCustomersId(Long customersId) {
        this.customersId = customersId;
    }
}
