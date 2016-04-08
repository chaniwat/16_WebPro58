export default class {

    static disableAll(formElem) {
        var elems = this.queryFormElems(formElem);

        elems.inputs.each(function() {
            if($(this).attr("type") == "hidden") return;
            $(this).attr("readonly", "true");
        });

        elems.selects.each(function() {
            $(this).attr("disabled", "true");
        });

        elems.textareas.each(function() {
            $(this).attr("readonly", "true");
        });
    }

    static enableAllWithException(formElem) {
        var elems = this.queryFormElems(formElem);

        elems.inputs.each(function() {
            if($(this).attr("type") == "hidden") return;
            if(!($(this).data("lock") != null && $(this).data("lock") == true)) {
                $(this).removeAttr("readonly");
            }
        });

        elems.selects.each(function() {
            if(!($(this).data("lock") != null && $(this).data("lock") == true)) {
                $(this).removeAttr("disabled");
            }
        });

        elems.textareas.each(function() {
            if(!($(this).data("lock") != null && $(this).data("lock") == true)) {
                $(this).removeAttr("readonly");
            }
        });
    }
    static enableAllBypassException(formElem) {
        var elems = this.queryFormElems(formElem);

        elems.inputs.each(function() {
            if($(this).attr("type") == "hidden") return;
            $(this).removeAttr("readonly");
        });

        elems.selects.each(function() {
            $(this).removeAttr("disabled");
        });

        elems.textareas.each(function() {
            $(this).removeAttr("readonly");
        });
    }

    static isFormComplete(formElem) {
        var elems = this.queryFormElems(formElem);
        var flag = true;
        var checkfn = function() {
            if($(this).attr("type") != null && $(this).attr("type") == "hidden") return;
            if($(this).data("empty") != null && $(this).data("empty") == false && $(this).val().trim() == "") {
                flag = false;
            }
        }

        elems.inputs.each(checkfn);
        elems.selects.each(checkfn);
        elems.textareas.each(checkfn);

        return flag;
    }

    static queryFormElems(formElem) {
        return {inputs : formElem.find("input"), selects : formElem.find("select"), textareas : formElem.find("textarea")};
    }

    static getDefaultVal(elem) {
        return elem.data("default");
    }

    static setValToDefault(elem) {
        var defVal = this.getDefaultVal(elem);
        if(defVal != null || defVal != "null") {
            elem.val(defVal);
        }
    }

}
