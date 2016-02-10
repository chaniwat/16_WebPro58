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
"use strict";

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var NewStudentPage = function () {
    function NewStudentPage(newStudentForm) {
        _classCallCheck(this, NewStudentPage);

        this.newStudentForm = newStudentForm;
        this.year = this.newStudentForm.find("div.birthdate-group select.birthdate-year");
        this.month = this.newStudentForm.find("div.birthdate-group select.birthdate-month");
        this.day = this.newStudentForm.find("div.birthdate-group select.birthdate-day");

        this.year.change(this, this.updateBirthdayGroupForm);
        this.month.change(this, this.updateBirthdayGroupForm);
    }

    _createClass(NewStudentPage, [{
        key: "updateBirthdayGroupForm",
        value: function updateBirthdayGroupForm(e) {
            var o = e.data;
            if (o.year.val() != null && o.month.val() != null) {
                o.day.html("<option class=\"default\" value=\"\" disabled selected>DD</option>");
                for (var i = o.month.val() == 2 ? o.year.val() % 4 == 0 && o.year.val() % 100 != 0 || o.year.val() % 400 == 0 ? 29 : 28 : $.inArray(o.month.val(), [1, 3, 5, 7, 8, 10, 12]) ? 31 : 30; i >= 1; i--) {
                    $("<option value='" + i + "'>" + i + "</option>").insertAfter(o.day.find("option.default"));
                }
            }
        }
    }]);

    return NewStudentPage;
}();
//# sourceMappingURL=script.js.map
