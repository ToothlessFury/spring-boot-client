package cz.cvut.fit.zatlodan.datamanip.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jack on 21.12.16.
 */
public class Customer extends Model {

    private String name;
    private String emailAddress;
    private String address;
    private String contact;

    private List<Sale> sales;

    public Customer() {

    }

    public Customer(Long id, String name, String emailAddress, String address, String contact) {
        this.id = id;
        this.name = name;
        this.emailAddress = emailAddress;
        this.address = address;
        this.contact = contact;
    }

    public Customer(Long id, String name, String emailAddress, String address, String contact, List<Sale> sales) {
        this.id = id;
        this.name = name;
        this.emailAddress = emailAddress;
        this.address = address;
        this.contact = contact;
        this.sales = sales;
    }

    public Customer(String name, String emailAddress, String address, String contact) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.address = address;
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", sales=" + sales +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }
}
