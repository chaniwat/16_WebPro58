<%@ page import="java.util.Calendar" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Alumni System - New Student</title>

    <%@ include file="/WEB-INF/layouts/header.jsp"%>
</head>

<body class="new-student-page">
    <div class="container">
        <center><h1>Add new student</h1></center>

        <form action="<%= RouteHelper.generateURL(request, "student/new") %>" method="POST" id="new-student-form">
            <div class="form-group stuid-group">
                <div class="input-group">
                    <span class="input-group-addon" id="stu-id-addon">
                        <span class="fa fa-user"></span>
                    </span>
                    <input type="text" class="form-control" id="stu-id" name="stu-id" placeholder="Student ID" aria-describedby="stu-id-addon" />
                </div>
            </div>

            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon" id="stu-name-th-addon">
                        <span class="fa fa-user"></span>
                    </span>
                    <input type="text" class="form-control" id="stu-name-th" name="stu-name-th" placeholder="Name in Thai" aria-describedby="stu-name-th-addon" />
                </div>
            </div>

            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon" id="stu-surname-th-addon">
                        <span class="fa fa-user"></span>
                    </span>
                    <input type="text" class="form-control" id="stu-surname-th" name="stu-surname-th" placeholder="Surname in Thai" aria-describedby="stu-surname-th-addon" />
                </div>
            </div>

            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon" id="stu-name-en-addon">
                        <span class="fa fa-user"></span>
                    </span>
                    <input type="text" class="form-control" id="stu-name-en" name="stu-name-en" placeholder="Name in English" aria-describedby="stu-name-en-addon" />
                </div>
            </div>

            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon" id="stu-surname-en-addon">
                        <span class="fa fa-user"></span>
                    </span>
                    <input type="text" class="form-control" id="stu-surname-en" name="stu-surname-en" placeholder="Surname in English" aria-describedby="stu-surname-en-addon" />
                </div>
            </div>

            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon" id="stu-nickname-addon">
                        <span class="fa fa-user"></span>
                    </span>
                    <input type="text" class="form-control" id="stu-nickname" name="stu-nickname" placeholder="Nickname" aria-describedby="stu-nickname-addon" />
                </div>
            </div>

            <div class="form-group birthdate-group">
                <span class="col-md-2 control-label">Birthdate</span>
                <div class="col-md-10">
                    <div class="form-group row">
                        <div class="col-md-3">
                            <select class="form-control birthdate-year">
                                <option class="default" value="" disabled selected>YYYY</option>
                                <% for(int i = Calendar.getInstance().get(Calendar.YEAR); i >= 1900; i--) { %>
                                <option value="<%= i %>"><%= i %></option>
                                <% } %>
                            </select>
                        </div>
                        <div class="col-md-1 seperator">
                            /
                        </div>
                        <div class="col-md-3">
                            <select class="form-control birthdate-month">
                                <option class="default" value="" disabled selected>MM</option>
                                <%
                                    String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
                                    for(int i = 0; i < 12; i++) {
                                %>
                                <option value="<%= i+1 %>"><%= months[i] %></option>
                                <% } %>
                            </select>
                        </div>
                        <div class="col-md-1 seperator">
                            /
                        </div>
                        <div class="col-md-3">
                            <select class="form-control birthdate-day">
                                <option class="default" value="" disabled selected>DD</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label for="stu-address">Address</label>
                <textarea class="form-control" id="stu-address" name="stu-address" rows="3" placeholder="Address"></textarea>
            </div>

            <div class="row">
                <div class="form-group col-md-3">
                    <div class="input-group">
                        <span class="input-group-addon" id="stu-amphure-addon">
                            <span class="fa fa-user"></span>
                        </span>
                        <input type="text" class="form-control" id="stu-amphure" name="stu-amphure" placeholder="Amphure" aria-describedby="stu-amphure-addon" />
                    </div>
                </div>
                <div class="form-group col-md-3">
                    <div class="input-group">
                        <span class="input-group-addon" id="stu-district-addon">
                            <span class="fa fa-user"></span>
                        </span>
                        <input type="text" class="form-control" id="stu-district" name="stu-district" placeholder="District" aria-describedby="stu-district-addon" />
                    </div>
                </div>
                <div class="form-group col-md-3">
                    <div class="input-group">
                        <span class="input-group-addon" id="stu-province-addon">
                            <span class="fa fa-user"></span>
                        </span>
                        <input type="text" class="form-control" id="stu-province" name="stu-province" placeholder="Province" aria-describedby="stu-province-addon" />
                    </div>
                </div>
                <div class="form-group col-md-3">
                    <div class="input-group">
                        <span class="input-group-addon" id="stu-zipcode-addon">
                            <span class="fa fa-user"></span>
                        </span>
                        <input type="text" class="form-control" id="stu-zipcode" name="stu-zipcode" placeholder="Zipcode" aria-describedby="stu-zipcode-addon" />
                    </div>
                </div>
            </div>

            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon" id="stu-email-addon">
                        <span class="fa fa-user"></span>
                    </span>
                    <input type="text" class="form-control" id="stu-email" name="stu-email" placeholder="Email" aria-describedby="stu-email-addon" />
                </div>
            </div>

            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon" id="stu-phone-addon">
                        <span class="fa fa-user"></span>
                    </span>
                    <input type="text" class="form-control" id="stu-phone" name="stu-phone" placeholder="Phone number" aria-describedby="stu-phone-addon" />
                </div>
            </div>

            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon" id="stu-occupation-addon">
                        <span class="fa fa-user"></span>
                    </span>
                    <input type="text" class="form-control" id="stu-occupation" name="stu-occupation" placeholder="Occupation" aria-describedby="stu-occupation-addon" />
                </div>
            </div>

            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon" id="stu-work-name-addon">
                        <span class="fa fa-user"></span>
                    </span>
                    <input type="text" class="form-control" id="stu-work-name" name="stu-work-name" placeholder="Work name" aria-describedby="stu-work-name-addon" />
                </div>
            </div>

            <%-- TODO Faculty and Major Selection --%>

            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon" id="stu-end-year-addon">
                        <span class="fa fa-user"></span>
                    </span>
                    <input type="text" class="form-control" id="stu-end-year" name="stu-end-year" placeholder="Graduate Year" aria-describedby="stu-end-year-addon" />
                </div>
            </div>

            <button type="button" class="btn btn-default">ADD</button>
        </form>
    </div>

    <%@ include file="/WEB-INF/layouts/script-all.jsp"%>
    <script>
        $(document).ready(function() {
           new NewStudentPage($("form#new-student-form"));
        });
    </script>
</body>
</html>