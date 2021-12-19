from kafka import KafkaProducer
import random
from time import sleep
from datetime import datetime

producer = KafkaProducer(bootstrap_servers='localhost:9092', value_serializer=lambda v: str(v).encode('utf-8'))  

while True:
    #TODO: then choose the time interval the broker will send the data
    sleep(5) 
    producer.send('vaccination', 'alert'+str(random.randint(1,999)))