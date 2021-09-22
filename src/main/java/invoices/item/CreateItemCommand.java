package invoices.item;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateItemCommand {

    private String productName;
    private double unitPrice;
    private int quantity;

    public CreateItemCommand(String productName, double unitPrice, int quantity) {
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;

    }
}
