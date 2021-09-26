package invoices;

import invoices.invoice.CreateInvoiceCommand;
import invoices.invoice.InvoiceDto;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Sql(statements = {"delete from invoices","delete from invoice_items"})
public class InvoiceControllerRestIT {

    @Autowired
    Flyway flyway;
    @Autowired
    TestRestTemplate template;



    @BeforeEach
    void init(){
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void testCreateNewInvoice(){
        InvoiceDto result =
                template.postForObject("/api/invoices",
                        new CreateInvoiceCommand("MOL Zrt.", LocalDate.of(2021, 9, 01),  LocalDate.of(2021, 11, 30)),
                        InvoiceDto.class);

        assertEquals("MOL Zrt.",result.getCustomerName());

    }


    @Test
    void testListALlInvoices(){
        template.postForObject("/api/invoices",
                new CreateInvoiceCommand("MOL Zrt.", LocalDate.of(2021, 9, 01),  LocalDate.of(2021, 11, 30)),
                InvoiceDto.class);

        template.postForObject("/api/invoices",
                new CreateInvoiceCommand("Kiss Bt.", LocalDate.of(2021, 9, 10),  LocalDate.of(2021, 11, 10)),
                InvoiceDto.class);

        List<InvoiceDto> result = template.exchange(
                "/api/invoices",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<InvoiceDto>>() {
                }
        ).getBody();

        assertThat(result).extracting(InvoiceDto::getCustomerName)
                .containsExactly("Mol Zrt.","Kiss Bt.");
    }

}
