package com.alumnisystem.model;

import com.alumnisystem.annotation.Model;

/**
 * Job Model
 */
@Model(factory = "JobFactory")
public class Job extends BaseModel {

    private int id;
    private JobType jobType;
    private String name_th, name_en;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
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

}
