package com.manavta.ngoapp.ngo.model;

public class HospitalNGO {

    String hospitalname,location,hospitaladdress,numberbeds,pincode,hospitalphonenumber;
    int hospitalid,img;
    String availableoxygen;

    public String getAvailableoxygen() {
        return availableoxygen;
    }

    public void setAvailableoxygen(String availableoxygen) {
        this.availableoxygen = availableoxygen;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getHospitalname() {
        return hospitalname;
    }

    public void setHospitalname(String hospitalname) {
        this.hospitalname = hospitalname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHospitaladdress() {
        return hospitaladdress;
    }

    public void setHospitaladdress(String hospitaladdress) {
        this.hospitaladdress = hospitaladdress;
    }

    public String getNumberbeds() {
        return numberbeds;
    }

    public void setNumberbeds(String numberbeds) {
        this.numberbeds = numberbeds;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getHospitalphonenumber() {
        return hospitalphonenumber;
    }

    public void setHospitalphonenumber(String hospitalphonenumber) {
        this.hospitalphonenumber = hospitalphonenumber;
    }

    public int getHospitalid() {
        return hospitalid;
    }

    public void setHospitalid(int hospitalid) {
        this.hospitalid = hospitalid;
    }
}
