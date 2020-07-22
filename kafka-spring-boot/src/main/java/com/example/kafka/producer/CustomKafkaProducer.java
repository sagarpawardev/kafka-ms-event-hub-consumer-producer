package com.example.kafka.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.util.Properties;

@Service
public class CustomKafkaProducer {
    private static final Logger logger = LoggerFactory.getLogger(CustomKafkaProducer.class);
    private static final String TOPIC = "my-event-hub";
    //private static final String configpath = "src/main/resources/kafka-producer.properties"; //TODO: uncomment this to activate local kafka connection
    private static final String configpath = "src/main/resources/ms-event-hub-kafka-producer.properties"; //TODO: comment this to activate local kafka connection and uncomment to activate microsoft event hub connection

    private final org.apache.kafka.clients.producer.Producer<Long, String> producer;

    public CustomKafkaProducer() {
        this.producer = createProducer();
    }

    public void sendMessage(String message) {
        logger.info(String.format("$$ -> Producing message --> %s", message));
        long time = System.currentTimeMillis();
        final ProducerRecord<Long, String> record = new ProducerRecord<Long, String>(TOPIC, time, message);
        producer.send(record, new Callback() {
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if (exception != null) {
                    System.exit(1);
                }
            }
        });
    }

    private static Producer<Long, String> createProducer() {
        try {
            Properties properties = new Properties();
            properties.load(new FileReader(configpath));
            properties.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExampleProducer");
            properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
            properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            return new org.apache.kafka.clients.producer.KafkaProducer<>(properties);
        } catch (Exception e) {
            System.err.println("Failed to create producer with exception: " + e);
            e.printStackTrace();
            return null;        //unreachable
        }
    }
}
