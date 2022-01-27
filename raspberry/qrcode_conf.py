import pika


class Receive:
    def __init__(self):
        credentials = pika.PlainCredentials('prod', 'prod')
        self.connection = pika.BlockingConnection(pika.ConnectionParameters(host='192.168.160.197', port='5672', credentials=credentials))  
        self.channel = self.connection.channel()
        self.channel.queue_declare(queue='qrcode_queue')
        
    def listener(self):
        self.channel.basic_consume(queue='qrcode_queue',auto_ack=True, on_message_callback=self.callback)
        self.channel.start_consuming()
    
    def callback(self, ch, method, properties, body):
        if "invalido" in body.decode():
            print('\033[92m' + body.decode() + '\033[0m' + '\n')
        else:
            print('\033[91m' + body.decode() + '\033[0m' + '\n')
            
if __name__ == '__main__':
    try:
        r = Receive()
        r.listener()
    except KeyboardInterrupt:
        print('\nKeyboard Interruption')