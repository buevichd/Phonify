<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="h1">${product.model}</div>

<a href="<c:url value='/productList'/>" class="btn btn-default">
    Back to product list
</a>

<div class="row">
    <div class="col-lg-8">
        <table class="table table-striped table-bordered">
            <tbody>
                <tr>
                    <td>Display</td>
                    <td>4"</td>
                </tr>
                <tr>
                    <td>Color</td>
                    <td>Black</td>
                </tr>
                <tr>
                    <td>Price</td>
                    <td class="text-right">${product.price}$</td>
                </tr>
            </tbody>
        </table>
    </div>
</div>

<div class="row">
    <s:form class="add-to-cart-form" modelAttribute="cartItem">
        <button class="btn btn-default">
            Add to Cart
        </button>
        <s:input path="quantity" type="text" value="1"/>
        <s:input path="phoneKey" type="hidden" value="${product.key}"/>
    </s:form>
    <div id="quantity-error-holder-${product.key}" class="col-lg-4">
    </div>
</div>