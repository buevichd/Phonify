<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>

<c:choose>
    <c:when test="${not empty shoppingCart.orderItems}">

        <s:form modelAttribute="cartItemListForm">

            <div class="h1">Cart</div>
            <div>
                <a href="<c:url value='/productList'/>" class="btn btn-default">
                    Back to product list
                </a>
                <s:button type="submit" class="btn btn-default pull-right" onclick="return validate(this.form)">
                    Update
                </s:button>
            </div>

            <div>
                <table class="table table-striped table-bordered">
                    <thead>
                        <tr>
                            <th>Number</th>
                            <th>Model</th>
                            <th>Price</th>
                            <th>Quantity</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${shoppingCart.orderItems}" var="orderItem" varStatus="status">
                            <c:set var="phone" value="${orderItem.phone}"/>
                            <tr>
                                <td>${status.count}</td>
                                <td>
                                    <a href="<c:url value="/productDetails/${phone.key}"/>">${phone.model}</a>
                                </td>
                                <td class="text-right">${phone.price}$</td>
                                <td>
                                    <s:input path="cartItemList[${status.index}].quantity" type="text"
                                             value="${cartItemListForm.cartItemList[status.index].quantity}"/>
                                    <s:errors path="cartItemList[${status.index}].quantity" cssClass="alert alert-danger" element="div"/>
                                </td>
                                <td>
                                    <button class="btn btn-default delete-item-button" value="${phone.key}">
                                        Delete
                                    </button>
                                </td>
                            </tr>
                            <s:input path="cartItemList[${status.index}].phoneKey" type="hidden"
                                     value="${cartItemListForm.cartItemList[status.index].phoneKey}"/>
                        </c:forEach>
                    </tbody>
                </table>
                <a href="<c:url value='/order'/>" class="btn btn-default pull-right">
                    Order
                </a>
                <s:button type="submit" class="btn btn-default pull-right">
                    Update
                </s:button>
            </div>

        </s:form>

        <s:errors path="*" class="has-error" />

    </c:when>
    <c:otherwise>

        <div class="h1">Empty Cart</div>
        <a href="<c:url value='/productList'/>" class="btn btn-default">
            Back to product list
        </a>

    </c:otherwise>
</c:choose>