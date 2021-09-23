package invoices.invoice;

import invoices.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InvoiceService {

    private ModelMapper modelMapper;
    private InvoiceRepository invoiceRepository;



    public List<InvoiceDto> listAllInvoices() {
        return invoiceRepository.findAll().stream().map(invoice -> modelMapper.map(invoice, InvoiceDto.class)).collect(Collectors.toList());
    }


    public InvoiceDto getInvoiceById(long id) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow (()->new EntityNotFoundException("Invoice cannot be found by id " + id));
        return modelMapper.map(invoice, InvoiceDto.class);
    }


    public InvoiceDto createInvoice(CreateInvoiceCommand command) {
        Invoice invoice = new Invoice(command.getCustomerName(), command.getIssueDate(), command.getIssueDate(), command.getComment());
        invoiceRepository.save(invoice);
        return modelMapper.map(invoice, InvoiceDto.class);
    }

    public void deleteAllInvoices() {
        invoiceRepository.deleteAll();
    }

    public void deleteInvoiceById(long id) {
        invoiceRepository.findById(id).orElseThrow (()->new EntityNotFoundException("Invoice cannot be found by id " + id));
        invoiceRepository.deleteById(id);
    }
}
