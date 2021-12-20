# Vaccination Desk

Sistema de agendamento de vacinação, distribuição de vacinas e verificação de administração de vacinas

## Sobre o Projeto
O sistema permite fazer a gestão de todo o processo relacionado com a administração de vacinas. Permite também o agendamento automático de vacinas, a sua distribuição por diferentes centros de vacinação e a verificação da presença da pessoa a levar a vacina no centro de vacinação. 

Para marcar uma vacina, o utilizador insere no sistema os seus dados para que lhe possa ser atribuída uma data e local para a toma da vacina no centro de vacinação mais próximo. Mais tarde, no centro de vacinação, a pessoa terá de validar o código QR que lhe foi enviado. Caso seja válido, poderá levar a vacina. 

A distribuição de vacinas pelos centros de vacinação é feita automaticamente pelo sistema.

O gerente do sistema monitoriza as vacinas e gere os centros de vacinação.

## Equipa
| NMEC | NOME| Papel |
|----:|-----|-----|
| 98323 | Raquel da Silva Ferreira | Team Manager, Developer |
| 98546 | Patrícia Matias Dias | Product Owner, Developer |
| 91359 | Juan Victor Lessa Gonçalves | DevOps Master, Developer |
| 98491 | Pedro Alexandre Coelho Sobral | Architect, Developer |

## DevOps
### Docker-compose
Na raiz do projeto basta correr o comando.  
    docker-compose up  
Na primeira execução deve tambem criar as tabelas na base de dados.  
Basta se conectar a db, por exemplo usando a extensão "mysql' do vs code e correr o script .sql de criação das dbs.
db connection:
            MYSQL_ROOT_PASSWORD: password  
            MYSQL_DATABASE: vaccinationdb  
            MYSQL_USER: ies  
            MYSQL_PASSWORD: password  
Para interromper o docker compose basta correr o comando.  
    docker-compose down  


### MySQL docker image
    docker run --name vaccinationdb-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=vaccinationdb -e MYSQL_USER=ies -e MYSQL_PASSWORD=password -d mysql:8.0
