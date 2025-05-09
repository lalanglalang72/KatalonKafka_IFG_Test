# IFG Test - Kafka Consumer Testing

This repository contains a Kafka consumer test scenario using Katalon Studio, created as part of the IFG technical assessment.

## Features

- Simulated Kafka consumer behavior using local Kafka setup
- Custom keyword/test case to consume and verify message
- Demonstrates ability to integrate Katalon with message queue systems

## How to Run

1. Make sure **Kafka** and **Zookeeper** are running locally.
2. Open the project in **Katalon Studio**.
3. Go to `Test Cases` and run `ConsumeKafkaMessage.tc`.
4. Check execution log/output for message verification.

## Notes

- Kafka version: `kafka_2.13-2.8.1`
- Zookeeper and Kafka started via `bin/windows` scripts
- Topic used: `test-topic`
- This test focuses on simulating message consumption using local resources

