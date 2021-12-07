import json
#from Kafka import KafkaProducer
import random
from random import randint

class Generator:

    def __init__(self):
        self.producer = None
        #while self.producer is None: #para inicilizar o producer
        #    try:
        #        pass
        #    except:
        #        pass
        #people = {n_utente: [informações do utente]}  
        self.number_of_vaccines = 0  
        self.people = {1234: ['1234','John', 'john@email.com', 'Aveiro', '01/01/2000', 'nada'],
              1235: ['1235','Jane', 'jane@email.com', 'Aveiro', '01/01/1987', 'cardiaca'],
              1236: ['1236','Johnny', 'johny@email.com', 'Porto', '01/01/1986', 'pulmunar'],
              1237: ['1237', 'Duarte', 'duarte@email.com', 'Porto', '01/01/1999', 'nada'],
              1238: ['1238','Tiago', 'tiago@email.com', 'Lisboa', '01/01/2012', 'nada'],
              1239: ['1239','Filipa', 'filipa@email.com', 'Lisboa', '01/01/2003', 'cardiaca'],
              1240: ['1240','Sebastiana', 'sebastiana@email.com', 'Coimbra', '01/01/1966', 'nada'],
              1241: ['1241','Goncalo', 'goncalo@email.com', 'Coimbra', '01/01/1939', 'nada'],
              1242: ['1242','Sofia', 'sofia@email.com', 'Setubal', '01/01/1949', 'sanguinia'],
              1243: ['1243','Lara', 'lara@email.com', 'Setubal', '01/01/1974', 'nada']
              }

        # vacination_centers = {n_centro: [informações do centro]}
        self.vaccination_centers = {1: ['Centro de Vacinação do Porto', 'Porto', 15, 0],
                            2: ['Centro de Vacinação do Lisboa', 'Lisboa', 23, 0],
                            3: ['Centro de Vacinação do Coimbra', 'Coimbra', 5, 0],
                            4: ['Centro de Vacinação do Aveiro', 'Aveiro', 8, 0]}
        
        self.vaccines = ['Pfizer', 'Moderna', 'Astrazeneca', 'J&J']

    def send(self, topic, message):
        """Send the message to the broker"""
        try:
            self.producer.send(topic, message)
        except:
            print(0)
            pass

    def get_random_person(self):
        """Return a random person"""
        #! maybe hear generate a random key and put the person on the list
        #! dictionary, only to dont use always the same amount of "people", look at this after
        people_list = list(self.people.items())
        return random.choice(people_list)
    
    def generate_vaccines_quantity(self):
        """Generates randomly vaccines quantity"""
        self.number_of_vaccines = randint(10, 50)
    
    def destribute_vaccines(self):
        """Destribute vaccines to vaccination centers"""
        #! maybe here this function dont make any  sense, coz I think is better
        #! to distribute vaccines in order of 
        pass
    
    def add_to_waiting_list(self, n_utente):
        """Add person to the waiting list to get a vaccine"""
        message = {"type": "schedule_vaccine", "utente": self.people[n_utente] }
        
if __name__ == '__main__':
    g = Generator()
    g.get_random_person()
    g.generate_vaccines_quantity()
    print(g.number_of_vaccines)
