Biblibouille
============

Application de gestion d'une bibliothèque


INSTALLATION DU PROJET
======================

Sous eclipse:
=============
	* Importer le projet depuis le repository GitHub
	* Placer vous dans le répertoire du projet, et lancer la commande play dependencies
	* Lancer également la commande play eclipsify
	* Revenez sous Eclipse et rafraichissez le projet
	* Modifier le classpath du projet pour rajouter les répertoire secure et crud en tant que répertoires sources du projet
	
	A partir de là, vous pouvez soit lancer le projet en ligne de commande (play run) soit installer le plugin eclipse de Play pour lancer le projet directement depuis eclipse.


CREATION DATABASE:
==================

CREATE DATABASE biblibouille;

GRANT ALL PRIVILEGES ON *.* TO 'bibliowner'@'localhost'
    -> IDENTIFIED BY '<password>' WITH GRANT OPTION;

Export de la database:
mysqldump biblibouille -u root > create_biblibouille.dmp

Création de la database:
mysql -u root < create_biblibouille.sql
mysql -u bibliowner -h ec2-23-21-211-172.compute-1.amazonaws.com -p < create_biblibouille.sql
