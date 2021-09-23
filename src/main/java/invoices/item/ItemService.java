package invoices.item;

import invoices.EntityNotFoundException;
import invoices.invoice.Invoice;
import invoices.invoice.InvoiceRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemService {

    private ModelMapper modelMapper;
    private ItemRepository itemRepository;
    private InvoiceRepository invoiceRepository;

    public List<ItemDto> listAllItemsByInvoiceId(long invoiceId) {
        return itemRepository.findAllByInvoice_Id(invoiceId).stream().map(item -> modelMapper.map(item, ItemDto.class)).collect(Collectors.toList());
    }


    /*   public ItemDto getItemById(long invoice_id, long id) {
    }*/

    @Transactional
    public ItemDto createItem(long invoice_id, CreateItemCommand command) {
        Invoice invoice = invoiceRepository.findById(invoice_id).orElseThrow(() -> new EntityNotFoundException("Invoice cannot be found by id " + invoice_id));
        double unitPrice = command.getUnitPrice();
        int quantity = command.getQuantity();
        double totalItemPrice = unitPrice * quantity;
        Item item = new Item(command.getProductName(), unitPrice, quantity, totalItemPrice);
        itemRepository.save(item);
        invoice.addItem(item);
        return modelMapper.map(item, ItemDto.class);
    }

    @Transactional
    public void deleteAllItemsByInvoiceId(long invoiceId) {
        itemRepository.deleteAllByInvoice_Id(invoiceId);
    }
}
