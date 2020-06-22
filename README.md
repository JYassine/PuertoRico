
<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="">
    <img src="https://images-na.ssl-images-amazon.com/images/I/71qZGRgjYBL._SY679_.jpg" width=220px">
  </a>

  <h2 align="center">Puerto Rico board game</h2>

  <p align="center">
    Prospector, captain, mayor, trader, settler, craftsman, or builder?
    Which roles will you play in the new world? Will you own the most
    prosperous plantations? Will you build the most valuable buildings?
    You have but one goal: achieve the greatest prosperity and highest respect!
    <br />
    <strong>This is shown by the player who earns the most victory points?</strong>
    <br />
    <br />
    <a href="http://riograndegames.com/uploads/Game/Game_4_gameRules.pdf"><strong>Explore the rules »</strong></a>
    <br />
    <br />
    <img src="https://forthebadge.com/images/badges/made-with-java.svg" width=250px">
  </p>
</p>

<br />

### Groupe DockuertoRico2019
* Remy Munier
* Yassine Jrad
* Alexandre Denos
* Ôtmane Rahim
* Kevin Alessandro

<br />
<br />

### Guide d'utilisation : 

Pour pouvoir executer notre jeu il faudra tout d'abord faire ces commandes :

git clone gitHubRepoHttpAdresse repertoireEquipe <br />
cd repertoireEquipe <br />
git checkout "lastVersion" <br />
mvn clean package <br />

<br />
Pour lancer les serveurs : <br /> 

Serveur moteur de jeu  : <br/>
cd PuertoRico <br/>
mvn exec:java@ServerGame
<br />

Serveur stats  : <br/>
cd server <br />
mvn spring-boot:run -Dspring-boot.run.arguments="stats" <br />

### EXECUTION DU JEU (se placer sous le dossier PuertoRico): 
<br />

Il y a 6 paramètre pour lancer une partie de 3 joueurs , le premier paramètre est le nombre de partie , deuxième paramètre est le nombres de joueurs, les 3 autres paramètres sont les choix des bots <br />

(Le flot de toute la partie s'affiche, nous n'avons pas réussi à enlever tout les affichages, donc il faut attendre un moment avant d'avoir les stats...)<br />

Si on veut lancer 1000 partie avec 3 joueurs et les 3 bots randoms, il faut executer :<br />

- mvn exec:java "-Dexec.args=1000 3" <br />
 
On a 3 bots : le BotRandom(1), le botBuilding(2) et le botCaptain(3) <br />

Si on veut lancer 1000 partie avec 3 joueurs et les 3 bots différents, il faut executer : <br />
- mvn exec:java "-Dexec.args=1000 3 1 2 3" <br />

Si on veut lancer 1000 parties de notre meilleur bot contre deux bots randoms, il faut executer : <br />
- mvn exec:java "-Dexec.args=1000 3 2 1 1" <br />

Si on veut lancer 1000 parties de notre meilleur bot contre lui même, il faut executer : <br />
- mvn exec:java "-Dexec.args=1000 3 2 2 2" <br />




Il faut que le serveur de statistique soit lancé pour voir les résultats des stats de la partie.

<b>IMPORTANT</b> : le découpage se trouve sous le dossier "doc"


