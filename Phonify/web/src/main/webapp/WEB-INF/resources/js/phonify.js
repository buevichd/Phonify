$(document).ready(function() {
    $(".add-to-cart-form").on("submit", function (event) {
        event.preventDefault();
        $.post("/cart/add", $(event.target).serialize())
            .done(function (response) {
                updateMiniCart(response.miniCart);
                updateErrorHolder(response.errorMessage);
            })
        ;
    });

    $(".delete-item-button").on("click", function (event) {
        event.preventDefault();
        $.post("/cart/delete", {"key" : event.target.value})
            .done(function(response) {
                location.reload(true);
            })
        ;
    });
});

function updateMiniCart(miniCart) {
    $("#item-count").text(miniCart.itemCount);
    $("#cart-amount").text(miniCart.amount.toFixed(2));
}

function updateErrorHolder(errorMessage) {
    var errorHolder = document.getElementById("quantity-error-holder-" + errorMessage.key);
    if (errorHolder.firstChild != null) {
        errorHolder.removeChild(errorHolder.firstChild);
    }
    if (errorMessage.message != null) {
        var errorDiv = document.createElement("div");
        errorDiv.className = "alert alert-danger";
        errorDiv.innerHTML = errorMessage.message;
        errorHolder.appendChild(errorDiv);
    }
}