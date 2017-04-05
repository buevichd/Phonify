<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>


<div class="h1">Phones </div>
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
            <c:forEach items="${products}" var="product" varStatus="status">
                <tr>

                    <td>${status.count}</td>
                    <td>
                        <a href="/productDetails/${product.key}">
                            ${product.model}
                        </a>
                    </td>
                    <td class="text-right">${product.price}$</td>
                    <s:form class="add-to-cart-form" modelAttribute="cartItem">
                        <s:input path="phoneKey" type="hidden" value="${product.key}"/>
                        <td>
                            <s:input path="quantity" type="text" value="1" />
                            <div id="quantity-error-holder-${product.key}"></div>
                        </td>
                        <td>
                            <s:button type="submit" class="btn btn-default">
                                Add To Cart
                            </s:button>
                        </td>
                    </s:form>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
