# Vaccination Desk

<p align="center">
  <img  src="/images/logo.png" height=200 width=200>
</p>
<p align="center"> Sistema de agendamento de vacinação, distribuição de vacinas e verificação de administração de vacinas. </p>
<hr>

## Sobre o Projeto
O sistema tem como objetivo realizar a gestão de todo o processo relacionado com a administração de vacinas. Permite também o agendamento automático de vacinas, a sua distribuição por diferentes centros de vacinação, podendo esta ser feita por grupo prioritários e a verificação da presença de utentes a levar a vacina num determinado centro de vacinação. 

Para marcar uma vacina, o utilizador insere no sistema os seus dados, ficando assim numa lista de espera a aguardar que receba uma notificação via e-mail, com as informações relativas ao agendamento da sua vacina, mais propiamente, qual o centro que lhe foi atribuido, e a data acompanhada do horário da mesma. Mais tarde, à chegada ao centro de vacinação, o utente terá de validar o código QR que lhe foi enviado no email. Caso seja válido, poderá levar a vacina. 

A distribuição de vacinas pelos centros de vacinação pode ser feita automaticamente pelo sistema (i.e. a distribuição é feita de acordo com a ordem de marcação no sistema), ou ser feita de acordo com critérios de prioridade definidos pelo gerente.

O gerente do sistema monitoriza as vacinas e gere os centros de vacinação.

> Todos os dados supramencionados são gerados automaticamente pelo sistema, não sendo reais.
<hr>

## Arquitetura
![arquitetura](./images/123.png)

>#### Componentes
>A arquitetura do sistema está dividida em 5 grupos principais:
>- **Geração de dados:** onde através de um script em Python, serão gerados dados relativamente à inscrição de utentes para a vacinação, chegada de vacinas e quantidades das mesmas.
>- **Broker:** irá receber as informações da geração de dados, e irá mandá-las para a parte do back-end onde estas serão processadas e guardadas (RabbitMQ).
>- **Back-end:** Terá acesso à base de dados e terá comunicações com a parte do cliente e do broker, será aqui que os principais processamentos serão feitos (Spring Boot - Rest API, Broker e BD).
>- **Client:** A aplicação web, será desenvolvida com base no template em JS React, sendo depois adaptado às necessidades quer de visualização quer de comunicação com o back-end.
>- **Persistence:** A base de dados (MySQL) do sistema é do tipo relacional sendo bastante importante guardar de forma segura todos os dados que o sistema gera.

<hr>

## Backlog

- Para o backlog foi usado o o *Github Projects*  ....

<hr>

## Como executar

 Toda a execução do projeto, é feita com recurso ao *Docker Compose*, sendo necessária a sua instalação.
Tendo os requesitos satisfeitos, basta correr o [docker-compose](docker-compose.yml) para iniciar o projeto.
```
docker-compose up
```

<hr>

## Deploy
.......
<hr>

## Relatório

O projeto está acompanhado de um relatório, sendo que o mesmo pode ser consultado [aqui](reports/IES%20Project%20Specification%20Report.pdf).

Em progresso: https://docs.google.com/document/d/16WMrNr4wjFYltof8mGiAAXVExDsUrgvn6NY8cXZja3s/edit?usp=sharing

<hr>

## Equipa
| NMEC | NOME| Papel |
|----:|-----|-----|
| 98323 | [Raquel da Silva Ferreira](https://github.com/Raqsf) | Team Manager, Developer |
| 98546 | [Patrícia Matias Dias](https://github.com/Patricia-Dias) | Product Owner, Developer |
| 91359 | [Juan Victor Lessa Gonçalves](https://github.com/juanlessa) | DevOps Master, Developer |
| 98491 | [Pedro Alexandre Coelho Sobral](https://github.com/TheScorpoi) | Architect, Developer |


## DevOps
### Docker-compose
Na raiz do projeto basta correr o comando.  
    `docker-compose up`  
Na primeira execução deve tambem criar as tabelas na base de dados.  
Basta se conectar a db, por exemplo usando a extensão "mysql' do vs code e correr o script .sql de criação das dbs.
db connection:
            MYSQL_ROOT_PASSWORD: password  
            MYSQL_DATABASE: vaccinationdb  
            MYSQL_USER: ies  
            MYSQL_PASSWORD: password  
Para interromper o docker compose basta correr o comando.  
    `docker-compose down`  


### MySQL docker image
    docker run --name vaccinationdb-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=vaccinationdb -e MYSQL_USER=ies -e MYSQL_PASSWORD=password -d mysql:8.0
