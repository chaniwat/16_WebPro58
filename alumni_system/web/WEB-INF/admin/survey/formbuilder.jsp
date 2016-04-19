<%@ page import="model.utility.RouteUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Bootstrap Form Builder</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
        <%--<link href="assets/css/lib/bootstrap-theme.min.css" rel="stylesheet">--%>
        <link href="<%= RouteUtils.generateURL(request, "assets/css/bootstrap-form-builder.css") %>" rel="stylesheet">
        <!--[if lt IE 9]>
        <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
    </head>

    <body>

        <nav class="navbar navbar-fixed-top navbar-inverse">
            <div class="container">
                <div class="navbar-header">
                    <a class="navbar-brand" href="#">Bootstrap Form Builder</a>
                </div>
            </div>
        </nav>

        <div class="container">
            <div class="row clearfix">
                <!-- Building Form. -->
                <div class="col-md-6">
                    <div class="clearfix">
                        <h2>Your Form</h2>
                        <hr>
                        <div id="build">
                            <form id="target" class="form-horizontal">
                            </form>
                        </div>
                    </div>
                </div>
                <!-- / Building Form. -->

                <!-- Components -->
                <div class="col-md-6">
                    <h2>Drag & Drop components</h2>
                    <hr>
                    <ul class="nav nav-tabs" id="formtabs">
                        <!-- Tab nav -->
                    </ul>
                    <form class="form-horizontal" id="components">
                        <fieldset>
                            <div class="tab-content">
                                <!-- Tabs of snippets go here -->
                            </div>
                        </fieldset>
                    </form>
                </div>
                <!-- / Components -->

            </div>

            <div class="row clearfix">
                <div class="col-md-12">
                    <hr>
                    Created By Adam Moore (<a href="http://twitter.com/minikomi" >@minikomi</a>).<br/>
                    Modified By Chaniwat Seangchai (<a href="http://twitter.com/meranotemajimo" >@meranotemajimo</a>).<br/>
                    Source on (<a href="https://github.com/chaniwat/Bootstrap-Form-Builder" >Github</a>).
                </div>
            </div>

        </div> <!-- /container -->

        <script data-main="<%= RouteUtils.generateURL(request, "assets/js/bootstrap-form-builder.min.js") %>" src="<%= RouteUtils.generateURL(request, "assets/js/lib/require.js") %>" ></script>
    </body>
</html>
