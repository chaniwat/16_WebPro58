package com.alumnisystem.model;

import com.alumnisystem.annotation.Model;

/**
 * Track Model
 */
@Model(factory = "TrackFactory")
public class Track extends BaseModel {

    private int id, student_id, generation, starteduyear, endeduyear;
    private Curriculum curriculum;
    private String name_th, name_en;

    public Track() {
        curriculum = new Curriculum();
    }

    public Curriculum getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }

    public String getName_th() {
        return name_th;
    }

    public void setName_th(String name_th) {
        this.name_th = name_th;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public int getStarteduyear() {
        return starteduyear;
    }

    public void setStarteduyear(int starteduyear) {
        this.starteduyear = starteduyear;
    }

    public int getEndeduyear() {
        return endeduyear;
    }

    public void setEndeduyear(int endeduyear) {
        this.endeduyear = endeduyear;
    }

}
