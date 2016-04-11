package model;

/**
 * Created by meranote on 4/5/2016 AD.
 */
public class Curriculum {

    private int curriculum_id;
    private String name_th, name_en;

    public void setCurriculum_id(int curriculum_id) {
        this.curriculum_id = curriculum_id;
    }

    public int getCurriculum_id() {
        return curriculum_id;
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
