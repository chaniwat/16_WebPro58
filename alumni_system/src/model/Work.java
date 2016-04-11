package model;

/**
 * Created by meranote on 4/5/2016 AD.
 */
public class Work {

    private Work() {}

    public enum Status {
        EMPLOYEE,
        OFFICIAL
    }

    public static class Section {
        private int section_id;
        private String name_th, name_en;

        public int getSection_id() {
            return section_id;
        }

        public void setSection_id(int section_id) {
            this.section_id = section_id;
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

        public static Section getSectionById(int section_id) {
            // TODO get section by section_id
            return null;
        }
    }

}
