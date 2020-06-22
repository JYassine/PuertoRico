# Architecture . md

# Organisant composants Spring

![40% center](./decomposition_composant.pdf)

# Documentation architecture REST+Spring

# Scénario de démarrage

Lors du lancement de partie il faut ouvrir le serveur de statistiques et le serveur de jeu. 

Pour que la partie soit lancé, trois joueurs doivent se connecter au moteur de jeu, une fois que les trois sont connectés la partie commence, et le gouverneur commence le tour.

# Déroulement de partie

# Déroulement du tour :
- Le serveur de moteur de jeu envoie une requête au gouverneur (joueur qui commence le tour)
- Une fois que le gouverneur reçoit l'ordre de jouer, il joue en faisant un choix (choix de son robot) et retourne le choix au moteur de jeu
- Le moteur de jeu mémorise le choix et met à jour les différents éléments du jeu
- Le moteur de jeu envoie une requête aux joueurs restants (ceux qui n'ont pas encore joués)
- Les joueurs jouent puis le tour est fini (erreur de règle, le tour ne devrait pas être fini)

# Déroulement fin de partie
- Une fois la partie terminé, le moteur de jeu envoie une requête au serveur de stats pour reçevoir le calcul des stats
- Le serveur de stats envoie les stats et ferment la connexion
- Le serveur de jeu demandent aux joueurs de se déconnecter
- Le serveur de jeu se déconnecte

# Documentation route et architecture REST

Il n'y a pas eu l'ajout d'une architecture REST dans les conteneurs Spring