export default class Route {

    constructor(contextURL) {
        this.contextURL = contextURL[0] == "/" ? contextURL.substring(1) : contextURL;

        this.routeMapping = [];
    }

    doRoute (path, fn) {
        let temp = [];
        let fixPath = (path) => path[path.length - 1] != "/" && path[path.length - 1] != "*" ? path + "/" : path;

        if(path instanceof Array) {
            for(let i = 0; i < path.length; i++) {
                temp.push(fixPath(path[i]))
            }
        } else temp = fixPath(path);

        this.routeMapping.push({path : temp, fn : fn});
    }

    execute() {
        let currentPath = this.getCurrentPath(this.contextURL);
        let match = [];

        let isMatchToCurrentPath = (path) => {
            if(currentPath.length == 0) return !!(path == "" || path == "*");
            if(currentPath.length == path.length && currentPath == path) return true;

            let c;
            for(c = 0; c < path.length; c++)
                if(c < currentPath.length && path[c] != currentPath[c])
                    return !!(c == path.length - 1 && path[c - 1] + path[c] == "/*");
                else if(c >= currentPath.length) break;

            if(currentPath.length - 1 > c) return false;
            else if(path.length > currentPath.length)
                return !!(c == path.length - 1 && path[c - 1] + path[c] == "/*");
            else return true;
        };

        for(let i = 0; i < this.routeMapping.length; i++) {
            let currentRoute = this.routeMapping[i];

            if(currentRoute.path instanceof Array) {
                for(let j = 0; j < currentRoute.path.length; j++)
                    if(isMatchToCurrentPath(currentRoute.path[j])) match.push(currentRoute.path[j]);
            } else {
                if(isMatchToCurrentPath(currentRoute.path)) match.push(currentRoute.path);
            }
        }

        let actionRoute = match.sort().reverse()[0];

        for(let i = 0; i < this.routeMapping.length; i++) {
            let currentRoute = this.routeMapping[i];

            if(currentRoute.path instanceof Array) {
                for(let j = 0; j < currentRoute.path.length; j++) {
                    if(currentRoute.path[j] == actionRoute) {
                        console.log("do route : " + currentRoute.path);
                        currentRoute.fn();
                        return;
                    }
                }
            } else {
                if(currentRoute.path == actionRoute) {
                    console.log("do route : " + currentRoute.path);
                    currentRoute.fn();
                    return;
                }
            }
        }
    }

    getCurrentPath(contextURL) {
        let currentPath = window.location.pathname[window.location.pathname.length - 1] != "/" ? window.location.pathname + "/" : window.location.pathname;
        let split = currentPath.split("/");
        let a = [];

        for(var i = split.length - 1; i >= 0; i--) {
            if(split[i] == contextURL) break;
            a.push(split[i]);
        }

        return a.reverse().join("/");
    }

}
