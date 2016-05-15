package com.alumnisystem.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Alumni Model
 */
public class Alumni extends User {

    private int alumni_id;
    private String nickname, work_name, address, amphure, district, zipcode, province;
    private Date birthdate;
    private Job job = new Job();
    private ArrayList<Track> tracks = new ArrayList<>();

    public int getAlumni_id() {
        return alumni_id;
    }

    public void setAlumni_id(int alumni_id) {
        this.alumni_id = alumni_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getWork_name() {
        return work_name;
    }

    public void setWork_name(String work_name) {
        this.work_name = work_name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getAmphure() {
        return amphure;
    }

    public void setAmphure(String amphure) {
        this.amphure = amphure;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

}
