<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>

<c:choose>
    <c:when test="${not empty order.orderItems}">

        <div class="h1">Order</div>
        <div>
            <a href="<c:url value='/cart'/>" class="btn btn-default">
                Back to cart
            </a>
        </div>

        <div>
            <table class="table table-striped table-bordered">
                <thead>
                <tr>
                    <th>Number</th>
                    <th>Model</th>
                    <th>Quantity</th>
                    <th>Price (For one)</th>
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

            <div class="col-lg-6 table-bordered ">

                <s:form modelAttribute="orderContactInfoForm" action="/order/submit" method="post">
                    <table class="table">
                        <tbody>
                            <tr>
                                <td>First Name:</td>
                                <td>
                                    <s:input path="firstName" type="text" value="${orderContactInfoForm.firstName}"/><br>
                                    <s:errors path="firstName" cssClass="alert alert-danger" element="div"/>
                                </td>
                            </tr>
                            <tr>
                                <td>Last Name:</td>
                                <td>
                                    <s:input path="lastName" type="text" value="${orderContactInfoForm.lastName}"/><br>
                                    <s:errors path="lastName" cssClass="alert alert-danger" element="div"/>
                                </td>
                            </tr>
                            <tr>
                                <td>Address:</td>
                                <td>
                                    <s:input path="deliveryAddress" type="text" value="${orderContactInfoForm.deliveryAddress}"/><br>
                                    <s:errors path="deliveryAddress" cssClass="alert alert-danger" element="div"/>
                                </td>
                            </tr>
                            <tr>
                                <td>Phone:</td>
                                <td>
                                    <s:input path="contactPhoneNo" type="text" value="${orderContactInfoForm.contactPhoneNo}"/><br>
                                    <s:errors path="contactPhoneNo" cssClass="alert alert-danger" element="div"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <s:button type="submit" class="btn btn-default">Order</s:button>
                </s:form>

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

    </c:when>
    <c:otherwise>

        <div class="h1">Empty Order</div>
        <a href="<c:url value='/productList'/>" class="btn btn-default">
            Back to product list
        </a>

    </c:otherwise>
</c:choose>