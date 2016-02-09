"use strict";

$("form#login-form").submit(function (e) {
    if ($("form#login-form input#username").val().trim() == "" && $("form#login-form input#password").val().trim() == "") {
        e.preventDefault();
        if ($(".login-page div.alert").not(".uncomplete-field").length) {
            $(".login-page div.alert").slideUp(function () {
                $(".login-page div.alert").remove();
                $("form#login-form .password-group").addClass("has-error");
                $("form#login-form .username-group").addClass("has-error");
                $("<div class=\"alert alert-danger uncomplete-field\" role=\"alert\">โปรดกรอกข้อมูลให้ครบ</div>").insertAfter(".login-page h1");
                $(".login-page div.alert.uncomplete-field").hide().slideDown();
            });
        } else if (!$(".login-page div.alert.uncomplete-field").length) {
            $("form#login-form .password-group").addClass("has-error");
            $("form#login-form .username-group").addClass("has-error");
            $("<div class=\"alert alert-danger uncomplete-field\" role=\"alert\">โปรดกรอกข้อมูลให้ครบ</div>").insertAfter(".login-page h1");
            $(".login-page div.alert.uncomplete-field").hide().slideDown();
        }
    }
});

$("form#login-form input#username").blur(function () {
    var value = $(this).val();
    if (value.trim() == "") {
        $("form#login-form .username-group").addClass("has-error");
    } else {
        $("form#login-form .username-group").removeClass("has-error");
    }
});

$("form#login-form input#password").blur(function () {
    var value = $(this).val();
    if (value.trim() == "") {
        $("form#login-form .password-group").addClass("has-error");
    } else {
        $("form#login-form .password-group").removeClass("has-error");
    }
});
"use strict";

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

require("login-page");

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var main = function () {
	function main(body) {
		_classCallCheck(this, main);

		this.body = body;
	}

	_createClass(main, [{
		key: "init",
		value: function init() {
			alert("init!");
		}
	}]);

	return main;
}();
//# sourceMappingURL=script.js.map
