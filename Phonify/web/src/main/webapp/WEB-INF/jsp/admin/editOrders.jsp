<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>

<c:set var="page" value="${(param.page != null) ? param.page : 1}"/>
<c:url var="editOrdersUrl" value="/admin/orders"/>

<table class="table table-striped table-bordered">
    <thead>
        <tr>
            <td>Order Key</td>
            <td>Total</td>
            <td>Delivery Address</td>
            <td>Contact Phone</td>
            <td>Delivery Status</td>
            <td>Action</td>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="order" items="${orders}">
            <tr>
                <td>${order.key}</td>
                <td>${order.totalPrice}</td>
                <td>${order.deliveryAddress}</td>
                <td>${order.contactPhoneNo}</td>
                <td>${order.deliveryStatus}</td>
                <td>
                    <form action="${editOrdersUrl}?page=${page}" method="post">
                        <input type="hidden" name="key" value="${order.key}">
                        <s:csrfInput/>
                        <button class="btn btn-default" ${order.deliveryStatus == "DELIVERED" ? "disabled" : ""}>
                            Mark As Delivered
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<div class="text-center">
    <ul class="pagination">
        <c:if test="${totalPageNumber != 1}">
            <c:if test="${page > 1}">
                <li><a href="${editOrdersUrl}?page=1">&laquo;</a></li>
                <c:if test="${page > 2}">
                    <li><a href="${editOrdersUrl}?page=${page - 2}">${page - 2}</a></li>
                </c:if>
                <li><a href="${editOrdersUrl}?page=${page - 1}">${page - 1}</a></li>
            </c:if>
            <li><a href=""><strong>${page}</strong></a></li>


            <c:if test="${page != totalPageNumber}">
                <li><a href="${editOrdersUrl}?page=${page + 1}">${page + 1}</a></li>
                <c:if test="${totalPageNumber >= page + 2}">
                    <li><a href="${editOrdersUrl}?page=${page + 2}">${page + 2}</a></li>
                </c:if>
                <li><a href="${editOrdersUrl}?page=${totalPageNumber}">&raquo;</a></li>
            </c:if>
        </c:if>
    </ul>
</div>