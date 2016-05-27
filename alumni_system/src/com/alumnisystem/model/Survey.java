package com.alumnisystem.model;

import java.util.Date;

/**
 * Survey BaseModel
 */
public class Survey extends BaseModel {

    public int id;
    public String name, description, schemafile;
    public Date timecreate;
    public boolean status;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSchemafile() {
        return schemafile;
    }

    public void setSchemafile(String schemafile) {
        this.schemafile = schemafile;
    }

    public Date getTimecreate() {
        return timecreate;
    }

    public void setTimecreate(Date timecreate) {
        this.timecreate = timecreate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
