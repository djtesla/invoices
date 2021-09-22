window.onload = function () {
    // alert("Hello JavaScript!");
    console.log("Hello JavaScript Console!");

    getInvoices();


    let customerNameInput = document.querySelector("#customer-name-input");
    let issueDateInput = document.querySelector("#issue-date-input");
    let dueDateInput = document.querySelector("#due-date-input");
    let commentInput = document.querySelector("#comment-input");
    let createInvoiceButton = document.querySelector("#create-invoice-button");


    createInvoiceButton.onclick = function (e) {
        //console.log("Button has pressed");

        let customerName = customerNameInput.value;
        let issueDate = issueDateInput.value;
        let dueDate = dueDateInput.value;
        let comment = commentInput.value;
        //console.log("Name: " + name);


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
    }
    getInvoices();
}

function rowFunction(el) {
    let id = el.parentNode.cells[0].textContent;
    console.log(id);
}

function getInvoices() {
    let url = 'http://localhost:8080/api/invoices';
    fetch(url)
        .then(function (response) {
            return response.json();
        })
        .then(function (jsonData) {
                console.log(jsonData);
                let invoicesTable = document.querySelector("#invoices-table");
                let invoicesTableBody = document.querySelector("#invoice-table-body");

                invoicesTable.innerHTML = '';

                // creates a <table> element and a <tbody> element


                // creating all cells
                let cell;
                let cellText;


                for (let invoice of jsonData) {
                    // creates a table row
                    let row = document.createElement("tr");


                    // Create a <td> element and a text node, make the text
                    // node the contents of the <td>, and put the <td> at
                    // the end of the table row

                    cell = document.createElement("td");
                    cellText = document.createTextNode(invoice.id);
                    cell.appendChild(cellText);
                    row.appendChild(cell);

                    cell = document.createElement("td");
                    cellText = document.createTextNode(invoice.customerName);
                    cell.appendChild(cellText);
                    row.appendChild(cell);

                    cell = document.createElement("td");
                    cellText = document.createTextNode(invoice.issueDate);
                    cell.appendChild(cellText);
                    row.appendChild(cell);

                    cell = document.createElement("td");
                    cellText = document.createTextNode(invoice.dueDate);
                    cell.appendChild(cellText);
                    row.appendChild(cell);

                    cell = document.createElement("td");
                    cellText = document.createTextNode(invoice.comment);
                    cell.appendChild(cellText);
                    row.appendChild(cell);

                    let viewButton = document.createElement('button');
                    viewButton.className="btn-info btn";
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



