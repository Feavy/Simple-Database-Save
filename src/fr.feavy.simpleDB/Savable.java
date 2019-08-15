package fr.feavy.simpleDB;

public interface Savable {
}

/*
Le principe :

Des classes peuvent implémenter 'Savable'.
A ce moment-là ces dernières peuvent appeler la méthode 'save' de 'DatabaseManager' pour sauvegarder l'objet en DB.
Seuls les attributs marqués par l'annotation 'Save' sont sauvegardés.

A l'appel de la méthode 'loadData' de 'DatabaseManager' les données sont lues en DB et les objets sont créés.

 */