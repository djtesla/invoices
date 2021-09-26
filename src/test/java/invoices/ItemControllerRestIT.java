package invoices;

import invoices.invoice.CreateInvoiceCommand;
import invoices.invoice.InvoiceDto;
import invoices.item.CreateItemCommand;
import invoices.item.ItemDto;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Sql(statements = {"delete from invoices","delete from invoice_items"})
public class ItemControllerRestIT {


    @Autowired
    Flyway flyway;

    @Autowired
    TestRestTemplate template;



    long invoiceId;

    @BeforeEach
    void init(){
        flyway.clean();
        flyway.migrate();
    }


    @Test
    void testAddNewItem() {
        InvoiceDto invoiceDto = template.postForObject("/api/invoices",
                new CreateInvoiceCommand("MOL Zrt.", LocalDate.of(2021, 9, 01), LocalDate.of(2021, 11, 30)),
                InvoiceDto.class);

        invoiceId = invoiceDto.getId();

        ItemDto result =
                template.postForObject("/api/invoices/1",
                        new CreateItemCommand("cell phone", 150000, 4),
                        ItemDto.class);

        assertEquals("cell phone", result.getProductName());
        assertEquals(150000, result.getUnitPrice());
        assertEquals(4, result.getQuantity());
    }

    @Test
    void testGetAllItemsByInvoiceId() {
        InvoiceDto invoiceDto = template.postForObject("/api/invoices",
                new CreateInvoiceCommand("MOL Zrt.", LocalDate.of(2021, 9, 01), LocalDate.of(2021, 11, 30)),
                InvoiceDto.class);

        long invoiceId = invoiceDto.getId();
        System.out.println(invoiceId);

        template.postForObject("/api/invoices/1",
                new CreateItemCommand("cell phone", 150000, 4),
                ItemDto.class);

        template.postForObject("/api/invoices/1",
                new CreateItemCommand("pen", 1500, 20),
                ItemDto.class);

        List<ItemDto> result = template.exchange(
                "/api/invoices",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ItemDto>>() {
                }
        ).getBody();

        assertThat(result).extracting(ItemDto::getProductName)
                .containsExactly("cell phone", "pen");
    }


}


