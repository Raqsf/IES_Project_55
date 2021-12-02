# IES Final Project


## DevOps
### NySQL docker image
docker run --name vaccinationdb-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=vaccinationdb -e MYSQL_USER=ies -e MYSQL_PASSWORD=password -d mysql:8.0
