package Model;

public class Gp {
    private int gpId;
    private String firstName;
    private String lastName;
    private String phone;
    private String expert;

    public Gp(int gpId, String firstName, String lastName, String phone, String expert) {
        this.gpId = gpId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.expert = expert;
    }

    public Gp(){

    }

    public int getGpId() {
        return gpId;
    }

    public void setGpId(int gpId) {
        this.gpId = gpId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getExpert() {
        return expert;
    }

    public void setExpert(String expert) {
        this.expert = expert;
    }
}

