package Scriptozavr.Mafia;

/**
 * Created by aleksejfilobok on 01.11.14.
 */
public class Player {
    String nick;
    int position;
    String role;
    //Constructor
    /*public Player()
    {

    }*/
    public Player(String _nick, int _position, String _role)
    {
        nick = _nick;
        position = _position;
        role = _role;
    }
}
