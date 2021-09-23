let listOfInvoiceObj = []

getInvoices();

document.getElementById("create-invoice-button").addEventListener("click", function(e) {
    e.stopImmediatePropagation();
    createInvoice();
})














