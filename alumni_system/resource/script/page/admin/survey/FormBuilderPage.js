import RouteUtils from "./../../../ultility/RouteUtils";

export default class {

    constructor(contextPath) {
        this.contextPath = contextPath[0] == "/" ? contextPath.substring(1) : contextPath;

        $("<link href=\"/" + this.contextPath + "/assets/css/bootstrap-form-builder.css\" rel=\"stylesheet\">").insertAfter("link:last");
        $("<script data-main=\"/" + this.contextPath + "/assets/js/bootstrap-form-builder.min.js\" src=\"/" + this.contextPath + "/assets/js/lib/require.js\"></script>").insertAfter("script:last");
    }

}
