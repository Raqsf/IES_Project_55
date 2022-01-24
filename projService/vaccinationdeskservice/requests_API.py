import requests
import time

AGENDAMENTO_URL = "http://192.168.160.197:8080/api/v1/agendamento/agendar"
VACINACAO_URL = "http://192.168.160.197:8080/api/v1/vacinacao/vacinas_a_ser_tomadas""

time.sleep(270) #sleep inicial para os 3 dias sendo que no python esta a 0.3, qd se aumentar o timer aumentar aqui tb
while True:
    response = requests.get(AGENDAMENTO_URL)
    time.sleep(5)
    response = requests.get(VACINACAO_URL)
    time.sleep(15)
