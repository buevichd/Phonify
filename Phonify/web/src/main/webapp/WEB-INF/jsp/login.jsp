<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div class="h1">Login Page</div>

<div class="col-lg-offset-3 col-lg-6">

    <c:if test="${not empty loginErrorMessage}">
        <div class="alert alert-danger">${loginErrorMessage}</div>
    </c:if>

    <c:if test="${not empty logoutMessage}">
        <div class="alert alert-success">${logoutMessage}</div>
    </c:if>

    <s:url value="/login" var="loginUrl"/>
    <form action="${loginUrl}" method="post">
        <table class="table">
            <tbody>
                <tr>
                    <td><label for="username">User:</label></td>
                    <td><input type='text' name='username' id="username"></td>
                </tr>
                <tr>
                    <td><label for="password">Password:</label></td>
                    <td><input type='password' name='password' id="password"/></td>
                </tr>
            </tbody>
        </table>

        <input id="remember_me"
               name="_spring_security_remember_me"
               type="checkbox"/>&nbsp;&nbsp;&nbsp;
        <label for="remember_me"
               class="inline">Remember me</label><br>

        <input name="submit" type="submit" value="Login" class="btn btn-primary"/>
        &nbsp;
        <input name="reset" type="reset" value="Reset" class="btn btn-default"/>

        <security:csrfInput/>

        <input type="hidden" name="redirectUrl" value="${redirectUrl}"/>

    </form>
</div>