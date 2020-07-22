package com.example.kafka;

import com.example.kafka.consumer.CustomKafkaConsumer;
import com.example.kafka.producer.CustomKafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {
    private final CustomKafkaProducer producer;
    private final CustomKafkaConsumer consumer;

    @Autowired
    public KafkaController(CustomKafkaProducer producer, CustomKafkaConsumer consumer) {
        this.producer = producer;
        this.consumer = consumer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message){
        this.producer.sendMessage(message);
    }

    @GetMapping(value = "/consume")
    public List<String> consumeMessage(){
        return this.consumer.consume();
    }
}
