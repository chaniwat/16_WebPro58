export default class FormUtils {

    static disableAll(formElem) {
        $.each(FormUtils.queryFormElems(formElem), (key, data) => {
            $.each(data, (i, data) => {
                if($(data).attr("type") == "hidden") return;
                $(data).attr("disabled", true);
            });
        });
    }

    static enableAllWithException(formElem) {
        $.each(FormUtils.queryFormElems(formElem), (key, data) => {
            $.each(data, (i, data) => {
                if($(data).attr("type") == "hidden") return;
                $(data).removeAttr("disabled");
                if($(data).data("lock") != null && $(data).data("lock") == true) {
                    $(data).attr("readonly", true);
                }
            });
        });
    }
    
    static enableAllBypassException(formElem) {
        $.each(FormUtils.queryFormElems(formElem), (key, data) => {
            $.each(data, (i, data) => {
                if($(data).attr("type") == "hidden") return;
                $(data).removeAttr("disabled");
            });
        });
    }

    static isFormComplete(formElem) {
        var flag = true;

        $.each(FormUtils.queryFormElems(formElem), (key, data) => {
            $.each(data, (i, data) => {
                if(($(data).attr("type") != null && $(data).attr("type") == "hidden")) return;
                if($(data).data("empty") != null && $(data).data("empty") == false && $(data).val().trim() == "") {
                    $(data).parent().parent().addClass("has-error");
                    flag = false;
                }
            });
        });

        return flag;
    }

    static queryFormElems(formElem) {
        return {inputs : formElem.find("input"), selects : formElem.find("select"), textareas : formElem.find("textarea")};
    }

    static getDefaultVal(inputElem) {
        return inputElem.data("default");
    }

    static setValToDefault(inputElem) {
        let defVal;
        if((defVal = FormUtils.getDefaultVal(inputElem)) != null && defVal != "null") {
            inputElem.val(defVal);
        }
    }

    static bindNotEmptyForm(formElem) {
        $.each(FormUtils.queryFormElems(formElem), (key, data) => {            
            $.each(data, (i, data) => {
                if($(data).data("empty") != null && !$(data).data("empty")) {
                    $(data).blur(FormUtils.updateNotEmptyInput);
                }
            });
        });
    }

    static updateNotEmptyInput() {
        let elemGroup = $(this).parent().parent();
        if($(this).val().trim() == "") {
            elemGroup.addClass("has-error");
        } else {
            elemGroup.removeClass("has-error");
        }
    }

}
