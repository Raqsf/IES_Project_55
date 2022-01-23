import pika
import json
import cv2
from pyzbar import pyzbar

class Reader:
    def __init__(self) -> None:
        credentials = pika.PlainCredentials('myuser', 'mypassword')
        self.connection = pika.BlockingConnection(pika.ConnectionParameters(host='192.168.160.197', port='5672', credentials=credentials))  
        self.channel = self.connection.channel()
        self.channel.queue_declare(queue='vaccination_queue', durable=True)
        self.last_code = None
    
    def send(self, topic="vaccination_queue", mes=None):
        """Send the message to the broker"""
        try:
            message = json.dumps(mes)
            self.channel.basic_publish(exchange='', routing_key=topic, body=message)
        except:
            print('\033[91m' + "ERROR: Could not send message to broker" + '\033[0m')
        
    def read_codes(self, frame):
        codes = pyzbar.decode(frame)
        info = ""
        for code in codes:
            x, y , w, h = code.rect 
            info = code.data.decode('utf-8')
            cv2.rectangle(frame, (x, y),(x+w, y+h), (0, 255, 0), 2)
            font = cv2.FONT_HERSHEY_DUPLEX
            cv2.putText(frame, "QRcode lido com sucesso", (x + 6, y - 6), font, 1.4, (0, 255, 127), 1)
        return frame, info
    
    def read(self):
        camera = cv2.VideoCapture(0)
        ret, frame = camera.read()
        while ret:
            ret, frame = camera.read()
            frame, info = self.read_codes(frame)
            if len(info) != 0 and info != self.last_code:
                self.last_code = info
                print(info)
                message = {"type" : "raspberry_reader", "data": info}
                self.send(mes=message)
            cv2.imshow('QRcode Reader', frame)
            if cv2.waitKey(1) & 0xFF == 27:
                break
        camera.release()
        cv2.destroyAllWindows()
                
if __name__ == '__main__':
    try: 
        r = Reader()
        r.read()
    except KeyboardInterrupt:
        print('\nKeyboard Interruption')