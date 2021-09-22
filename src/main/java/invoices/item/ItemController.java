package invoices.item;

import invoices.EntityNotFoundException;
import invoices.Violation;
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
public class ItemController {

    private ItemService itemService;

    @GetMapping("/{invoice_id}/items")
    //@Operation(summary = "list all items")
    public List<ItemDto> listAllItemsByInvoiceId(@PathVariable("invoice_id") long invoiceId) {
        return itemService.listAllItemsByInvoiceId(invoiceId);
    }


 /*   @GetMapping("/{invoice_id}/items/{id}")
    //@Operation(summary = "get item by id")
    public ItemDto getItemById(@PathVariable("invoice_id") long invoice_id, @PathVariable("id") long id) {
        return itemService.getItemById(invoice_id, id);
    }*/


    @PostMapping("/{invoice_id}/items")
    //@Operation(summary = "create an item")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto createItem(@PathVariable("invoice_id") long invoiceId, @RequestBody @Valid CreateItemCommand command) {
        return itemService.createItem(invoiceId, command);
    }


    @DeleteMapping("/{invoice_id}/items")
    public void deleteAllItemsByInvoiceId(@PathVariable("invoice_id") long invoiceId) {
        itemService.deleteAllItemsByInvoiceId(invoiceId);

    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Problem> handleMachineNotFound(EntityNotFoundException enfe) {
        Problem problem = Problem.builder()
                .withType(URI.create("item/not-found"))
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
                .withType(URI.create("item/not-valid"))
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


