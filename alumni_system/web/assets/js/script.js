(function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);var f=new Error("Cannot find module '"+o+"'");throw f.code="MODULE_NOT_FOUND",f}var l=n[o]={exports:{}};t[o][0].call(l.exports,function(e){var n=t[o][1][e];return s(n?n:e)},l,l.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s})({1:[function(require,module,exports){
"use strict";

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _Route = require("./Route");

var _Route2 = _interopRequireDefault(_Route);

var _LoginPage = require("./page/LoginPage");

var _LoginPage2 = _interopRequireDefault(_LoginPage);

var _ProfilePage = require("./page/ProfilePage");

var _ProfilePage2 = _interopRequireDefault(_ProfilePage);

var _AlumniTrackPage = require("./page/AlumniTrackPage");

var _AlumniTrackPage2 = _interopRequireDefault(_AlumniTrackPage);

var _ViewDataPage = require("./page/ViewDataPage");

var _ViewDataPage2 = _interopRequireDefault(_ViewDataPage);

var _NewAlumni = require("./page/admin/NewAlumni");

var _NewAlumni2 = _interopRequireDefault(_NewAlumni);

var _NewUser = require("./page/admin/NewUser");

var _NewUser2 = _interopRequireDefault(_NewUser);

var _SurveyFormCreate = require("./page/admin/SurveyFormCreate");

var _SurveyFormCreate2 = _interopRequireDefault(_SurveyFormCreate);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var Main = function () {
    function Main() {
        _classCallCheck(this, Main);

        var sitedataElem = $("sitedata");

        this.route(sitedataElem.attr("contextPath"));

        sitedataElem.remove();
    }

    _createClass(Main, [{
        key: "route",
        value: function route(contextURL) {
            var route = new _Route2.default(contextURL);

            route.doRoute("login/", function () {
                new _LoginPage2.default();
            });

            route.doRoute(["profile/*", "admin/profile/*"], function () {
                new _ProfilePage2.default(contextURL);
            });

            route.doRoute(["alumni/*", "admin/alumni/*", "admin/user/*", "admin/survey/*"], function () {
                new _ViewDataPage2.default();
            });

            route.doRoute(["admin/event/all"], function () {
                var alumnitable = $("#alumni-table");
                var searchform = $("#table-searchform");

                if (alumnitable.length > 0) {
                    (function () {
                        var datatable = alumnitable.DataTable({
                            "order": [[0, "desc"]]
                        });

                        var searchtype = searchform.find("#table-searchtype");
                        var searchinput = searchform.find("#table-searchinput");

                        searchinput.keyup(function () {
                            datatable.columns(searchtype.val()).search(searchinput.val()).draw();
                        });

                        searchtype.change(function () {
                            searchinput.trigger('keyup');
                        });

                        $("#alumni-table_filter").html(searchform);
                    })();
                }
            });

            route.doRoute(["admin/user/add"], function () {
                new _NewUser2.default();
            });

            route.doRoute(["track/edit/*", "admin/track/edit/*"], function () {
                new _AlumniTrackPage2.default(contextURL);
            });

            route.doRoute("admin/", function () {
                var flag = true;

                try {
                    if (window.data == null) {
                        flag = false;
                    }
                } catch (ReferenceError) {
                    flag = false;
                }

                if (!flag) {
                    window.data = {
                        labels: ["ปริญญาตรี", "ปริญญาโท", "ปริญญาเอก"],
                        datasets: [{
                            label: "จำนวนศิษย์เก่า",
                            backgroundColor: "rgba(16, 171, 234, 1)",
                            borderWidth: 0,
                            hoverBackgroundColor: "rgba(89, 211, 234, 1)",
                            data: [0, 0, 0],
                            yAxisID: "y-axis-0"
                        }]
                    };
                }

                new Chart($("#alumniChart").get(0).getContext("2d"), {
                    type: 'bar',
                    data: window.data,
                    options: {}
                });
            });

            route.doRoute("admin/alumni/add", function () {
                new _NewAlumni2.default(contextURL);
            });

            route.doRoute("admin/event/create", function () {
                $(".textarea").wysihtml5();
            });

            route.doRoute("admin/survey/create", function () {
                new _SurveyFormCreate2.default();
            });

            route.execute();
        }
    }]);

    return Main;
}();

$(document).ready(function () {
    new Main();
});

},{"./Route":2,"./page/AlumniTrackPage":3,"./page/LoginPage":4,"./page/ProfilePage":5,"./page/ViewDataPage":6,"./page/admin/NewAlumni":7,"./page/admin/NewUser":8,"./page/admin/SurveyFormCreate":9}],2:[function(require,module,exports){
"use strict";

Object.defineProperty(exports, "__esModule", {
    value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var Route = function () {
    function Route(contextURL) {
        _classCallCheck(this, Route);

        this.contextURL = contextURL[0] == "/" ? contextURL.substring(1) : contextURL;

        this.routeMapping = [];
    }

    _createClass(Route, [{
        key: "doRoute",
        value: function doRoute(path, fn) {
            var temp = [];
            var fixPath = function fixPath(path) {
                return path[path.length - 1] != "/" && path[path.length - 1] != "*" ? path + "/" : path;
            };

            if (path instanceof Array) {
                for (var i = 0; i < path.length; i++) {
                    temp.push(fixPath(path[i]));
                }
            } else temp = fixPath(path);

            this.routeMapping.push({ path: temp, fn: fn });
        }
    }, {
        key: "execute",
        value: function execute() {
            var currentPath = this.getCurrentPath(this.contextURL);
            var match = [];

            var isMatchToCurrentPath = function isMatchToCurrentPath(path) {
                if (currentPath.length == 0) return !!(path == "" || path == "*");
                if (currentPath.length == path.length && currentPath == path) return true;

                var c = void 0;
                for (c = 0; c < path.length; c++) {
                    if (c < currentPath.length && path[c] != currentPath[c]) return !!(c == path.length - 1 && path[c - 1] + path[c] == "/*");else if (c >= currentPath.length) break;
                }if (currentPath.length - 1 > c) return false;else if (path.length > currentPath.length) return !!(c == path.length - 1 && path[c - 1] + path[c] == "/*");else return true;
            };

            for (var i = 0; i < this.routeMapping.length; i++) {
                var currentRoute = this.routeMapping[i];

                if (currentRoute.path instanceof Array) {
                    for (var j = 0; j < currentRoute.path.length; j++) {
                        if (isMatchToCurrentPath(currentRoute.path[j])) match.push(currentRoute.path[j]);
                    }
                } else {
                    if (isMatchToCurrentPath(currentRoute.path)) match.push(currentRoute.path);
                }
            }

            var actionRoute = match.sort().reverse()[0];

            for (var _i = 0; _i < this.routeMapping.length; _i++) {
                var _currentRoute = this.routeMapping[_i];

                if (_currentRoute.path instanceof Array) {
                    for (var _j = 0; _j < _currentRoute.path.length; _j++) {
                        if (_currentRoute.path[_j] == actionRoute) {
                            console.log("do route : " + _currentRoute.path);
                            _currentRoute.fn();
                            return;
                        }
                    }
                } else {
                    if (_currentRoute.path == actionRoute) {
                        console.log("do route : " + _currentRoute.path);
                        _currentRoute.fn();
                        return;
                    }
                }
            }
        }
    }, {
        key: "getCurrentPath",
        value: function getCurrentPath(contextURL) {
            var currentPath = window.location.pathname[window.location.pathname.length - 1] != "/" ? window.location.pathname + "/" : window.location.pathname;
            var split = currentPath.split("/");
            var a = [];

            for (var i = split.length - 1; i >= 0; i--) {
                if (split[i] == contextURL) break;
                a.push(split[i]);
            }

            return a.reverse().join("/");
        }
    }]);

    return Route;
}();

exports.default = Route;

},{}],3:[function(require,module,exports){
"use strict";

Object.defineProperty(exports, "__esModule", {
    value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _FormUtils = require("../ultility/FormUtils");

var _FormUtils2 = _interopRequireDefault(_FormUtils);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var AlumniTrackPage = function () {
    function AlumniTrackPage(contextURL) {
        var _this = this;

        _classCallCheck(this, AlumniTrackPage);

        this.contextURL = contextURL;

        this.page = { bachelor: { form: $("#bachelor-form") }, master: { form: $("#master-form") }, doctoral: { form: $("#doctoral-form") } };

        $.each(this.page, function (i, data) {
            if (data.form != null) {
                data.formbtn = data.form.find("button#" + i + "-form-btn");
                _FormUtils2.default.setValToDefault(data.form.find("#" + i + "-form-trackid"));

                data.formstate = "VIEW";
                data.formbtn.click(data, _this.submitForm);
                _FormUtils2.default.bindNotEmptyForm(data.form);
                _FormUtils2.default.disableAll(data.form);
            }
        });
    }

    _createClass(AlumniTrackPage, [{
        key: "submitForm",
        value: function submitForm(e) {
            var o = e.data;

            if (o.formstate == "VIEW") {
                _FormUtils2.default.enableAllWithException(o.form);

                o.formstate = "EDIT";
                o.formbtn.attr("type", "submit");
                o.formbtn.removeClass("btn-primary").addClass("btn-success");
                o.formbtn.html("บันทึก");

                e.preventDefault();
            } else if (o.formstate == "EDIT") {
                if (!_FormUtils2.default.isFormComplete(o.form)) {
                    e.preventDefault();
                    if ($("span.submit-alert").length <= 0) {
                        $("<span class=\"submit-alert text-danger\">โปรดกรอกข้อมูลที่ต้องการให้ครบ</span>").insertAfter(o.formbtn);
                    }
                }
            }
        }
    }]);

    return AlumniTrackPage;
}();

exports.default = AlumniTrackPage;

},{"../ultility/FormUtils":11}],4:[function(require,module,exports){
"use strict";

Object.defineProperty(exports, "__esModule", {
    value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _FormUtils = require("./../ultility/FormUtils");

var _FormUtils2 = _interopRequireDefault(_FormUtils);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var LoginPage = function () {
    function LoginPage() {
        _classCallCheck(this, LoginPage);

        this.loginForm = $("form#login-form");

        _FormUtils2.default.bindNotEmptyForm(this.loginForm);
        this.loginForm.submit(this, this.checkSubmitForm);
    }

    _createClass(LoginPage, [{
        key: "checkSubmitForm",
        value: function checkSubmitForm(e) {
            var alertElem = void 0;

            if (!_FormUtils2.default.isFormComplete(e.data.loginForm)) {
                e.preventDefault();
                if ((alertElem = $("div.alert")).length) alertElem.remove();
                $("<div class=\"alert alert-danger uncomplete-field\" role=\"alert\">\n" + "    <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>\n" + "    โปรดกรอกข้อมูลให้ครบ\n" + "</div>").insertAfter("h1");
            }
        }
    }]);

    return LoginPage;
}();

exports.default = LoginPage;

},{"./../ultility/FormUtils":11}],5:[function(require,module,exports){
"use strict";

Object.defineProperty(exports, "__esModule", {
    value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _FormUtils = require("./../ultility/FormUtils");

var _FormUtils2 = _interopRequireDefault(_FormUtils);

var _DateSelectionBuilder = require("./../ultility/DateSelectionBuilder");

var _DateSelectionBuilder2 = _interopRequireDefault(_DateSelectionBuilder);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var ProfilePage = function () {
    function ProfilePage(contextURL) {
        var _this = this;

        _classCallCheck(this, ProfilePage);

        this.contextURL = contextURL;
        this.usertype = $("#profilepage-usertype").val();

        if (this.usertype == "ALUMNI") {
            this.profileform = $("#alumni-form");
            this.profileformbtn = this.profileform.find("button#alumni-form-btn");

            new _DateSelectionBuilder2.default(this.profileform.find("#alumni-form-birthdate-year"), this.profileform.find("#alumni-form-birthdate-month"), this.profileform.find("#alumni-form-birthdate-day"));

            this.profileform.find("#alumni-form-jobtype").change(this, this.jobTypeChange);
            this.profileform.find("#alumni-form-jobname").change(this, this.jobChange);

            _FormUtils2.default.setValToDefault(this.profileform.find("#alumni-form-province"));
            _FormUtils2.default.setValToDefault(this.profileform.find("#alumni-form-jobtype"));
            this.profileform.find("#alumni-form-jobtype").trigger("change", function () {
                _FormUtils2.default.setValToDefault(_this.profileform.find("#alumni-form-jobname"));
            });
        } else if (this.usertype == "TEACHER") {
            this.profileform = $("#teacher-form");
            this.profileformbtn = this.profileform.find("button#teacher-form-btn");

            _FormUtils2.default.setValToDefault(this.profileform.find("#teacher-form-workstatus"));
        } else if (this.usertype == "STAFF") {
            this.profileform = $("#staff-form");
            this.profileformbtn = this.profileform.find("button#staff-form-btn");

            _FormUtils2.default.setValToDefault(this.profileform.find("#staff-form-worksection"));
        }

        this.profileformstate = "VIEW";
        this.profileformbtn.click(this, this.submitForm);
        _FormUtils2.default.bindNotEmptyForm(this.profileform);
        _FormUtils2.default.disableAll(this.profileform);
    }

    _createClass(ProfilePage, [{
        key: "jobTypeChange",
        value: function jobTypeChange(e, callback) {
            var o = e.data;
            var jobtype_id = o.profileform.find("#alumni-form-jobtype").val();
            if (jobtype_id > 0) {
                o.profileform.find("#alumni-form-jobtypeother").attr("readonly", true);
                o.profileform.find("#alumni-form-jobtypeother").val("");
                o.profileform.find("#alumni-form-jobname").removeAttr("readonly");
                o.profileform.find("#alumni-form-jobname").val("null");
                o.profileform.find("#alumni-form-jobnameother").val("");
                o.profileform.find("#alumni-form-jobnameother").attr("readonly", true);

                $.get(o.contextURL + "/ajax/job?jobtype_id=" + jobtype_id, function (data) {
                    var insideElem = "<option value=\"null\">โปรดเลือก</option>\n";
                    $.each(data, function (i, data) {
                        insideElem += "<option value=\"" + data.id + "\">" + data.name_th + "</option>";
                    });
                    insideElem += "<option value=\"0\">อื่นๆ</option>";
                    o.profileform.find("#alumni-form-jobname").html(insideElem);

                    if (callback) callback();
                }, "json");
            } else if (jobtype_id == 0) {
                o.profileform.find("#alumni-form-jobtypeother").removeAttr("readonly");
                o.profileform.find("#alumni-form-jobname").attr("readonly", true);
                o.profileform.find("#alumni-form-jobname").val("0");
                o.profileform.find("#alumni-form-jobnameother").removeAttr("readonly");
            } else {
                o.profileform.find("#alumni-form-jobtypeother").attr("readonly", true);
                o.profileform.find("#alumni-form-jobtypeother").val("");
                o.profileform.find("#alumni-form-jobname").attr("readonly", true);
                o.profileform.find("#alumni-form-jobname").val("null");
                o.profileform.find("#alumni-form-jobnameother").val("");
                o.profileform.find("#alumni-form-jobnameother").attr("readonly", true);
            }
        }
    }, {
        key: "jobChange",
        value: function jobChange(e) {
            var o = e.data;
            var job_id = o.profileform.find("#alumni-form-jobname").val();
            if (job_id == "null" || job_id > 0) {
                o.profileform.find("#alumni-form-jobnameother").val("");
                o.profileform.find("#alumni-form-jobnameother").attr("readonly", true);
            } else if (job_id == 0) {
                o.profileform.find("#alumni-form-jobnameother").removeAttr("readonly");
            }
        }
    }, {
        key: "submitForm",
        value: function submitForm(e) {
            var o = e.data;

            if (o.profileformstate == "VIEW") {
                _FormUtils2.default.enableAllWithException(o.profileform);

                o.profileformstate = "EDIT";
                o.profileformbtn.attr("type", "submit");
                o.profileformbtn.removeClass("btn-primary").addClass("btn-success");
                o.profileformbtn.html("บันทึก");

                e.preventDefault();
            } else if (o.profileformstate == "EDIT") {
                if (!_FormUtils2.default.isFormComplete(o.profileform)) {
                    e.preventDefault();
                    if ($("span.submit-alert").length <= 0) {
                        $("<span class=\"submit-alert text-danger\">โปรดกรอกข้อมูลที่ต้องการให้ครบ</span>").insertAfter(o.profileformbtn);
                    }
                }
            }
        }
    }]);

    return ProfilePage;
}();

exports.default = ProfilePage;

},{"./../ultility/DateSelectionBuilder":10,"./../ultility/FormUtils":11}],6:[function(require,module,exports){
"use strict";

Object.defineProperty(exports, "__esModule", {
    value: true
});

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var ViewDataPage = function ViewDataPage() {
    var _this = this;

    _classCallCheck(this, ViewDataPage);

    this.alumnitable = $("#alumni-table");
    this.searchform = $("#table-searchform");

    if (this.alumnitable.length > 0) {
        (function () {
            _this.datatable = _this.alumnitable.DataTable();

            var searchtype = _this.searchform.find("#table-searchtype");
            var searchinput = _this.searchform.find("#table-searchinput");

            searchinput.keyup(function () {
                _this.datatable.columns(searchtype.val()).search(searchinput.val()).draw();
            });

            searchtype.change(function () {
                searchinput.trigger('keyup');
            });

            $("#alumni-table_filter").html(_this.searchform);
        })();
    }
};

exports.default = ViewDataPage;

},{}],7:[function(require,module,exports){
"use strict";

Object.defineProperty(exports, "__esModule", {
    value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _FormUtils = require("../../ultility/FormUtils");

var _FormUtils2 = _interopRequireDefault(_FormUtils);

var _DateSelectionBuilder = require("../../ultility/DateSelectionBuilder");

var _DateSelectionBuilder2 = _interopRequireDefault(_DateSelectionBuilder);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var NewAlumni = function () {
    function NewAlumni(contextURL) {
        _classCallCheck(this, NewAlumni);

        this.contextURL = contextURL;

        this.alumnifindform = $("#alumni-find-form");
        this.alumninewform = $("#alumni-form");
        this.alumnitrackform = $("#alumni-track-form");

        if (this.alumnifindform.length > 0) {
            _FormUtils2.default.bindNotEmptyForm(this.alumnifindform);

            this.alumnifindform.submit(this, function (e) {
                var o = e.data;
                if (!_FormUtils2.default.isFormComplete(o.alumnifindform)) {
                    e.preventDefault();
                    if ($("span.submit-alert").length <= 0) {
                        $("<span class=\"submit-alert text-danger\">โปรดกรอกข้อมูลที่ต้องการให้ครบ</span>").insertAfter(o.alumnifindform.find("#alumni-find-form-btn"));
                    }
                }
            });
        } else if (this.alumninewform.length > 0) {
            this.doAlumniNewForm();
        } else if (this.alumnitrackform.length > 0) {
            this.doAlumniTrackForm();
        }
    }

    _createClass(NewAlumni, [{
        key: "doAlumniNewForm",
        value: function doAlumniNewForm() {
            var _this = this;

            new _DateSelectionBuilder2.default(this.alumninewform.find("#alumni-form-birthdate-year"), this.alumninewform.find("#alumni-form-birthdate-month"), this.alumninewform.find("#alumni-form-birthdate-day"));

            this.alumninewform.find("#alumni-form-jobtype").change(this, this.jobTypeChange);
            this.alumninewform.find("#alumni-form-jobname").change(this, this.jobChange);

            _FormUtils2.default.setValToDefault(this.alumninewform.find("#alumni-form-jobtype"));
            this.alumninewform.find("#alumni-form-jobtype").trigger("change", function () {
                _FormUtils2.default.setValToDefault(_this.alumninewform.find("#alumni-form-jobname"));
            });
        }
    }, {
        key: "jobTypeChange",
        value: function jobTypeChange(e, callback) {
            var o = e.data;
            var jobtype_id = o.alumninewform.find("#alumni-form-jobtype").val();
            if (jobtype_id > 0) {
                o.alumninewform.find("#alumni-form-jobtypeother").attr("readonly", true);
                o.alumninewform.find("#alumni-form-jobtypeother").val("");
                o.alumninewform.find("#alumni-form-jobname").removeAttr("readonly");
                o.alumninewform.find("#alumni-form-jobname").val("null");
                o.alumninewform.find("#alumni-form-jobnameother").val("");
                o.alumninewform.find("#alumni-form-jobnameother").attr("readonly", true);

                $.get(o.contextURL + "/ajax/job?jobtype_id=" + jobtype_id, function (data) {
                    var insideElem = "<option value=\"null\">โปรดเลือก</option>\n";
                    $.each(data, function (i, data) {
                        insideElem += "<option value=\"" + data.id + "\">" + data.name_th + "</option>";
                    });
                    insideElem += "<option value=\"0\">อื่นๆ</option>";
                    o.alumninewform.find("#alumni-form-jobname").html(insideElem);

                    if (callback) callback();
                }, "json");
            } else if (jobtype_id == 0) {
                o.alumninewform.find("#alumni-form-jobtypeother").removeAttr("readonly");
                o.alumninewform.find("#alumni-form-jobname").attr("readonly", true);
                o.alumninewform.find("#alumni-form-jobname").val("0");
                o.alumninewform.find("#alumni-form-jobnameother").removeAttr("readonly");
            } else {
                o.alumninewform.find("#alumni-form-jobtypeother").attr("readonly", true);
                o.alumninewform.find("#alumni-form-jobtypeother").val("");
                o.alumninewform.find("#alumni-form-jobname").attr("readonly", true);
                o.alumninewform.find("#alumni-form-jobname").val("null");
                o.alumninewform.find("#alumni-form-jobnameother").val("");
                o.alumninewform.find("#alumni-form-jobnameother").attr("readonly", true);
            }
        }
    }, {
        key: "jobChange",
        value: function jobChange(e) {
            var o = e.data;
            var job_id = o.alumninewform.find("#alumni-form-jobname").val();
            if (job_id == "null" || job_id > 0) {
                o.alumninewform.find("#alumni-form-jobnameother").val("");
                o.alumninewform.find("#alumni-form-jobnameother").attr("readonly", true);
            } else if (job_id == 0) {
                o.alumninewform.find("#alumni-form-jobnameother").removeAttr("readonly");
            }
        }
    }, {
        key: "doAlumniTrackForm",
        value: function doAlumniTrackForm() {
            this.select = {
                degreeElem: $("#alumni-track-degree"),
                curriculumElem: $("#alumni-track-curriculumid"),
                trackElem: $("#alumni-track-trackid")
            };

            this.select.curriculumElem.attr("disabled", true);
            this.select.trackElem.attr("disabled", true);

            this.select.degreeElem.change(this, function (e) {
                var o = e.data;
                if (o.select.degreeElem.val() != "") {
                    $.get(o.contextURL + "/ajax/curriculum?degree=" + o.select.degreeElem.val(), function (data) {
                        var insideElem = "<option value=\"\">โปรดเลือก</option>\n";
                        $.each(data, function (i, data) {
                            insideElem += "<option value=\"" + data.id + "\">" + data.cname_th + " " + data.sname_th + "(ปี " + data.cyear + ")</option>";
                        });
                        o.select.curriculumElem.html(insideElem);
                        o.select.curriculumElem.removeAttr("disabled");
                        o.select.curriculumElem.val("");
                        o.select.trackElem.attr("disabled", true);
                        o.select.trackElem.val("");
                    }, "json");
                } else {
                    var insideElem = "<option value=\"\">โปรดเลือก</option>\n";
                    o.select.curriculumElem.html(insideElem);
                    o.select.trackElem.html(insideElem);

                    o.select.curriculumElem.attr("disabled", true);
                    o.select.curriculumElem.val("");
                    o.select.trackElem.attr("disabled", true);
                    o.select.trackElem.val("");
                }
            });

            this.select.curriculumElem.change(this, function (e) {
                var o = e.data;
                if (o.select.curriculumElem.val() != "") {
                    $.get(o.contextURL + "/ajax/track?curriculum_id=" + o.select.curriculumElem.val(), function (data) {
                        var insideElem = "<option value=\"\">โปรดเลือก</option>\n";
                        $.each(data, function (i, data) {
                            insideElem += "<option value=\"" + data.id + "\">" + data.name_th + "</option>";
                        });
                        o.select.trackElem.html(insideElem);
                        o.select.trackElem.removeAttr("disabled");
                        o.select.trackElem.val("");
                    }, "json");
                } else {
                    var insideElem = "<option value=\"\">โปรดเลือก</option>\n";
                    o.select.trackElem.html(insideElem);

                    o.select.trackElem.attr("disabled", true);
                    o.select.trackElem.val("");
                }
            });

            _FormUtils2.default.bindNotEmptyForm(this.alumnitrackform);

            this.alumnitrackform.submit(this, function (e) {
                var o = e.data;
                if (!_FormUtils2.default.isFormComplete(o.alumnitrackform)) {
                    e.preventDefault();
                    if ($("span.submit-alert").length <= 0) {
                        $("<span class=\"submit-alert text-danger\">โปรดกรอกข้อมูลที่ต้องการให้ครบ</span>").insertAfter(o.alumnitrackform.find("#alumni-track-form-btn"));
                    }
                }
            });
        }
    }]);

    return NewAlumni;
}();

exports.default = NewAlumni;

},{"../../ultility/DateSelectionBuilder":10,"../../ultility/FormUtils":11}],8:[function(require,module,exports){
"use strict";

Object.defineProperty(exports, "__esModule", {
    value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _FormUtils = require("../../ultility/FormUtils");

var _FormUtils2 = _interopRequireDefault(_FormUtils);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var NewUser = function () {
    function NewUser() {
        _classCallCheck(this, NewUser);

        this.teacher = { form: $("#teacher-form") };
        this.staff = { form: $("#staff-form") };

        _FormUtils2.default.bindNotEmptyForm(this.teacher.form);
        _FormUtils2.default.bindNotEmptyForm(this.staff.form);

        this.teacher.form.submit(this.teacher, this.submitForm);
        this.staff.form.submit(this.staff, this.submitForm);
    }

    _createClass(NewUser, [{
        key: "submitForm",
        value: function submitForm(e) {
            var o = e.data;
            if (!_FormUtils2.default.isFormComplete(o.form)) {
                e.preventDefault();
                if ($("span.submit-alert").length <= 0) {
                    $("<span class=\"submit-alert text-danger\">โปรดกรอกข้อมูลที่ต้องการให้ครบ</span>").insertAfter(o.form.find("button"));
                }
            }
        }
    }]);

    return NewUser;
}();

exports.default = NewUser;

},{"../../ultility/FormUtils":11}],9:[function(require,module,exports){
"use strict";

Object.defineProperty(exports, "__esModule", {
    value: true
});

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var SurveyFormCreate = function SurveyFormCreate() {
    _classCallCheck(this, SurveyFormCreate);

    $("#survey-form").submit(function () {
        $("#survey-schema").val($("#render").val());
    });
};

exports.default = SurveyFormCreate;

},{}],10:[function(require,module,exports){
"use strict";

Object.defineProperty(exports, "__esModule", {
    value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _FormUtils = require("./FormUtils");

var _FormUtils2 = _interopRequireDefault(_FormUtils);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var DateSelectionBuilder = function () {
    function DateSelectionBuilder(yearElem, monthElem, dayElem) {
        _classCallCheck(this, DateSelectionBuilder);

        this.yearElem = yearElem;
        this.monthElem = monthElem;
        this.dayElem = dayElem;

        var yearElem_default = this.yearElem.find("option").first();
        for (var i = 1900; i <= new Date().getFullYear(); i++) {
            yearElem_default.after("<option value=\"" + i + "\">" + i + "</option>");
        }

        var month = ["มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน", "พฤษภาคม", "มิถุนายน", "กรกฎาคม", "สิงหาคม", "กันยายน", "ตุลาคม", "พฤศจิกายน", "ธันวาคม"];
        var monthElem_default = this.monthElem.find("option").first();
        for (var _i = 11; _i >= 0; _i--) {
            monthElem_default.after("<option value=\"" + (_i + 1) + "\">" + month[_i] + "</option>");
        }

        this.yearElem.change(this.doRefreshForm());
        this.monthElem.change(this.doRefreshForm());

        if (_FormUtils2.default.getDefaultVal(this.yearElem) != null && _FormUtils2.default.getDefaultVal(this.monthElem) != null && _FormUtils2.default.getDefaultVal(this.dayElem) != null) {
            _FormUtils2.default.setValToDefault(this.yearElem);
            _FormUtils2.default.setValToDefault(this.monthElem);
            this.doRefreshForm();
            _FormUtils2.default.setValToDefault(this.dayElem);
        }
    }

    _createClass(DateSelectionBuilder, [{
        key: "doRefreshForm",
        value: function doRefreshForm() {
            var yData = this.yearElem.val();
            var mData = this.monthElem.val();

            this.dayElem.html("<option value=\"null\">Day</option>");
            var defaultDay = this.dayElem.find("option").first();

            if ((yData != null || yData != "null") && (mData != null || mData != "null")) {
                for (var i = mData == 2 ? yData % 4 == 0 && yData % 100 != 0 || yData % 400 == 0 ? 29 : 28 : $.inArray(mData, [1, 3, 5, 7, 8, 10, 12]) ? 31 : 30; i >= 1; i--) {
                    $("<option value='" + i + "'>" + i + "</option>").insertAfter(defaultDay);
                }
            }
        }
    }]);

    return DateSelectionBuilder;
}();

exports.default = DateSelectionBuilder;

},{"./FormUtils":11}],11:[function(require,module,exports){
"use strict";

Object.defineProperty(exports, "__esModule", {
    value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var FormUtils = function () {
    function FormUtils() {
        _classCallCheck(this, FormUtils);
    }

    _createClass(FormUtils, null, [{
        key: "disableAll",
        value: function disableAll(formElem) {
            $.each(FormUtils.queryFormElems(formElem), function (key, data) {
                $.each(data, function (i, data) {
                    if ($(data).attr("type") == "hidden") return;
                    $(data).attr("disabled", true);
                });
            });
        }
    }, {
        key: "enableAllWithException",
        value: function enableAllWithException(formElem) {
            $.each(FormUtils.queryFormElems(formElem), function (key, data) {
                $.each(data, function (i, data) {
                    if ($(data).attr("type") == "hidden") return;
                    $(data).removeAttr("disabled");
                    if ($(data).data("lock") != null && $(data).data("lock") == true) {
                        $(data).attr("readonly", true);
                    }
                });
            });
        }
    }, {
        key: "enableAllBypassException",
        value: function enableAllBypassException(formElem) {
            $.each(FormUtils.queryFormElems(formElem), function (key, data) {
                $.each(data, function (i, data) {
                    if ($(data).attr("type") == "hidden") return;
                    $(data).removeAttr("disabled");
                });
            });
        }
    }, {
        key: "isFormComplete",
        value: function isFormComplete(formElem) {
            var flag = true;

            $.each(FormUtils.queryFormElems(formElem), function (key, data) {
                $.each(data, function (i, data) {
                    if ($(data).attr("type") != null && $(data).attr("type") == "hidden") return;
                    if ($(data).data("empty") != null && $(data).data("empty") == false && $(data).val().trim() == "") {
                        var parent = $(data).parent();
                        if (parent.is(".input-group")) {
                            parent.parent().parent().addClass("has-error");
                        } else {
                            parent.parent().addClass("has-error");
                        }
                        flag = false;
                    }
                });
            });

            return flag;
        }
    }, {
        key: "queryFormElems",
        value: function queryFormElems(formElem) {
            return { inputs: formElem.find("input"), selects: formElem.find("select"), textareas: formElem.find("textarea") };
        }
    }, {
        key: "getDefaultVal",
        value: function getDefaultVal(inputElem) {
            return inputElem.data("default");
        }
    }, {
        key: "setValToDefault",
        value: function setValToDefault(inputElem) {
            var defVal = void 0;
            if ((defVal = FormUtils.getDefaultVal(inputElem)) != null && defVal != "null") {
                inputElem.val(defVal);
            }
        }
    }, {
        key: "bindNotEmptyForm",
        value: function bindNotEmptyForm(formElem) {
            $.each(FormUtils.queryFormElems(formElem), function (key, data) {
                $.each(data, function (i, data) {
                    if ($(data).data("empty") != null && !$(data).data("empty")) {
                        $(data).blur(FormUtils.updateNotEmptyInput);
                    }
                });
            });
        }
    }, {
        key: "updateNotEmptyInput",
        value: function updateNotEmptyInput() {
            var elemGroup = $(this).parent().parent();
            if ($(this).val().trim() == "") {
                elemGroup.addClass("has-error");
            } else {
                elemGroup.removeClass("has-error");
            }
        }
    }]);

    return FormUtils;
}();

exports.default = FormUtils;

},{}]},{},[1])


//# sourceMappingURL=script.js.map
