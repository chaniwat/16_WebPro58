package com.alumnisystem.model;

/**
 * Teacher Model
 */
public class Teacher extends User {

    private int teacher_id;
    private Work.Status work_status;

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public Work.Status getWork_status() {
        return work_status;
    }

    public void setWork_status(Work.Status work_status) {
        this.work_status = work_status;
    }

}
