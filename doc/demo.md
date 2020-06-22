# Demo . md

Ce fichier markdown présente notre CI.



## Travis-CI

Notre application est vérifiée par Travis-CI à chaque commit, et ces derniers sont actuellement confirmés comme étant opérationnels et ne contenant pas d'erreurs qui pourrait affecter la stabilité du build du reste de l'équipe de développement. Pour justifier cela Travis automatiquement build l'application puis vérifie que toutes les exécutions, dans l'ordre de lancement produisent un programme fonctionnel. Le yml ci-dessous décrit son comportement :
```yml
language: java
jdk:
  - openjdk10
script:
  - mvn clean package
  - cd server
  - mvn exec:java@ServerSpring &
  - cd ../PuertoRico
  - cd serverGame
  - mvn exec:java@ServerGame &
  - cd ../serverPlayer
  - mvn exec:java@ServerPlayer &
  - mvn exec:java@ServerPlayer &
  - mvn exec:java@ServerPlayer &


```


## Docker et containeurs
Nous avons choisi de produire un conteneur par module : un pour ServerGame, un pour ServerPlayer, et un pour le serveur de stats. L'idée est donc que l'image fournie par un build de nos Dockerfiles puisse être run chez un autre utilisateur.
Les Dockerfiles se trouvent à la racine du repository et se nomment DCGame, DCPlayer et DCServer respectivement. Pour build un de ces fichiers il faut :
> 1) Renommer l'un d'entre eux en "Dockerfile" (sans les guillemets)
> 2) Avec un terminal se placer dans le dossier de ce Dockerfile (le root)
> 3) 
```shell
docker build .
```
Cette commande suit les instructions du Dockerfile. A noter : le build est relativement long dû aux dépendances et pour des raisons techniques nous n'avons pas pu tester convenablement ces Dockerfile.
4) Pour voir les images créées en cas de succès :
```shell
docker images
```
5) Des images avec des ID s'afficheront. Ensuite :
```shell
docker run id
```
Remplacez id par l'ID de votre image affichée par "docker images".

### Forme général des Dockerfiles

```dockerfile
# On crée d'abord les JARs nécéssaires à potentiellement n'importe quel module
FROM maven:3.6.0-jdk-11-slim AS globalBuild
COPY . /app
WORKDIR /app
RUN mvn install

# Puis, en utilisant openjdk :
FROM openjdk:11-jre-slim
WORKDIR /app
# On copie les JARs générés à l'étape précédente (avec Maven)
COPY --from=globalBuild /app/PuertoRico/serverGame/target/serverGame-1.0-SNAPSHOT-jar-with-dependencies.jar /app
# Nous avons choisi le port 8080 par défaut par standardisation
EXPOSE 8080
# Exécution du programme en donnant en argument le jar
CMD ["java -jar serverGame-1.0-SNAPSHOT-jar-with-dependencies.jar"]
```

