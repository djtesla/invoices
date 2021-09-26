package invoices.rate;

import mnb.MNBArfolyamServiceSoap;
import mnb.MNBArfolyamServiceSoapImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Calendar;

@Service
public class RateService {

    public double getActualHufEurExchangeRate() {
        MNBArfolyamServiceSoapImpl impl = new mnb.MNBArfolyamServiceSoapImpl();
        MNBArfolyamServiceSoap service = impl.getCustomBindingMNBArfolyamServiceSoap();
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
            return rate;
        } catch (Exception e) {
            throw new IllegalArgumentException("Rate is not defined");
        }
    }

}

