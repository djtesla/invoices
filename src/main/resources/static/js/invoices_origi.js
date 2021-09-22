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
            .then(function(response) {
                console.log("Success");
                getInvoices();
                input.value = "";
                let messageParagraph = document.querySelector("#message-p");
                messageParagraph.innerHTML = "Success save";
            })

    };

}

function getInvoices() {
    let url = 'http://localhost:8080/api/invoices';
    fetch(url)
        .then(function(response) {
            return response.json();
        })
        .then(function(jsonData) {
            console.log(jsonData);
            let ul = document.querySelector("#invoices-ul");
            ul.innerHTML = "";

            for (let invoice of jsonData) {
                let row = "<li>" + invoice.id + " - " + invoice.customerName + " - " +  invoice.issueDate + " - " + invoice.dueDate + " - " + invoice.comment + "</li>"
                ul.innerHTML += row;
            }
        });
}

