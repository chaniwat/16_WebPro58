import FormUtils from "./FormUtils";

export default class DateSelectionBuilder {

    constructor(yearElem, monthElem, dayElem) {
        this.yearElem = yearElem;
        this.monthElem = monthElem;
        this.dayElem = dayElem;
    
        let yearElem_default = this.yearElem.find("option").first();
        for(let i = 1900; i <= new Date().getFullYear(); i++) {
            yearElem_default.after("<option value=\"" + i + "\">" + i +"</option>");
        }
    
        let month = ["มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน", "พฤษภาคม", "มิถุนายน", "กรกฎาคม", "สิงหาคม", "กันยายน", "ตุลาคม", "พฤศจิกายน", "ธันวาคม"];
        let monthElem_default = this.monthElem.find("option").first();
        for(let i = 11; i >= 0; i--) {
            monthElem_default.after("<option value=\"" + (i + 1) + "\">" + month[i] + "</option>")
        }
    
        this.yearElem.change(this.doRefreshForm());
        this.monthElem.change(this.doRefreshForm());
    
        if(FormUtils.getDefaultVal(this.yearElem) != null && FormUtils.getDefaultVal(this.monthElem) != null && FormUtils.getDefaultVal(this.dayElem) != null) {
            FormUtils.setValToDefault(this.yearElem);
            FormUtils.setValToDefault(this.monthElem);
            this.doRefreshForm();
            FormUtils.setValToDefault(this.dayElem);
        }
    }
    
    doRefreshForm() {
        var yData = this.yearElem.val();
        var mData = this.monthElem.val();
    
        this.dayElem.html("<option value=\"null\">Day</option>");
        let defaultDay = this.dayElem.find("option").first();
    
        if((yData != null || yData != "null") && (mData != null || mData != "null")) {
            for(var i = mData == 2 ?
                ((yData % 4 == 0 && yData % 100 != 0) || yData % 400 == 0 ? 29 : 28) :
                ($.inArray(mData, [1, 3, 5, 7, 8, 10, 12]) ? 31 : 30); i >= 1; i--) {
                $("<option value='"+ i +"'>"+ i +"</option>").insertAfter(defaultDay);
            }
        }
    }

}