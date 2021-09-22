package invoices.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    public List<Item> findAllByInvoice_Id(long invoiceId);

    public void deleteAllByInvoice_Id(long invoiceId);


/*    @Query("select i from Item i where i.invoice.id =:invoiceId")
    public List<Item> findItemsByInvoiceId(long invoiceId);*/
}
