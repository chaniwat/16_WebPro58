import FormUtils from "../ultility/FormUtils";

export default class AlumniTrackPage {

    constructor(contextURL) {
        this.contextURL = contextURL;
        
        this.page = {bachelor: {form: $("#bachelor-form")}, master: {form: $("#master-form")}, doctoral: {form: $("#doctoral-form")}};

        $.each(this.page, (i, data) => {
           if(data.form != null) {
               data.formbtn = data.form.find("button#" + i + "-form-btn");
               FormUtils.setValToDefault(data.form.find("#" + i + "-form-trackid"));

               data.formstate = "VIEW";
               data.formbtn.click(data, this.submitForm);
               FormUtils.bindNotEmptyForm(data.form);
               FormUtils.disableAll(data.form);
           } 
        });
    }

    submitForm(e) {
        var o = e.data;

        if(o.formstate == "VIEW") {
            FormUtils.enableAllWithException(o.form);

            o.formstate = "EDIT";
            o.formbtn.attr("type", "submit");
            o.formbtn.removeClass("btn-primary").addClass("btn-success");
            o.formbtn.html("บันทึก");

            e.preventDefault();
        } else if(o.formstate == "EDIT") {
            if(!FormUtils.isFormComplete(o.form)) {
                e.preventDefault();
                if($("span.submit-alert").length <= 0) {
                    $("<span class=\"submit-alert text-danger\">โปรดกรอกข้อมูลที่ต้องการให้ครบ</span>").insertAfter(o.formbtn);
                }
            }
        }
    }

}
