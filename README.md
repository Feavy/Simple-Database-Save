# Simple Java Database (*WIP*)

## A propos
Bibliothèque Java permettant de sauvegarder simplement des objets dans une base de données.

*! Ce projet est encore en cours de développement !*
## Utilisation
### Initialisation :
```java
// Exemple avec l'utilisation d'une base de données Oracle XE :
DriverManager.registerDriver(new OracleDriver());  
Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "test", "admin");
// Création de l'instance :
DatabaseManager.createInstance(connection);  
```
### Sauvegarde d'un objet :
#### Définition des attributs à sauvegarder :
```java
public class Player {
    @Save(primary = true, // Clé primaire
	      args = {  
            @Arg(type = SQLType.DataType.NUMBER, value = "5")  // Stocké dans un NUMBER(5)
	      }
	)  
    private int id = 0;  // Valeur par défaut : 0
  
  @Save(args = {  
            @Arg(type = SQLType.DataType.NUMBER, value = "2")  // Stocké dans un NUMBER(2)
    })  
    private int health = 10;  // Valeur par défaut : 10
  
  @Save(args = {  
            @Arg(type = SQLType.DataType.NUMBER, value = "16")  // Stocké dans un VARCHAR(16)
    })  
    private String name = "Undefined";  // Valeur par défaut : 'Undefined'
  
    /* Constructeur par défaut obligatoire (si d'autres constructeurs existent) : */  
    public Player() {  
    }  
  
    public Player(String name, int health) {  
        this.name = name;  
        this.health = health;  
    }  
}
```
#### Sauvegarde en base de données :
```Java
DatabaseManager databaseManager = DatabaseManager.getInstance();  
Player player = new Player("Jean" , 20);  
databaseManager.save(player);
```
## Roadmap

1. Sauvegarde des attributs ayant un type primitif.
2. Mise à jour automatique de la base de données lorsque la structure d'une classe change.
3. Sauvegarde des attributs complexes (autre objet devant être sauvegardé → clé étrangère).
