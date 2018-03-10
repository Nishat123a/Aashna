package aashna.com.aashna.S_RequestNGO;

/**
 * Created by Dell on 27-Feb-18.
 */

public class NGO_details {

    private String ngo_name;
    private Double lat;
    private Double longi;
    private String contact;
    private String email;
    private String address;

    public NGO_details(String ngo_name, Double lat, Double longi, String contact, String email, String address) {
        this.ngo_name = ngo_name;
        this.lat = lat;
        this.longi = longi;
        this.contact = contact;
        this.email = email;
        this.address = address;
    }



    public String getNgo_name() {
        return ngo_name;
    }

    public void setNgo_name(String ngo_name) {
        this.ngo_name = ngo_name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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



}
