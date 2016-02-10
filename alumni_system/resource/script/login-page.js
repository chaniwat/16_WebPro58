"use strict";

class LoginPage {

    constructor(loginForm) {
        this.loginForm = loginForm;
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
        o.checkEmptyInput(e, o);
    }

    checkEmptyInput(e, o) {
        var isUserValEmpty = o.userElem.val().trim() == "",
            isPassValEmpty = o.passElem.val().trim() == "";
        if(isUserValEmpty || isPassValEmpty) {
            e.preventDefault();
            if($(".login-page div.alert").not(".uncomplete-field").length) {
                $(".login-page div.alert").slideUp(function() {
                    $(".login-page div.alert").remove();
                    isUserValEmpty ? o.loginForm.find(".username-group").addClass("has-error") : 0;
                    isPassValEmpty ? o.loginForm.find(".password-group").addClass("has-error") : 0;
                    $("<div class=\"alert alert-danger uncomplete-field\" role=\"alert\">โปรดกรอกข้อมูลให้ครบ</div>").insertAfter(".login-page h1");
                    $(".login-page div.alert.uncomplete-field").hide().slideDown();
                });
            } else if(!$(".login-page div.alert.uncomplete-field").length) {
                isUserValEmpty ? o.loginForm.find(".username-group").addClass("has-error") : 0;
                isPassValEmpty ? o.loginForm.find(".password-group").addClass("has-error") : 0;
                $("<div class=\"alert alert-danger uncomplete-field\" role=\"alert\">โปรดกรอกข้อมูลให้ครบ</div>").insertAfter(".login-page h1");
                $(".login-page div.alert.uncomplete-field").hide().slideDown();
            }
        }
    }

}