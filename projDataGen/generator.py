import json
from Kafka import KafkaProducer

class Generator:

    def __init__(self):
        self.producer = None
        while self.producer is None: #para inicilizar o producer
            try:
                pass
            except:
                pass

    def send(self, topic, message):
        """Send the message to the broker"""
        try:
            self.producer.send(topic, message)
        except:
            print(0)
            pass

    def get_random_person(self):
        """Return a random person"""
        pass
    
    def generate_vaccines_quantity(self):
        """Generates randomly vaccines quantity"""
        pass
    
    def destribute_vaccines(self):
        """Destribute vaccines to vaccination centers"""
        pass
    
    def add_to_waiting_list(self, n_utente):
        """Add person to the waiting list to get a vaccine"""
        message = {"type": "schedule_vaccine", "n_utente": n_utente }
        

    # people = {n_utente: [informações do utente]}
    people = {1234: ['John', 'john@email.com', 'Aveiro', '01/01/2000', 'nada'],
              1235: ['Jane', 'jane@email.com', 'Aveiro', '01/01/1987', 'cardiaca'],
              1236: ['Johnny', 'johny@email.com', 'Porto', '01/01/1986', 'pulmunar'],
              1237: ['Duarte', 'duarte@email.com', 'Porto', '01/01/1999', 'nada'],
              1238: ['Tiago', 'tiago@email.com', 'Lisboa', '01/01/2012', 'nada'],
              1239: ['Filipa', 'filipa@email.com', 'Lisboa', '01/01/2003', 'cardiaca'],
              1240: ['Sebastiana', 'sebastiana@email.com', 'Coimbra', '01/01/1966', 'nada'],
              1241: ['Goncalo', 'goncalo@email.com', 'Coimbra', '01/01/1939', 'nada'],
              1242: ['Sofia', 'sofia@email.com', 'Setubal', '01/01/1949', 'sanguinia'],
              1243: ['Lara', 'lara@email.com', 'Setubal', '01/01/1974', 'nada']
              }

    # vacination_centers = {n_centro: [informações do centro]}
    vaccination_centers = {1: ['Centro de Vacinação do Porto', 'Porto', 15, 0],
                           2: ['Centro de Vacinação do Lisboa', 'Lisboa', 23, 0],
                           3: ['Centro de Vacinação do Coimbra', 'Coimbra', 5, 0],
                           4: ['Centro de Vacinação do Aveiro', 'Aveiro', 8, 0]}
    
    vaccines = ['Pfizer', 'Moderna', 'Astrazeneca', 'J&J']

if __name__ == '__main__':
    print(Generator.people)
