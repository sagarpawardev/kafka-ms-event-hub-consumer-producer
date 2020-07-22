package com.example.kafka.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

@Service
public class CustomKafkaConsumer {
    private final Logger logger = LoggerFactory.getLogger(CustomKafkaConsumer.class);

    private static final String TOPIC = "{TODO: your topic/event-hub name}";
    //private static final String configPath = "src/main/resources/kafka-consumer.properties"; //TODO: uncomment this to activate local kafka connection
    private static final String configPath = "src/main/resources/ms-event-hub-kafka-consumer.properties"; //TODO: comment this to activate local kafka connection and uncomnent fo miscrosoft event hub
    private final Consumer<Long, String> consumer;

    public CustomKafkaConsumer() {
        this.consumer = createConsumer();
        this.consumer.subscribe(Collections.singletonList(TOPIC));
    }

    public List<String> consume() {
        List<String> msgs = new ArrayList<>(10);

        //Consumes atleast 3 Messages
        int requiredCount=3;
        while(true) {
            final ConsumerRecords<Long, String> consumerRecords = consumer.poll(1000);
            for (ConsumerRecord<Long, String> cr : consumerRecords) {
                System.out.printf("Consumer Record:(%d, %s, %d, %d)\n", cr.key(), cr.value(), cr.partition(), cr.offset());
                msgs.add(cr.value());
            }
            consumer.commitAsync();

            if(msgs.size() >= requiredCount) {
                break;
            }
        }

        return msgs;
    }

    private Consumer<Long, String> createConsumer() {
        try {
            Properties properties = new Properties();
            properties.load(new FileReader(configPath));
            properties.put(CLIENT_ID_CONFIG, "KafkaExampleConsumer"+System.currentTimeMillis());
            properties.put(KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
            properties.put(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            return new org.apache.kafka.clients.consumer.KafkaConsumer<>(properties);
        } catch (Exception e) {
            System.err.println("Failed to create consumer with exception: " + e);
            e.printStackTrace();
            return null;        //unreachable
        }
    }
}