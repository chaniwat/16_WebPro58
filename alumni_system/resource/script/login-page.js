$("form#login-form").submit(function(e) {
    if($("form#login-form input#username").val().trim() == "" && $("form#login-form input#password").val().trim() == "") {
        e.preventDefault();
        if($(".login-page div.alert").not(".uncomplete-field").length) {
            $(".login-page div.alert").slideUp(function() {
                $(".login-page div.alert").remove();
                $("form#login-form .password-group").addClass("has-error");
                $("form#login-form .username-group").addClass("has-error");
                $("<div class=\"alert alert-danger uncomplete-field\" role=\"alert\">โปรดกรอกข้อมูลให้ครบ</div>").insertAfter(".login-page h1");
                $(".login-page div.alert.uncomplete-field").hide().slideDown();
            });
        } else if(!$(".login-page div.alert.uncomplete-field").length) {
            $("form#login-form .password-group").addClass("has-error");
            $("form#login-form .username-group").addClass("has-error");
            $("<div class=\"alert alert-danger uncomplete-field\" role=\"alert\">โปรดกรอกข้อมูลให้ครบ</div>").insertAfter(".login-page h1");
            $(".login-page div.alert.uncomplete-field").hide().slideDown();
        }

    }
});

$("form#login-form input#username").blur(function() {
    var value = $(this).val();
    if(value.trim() == "") {
        $("form#login-form .username-group").addClass("has-error");
    } else {
        $("form#login-form .username-group").removeClass("has-error");
    }
});

$("form#login-form input#password").blur(function() {
    var value = $(this).val();
    if(value.trim() == "") {
        $("form#login-form .password-group").addClass("has-error");
    } else {
        $("form#login-form .password-group").removeClass("has-error");
    }
});