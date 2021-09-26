package invoices.rate;

import invoices.item.ItemDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/invoices/rate")
public class RateController {

    private RateService rateService;

    @GetMapping
    public double getActualHufEurExchangeRate() {
        return rateService.getActualHufEurExchangeRate();
    }

}
