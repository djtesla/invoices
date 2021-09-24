window.onload = function () {

    let listOfItemObj = [];

    let invoice = JSON.parse(localStorage.getItem('invoice'));
    console.log(invoice);
    let invoiceId = invoice[0];


    getInvoiceSummary(invoice);

    document.getElementById("create-item-button").addEventListener("click", function () {
        createItem(invoiceId);
    })
}

