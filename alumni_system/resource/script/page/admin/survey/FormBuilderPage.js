export default class {

    constructor(contextURL) {
        this.contextURL = contextURL[0] == "/" ? contextURL.substring(1) : contextURL;

        $("<link href=\"/" + this.contextURL + "/assets/css/bootstrap-form-builder.css\" rel=\"stylesheet\">").insertAfter("link:last");
        $("<script data-main=\"/" + this.contextURL + "/assets/js/bootstrap-form-builder.min.js\" src=\"/" + this.contextURL + "/assets/js/lib/require.js\"></script>").insertAfter("script:last");
    }

}
