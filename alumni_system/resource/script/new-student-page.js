"use strict";

class NewStudentPage {

    constructor(newStudentForm) {
        this.newStudentForm = newStudentForm;
        this.year = this.newStudentForm.find("div.birthdate-group select.birthdate-year");
        this.month = this.newStudentForm.find("div.birthdate-group select.birthdate-month");
        this.day = this.newStudentForm.find("div.birthdate-group select.birthdate-day");

        this.year.change(this, this.updateBirthdayGroupForm);
        this.month.change(this, this.updateBirthdayGroupForm);
    }

    updateBirthdayGroupForm(e) {
        var o = e.data;
        if(o.year.val() != null && o.month.val() != null) {
            o.day.html(
                "<option class=\"default\" value=\"\" disabled selected>DD</option>"
            );
            for(var i = o.month.val() == 2 ?
                ((o.year.val() % 4 == 0 && o.year.val() % 100 != 0) || o.year.val() % 400 == 0 ? 29 : 28) :
                ($.inArray(o.month.val(), [1, 3, 5, 7, 8, 10, 12]) ? 31 : 30);
                i >= 1; i--) {
                $("<option value='"+ i +"'>"+ i +"</option>").insertAfter(o.day.find("option.default"));
            }
        }
    }

}