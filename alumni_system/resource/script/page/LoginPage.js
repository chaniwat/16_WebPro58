import FormUtils from "./../ultility/FormUtils";

export default class {

    constructor() {
        this.loginForm = $("form#login-form");
        this.userElem = this.loginForm.find("input#username");
        this.passElem = this.loginForm.find("input#password");

        this.userElem.blur(this, this.updateInput);
        this.passElem.blur(this, this.updateInput);
        this.loginForm.submit(this, this.checkSubmitForm);
    }

    updateInput(e) {
        var elemGroup = $(this).is("#username") ? e.data.loginForm.find(".username-group") : e.data.loginForm.find(".password-group");
        if($(this).val().trim() == "") {
            elemGroup.addClass("has-error");
        } else {
            elemGroup.removeClass("has-error");
        }
    }

    checkSubmitForm(e) {
        var o = e.data;
        var showErrorfn = function(userVal, passVal) {
            userVal == "" ? o.loginForm.find(".username-group").addClass("has-error") : 0;
            passVal == "" ? o.loginForm.find(".password-group").addClass("has-error") : 0;
            $("<div class=\"alert alert-danger uncomplete-field\" role=\"alert\">โปรดกรอกข้อมูลให้ครบ</div>").insertAfter(".login-page h1");
            $(".login-page div.alert.uncomplete-field").hide().slideDown();
        }

        if(!FormUtils.isFormComplete(o.loginForm)) {
            e.preventDefault();
            if($(".login-page div.alert").not(".uncomplete-field").length) {
                $(".login-page div.alert").slideUp(function() {
                    $(".login-page div.alert").remove();
                    showErrorfn(o.userElem.val().trim(), o.passElem.val().trim());
                });
            } else if(!$(".login-page div.alert.uncomplete-field").length) {
                showErrorfn(o.userElem.val().trim(), o.passElem.val().trim());
            }
        }
    }

}
