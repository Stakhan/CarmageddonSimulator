# Fonctionnalités liées aux unités d'espace et de temps

L’unité de base est définie par la taille et le déplacement d’un piéton. 
En effet, un piéton avance à une vitesse moyenne de 3 km/h, ce qui équivaut à un peu plus de 0,8 m/s. Cette vitesse minimale correspond à un déplacement d’une case par tour dans notre simulation. 
- 1 case = 0,8 mètre 
- 1 tour = 1 seconde 

Ainsi, une cellule correspond à une superficie de 0,8*0,8 m², soit à peu près l’espace couvert par un piéton sur le trottoir. Une voiture “par défaut” couvre un rectangle de 5\*3 cellules, soit 4*2,4 mètres environ. 
En ville, une voiture est limitée à 50 km/h, soit une avancée de 50/3 = 17 cases en un tour. 
Le code de la route donne le calcul suivant pour la distance d’arrêt = distance parcourue pendant le temps de réaction + distance de freinage. 
Temps de réaction = 1 seconde (conducteur en forme, sinon le double voire le triple), soit Dist = Vitesse * Temps. 
Distance de freinage = Vkmh * 3/10 (en m/s). 
Pour 50 km/h, un arrêt brusque nécessite (50000/3600 + 50*3/10) * 3600/3000  
soit 50/3 + 5 * 3600/1000 = 17 + 18 = 35 cases. 


# Durée des feux

http://www.equipementsdelaroute.equipement.gouv.fr/IMG/pdf/IISR_6ePARTIE_vc20120402_cle573dda.pdf
Feu jaune : 3-5 secondes
Attente : 120 secondes max
Feu vert : 6 secondes min
