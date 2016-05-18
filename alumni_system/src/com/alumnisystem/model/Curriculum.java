package com.alumnisystem.model;

/**
 * Curriculum Model
 */
public class Curriculum extends BaseModel {

    public enum Degree {
        BACHELOR(1, "ปริญญาตรี"),
        MASTER(2, "ปริญญาโท"),
        DOCTORAL(3, "ปริญญาเอก");

        private int _value;
        private String _nameth;

        Degree(int _value, String _nameth) {
            this._value = _value;
            this._nameth = _nameth;
        }

        public int getValue() {
            return _value;
        }

        public String getNameTH() {
            return _nameth;
        }

    }

    private int id, cyear;
    private String cname_th, cname_en, sname_th, sname_en;
    private Degree degree;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCyear() {
        return cyear;
    }

    public void setCyear(int cyear) {
        this.cyear = cyear;
    }

    public String getCname_th() {
        return cname_th;
    }

    public void setCname_th(String cname_th) {
        this.cname_th = cname_th;
    }

    public String getCname_en() {
        return cname_en;
    }

    public void setCname_en(String cname_en) {
        this.cname_en = cname_en;
    }

    public String getSname_th() {
        return sname_th;
    }

    public void setSname_th(String sname_th) {
        this.sname_th = sname_th;
    }

    public String getSname_en() {
        return sname_en;
    }

    public void setSname_en(String sname_en) {
        this.sname_en = sname_en;
    }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

}
