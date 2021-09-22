package invoices.invoice;

import invoices.item.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "invoices")

public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private String comment;

    @OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY)
    private List<Item> items;



    public Invoice(String customerName, LocalDate issueDate, LocalDate dueDate, String comment) {
        this.customerName = customerName;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.comment = comment;
    }

    public Invoice(String customerName, LocalDate issueDate, LocalDate dueDate) {
        this.customerName = customerName;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }

    public void addItem(Item item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(item);
        item.setInvoice(this);
    }
}
