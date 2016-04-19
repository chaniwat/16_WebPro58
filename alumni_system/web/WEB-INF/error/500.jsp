<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="template" uri="/WEB-INF/tlds/TemplateTag.tld" %>

<%
    boolean isFromAdmin = (boolean)session.getAttribute("errorfromadmin");
%>

<% if(isFromAdmin) { %>
<template:page title="Alumni System">

    <div class="container">
        <h1>ระบบผิดพลาด - 500</h1>

        <hr />

        <footers>
            <p>คณะเทคโนโลยีสารสนเทศ สถาบันเทคโนโลยีพระจอมเกล้าเจ้าคุณทหารลาดกระบัง<br>
                เลขที่ 1 ซอยฉลองกรุง 1 แขวงลาดกระบัง เขตลาดกระบัง กรุงเทพมหานคร 10520<br>
                โทรศัพท์ +66 (0) 2723 4900 โทรสาร +66 (0) 2723 4910</p>
        </footers>
    </div>

</template:page>
<% } else { %>
<template:pageadmin>

    <div class="container-admin">

        <h1>ระบบผิดพลาด - 500</h1>

    </div>

</template:pageadmin>
<% } %>