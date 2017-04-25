<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>Phonify</title>

        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/bootstrap/css/bootstrap.css"/>">
        <script type="text/javascript" src="<c:url value="/resources/jquery/jquery-3.1.1.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/phonify.js"/>"></script>
        <link rel="icon" type="image/vnd.microsoft.icon" href="<c:url value="/resources/common/favicon.ico"/>"/>
    </head>
    <body>
        <div class="col-lg-offset-2 col-lg-8">
            <tiles:insertAttribute name="header"/>
            <tiles:insertAttribute name="content"/>
            <tiles:insertAttribute name="footer"/>
        </div>
    </body>
</html>
