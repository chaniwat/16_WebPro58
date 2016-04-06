"use strict";

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var LoginPage = function () {
    function LoginPage(loginForm) {
        _classCallCheck(this, LoginPage);

        this.loginForm = loginForm;
        this.userElem = this.loginForm.find("input#username");
        this.passElem = this.loginForm.find("input#password");

        this.userElem.blur(this, this.updateInput);
        this.passElem.blur(this, this.updateInput);
        this.loginForm.submit(this, this.checkSubmitForm);
    }

    _createClass(LoginPage, [{
        key: "updateInput",
        value: function updateInput(e) {
            var elemGroup = $(this).is("#username") ? e.data.loginForm.find(".username-group") : e.data.loginForm.find(".password-group");
            if ($(this).val().trim() == "") {
                elemGroup.addClass("has-error");
            } else {
                elemGroup.removeClass("has-error");
            }
        }
    }, {
        key: "checkSubmitForm",
        value: function checkSubmitForm(e) {
            var o = e.data;
            o.checkEmptyInput(e, o);
        }
    }, {
        key: "checkEmptyInput",
        value: function checkEmptyInput(e, o) {
            var isUserValEmpty = o.userElem.val().trim() == "",
                isPassValEmpty = o.passElem.val().trim() == "";
            if (isUserValEmpty || isPassValEmpty) {
                e.preventDefault();
                if ($(".login-page div.alert").not(".uncomplete-field").length) {
                    $(".login-page div.alert").slideUp(function () {
                        $(".login-page div.alert").remove();
                        isUserValEmpty ? o.loginForm.find(".username-group").addClass("has-error") : 0;
                        isPassValEmpty ? o.loginForm.find(".password-group").addClass("has-error") : 0;
                        $("<div class=\"alert alert-danger uncomplete-field\" role=\"alert\">โปรดกรอกข้อมูลให้ครบ</div>").insertAfter(".login-page h1");
                        $(".login-page div.alert.uncomplete-field").hide().slideDown();
                    });
                } else if (!$(".login-page div.alert.uncomplete-field").length) {
                    isUserValEmpty ? o.loginForm.find(".username-group").addClass("has-error") : 0;
                    isPassValEmpty ? o.loginForm.find(".password-group").addClass("has-error") : 0;
                    $("<div class=\"alert alert-danger uncomplete-field\" role=\"alert\">โปรดกรอกข้อมูลให้ครบ</div>").insertAfter(".login-page h1");
                    $(".login-page div.alert.uncomplete-field").hide().slideDown();
                }
            }
        }
    }]);

    return LoginPage;
}();
//# sourceMappingURL=script.js.map
