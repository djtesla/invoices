package invoices.invoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateInvoiceCommand {

    @NotNull
    @NotBlank
    private String customerName;
    @NotNull
    private LocalDate issueDate;
    @NotNull
    private LocalDate dueDate;
    private String comment;

    public CreateInvoiceCommand(String customerName, LocalDate issueDate, LocalDate dueDate) {
        this.customerName = customerName;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }
}
