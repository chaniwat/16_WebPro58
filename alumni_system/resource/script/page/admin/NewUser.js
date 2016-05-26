import FormUtils from "../../ultility/FormUtils";

export default class NewUser {

    constructor() {
        this.teacher = {form: $("#teacher-form")};
        this.staff = {form: $("#staff-form")};

        FormUtils.bindNotEmptyForm(this.teacher.form);
        FormUtils.bindNotEmptyForm(this.staff.form);
        
        this.teacher.form.submit(this.teacher, this.submitForm);
        this.staff.form.submit(this.staff, this.submitForm);
    }
    
    submitForm(e) {
        let o = e.data;
        if(!FormUtils.isFormComplete(o.form)) {
            e.preventDefault();
            if($("span.submit-alert").length <= 0) {
                $("<span class=\"submit-alert text-danger\">โปรดกรอกข้อมูลที่ต้องการให้ครบ</span>").insertAfter(o.form.find("button"));
            }
        }
    }

}
