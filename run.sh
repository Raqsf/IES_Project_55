#! bin/bash

docker-compose up
sleep(10)

cd projService/vaccinationdeskservice
./mvnw spring-boot:run

sleep(25)
cd ../..
cd projFrontEnd
sudo npm start

sleep(15)

cd ..
cd projDataGen
souce venv/bin/activate
python3 generator.py

cd ..
cd projService/vaccinationdeskservice
python3 requests_API.py