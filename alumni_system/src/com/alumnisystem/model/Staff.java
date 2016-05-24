package com.alumnisystem.model;

/**
 * Staff Model
 */
public class Staff extends User {

    private int staff_id;
    private Work.Section section;

    public Staff() {
        type = Type.STAFF;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public Work.Section getSection() {
        return section;
    }

    public void setSection(Work.Section section) {
        this.section = section;
    }

}
