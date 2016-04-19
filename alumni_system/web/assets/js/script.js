(function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);var f=new Error("Cannot find module '"+o+"'");throw f.code="MODULE_NOT_FOUND",f}var l=n[o]={exports:{}};t[o][0].call(l.exports,function(e){var n=t[o][1][e];return s(n?n:e)},l,l.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s})({1:[function(require,module,exports){
"use strict";

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _Route = require("./Route");

var _Route2 = _interopRequireDefault(_Route);

var _LoginPage = require("./page/LoginPage");

var _LoginPage2 = _interopRequireDefault(_LoginPage);

var _ProfilePage = require("./page/ProfilePage");

var _ProfilePage2 = _interopRequireDefault(_ProfilePage);

var _AlumniViewPage = require("./page/AlumniViewPage");

var _AlumniViewPage2 = _interopRequireDefault(_AlumniViewPage);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var Main = function () {
    function Main() {
        _classCallCheck(this, Main);

        var siteDataContextElem = $("sitedata");
        var contextPath = siteDataContextElem.attr("contextPath");

        this.route(contextPath);

        siteDataContextElem.remove();
    }

    _createClass(Main, [{
        key: "route",
        value: function route(contextPath) {
            var route = new _Route2.default(contextPath);

            route.doRoute("login/", function () {
                new _LoginPage2.default();
            });

            route.doRoute(["profile/*"], function () {
                new _ProfilePage2.default();
            });

            route.doRoute(["alumni/*"], function () {
                new _AlumniViewPage2.default();
            });

            route.execute();
        }
    }]);

    return Main;
}();

new Main();

},{"./Route":2,"./page/AlumniViewPage":3,"./page/LoginPage":4,"./page/ProfilePage":5}],2:[function(require,module,exports){
"use strict";

Object.defineProperty(exports, "__esModule", {
    value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _RouteUtils = require("./ultility/RouteUtils");

var _RouteUtils2 = _interopRequireDefault(_RouteUtils);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var _class = function () {
    function _class(contextPath) {
        _classCallCheck(this, _class);

        this.contextPath = contextPath[0] == "/" ? contextPath.substring(1) : contextPath;
        this.currentPath = _RouteUtils2.default.getCurrentPath(this.contextPath);
        this.routeMapping = [];

        console.log(this.contextPath);
        console.log(this.currentPath);
    }

    _createClass(_class, [{
        key: "doRoute",
        value: function doRoute(path, fn) {
            var temp;
            var fixPath = function fixPath(path) {
                return path[path.length - 1] != "/" && path[path.length - 1] != "*" ? path + "/" : path;
            };

            if (path instanceof Array) {
                temp = [];
                for (var i = 0; i < path.length; i++) {
                    temp.push(fixPath(path[i]));
                }
            } else temp = fixPath(path);

            this.routeMapping.push({ path: temp, fn: fn });
        }
    }, {
        key: "execute",
        value: function execute() {
            var match = [];

            var isMatchToCurrentPath = function isMatchToCurrentPath(path, currentpath) {
                if (currentpath.length == 0) if (path == "" || path == "*") return true;else return false;
                if (currentpath.length == path.length && currentpath == path) return true;

                var c = 0;
                for (c; c < path.length; c++) {
                    if (c < currentpath.length && path[c] != currentpath[c]) {
                        if (c == path.length - 1 && path[c - 1] + path[c] == "/*") return true;else return false;
                    } else if (c >= currentpath.length) break;
                }if (currentpath.length - 1 > c) return false;else if (path.length > currentpath.length) {
                    if (c == path.length - 1 && path[c - 1] + path[c] == "/*") return true;else return false;
                } else return true;
            };

            for (var i = 0; i < this.routeMapping.length; i++) {
                var currentRoute = this.routeMapping[i];

                if (currentRoute.path instanceof Array) {
                    for (var j = 0; j < currentRoute.path.length; j++) {
                        if (isMatchToCurrentPath(currentRoute.path[j], this.currentPath)) match.push(currentRoute.path[j]);
                    }
                } else {
                    if (isMatchToCurrentPath(currentRoute.path, this.currentPath)) match.push(currentRoute.path);
                }
            }

            var actionroute = match.sort().reverse()[0];
            console.log(match);

            for (var i = 0; i < this.routeMapping.length; i++) {
                var currentRoute = this.routeMapping[i];

                if (currentRoute.path instanceof Array) {
                    for (var j = 0; j < currentRoute.path.length; j++) {
                        if (currentRoute.path[j] == actionroute) {
                            console.log("do route : " + currentRoute.path);
                            currentRoute.fn();
                            return;
                        }
                    }
                } else {
                    if (currentRoute.path == actionroute) {
                        console.log("do route : " + currentRoute.path);
                        currentRoute.fn();
                        return;
                    }
                }
            }
        }
    }]);

    return _class;
}();

exports.default = _class;

},{"./ultility/RouteUtils":8}],3:[function(require,module,exports){
"use strict";

Object.defineProperty(exports, "__esModule", {
    value: true
});

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var _class = function _class() {
    _classCallCheck(this, _class);

    this.alumnitable = $("#alumni-table");

    if (this.alumnitable.length > 0) {
        this.alumnitable.DataTable();
    }
};

exports.default = _class;

},{}],4:[function(require,module,exports){
"use strict";

Object.defineProperty(exports, "__esModule", {
    value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _FormUtils = require("./../ultility/FormUtils");

var _FormUtils2 = _interopRequireDefault(_FormUtils);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var _class = function () {
    function _class() {
        _classCallCheck(this, _class);

        this.loginForm = $("form#login-form");

        _FormUtils2.default.bindNotEmptyForm(this.loginForm);
        this.loginForm.submit(this, this.checkSubmitForm);
    }

    _createClass(_class, [{
        key: "checkSubmitForm",
        value: function checkSubmitForm(e) {
            var o = e.data;
            var showErrorfn = function showErrorfn() {
                $("<div class=\"alert alert-danger uncomplete-field\" role=\"alert\">โปรดกรอกข้อมูลให้ครบ</div>").insertAfter(".login-page h1");
                $(".login-page div.alert.uncomplete-field").hide().slideDown();
            };

            if (!_FormUtils2.default.isFormComplete(o.loginForm)) {
                e.preventDefault();
                if ($(".login-page div.alert").not(".uncomplete-field").length) {
                    $(".login-page div.alert").slideUp(function () {
                        $(".login-page div.alert").remove();
                        showErrorfn();
                    });
                } else if (!$(".login-page div.alert.uncomplete-field").length) {
                    showErrorfn();
                }
            }
        }
    }]);

    return _class;
}();

exports.default = _class;

},{"./../ultility/FormUtils":7}],5:[function(require,module,exports){
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

var _class = function () {
    function _class() {
        _classCallCheck(this, _class);

        this.usertype = $("#profilepage-usertype").val();

        if (this.usertype == "ALUMNI") {
            this.profileform = $("#alumni-form");
            this.profileformbtn = this.profileform.find("button#alumni-form-btn");

            new _DateSelectionBuilder2.default(this.profileform.find("#alumni-form-birthdate-year"), this.profileform.find("#alumni-form-birthdate-month"), this.profileform.find("#alumni-form-birthdate-day"));

            _FormUtils2.default.setValToDefault(this.profileform.find("#alumni-form-province"));
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

    _createClass(_class, [{
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
                    $("<span class=\"submit-alert\">โปรดกรอกข้อมูลที่ต้องการให้ครบ</span>").insertAfter(o.profileformbtn);
                }
            }
        }
    }]);

    return _class;
}();

exports.default = _class;

},{"./../ultility/DateSelectionBuilder":6,"./../ultility/FormUtils":7}],6:[function(require,module,exports){
"use strict";

Object.defineProperty(exports, "__esModule", {
    value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _FormUtils = require("./FormUtils");

var _FormUtils2 = _interopRequireDefault(_FormUtils);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var _class = function () {
    function _class(yearElem, monthElem, dayElem) {
        _classCallCheck(this, _class);

        this.yearElem = yearElem;
        this.monthElem = monthElem;
        this.dayElem = dayElem;

        var yearElem_default = this.yearElem.find("option").first();
        for (var i = 1900; i <= new Date().getFullYear(); i++) {
            yearElem_default.after("<option value=\"" + i + "\">" + i + "</option>");
        }

        var month = ["มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน", "พฤษภาคม", "มิถุนายน", "กรกฎาคม", "สิงหาคม", "กันยายน", "ตุลาคม", "พฤศจิกายน", "ธันวาคม"];
        var monthElem_default = this.monthElem.find("option").first();
        for (var i = 11; i >= 0; i--) {
            monthElem_default.after("<option value=\"" + (i + 1) + "\">" + month[i] + "</option>");
        }

        this.yearElem.change(this, this.refreshForm);
        this.monthElem.change(this, this.refreshForm);

        if (_FormUtils2.default.getDefaultVal(this.yearElem) != null && _FormUtils2.default.getDefaultVal(this.monthElem) != null && _FormUtils2.default.getDefaultVal(this.dayElem) != null) {
            _FormUtils2.default.setValToDefault(this.yearElem);
            _FormUtils2.default.setValToDefault(this.monthElem);

            this.doRefreshForm(this);

            _FormUtils2.default.setValToDefault(this.dayElem);
        }
    }

    _createClass(_class, [{
        key: "refreshForm",
        value: function refreshForm(e) {
            var o = e.data;

            o.doRefreshForm(o);
        }
    }, {
        key: "doRefreshForm",
        value: function doRefreshForm(o) {
            var yData = o.yearElem.val();
            var mData = o.monthElem.val();

            o.dayElem.html("<option value=\"null\">Day</option>");
            var defaultday = o.dayElem.find("option").first();

            if ((yData != null || yData != "null") && (mData != null || mData != "null")) {
                for (var i = mData == 2 ? yData % 4 == 0 && yData % 100 != 0 || yData % 400 == 0 ? 29 : 28 : $.inArray(mData, [1, 3, 5, 7, 8, 10, 12]) ? 31 : 30; i >= 1; i--) {
                    $("<option value='" + i + "'>" + i + "</option>").insertAfter(defaultday);
                }
            }
        }
    }]);

    return _class;
}();

exports.default = _class;

},{"./FormUtils":7}],7:[function(require,module,exports){
"use strict";

Object.defineProperty(exports, "__esModule", {
    value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var _class = function () {
    function _class() {
        _classCallCheck(this, _class);
    }

    _createClass(_class, null, [{
        key: "disableAll",
        value: function disableAll(formElem) {
            var elems = this.queryFormElems(formElem);

            elems.inputs.each(function () {
                if ($(this).attr("type") == "hidden") return;
                $(this).attr("readonly", "true");
            });

            elems.selects.each(function () {
                $(this).attr("disabled", "true");
            });

            elems.textareas.each(function () {
                $(this).attr("readonly", "true");
            });
        }
    }, {
        key: "enableAllWithException",
        value: function enableAllWithException(formElem) {
            var elems = this.queryFormElems(formElem);

            elems.inputs.each(function () {
                if ($(this).attr("type") == "hidden") return;
                if (!($(this).data("lock") != null && $(this).data("lock") == true)) {
                    $(this).removeAttr("readonly");
                }
            });

            elems.selects.each(function () {
                if (!($(this).data("lock") != null && $(this).data("lock") == true)) {
                    $(this).removeAttr("disabled");
                }
            });

            elems.textareas.each(function () {
                if (!($(this).data("lock") != null && $(this).data("lock") == true)) {
                    $(this).removeAttr("readonly");
                }
            });
        }
    }, {
        key: "enableAllBypassException",
        value: function enableAllBypassException(formElem) {
            var elems = this.queryFormElems(formElem);

            elems.inputs.each(function () {
                if ($(this).attr("type") == "hidden") return;
                $(this).removeAttr("readonly");
            });

            elems.selects.each(function () {
                $(this).removeAttr("disabled");
            });

            elems.textareas.each(function () {
                $(this).removeAttr("readonly");
            });
        }
    }, {
        key: "isFormComplete",
        value: function isFormComplete(formElem) {
            var elems = this.queryFormElems(formElem);
            var flag = true;
            var checkfn = function checkfn() {
                if ($(this).attr("type") != null && $(this).attr("type") == "hidden") return;
                if ($(this).data("empty") != null && $(this).data("empty") == false && $(this).val().trim() == "") {
                    $(this).parent().parent().addClass("has-error");
                    flag = false;
                }
            };

            elems.inputs.each(checkfn);
            elems.selects.each(checkfn);
            elems.textareas.each(checkfn);

            return flag;
        }
    }, {
        key: "queryFormElems",
        value: function queryFormElems(formElem) {
            return { inputs: formElem.find("input"), selects: formElem.find("select"), textareas: formElem.find("textarea") };
        }
    }, {
        key: "getDefaultVal",
        value: function getDefaultVal(elem) {
            return elem.data("default");
        }
    }, {
        key: "setValToDefault",
        value: function setValToDefault(elem) {
            var defVal = this.getDefaultVal(elem);
            if (defVal != null && defVal != "null") {
                elem.val(defVal);
            }
        }
    }, {
        key: "bindNotEmptyForm",
        value: function bindNotEmptyForm(formElem) {
            var notEmptyElems = [];
            $("input[data-empty=false]").each(function () {
                notEmptyElems.push($(this));
            });

            for (var i = 0; i < notEmptyElems.length; i++) {
                notEmptyElems[i].blur(this, this.updateNotEmptyInput);
            }
        }
    }, {
        key: "updateNotEmptyInput",
        value: function updateNotEmptyInput(e) {
            var elemGroup = $(this).parent().parent();
            if ($(this).val().trim() == "") {
                elemGroup.addClass("has-error");
            } else {
                elemGroup.removeClass("has-error");
            }
        }
    }]);

    return _class;
}();

exports.default = _class;

},{}],8:[function(require,module,exports){
"use strict";

Object.defineProperty(exports, "__esModule", {
    value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var _class = function () {
    function _class() {
        _classCallCheck(this, _class);
    }

    _createClass(_class, null, [{
        key: "getCurrentPath",
        value: function getCurrentPath(contextPath) {
            var currentpath = window.location.pathname[window.location.pathname.length - 1] != "/" ? window.location.pathname + "/" : window.location.pathname;
            var split = currentpath.split("/");
            var a = [];

            for (var i = split.length - 1; i >= 0; i--) {
                if (split[i] == contextPath) {
                    break;
                }
                a.push(split[i]);
            }

            a.reverse();
            return a.join("/");
        }
    }]);

    return _class;
}();

exports.default = _class;

},{}]},{},[1])


//# sourceMappingURL=script.js.map
