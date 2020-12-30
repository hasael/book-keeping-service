package app;

import app.core.Sale;
import app.core.SaleInput;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {

    @Autowired
    SalesRepository salesRepository;

    @KafkaListener(topics = "sales", groupId = "test-consumer-group-1")
    public void listenGroupFoo(String message) {

        log.info("Receiving sales: " + message);
        SaleInput saleInput = null;
        try {
            saleInput = fromJson(message);
            Sale sale = new Sale();
            sale.setCostumeId(saleInput.getCostumeId());
            sale.setChannel(saleInput.getChannel());
            salesRepository.save(sale);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SaleInput fromJson(String data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(data, SaleInput.class);
    }
}
