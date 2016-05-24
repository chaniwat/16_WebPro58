import FormUtils from "./../ultility/FormUtils";

export default class LoginPage {

    constructor() {
        this.loginForm = $("form#login-form")

        FormUtils.bindNotEmptyForm(this.loginForm);
        this.loginForm.submit(this, this.checkSubmitForm);
    }

    checkSubmitForm(e) {
        let alertElem;
        
        if (!FormUtils.isFormComplete(e.data.loginForm)) {
            e.preventDefault();
            if ((alertElem = $("div.alert")).length) alertElem.remove();
            $("<div class=\"alert alert-danger uncomplete-field\" role=\"alert\">\n" +
                "    <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>\n" +
                "    โปรดกรอกข้อมูลให้ครบ\n" +
                "</div>").insertAfter("h1");
        }
    }

}
