<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--@elvariable id="miniCart" type="com.expertsoft.core.cart.MiniCart"--%>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <div class="col-lg-4">
                <a href="<c:url value="/productList"/>">
                    <img src="<c:url value="/resources/common/phonify.png"/>" />
                </a>
            </div>
        </div>
        <c:if test="${not empty miniCart}">
            <div class="collapse navbar-collapse">
                <div id="mini-cart" class="nav navbar-nav navbar-right navbar">
                    <a href="<c:url value="/cart"/>" class="btn btn-default">
                        <span id="item-count">${miniCart.itemCount}</span> items
                        (<span id="cart-amount">${miniCart.amount}</span>$)
                    </a>
                </div>
            </div>
        </c:if>
    </div>
</nav>