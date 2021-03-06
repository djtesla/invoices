package invoices;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Calendar;

@SpringBootApplication
public class InvoicesApplication {


    public static void main(String[] args) {
        SpringApplication.run(InvoicesApplication.class, args);

        mnb.MNBArfolyamServiceSoapImpl impl = new mnb.MNBArfolyamServiceSoapImpl();
        mnb.MNBArfolyamServiceSoap service = impl.getCustomBindingMNBArfolyamServiceSoap();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.now();
            if (date.get(ChronoField.DAY_OF_WEEK) == Calendar.SATURDAY) {
                date = date.minusDays(2);
            } else if (date.get(ChronoField.DAY_OF_WEEK) == Calendar.SUNDAY) {
                date = date.minusDays(1);
            }

            String dateStr = date.format(formatter);
            String resp = service.getExchangeRates(dateStr, dateStr, "EUR,HUF");
            String[] respArr = resp.split("curr=\"EUR\">");
            String[] respArr2 = respArr[1].split("</Rate></Day");
            respArr2[0] = respArr2[0].replace(",", ".");
            double rate = Double.parseDouble(respArr2[0]);
            System.out.println(rate);
        } catch (Exception e) {
            System.err.print(e);
        }
    }


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().findAndRegisterModules();
    }


}
