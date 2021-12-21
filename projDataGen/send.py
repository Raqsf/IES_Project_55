import pika


credentials = pika.PlainCredentials('myuser', 'mypassword')
connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost', port=5672, credentials=credentials))
channel = connection.channel()

channel.queue_declare(queue='vaccination_queue')

for i in range(10):
    channel.basic_publish(exchange='', routing_key='vaccination_queue', body='Hello World! VAMOS PORTO')
    print(" [x] Sent 'Hello World!'")
connection.close()