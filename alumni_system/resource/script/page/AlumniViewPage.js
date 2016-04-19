export default class {

    constructor() {
        this.alumnitable = $("#alumni-table");

        if(this.alumnitable.length > 0) {
            this.alumnitable.DataTable();
        }
    }

}
