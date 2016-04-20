import RouteUtils from "./ultility/RouteUtils";

export default class {

    constructor(contextPath) {
        this.contextPath = contextPath[0] == "/" ? contextPath.substring(1) : contextPath;
        this.currentPath = RouteUtils.getCurrentPath(this.contextPath);
        this.routeMapping = [];

        console.log(this.contextPath);
        console.log(this.currentPath);
    }

    doRoute (path, fn) {
        var temp;
        var fixPath = function(path) {
            return path[path.length - 1] != "/" && path[path.length - 1] != "*" ? path + "/" : path;
        }

        if(path instanceof Array) {
            temp = [];
            for(var i = 0; i < path.length; i++) {
                temp.push(fixPath(path[i]))
            }
        } else temp = fixPath(path);

        this.routeMapping.push({path : temp, fn : fn});
    }

    execute() {
        var match = [];

        var isMatchToCurrentPath = function(path, currentpath) {
            if(currentpath.length == 0)
                if(path == "" || path == "*") return true;
                else return false;
            if(currentpath.length == path.length && currentpath == path) return true;

            var c = 0;
            for(c; c < path.length; c++)
                if(c < currentpath.length && path[c] != currentpath[c])
                    if(c == path.length - 1 && path[c - 1] + path[c] == "/*") return true;
                    else return false;
                else if(c >= currentpath.length) break;

            if(currentpath.length - 1 > c) return false;
            else if(path.length > currentpath.length)
                if(c == path.length - 1 && path[c - 1] + path[c] == "/*") return true;
                else return false;
            else return true;
        };

        for(var i = 0; i < this.routeMapping.length; i++) {
            var currentRoute = this.routeMapping[i];

            if(currentRoute.path instanceof Array) {
                for(var j = 0; j < currentRoute.path.length; j++)
                    if(isMatchToCurrentPath(currentRoute.path[j], this.currentPath)) match.push(currentRoute.path[j]);
            } else {
                if(isMatchToCurrentPath(currentRoute.path, this.currentPath)) match.push(currentRoute.path);
            }
        }

        var actionroute = match.sort().reverse()[0];
        console.log(match);

        for(var i = 0; i < this.routeMapping.length; i++) {
            var currentRoute = this.routeMapping[i];

            if(currentRoute.path instanceof Array) {
                for(var j = 0; j < currentRoute.path.length; j++) {
                    if(currentRoute.path[j] == actionroute) {
                        console.log("do route : " + currentRoute.path);
                        currentRoute.fn();
                        return;
                    }
                }
            } else {
                if(currentRoute.path == actionroute) {
                    console.log("do route : " + currentRoute.path);
                    currentRoute.fn();
                    return;
                }
            }
        }
    }

}
