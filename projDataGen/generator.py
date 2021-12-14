import json
from kafka import KafkaProducer
import random
from random import randint
import time
import datetime
import copy

class Generator:
    def __init__(self):
        self.producer = None        
        #while self.producer is None:
        #    try:
        #        self.producer = KafkaProducer(bootstrap_servers='kafka:29092', value_serializer=lambda v: json.dumps(v).encode('utf-8'))
        #        pass
        #    except:
        #        print('\033[91m' + "Broker is not reachable!" + '\033[0m')
                
        #people = {n_utente: [informações do utente]}  
        self.number_of_vaccines = 0
        self.date = datetime.datetime(2021,12,24)
        self.people = {1234: {'n_utente':'1234','nome':'John','email':'john@email.com', 'local':'Aveiro', 'data_nasc':'01/01/2000', 'doença':'nada', 'data_vacina':"12/25/21"},
              1235: {'n_utente':'1235','nome':'Jane', 'email':'jane@email.com', 'local':'Aveiro', 'data_nasc':'01/01/1987', 'doença':'cardiaca','data_vacina': "12/24/21"},
              1236: {'n_utente':'1236','nome':'Johnny', 'email':'johny@email.com', 'local':'Porto', 'data_nasc':'01/01/1986', 'doença':'pulmunar','data_vacina': "12/25/21"},
              1237: {'n_utente':'1237','nome':'Duarte','email': 'duarte@email.com', 'local':'Porto', 'data_nasc':'01/01/1999', 'doença':'nada', 'data_vacina':"12/25/21"},
              1238: {'n_utente':'1238','nome':'Tiago', 'email':'tiago@email.com','local': 'Lisboa', 'data_nasc':'01/01/2012', 'doença':'nada', 'data_vacina':"12/26/21"},
              1239: {'n_utente':'1239','nome':'Filipa','email': 'filipa@email.com', 'local':'Lisboa', 'data_nasc':'01/01/2003', 'doença':'cardiaca','data_vacina': "12/24/21"},
              1240: {'n_utente':'1240','nome':'Sebastiana', 'email':'sebastiana@email.com','local': 'Coimbra', 'data_nasc':'01/01/1966', 'doença':'nada', 'data_vacina':"12/26/21"},
              1241: {'n_utente':'1241','nome':'Goncalo','email': 'goncalo@email.com', 'local':'Coimbra', 'data_nasc':'01/01/1939','doença': 'nada', 'data_vacina':"12/26/21"},
              1242: {'n_utente':'1242','nome':'Sofia', 'email':'sofia@email.com','local': 'Setubal', 'data_nasc':'01/01/1949','doença': 'sanguinia', 'data_vacina':"12/24/21"},
              1243: {'n_utente':'1243','nome':'Lara', 'email':'lara@email.com','local': 'Setubal', 'data_nasc':'01/01/1974','doença': 'nada', 'data_vacina':"12/24/21"}
              }

        # vacination_centers = {n_centro: [informações do centro]}
        #! The vaccination centers may be in DB from the beggining, in the data generation I think 
        #! that the only thing is needed is changing the capacity of the centers
        self.vaccination_centers = {1: ['Centro de Vacinação do Porto', 'Porto', 15, 0],
                            2: ['Centro de Vacinação do Lisboa', 'Lisboa', 23, 0],
                            3: ['Centro de Vacinação do Coimbra', 'Coimbra', 5, 0],
                            4: ['Centro de Vacinação do Aveiro', 'Aveiro', 8, 0]}
        
        self.vaccines = ['Pfizer', 'Moderna', 'Astrazeneca', 'J&J']
        self.waiting_list = list()

    def send(self, topic, message):
        """Send the message to the broker"""
        try:
            self.producer.send(topic, message)
        except:
            print('\033[91m' + "ERROR: Could not send message to broker" + '\033[0m')

    def get_random_person(self, date):
        """Return a random person"""
        random_n_utente = randint(1000,9999)
        while True:
            if random_n_utente not in self.people.keys():
                people_list = list(self.people.keys())
                person = random.choice(people_list)
                self.people[random_n_utente] = copy.deepcopy(self.people[person])
                self.people[random_n_utente]['n_utente'] = copy.deepcopy(random_n_utente)
                self.people[random_n_utente]["data_vacina"] = date.strftime("%x")
                return random_n_utente
            else:
                random_n_utente = randint(1000,9999)
    def generate_vaccines_quantity(self):
        """Generates randomly vaccines quantity"""
        self.number_of_vaccines = randint(10, 50)
        message = {"type" : "vaccines_quantity", "quantity": self.number_of_vaccines}
        #self.send("vaccines_quantity", message)
        print('\033[92m' + message.__str__() +  '\033[0m')
                
    def changing_center_capacity(self):
        """Destribute vaccines to vaccination centers"""
        centers_list = list(self.vaccination_centers.keys())
        center = [random.choice(centers_list), randint(20,30)] #[center's id, new capacity]
        message = {"type": "changing_center_capacity", "centers": center}
        print(message)
    
    def add_to_waiting_list(self, date):
        """Add person to the waiting list to get a vaccine"""
        n_utente = self.get_random_person(date)
        self.waiting_list.append(copy.deepcopy(self.people[n_utente])) 
        message = {"type": "schedule_vaccine", "utente": self.people[n_utente] }
        #self.send("schedule_vaccine", message)
        print('\033[94m' + message.__str__() + '\033[0m')
        
    def generate_people_getting_vaccinated(self, date):
        """Generates the people getting vaccinated"""
        if len(self.waiting_list) > 0:
            for person in self.waiting_list:
                if person["data_vacina"] == date:
                    self.waiting_list.remove(person)

                    message = {"type": "people_getting_vaccinated", "utente": self.people[int(person["n_utente"])]}
                    #self.send("people_getting_vaccinated", message)        
                    print('\033[93m' + message.__str__() + '\033[0m')
        
if __name__ == '__main__':
    g = Generator()
    counter_days = -1
    date = datetime.datetime(2021,12,24)
    while True:
        if counter_days == 16 or counter_days == -1:
            g.generate_vaccines_quantity()
            g.generate_people_getting_vaccinated(date.strftime("%x"))
            date = date + datetime.timedelta(days=1)
            counter_days = 0
        counter_days += 1
        g.add_to_waiting_list(date + datetime.timedelta(days=3))
            
            
        
        
        
        
        