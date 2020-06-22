# Analyse . md

Ce fichier markdown présente l'équipe de développement ainsi que les points faibles et forts du repository.

## Projet
### Avantages

- La décomposition moteur jeu - moteur joueur - moteur stats est modulaire et flexible, par la suite si le projet devait continuer, l'ajout de l'architecture REST par dessus les web sockets ne poseraient pas de problème.

- L'organisation du GIT a été plutôt réussi, chaque commit effectué par un de nos membres, permet de savoir
 les tâches qu'il a effectués et permet de mieux retracer le suivi du projet dans sa globalité.


### Défauts

- Le code des Dockerfiles (présents à la racine du repository) est *théorique*. Ces fichiers ont été écrits dans le but d'encapsuler les ressources nécéssaires
minimales pour faire tourner trois conteneurs (ServerGame, ServerPlayer, et Server (le serveur de stats)), mais ils buildent plus 
de ressources que nécéssaire (le pom.xml à la racine est lancé pour n'importe quel container). De plus pour des raisons techniques ils n'ont pas pu être correctement testés.

- Le plus gros défaut de notre projet est qu'il manque cruellement de tests unitaire et de tests d'intégrations, ainsi la qualité de notre logiciel n'est pas viable.

- La documentation est inexistante.

## Equipe

**Kévin Alessandro** : conteneurisation

**Jrad Yassine** : travail sur le découpage moteur serveur - moteur player - serveur stats, gestion du git

**Otmane Rahim** : travail sur le  découpage moteur serveur - moteur player - serveur stats

**Alexandre denos** : travail sur le découpage moteur serveur - moteur player 

**Remy Munier** : Participation à l'architecture du découpage dans sa globalité 
