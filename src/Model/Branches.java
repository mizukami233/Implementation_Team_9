package Model;

public class Branches {
    private int id;
    private String name;
    private int postcode;
    private String openingHour;
    private int phone;
    private String streetName;
    private String suburb;

    public Branches(int id, String name, int postcode, String openingHour, int phone, String streetName, String suburb) {
        this.id = id;
        this.name = name;
        this.postcode = postcode;
        this.openingHour = openingHour;
        this.phone = phone;
        this.streetName = streetName;
        this.suburb = suburb;
    }

    public Branches() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public String getOpeningHour() {
        return openingHour;
    }

    public void setOpeningHour(String openingHour) {
        this.openingHour = openingHour;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }


}
