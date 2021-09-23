window.onload = function () {
    let invId = localStorage.getItem("invoiceId")
//console.log('Ez az id: ' + invId);

    getItems();

    document.getElementById("create-item-button").addEventListener("click", function () {
        createItem(invId);
    })
}

