package invoices.invoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {

    private Long id;
    private String customerName;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private String comment;
}
