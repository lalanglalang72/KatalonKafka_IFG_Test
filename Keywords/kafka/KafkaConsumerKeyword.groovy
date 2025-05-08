package kafka

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable
import org.apache.kafka.clients.consumer.*
import org.apache.kafka.common.serialization.StringDeserializer
import java.util.Properties
import java.util.Arrays
import java.time.Duration
import groovy.json.JsonSlurper


class KafkaConsumerKeyword {

    @Keyword
    def consumeMessage(String topic, String bootstrapServers, String groupId) {
        Properties props = new Properties()
        props.put("bootstrap.servers", bootstrapServers)
        props.put("group.id", groupId)
        props.put("key.deserializer", StringDeserializer.class.getName())
        props.put("value.deserializer", StringDeserializer.class.getName())
        props.put("auto.offset.reset", "earliest")

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)
        consumer.subscribe(Arrays.asList(topic))

        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10))

        if (records.isEmpty()) {
            println "⚠️ No messages received."
        }

        for (ConsumerRecord<String, String> record : records) {
            println("=== Kafka Message Metadata ===")
            println("Topic: " + record.topic())
            println("Partition: " + record.partition())
            println("Offset: " + record.offset())
            println("Key: " + record.key())
            println("Raw Value: " + record.value())

            try {
                def json = new JsonSlurper().parseText(record.value())
                println("=== Kafka JSON Payload ===")
                json.each { k, v -> println("$k: $v") }
            } catch (Exception e) {
                println("❌ Failed to parse message as JSON: ${e.message}")
            }

            consumer.close()
            return record.value()
        }

        consumer.close()
        return null
    }
}