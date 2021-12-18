from kafka import KafkaConsumer

consumer = KafkaConsumer('vaccination')
for msg in consumer:
    print("Topic name=%s, Message=%s"%(msg.topic,msg.value))