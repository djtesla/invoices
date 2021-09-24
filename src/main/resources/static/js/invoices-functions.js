function getInvoices() {
    let invoicesTable = document.querySelector("#invoices-table");
    let invoicesTableBody = document.querySelector("#invoice-table-body");

    invoicesTableBody.innerHTML = '';

    let url = 'http://localhost:8080/api/invoices';
    fetch(url)
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {
                //console.log(jsonData);

                let tableI = 0;

                for (let invoice of jsonData) {
                    let cell;
                    let cellText;
                    tableI++;
                    let invoiceObj = [invoice.id, tableI, invoice.customerName, invoice.issueDate, invoice.dueDate,invoice.comment]
                    listOfInvoiceObj.push(invoiceObj);
                    let row = document.createElement("tr");
                    invoiceObj[1] = tableI;
                    let myDate = new Date();
                    for (let i = 1; i < 6; i++) {
                        cell = document.createElement("td");
                        cellText = document.createTextNode(invoiceObj[i]);
                        if (i == 3 || i == 4) {

                            cellText.textContent = cellText.textContent.replace(",", "/")
                            cellText.textContent = cellText.textContent.replace(",", "/")
                        }
                        cell.appendChild(cellText);
                        row.appendChild(cell);
                    }


                    let viewButton = document.createElement('button');
                    viewButton.className = "btn-info btn";
                    viewButton.textContent = 'View items';
                    row.appendChild(viewButton);
                    viewButton.addEventListener('click', function () {
                        viewItems(this)
                    })
                    invoicesTableBody.appendChild(row);

                }

            }
        )
}

function createInvoice() {
    let customerNameInput = document.querySelector("#customer-name-input");
    let issueDateInput = document.querySelector("#issue-date-input");
    let dueDateInput = document.querySelector("#due-date-input");
    let commentInput = document.querySelector("#comment-input");

    let customerName = customerNameInput.value;
    let issueDate = issueDateInput.value;
    let dueDate = dueDateInput.value;
    let comment = commentInput.value;


    let data = {"customerName": customerName, "issueDate": issueDate, "dueDate": dueDate, "comment": comment};
    console.log(data);

    let url = 'http://localhost:8080/api/invoices';
    fetch(url, {
        method: "POST",
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json"
        }
    })
    // getInvoices();
}


function viewItems(el) {
    let i = el.parentNode.cells[0].textContent;
    let invoiceId = listOfInvoiceObj[i-1][0];
    localStorage.setItem("invoiceId",invoiceId);
    window.location.href = "items.html";

}


function checkMandatories(e) {
    let customerNameInput = document.querySelector("#customer-name-input");
    let issueDateInput = document.querySelector("#issue-date-input");
    let dueDateInput = document.querySelector("#due-date-input");
    let commentInput = document.querySelector("#comment-input");

    if (customerNameInput.value == '' || issueDateInput.vale == '' || dueDateInput.value == '') {
        let mandatoryDataMissingSpan = document.querySelector("#mandatory-data-missing-span");
        mandatoryDataMissingSpan.innerHTML='Note that customer name, issue date and due date are mandatory!'

        return;
    }
    $('#myModal').modal('hide');
    e.stopImmediatePropagation();
    createInvoice();
    $('#myModal2').modal('show');
    getInvoices();
}


