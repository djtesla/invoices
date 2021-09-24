function getItems() {
    //let listOfItemObj = []
    let itemsTableBody = document.querySelector('#items-table-body');
    itemsTableBody.innerHTML = '';

    let invoiceId = localStorage.getItem('invoiceId');

    let url = 'http://localhost:8080/api/invoices/{invoiceId}/items';
    fetch(url)
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {
                console.log(jsonData);
                let tableI = 0;
                let totalItemPriceInEur = 0;
                for (let item of jsonData) {
                    let cell;
                    let cellText;
                    tableI++;
                    let itemObj = [item.id, tableI, item.productName, item.unitPrice, item.quantity, item.totalItemPrice, totalItemPriceInEur]
                    //listOfItemObj.push(itemObj);
                    let row = document.createElement("tr");
                    itemObj[1] = tableI;
                    for (let i = 1; i < 7; i++) {
                        cell = document.createElement("td");
                        cell.className="text-center";
                        cellText = document.createTextNode(itemObj[i]);
                        cell.appendChild(cellText);
                        row.appendChild(cell);
                    }
                    itemsTableBody.appendChild(row);
                }
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

    let url = 'http://localhost:8080/api/invoices/invoiceId/items';
    fetch(url, {
        method: "POST",
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json"
        }
    })

}




