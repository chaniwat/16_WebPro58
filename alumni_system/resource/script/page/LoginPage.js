import FormUtils from "./../ultility/FormUtils";

export default class {

    constructor() {
        this.loginForm = $("form#login-form")

        FormUtils.bindNotEmptyForm(this.loginForm);
        this.loginForm.submit(this, this.checkSubmitForm);
    }

    checkSubmitForm(e) {
        var o = e.data;
        var showErrorfn = function() {
            $("<div class=\"alert alert-danger uncomplete-field\" role=\"alert\">โปรดกรอกข้อมูลให้ครบ</div>").insertAfter(".login-page h1");
            $(".login-page div.alert.uncomplete-field").hide().slideDown();
        }

        if(!FormUtils.isFormComplete(o.loginForm)) {
            e.preventDefault();
            if($(".login-page div.alert").not(".uncomplete-field").length) {
                $(".login-page div.alert").slideUp(function() {
                    $(".login-page div.alert").remove();
                    showErrorfn();
                });
            } else if(!$(".login-page div.alert.uncomplete-field").length) {
                showErrorfn();
            }
        }
    }

}
