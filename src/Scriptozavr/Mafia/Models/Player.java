package Scriptozavr.Mafia.Models;


public class Player {
    String nick;
    int position;
    String role;

    //Constructor
    public Player() {

    }

    public Player(String _nick, int _position, String _role) {
        nick = _nick;
        position = _position;
        role = _role;
    }
}
