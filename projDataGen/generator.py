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
    def __init__(self, people, surnames, vaccination_centers=None, vaccines=None):
        credentials = pika.PlainCredentials('myuser', 'mypassword')
        self.connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost', port='5672', credentials=credentials))  
        self.channel = self.connection.channel()
        self.channel.queue_declare(queue='vaccination_queue', durable=True)
        self.number_of_vaccines_for_today = 261
        self.people = people
        self.surnames = surnames
        self.vaccination_centers = vaccination_centers
        self.vaccines = vaccines
        self.waiting_list = list()
        self.minutes = 0
        self.hours = 8
        self.initial_date = datetime.now().replace(hour=0, minute=0, second=0, microsecond=0)
        self.arriving_date = datetime.now().replace(hour=0, minute=0, second=0, microsecond=0) + timedelta(days=2)
        self.date_to_change = datetime.now().replace(hour=0, minute=0, second=0, microsecond=0)
        self.capacity_day_date = datetime.now().replace(hour=0, minute=0, second=0, microsecond=0) + timedelta(days=2)
        self.first_name_temp = None
        self.first_time = True

    def send(self, topic="vaccination_queue", mes=None):
        """Send the message to the broker"""
        try:
            message = json.dumps(mes)
            self.channel.basic_publish(exchange='', routing_key=topic, body=message)
        except:
            print('\033[91m' + "ERROR: Could not send message to broker" + '\033[0m')

    def get_random_person(self):
        """Return a random person"""
        random_n_utente = randint(1000,9999)
        while True:
            if random_n_utente not in self.people.keys():
                people_list = list(self.people.keys())
                person = random.choice(people_list)
                surname = random.choice(self.surnames)
                self.people[random_n_utente] = copy.deepcopy(self.people[person])
                self.people[random_n_utente]['n_utente'] = copy.deepcopy(random_n_utente)
                self.people[random_n_utente]['nome'] = copy.deepcopy(self.people[random_n_utente]['nome'] + " " + surname)
                minutes_to_sum = 4
                if (self.minutes + minutes_to_sum + 1) > 60:
                    if (self.hours + 1) == 24:
                        self.date_to_change = self.date_to_change + timedelta(days=1)
                        self.hours = 8
                        self.minutes = 0
                    else:
                        self.hours += 1
                        self.minutes = 0
                self.minutes = (self.minutes + minutes_to_sum) % 60
                seconds = randint(0, 59)
                self.people[random_n_utente]["data_inscricao"] = self.date_to_change.replace(hour=self.hours, minute=self.minutes, second=seconds).strftime("%Y-%m-%d %H:%M:%S")
                return random_n_utente
            else:
                random_n_utente = randint(1000,9999)
    
    def generate_vaccines_quantity(self):
        """Generates randomly vaccines quantity"""
        self.initial_date = self.initial_date + timedelta(days=1)
        #self.number_of_vaccines_for_today = randint(10, 50)
        self.capacity_day_date = self.capacity_day_date + timedelta(days=1)
        message = {"type" : "vaccines_quantity", "date": self.capacity_day_date.strftime("%d/%m/%Y"), "quantity": self.number_of_vaccines_for_today}
        self.send(mes=message)
        print('\033[92m' + message.__str__() +  '\033[0m')
        
    def destribute_vaccines_per_centers(self):
        # To facilitate the calculous, todays_date start always at 00:00, and thus the time when the 
        # program is running don't interfere with the calculation
        center = 1
        expiration_date = self.initial_date + timedelta(weeks=5) #expiration_date of each lote is about 5 weeks
        self.arriving_date = self.arriving_date + timedelta(days=1)
        quantity = 38
        for id_center in range(1, 5):
            id_lote = random.choice(self.vaccines) + str(randint(1000, 9999)) #! criar uma lista para nao deixar haver id iguais?
            center =  id_center
            if center == 1:
                quantity += 26
            elif center == 2:
                quantity += 15
            elif center == 3:
                quantity -= 20
            elif center == 4:
                quantity = quantity
            message = {"type": "vaccines_per_centers", "lote_id": id_lote, "quantity": quantity, "arriving_date": self.arriving_date.strftime("%d/%m/%Y"), "expiration_date": expiration_date.strftime("%d/%m/%Y "), "center_id": center}
            self.send(mes=message)
            print('\033[93m' + message.__str__() +  '\033[0m')
            time.sleep(1.5)
                
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
        first_name = self.people[n_utente]['nome'].split(' ')[0]
        self.people[n_utente]['nome'] = first_name
        
        
if __name__ == '__main__':
    #people = {n_utente: [informações do utente]}  
    people =  {1234:{'n_utente':'1234','nome':'John','email':'john@email.com', 'local':'Porto', 'data_nasc':'01/01/2000', 'doença':'Doença Cardíaca', 'data_inscricao':"12/25/2021"},
              1235: {'n_utente':'1235','nome':'Jane', 'email':'jane@email.com', 'local':'Aveiro', 'data_nasc':'01/01/1987', 'doença':'Doença Pulmonar','data_inscricao': "12/24/2021"},
              1236: {'n_utente':'1236','nome':'Johnny', 'email':'johny@email.com', 'local':'Porto', 'data_nasc':'01/01/1986', 'doença':'Diabetes','data_inscricao': "12/25/2021"},
              1237: {'n_utente':'1237','nome':'Duarte','email': 'duarte@email.com', 'local':'Porto', 'data_nasc':'01/01/1999', 'doença':'Cancro', 'data_inscricao':"12/25/2021"},
              1238: {'n_utente':'1238','nome':'Tiago', 'email':'tiago@email.com','local': 'Lisboa', 'data_nasc':'01/01/2012', 'doença':'nada', 'Obesidade':"12/26/2021"},
              1239: {'n_utente':'1239','nome':'Filipa','email': 'filipa@email.com', 'local':'Lisboa', 'data_nasc':'01/01/2003', 'doença':'Doença AutoImune','data_inscricao': "12/24/2021"},
              1240: {'n_utente':'1240','nome':'Sebastiana', 'email':'sebastiana@email.com','local': 'Porto', 'data_nasc':'01/01/1966', 'doença':'nada', 'data_inscricao':"12/26/2021"},
              1241: {'n_utente':'1241','nome':'Goncalo','email': 'goncalo@email.com', 'local':'Coimbra', 'data_nasc':'01/01/1939','doença': 'nada', 'data_inscricao':"12/26/2021"},
              1242: {'n_utente':'1242','nome':'Sofia', 'email':'sofia@email.com','local': 'Lisboa', 'data_nasc':'01/01/1949','doença': 'Diabetes', 'data_inscricao':"12/24/2021"},
              1243: {'n_utente':'1243','nome':'Lara', 'email':'lara@email.com','local': 'Setubal', 'data_nasc':'01/01/1974','doença': 'nada', 'data_inscricao':"12/24/2021"}
              }
    
    surnames = ["Silva", "Costa", "Ferreira", "Moreira", "Dias", "Reis", "Leal", "Silveira", "Tavares", "Santos", "Oliveira", "Pereira", "Alves", "Lopes"]
    #! The vaccination centers may be in DB from the beggining, in the data generation I think 
    #! that the only thing is needed is changing the capacity of the centers
    # vacination_centers = {n_centro: [informações do centro]}
    vaccination_centers =  {1: ['Centro de Vacinação do Porto', 'Porto', 15, 0],
                            2: ['Centro de Vacinação do Lisboa', 'Lisboa', 23, 0],
                            3: ['Centro de Vacinação do Coimbra', 'Coimbra', 5, 0],
                            4: ['Centro de Vacinação do Aveiro', 'Aveiro', 8, 0]
                            }
    vaccines = ['PF', 'MO', 'AZ', 'JJ']
    
    g = Generator(people, surnames, vaccination_centers, vaccines)
    try:
        for _ in range(3):
            g.generate_vaccines_quantity()
            time.sleep(2)
            for _ in range(224):
                g.add_to_waiting_list()
                time.sleep(0.3)
        while True:
            g.destribute_vaccines_per_centers()
            g.generate_vaccines_quantity()
            time.sleep(2)
            for _ in range(224):
                g.add_to_waiting_list()
                time.sleep(0.3)
            time.sleep(2)
    except KeyboardInterrupt:
        print('\nKeyboard Interruption')
            
            