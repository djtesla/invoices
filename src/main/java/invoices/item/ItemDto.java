package invoices.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {

    private Long id;
    private String productName;
    private double unitPrice;
    private int quantity;
    private double totalItemPrice;
}
