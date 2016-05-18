<%@ include file="/WEB-INF/importlib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<template:page>

    <template:head title="Alumni System">

        <template:style />

    </template:head>

    <template:body>

        <template:navbar />

        <div class="jumbotron">
            <div class="container">
                <h1>ยินดีต้อนรับ</h1>
                <p>ระบบศิษย์เก่า คณะเทคโนโลยีสารสนเทศ สถาบันเทคโนโลยีพระจอมเกล้าเจ้าคุณทหารลาดกระบัง</p>
                <c:if test="${Authorization:isLogin()}">
                    <p><a class="btn btn-success btn-lg" href="<%= RouteHelper.generateURL("profile") %>" role="button">จัดการโปรไฟล์ »</a></p>
                </c:if>
                <c:if test="${!Authorization:isLogin()}">
                    <p><a class="btn btn-primary btn-lg" href="<%= RouteHelper.generateURL("login") %>" role="button">เข้าสู่ระบบศิษย์เก่า »</a></p>
                </c:if>
            </div>
        </div>

        <div class="container">

            <div class="row">
                <div class="col-md-12">
                    <div id="carousel-example-captions" class="carousel slide" data-ride="carousel">
                        <ol class="carousel-indicators">
                            <li data-target="#carousel-example-captions" data-slide-to="0" class=""></li>
                            <li data-target="#carousel-example-captions" data-slide-to="1" class="active"></li>
                            <li data-target="#carousel-example-captions" data-slide-to="2" class=""></li>
                        </ol>
                        <div class="carousel-inner" role="listbox">
                            <div class="item">
                                <img src="holder.js/1200x500?bg=#777" alt="1200x500" data-holder-rendered="true">
                                <div class="carousel-caption">
                                    <h3>First slide label</h3>
                                    <p>Nulla vitae elit libero, a pharetra augue mollis interdum.</p>
                                </div>
                            </div>
                            <div class="item active">
                                <img src="holder.js/1200x500?bg=#666" alt="1200x500" data-holder-rendered="true">
                                <div class="carousel-caption">
                                    <h3>Second slide label</h3>
                                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                                </div>
                            </div>
                            <div class="item">
                                <img src="holder.js/1200x500?bg=#555" alt="1200x500" data-holder-rendered="true">
                                <div class="carousel-caption">
                                    <h3>Third slide label</h3>
                                    <p>Praesent commodo cursus magna, vel scelerisque nisl consectetur.</p>
                                </div>
                            </div>
                        </div>
                        <a class="left carousel-control" href="#carousel-example-captions" role="button" data-slide="prev">
                            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                            <span class="sr-only">Previous</span>
                        </a>
                        <a class="right carousel-control" href="#carousel-example-captions" role="button" data-slide="next">
                            <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                            <span class="sr-only">Next</span>
                        </a>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <h2>Heading</h2>
                    <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
                    <p><a class="btn btn-default" href="#" role="button">View details »</a></p>
                </div>
                <div class="col-md-6">
                    <h2>Heading</h2>
                    <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
                    <p><a class="btn btn-default" href="#" role="button">View details »</a></p>
                </div>
            </div>

            <template:footer/>

        </div>

        <template:script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/holder/2.9.3/holder.min.js"></script>
        </template:script>

    </template:body>

</template:page>