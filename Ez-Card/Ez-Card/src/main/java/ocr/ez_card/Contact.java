package ocr.ez_card;

import java.io.Serializable;

/**
 * Created by luizhcb0 on 28/04/14.
 */
public class Contact implements Serializable {
    private String name;
    private String email;
    private String address;
    private String phone;
    private String fax;

    public Contact(String name, String email, String address, String phone, String fax) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.fax = fax;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
}
