
let listOfInvoiceObj = []

getEurHufExchangeRate();

getInvoices();

document.getElementById("create-invoice-button").addEventListener("click", function(e) {
    checkMandatories(e);
})














