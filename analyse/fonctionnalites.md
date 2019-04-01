# Fonctionnalités de l'application

## 0. Préambule

Une première analyse nous a menés à considérer trois fonctionnalités principales :
+ Configuration
+ Visualisation
+ Rapport Statistique

A celles-ci s'ajoutent d'autres fonctionnalités auxiliaires :
+ Ouverture et Fermeture
+ Menu Principal

## 1. Ouverture et Fermeture

### Ouverture
L'ouverture de l'application s'effectue en lançant l'exécutable. (quel type d'exécutable ? archive java ? Quel commande à lancé ?)

### Fermeture
Dans une version graphique, la fermeture de l'application s'effectue en fermant la fenêtre ou en cliquant sur une icône prévue à cet effet.

## 2. Menu principal
On peut envisager la présence d'un menu permettant d'accéder aux différentes fonctionnalités principales de la simulation (cf. sections 3).
Alternativement on peut implémenter un système d'onglets pour passer d'une fonctionnalité à l'autre.

## 3. Configuration
Le __panel de configuration__ de l'application permet à l'utilisateur de configurer divers aspect.

On distingue deux types de configurations : 
* celles qui concernent __l'affichage graphique__ ;
* celles qui concernent __la simulation elle-même__.
    Parmi ces dernières on divise entre :
    + La __configuration structurelle__ ;
    + La __configuration de flux__. 
### Configuration structurelle
Elle correspond à des informations structurelles de la simulation : la taille des voies, le type de carrefour, le nombre de carrefours en série... Ces informations doivent être définies avant le début de la simulation.

La __configuration structurelle__ sera donc effectuée avant toute simulation dans un panel dédié.

### Configuration de flux
Elle concerne le nombre de voitures par secondes, le nombre de piétons par seconde, la fréquence des feux... Ces informations peuvent évoluer au cours de la simulation.
La __configuration de flux__ pourra donc se trouver dans une colonne adjacente à la visualisation.
  
## 4. Visualisation
La Visualisation affiche l'état de la simulation à une étape donnée. L'état est mis à jour à une certaine fréquence (par exemple 1s)

