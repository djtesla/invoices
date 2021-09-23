package invoices.invoice;

import invoices.EntityNotFoundException;
import invoices.Violation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/invoices")
public class InvoiceController {

    private InvoiceService invoiceService;

    @GetMapping
    //@Operation(summary = "list all invoices")
    public List<InvoiceDto> listAllInvoices() {
        return invoiceService.listAllInvoices();
    }

    @GetMapping("/{id}")
    //@Operation(summary = "get invoice by id")
    public InvoiceDto getInvoiceById(@PathVariable("id") long id) {
        return invoiceService.getInvoiceById(id);
    }

    @PostMapping
    //@Operation(summary = "create an invoice")
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceDto createInvoice(@RequestBody @Valid CreateInvoiceCommand command) {
        return invoiceService.createInvoice(command);
    }

    @DeleteMapping
    //@Operation(summary = "list all invoices")
    public void deleteAllInvoices() {
         invoiceService.deleteAllInvoices();
    }

    @DeleteMapping("/{id}")
    //@Operation(summary = "list all invoices")
    public void deleteInvoiceById(@PathVariable ("id") long id) {
        invoiceService.deleteInvoiceById(id);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Problem> handleMachineNotFound(EntityNotFoundException enfe) {
        Problem problem = Problem.builder()
                .withType(URI.create("invoice/not-found"))
                .withStatus(Status.NOT_FOUND)
                .withTitle("Not found")
                .withDetail(enfe.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(problem);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Problem> handleNotValidException(MethodArgumentNotValidException mae) {

        List<Violation> violations = mae.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new Violation(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        Problem problem = Problem.builder()
                .withType(URI.create("invoice/not-valid"))
                .withTitle(("Validation error"))
                .withStatus(Status.BAD_REQUEST)
                .withDetail(mae.getMessage())
                .with("violations", violations)
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(problem);
    }

}
