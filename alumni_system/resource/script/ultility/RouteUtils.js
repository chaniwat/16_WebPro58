export default class {

    static getCurrentPath(contextPath) {
        var currentpath = window.location.pathname[window.location.pathname.length - 1] != "/" ? window.location.pathname + "/" : window.location.pathname;
        var split = currentpath.split("/");
        var a = [];

        for(var i = split.length - 1; i >= 0; i--) {
            if(split[i] == contextPath) {
                break;
            }
            a.push(split[i]);
        }

        a.reverse();
        return a.join("/");
    }

}