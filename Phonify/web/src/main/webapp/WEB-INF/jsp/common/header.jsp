<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%--@elvariable id="miniCart" type="com.expertsoft.core.cart.MiniCart"--%>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a  href="<c:url value="/productList"/>">
                <img src="<c:url value="/resources/common/phonify.png"/>" />
            </a>
        </div>

        <ul class="nav navbar-nav navbar-right">

            <security:authorize var="isAuthenticated" access="isAuthenticated()"/>

            <c:choose>
                <c:when test="${isAuthenticated}">

                    <security:authorize access="hasRole('ROLE_ADMIN')">
                        <li class="active">
                            <c:url var="editOrdersUrl" value="/admin/orders"/>
                            <a href="${editOrdersUrl}">Edit Orders</a>
                        </li>
                    </security:authorize>

                    <li>
                        <a>
                            Hi, <security:authentication property="principal.username"/>!
                        </a>
                    </li>

                    <li class="">
                        <c:url var="logoutUrl" value="/logout"/>
                        <form action="${logoutUrl}" method="post">
                            <button class="btn navbar-btn">Log Out</button>
                            <security:csrfInput/>
                        </form>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="active">
                        <a href="<c:url value="/login" />" class="">Log In</a>
                    </li>
                </c:otherwise>
            </c:choose>

            <c:if test="${not empty miniCart}">
                <li>
                    <a id="mini-cart" href="<c:url value="/cart"/>">
                        <span id="item-count">${miniCart.itemCount}</span> items
                        (<span id="cart-amount">${miniCart.amount}</span>$)
                    </a>
                </li>
            </c:if>

    </div>
</nav>