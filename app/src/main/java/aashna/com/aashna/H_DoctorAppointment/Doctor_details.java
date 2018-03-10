package aashna.com.aashna.H_DoctorAppointment;

/**
 * Created by Dell on 27-Feb-18.
 */

public class Doctor_details {

    private String doc_name;
    private Double lat;
    private Double longi;
    private String contact;
    private String email;
    private String address;
    private String timing;


    public Doctor_details(String doc_name, Double lat, Double longi, String contact, String email, String address, String timing) {
        this.doc_name = doc_name;

        this.lat = lat;
        this.longi = longi;
        this.contact = contact;
        this.email = email;
        this.address = address;
        this.timing = timing;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public void setDoc_name(String doc_name) {
        this.doc_name = doc_name;
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
