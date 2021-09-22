package invoices.item;

import invoices.invoice.Invoice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoice_items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private double unitPrice;
    private int quantity;
    private double totalItemPrice;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    public Item(String productName, double unitPrice, int quantity, double totalItemPrice) {
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.totalItemPrice = totalItemPrice;
    }


}
