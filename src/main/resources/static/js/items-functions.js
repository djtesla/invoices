function getInvoiceSummary(invoice) {

    let listOfItemObj = []

    let rate = localStorage.getItem("rate");

    console.log(invoice[2]);

    let itemsTableBody = document.querySelector('#items-table-body');
    itemsTableBody.innerHTML = '';

    let customerNameSpan = document.querySelector('#customer-name-span');
    let issueDateSpan = document.querySelector('#issue-date-span');
    let dueDateSpan = document.querySelector('#due-date-span');
    let commentSpan = document.querySelector('#comment-span');
    let invoiceTotalInHufSpan = document.querySelector('#invoice-total-in-huf-span');
    let invoiceTotalInEurSpan = document.querySelector('#invoice-total-in-eur-span');

    let itemsBody = document.querySelector('#items-body');

    customerNameSpan.innerHTML = invoice[2];
    issueDateSpan.innerHTML = invoice[3];
    dueDateSpan.innerHTML = invoice[4];
    issueDateSpan.textContent = issueDateSpan.textContent.replace(",", "-");
    issueDateSpan.textContent = issueDateSpan.textContent.replace(",", "-");
    dueDateSpan.textContent = dueDateSpan.textContent.replace(",", "-");
    dueDateSpan.textContent = dueDateSpan.textContent.replace(",", "-");
    commentSpan.innerHTML = invoice[5];

    let invoiceId = invoice[0];
    let url = 'http://localhost:8080/api/invoices/' + invoiceId + '/items';
    fetch(url)
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {
                console.log(jsonData);
                let tableI = 0;
                let totalHUF = 0;
                let totalEUR = 0;
                for (let item of jsonData) {
                    let totalItemPriceInEur = (item.totalItemPrice/rate).toFixed(2);
                    console.log(item.totalItemPrice);
                    console.log(rate);
                    let cell;
                    let cellText;
                    tableI++;
                    let itemObj = [item.id, tableI, item.productName, item.unitPrice, item.quantity, item.totalItemPrice, totalItemPriceInEur]
                    totalHUF += item.unitPrice * item.quantity;
                    listOfItemObj.push(itemObj);
                    let row = document.createElement("tr");
                    itemObj[1] = tableI;
                    for (let i = 1; i < 7; i++) {
                        cell = document.createElement("td");
                        cell.className = "text-center";
                        cellText = document.createTextNode(itemObj[i]);
                        cell.appendChild(cellText);
                        row.appendChild(cell);
                    }
                    itemsTableBody.appendChild(row);
                }
            invoiceTotalInHufSpan.innerHTML = totalHUF;
            totalEUR = (totalHUF / rate).toFixed(2);
                invoiceTotalInEurSpan.innerHTML = totalEUR;
            itemsBody.style.visibility="visible";

            }
        )
}

function createItem(invoiceId) {
    let productNameInput = document.querySelector("#product-name-input");
    let unitPriceInput = document.querySelector("#unit-price-input");
    let quantityInput = document.querySelector("#quantity-input");

    let productName = productNameInput.value;
    let unitPrice = unitPriceInput.value;
    let quantity = quantityInput.value;

    let data = {
        "productName": productName,
        "unitPrice": unitPrice,
        "quantity": quantity,
    };
    console.log(data);

    let url = 'http://localhost:8080/api/invoices/' + invoiceId + '/items';
    fetch(url, {
        method: "POST",
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json"
        }
    })
    location.reload();

}




