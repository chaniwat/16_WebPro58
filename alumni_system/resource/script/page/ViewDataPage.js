export default class ViewDataPage {

    constructor() {
        this.alumnitable = $("#alumni-table");
        this.searchform = $("#table-searchform");

        if(this.alumnitable.length > 0) {
            this.datatable = this.alumnitable.DataTable();
            
            let searchtype = this.searchform.find("#table-searchtype");
            let searchinput = this.searchform.find("#table-searchinput");

            searchinput.keyup(() => {
                this.datatable.columns(searchtype.val()).search(searchinput.val()).draw();
            });

            searchtype.change(() => {
                searchinput.trigger('keyup');
            });
            
            $("#alumni-table_filter").html(this.searchform);
        }
    }

}