<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>

<c:choose>
    <c:when test="${not empty order.orderItems}">

        <div class="h1">Thank you for your order!</div>

        <div class="row">
            <table class="table table-striped table-bordered">
                <thead>
                <tr>
                    <th>Number</th>
                    <th>Model</th>
                    <th>Quantity</th>
                    <th>Price</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${order.orderItems}" var="orderItem" varStatus="status">
                        <c:set var="phone" value="${orderItem.phone}"/>
                        <c:set var="quantity" value="${orderItem.quantity}"/>
                        <tr>
                            <td>${status.count}</td>
                            <td>
                                <a href="<c:url value="/productDetails/${phone.key}"/>">${phone.model}</a>
                            </td>
                            <td class="text-right">${quantity}</td>
                            <td class="text-right">${phone.price}$</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div class="col-lg-6">

                <table class="table">
                    <tbody>
                    <tr>
                        <td>First Name:</td>
                        <td>${order.firstName}</td>
                    </tr>
                    <tr>
                        <td>Last Name:</td>
                        <td>${order.lastName}</td>
                    </tr>
                    <tr>
                        <td>Address:</td>
                        <td>${order.deliveryAddress}</td>
                    </tr>
                    <tr>
                        <td>Phone:</td>
                        <td>${order.contactPhoneNo}</td>
                    </tr>
                    </tbody>
                </table>

            </div>

            <div class="col-lg-offset-2 col-lg-4">
                <table class="table table-striped table-bordered">
                    <tbody>
                    <tr>
                        <td><strong>Subtotal:</strong></td>
                        <td class="text-right"><strong>${order.subtotal}$</strong></td>
                    </tr>
                    <tr>
                        <td><strong>Delivery:</strong></td>
                        <td class="text-right"><strong>${order.deliveryPrice}$</strong><br></td>
                    </tr>
                    <tr>
                        <td><strong>TOTAL:</strong></td>
                        <td class="text-right"><strong>${order.totalPrice}$</strong></td>
                    </tr>
                    </tbody>
                </table>
            </div>

        </div>

        <div>
            <a href="<c:url value='/productList'/>" class="btn btn-default">
                Back to product list
            </a>
        </div>
    </c:when>
    <c:otherwise>

        <div class="h1">Empty Order</div>
        <a href="<c:url value='/productList'/>" class="btn btn-default">
            Back to product list
        </a>

    </c:otherwise>
</c:choose>