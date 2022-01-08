import pika
import json
import datetime
import random
import time
import datetime
import copy
from datetime import datetime, timedelta
from random import randint

class Generator:
    def __init__(self, people, vaccination_centers=None, vaccines=None):
        credentials = pika.PlainCredentials('myuser', 'mypassword')
        self.connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost', port='5672', credentials=credentials))  
        self.channel = self.connection.channel()
        self.channel.queue_declare(queue='vaccination_queue', durable=True)
        self.number_of_vaccines_for_today = 0
        self.people = people
        self.vaccination_centers = vaccination_centers
        self.vaccines = vaccines
        self.waiting_list = list()
        self.minutes = 0
        self.hours = 8
        self.initial_date = datetime.now().replace(hour=0, minute=0, second=0, microsecond=0)
        self.date_to_change = datetime.now().replace(hour=0, minute=0, second=0, microsecond=0)

    def send(self, topic="vaccination_queue", mes=None):
        """Send the message to the broker"""
        try:
            message = json.dumps(mes)
            self.channel.basic_publish(exchange='', routing_key=topic, body=message)
        except:
            print('\033[91m' + "ERROR: Could not send message to broker" + '\033[0m')

    def get_random_person(self):
        """Return a random person"""
        #! At this point the system is able to deal with a maximum of 9000 people, coz the id is a 4 digit number
        random_n_utente = randint(1000,9999)
        while True:
            if random_n_utente not in self.people.keys():
                people_list = list(self.people.keys())
                person = random.choice(people_list)
                self.people[random_n_utente] = copy.deepcopy(self.people[person])
                self.people[random_n_utente]['n_utente'] = copy.deepcopy(random_n_utente)
                minutes_to_sum = 8
                if (self.minutes + minutes_to_sum) > 60:
                    if (self.hours + 1) == 24:
                        self.date_to_change = self.date_to_change + timedelta(days=1)
                        self.hours = 8
                    self.hours += 1
                    self.minutes = 0
                self.minutes = (self.minutes + minutes_to_sum) % 60
                seconds = randint(0, 59)
                self.people[random_n_utente]["data_inscricao"] = self.date_to_change.replace(hour=self.hours, minute=self.minutes, second=seconds).strftime("%d/%m/%Y %H:%M:%S")
                return random_n_utente
            else:
                random_n_utente = randint(1000,9999)
                                          
    def generate_vaccines_quantity(self):
        """Generates randomly vaccines quantity"""
        self.initial_date = self.initial_date + timedelta(days=1)
        #self.number_of_vaccines_for_today = randint(10, 50)
        self.number_of_vaccines_for_today = 40
        message = {"type" : "vaccines_quantity", "date": self.initial_date.strftime("%d/%m/%Y"), "quantity": self.number_of_vaccines_for_today}
        self.send(mes=message)
        print('\033[92m' + message.__str__() +  '\033[0m')
        
    def destribute_vaccines_per_centers(self):
        # To facilitate the calculous, todays_date start always at 00:00, and thus the time when the 
        # program is running don't interfere with the calculation
        center = 1
        expiration_date = self.initial_date + timedelta(weeks=5) #expiration_date of each lote is about 5 weeks
        for id_center in range(1, 5):
            id_lote = random.choice(self.vaccines) + str(randint(1000, 9999)) #! criar uma lista para nao deixar haver id iguais?
            center =  id_center
            message = {"type": "vaccines_per_centers", "lote_id": id_lote, "quantity": 10, "expiration_date": expiration_date.strftime("%d/%m/%Y "), "center_id": center}
            self.send(mes=message)
            print('\033[93m' + message.__str__() +  '\033[0m')
                
    def changing_center_capacity(self):
        """Changing maximum capacity of vaccination centers"""
        centers_list = list(self.vaccination_centers.keys())
        center = [random.choice(centers_list), randint(20,30)] #[center's id, new capacity]
        message = {"type": "vaccination_queue", "centers": center}
        print(message)
    
    def add_to_waiting_list(self):
        """Add person to the waiting list to get a vaccine"""
        n_utente = self.get_random_person()
        self.waiting_list.append(copy.deepcopy(self.people[n_utente])) 
        message = {"type": "schedule_vaccine", "utente": self.people[n_utente] }
        self.send(mes=message)
        print('\033[94m' + message.__str__() + '\033[0m')
        
    def generate_people_getting_vaccinated(self, date):
        #TODO: Try to understand why it only "get vaccinated" the odd indexs of self.waiting_list
        #TODO: and put the ones left behind getting the vaccine as well 
        """Generates the people getting vaccinated"""
        if len(self.waiting_list) > 0:
            for person in self.waiting_list:
                if person["data_inscricao"] == date:
                    self.waiting_list.remove(person)
                    message = {"type": "people_getting_vaccinated", "utente": self.people[int(person["n_utente"])]}
                    self.send(mes=message)        
                    print('\033[93m' + message.__str__() + '\033[0m')
        
if __name__ == '__main__':
    #people = {n_utente: [informações do utente]}  
    people =  {1234:{'n_utente':'1234','nome':'John','email':'john@email.com', 'local':'Aveiro', 'data_nasc':'01/01/2000', 'doença':'nada', 'data_inscricao':"12/25/2021"},
              1235: {'n_utente':'1235','nome':'Jane', 'email':'jane@email.com', 'local':'Aveiro', 'data_nasc':'01/01/1987', 'doença':'cardiaca','data_inscricao': "12/24/2021"},
              1236: {'n_utente':'1236','nome':'Johnny', 'email':'johny@email.com', 'local':'Porto', 'data_nasc':'01/01/1986', 'doença':'pulmunar','data_inscricao': "12/25/2021"},
              1237: {'n_utente':'1237','nome':'Duarte','email': 'duarte@email.com', 'local':'Porto', 'data_nasc':'01/01/1999', 'doença':'nada', 'data_inscricao':"12/25/2021"},
              1238: {'n_utente':'1238','nome':'Tiago', 'email':'tiago@email.com','local': 'Lisboa', 'data_nasc':'01/01/2012', 'doença':'nada', 'data_inscricao':"12/26/2021"},
              1239: {'n_utente':'1239','nome':'Filipa','email': 'filipa@email.com', 'local':'Lisboa', 'data_nasc':'01/01/2003', 'doença':'cardiaca','data_inscricao': "12/24/2021"},
              1240: {'n_utente':'1240','nome':'Sebastiana', 'email':'sebastiana@email.com','local': 'Coimbra', 'data_nasc':'01/01/1966', 'doença':'nada', 'data_inscricao':"12/26/2021"},
              1241: {'n_utente':'1241','nome':'Goncalo','email': 'goncalo@email.com', 'local':'Coimbra', 'data_nasc':'01/01/1939','doença': 'nada', 'data_inscricao':"12/26/2021"},
              1242: {'n_utente':'1242','nome':'Sofia', 'email':'sofia@email.com','local': 'Setubal', 'data_nasc':'01/01/1949','doença': 'sanguinia', 'data_inscricao':"12/24/2021"},
              1243: {'n_utente':'1243','nome':'Lara', 'email':'lara@email.com','local': 'Setubal', 'data_nasc':'01/01/1974','doença': 'nada', 'data_inscricao':"12/24/2021"}
              }
    #! The vaccination centers may be in DB from the beggining, in the data generation I think 
    #! that the only thing is needed is changing the capacity of the centers
    # vacination_centers = {n_centro: [informações do centro]}
    vaccination_centers =  {1: ['Centro de Vacinação do Porto', 'Porto', 15, 0],
                            2: ['Centro de Vacinação do Lisboa', 'Lisboa', 23, 0],
                            3: ['Centro de Vacinação do Coimbra', 'Coimbra', 5, 0],
                            4: ['Centro de Vacinação do Aveiro', 'Aveiro', 8, 0]
                            }
    vaccines = ['PF', 'MO', 'AZ', 'JJ']
    
    g = Generator(people, vaccination_centers, vaccines)
    while True:
        time.sleep(0.5)
        #g.generate_vaccines_quantity()
        #g.destribute_vaccines_per_centers()
        for _ in range(10):
            g.add_to_waiting_list()
        '''if counter_days == quantity or counter_days == -1:
            #g.generate_people_getting_vaccinated(date.strftime("%m/%d/%Y"))
            date = date + datetime.timedelta(days=1)
            counter_days = 0
        counter_days += 1
        g.add_to_waiting_list(date + datetime.timedelta(days=3))'''