package fr.feavy.simpleDB;

public class Player {
    @Save(primary = true, type = "NUMBER(5)")
    private int id;

    @Save(type = "NUMBER(2) DEFAULT 20 NOT NULL")
    private int health = 20;

    @Save(type = "VARCHAR(16) DEFAULT 'Undefined' NOT NULL")
    private String name = "Undefined";

    @Save(type = "NUMBER(1) DEFAULT 0 NOT NULL")
    private boolean isAdmin = false;

    @Save(type = "NUMBER(5) DEFAULT 0 NOT NULL")
    private int xp = 0;

    public Player() {}

    public Player(String name, int health, int xp, boolean isAdmin) {
        this.health = health;
        this.name = name;
        this.isAdmin = isAdmin;
        this.xp = xp;
    }
}
