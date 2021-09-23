function getInvoices() {
    let url = 'http://localhost:8080/api/invoices';
    fetch(url)
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {
                //console.log(jsonData);
                let invoicesTable = document.querySelector("#invoices-table");
                let invoicesTableBody = document.querySelector("#invoice-table-body");

                invoicesTableBody.innerHTML = '';



                let tableI = 0;

                for (let invoice of jsonData) {
                    let cell;
                    let cellText;
                    tableI++;
                    let invoiceObj = [invoice.id, tableI, invoice.customerName, invoice.issueDate, invoice.comment]
                    listOfInvoiceObj.push(invoiceObj);
                    let row = document.createElement("tr");
                    invoiceObj[1] = tableI;
                    for (let i = 1; i < 5; i++) {
                        cell = document.createElement("td");
                        cellText = document.createTextNode(invoiceObj[i]);
                        cell.appendChild(cellText);
                        row.appendChild(cell);
                    }


                    let viewButton = document.createElement('button');
                    viewButton.className = "btn-info btn";
                    viewButton.textContent = 'View items';
                    row.appendChild(viewButton);
                    viewButton.addEventListener('click', function () {
                        rowFunction(this)
                    })
                    invoicesTable.appendChild(row);

                }

            }
        )
}
