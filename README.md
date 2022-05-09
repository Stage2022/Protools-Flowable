# Protools POC avec Flowable
![](https://github.com/Stage2022/protools-activiti/blob/main/images/bpmn.png?raw=true)
### Installation
[Lien vers le deployment](https://protools-flowable.dev.insee.io/)

##### Installation via Docker
```bash
docker pull mailinenguyen/protools-flowable
docker run -d --name protoolsflowable -p 8080:8080  mailinenguyen/protools-flowable:latest
```
##### Installation manuelle
``` bash
git clone git@github.com:Stage2022/Protools-Flowable.git
cd Protools-Flowable
./mvnw spring-boot:run
```
