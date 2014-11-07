package Scriptozavr.Mafia.Models;

public class Player {

    private String nick;
    private int position;
    private String role;
    private String status;
    private int faults;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("%2d%11s%6s%3d", this.position, this.nick, this.status, this.faults);
    }

    public Player() {
    }

    public Player(String _nick, int _position, String _role, String _status, int _faults) {
        nick = _nick;
        position = _position;
        role = _role;
        status = _status;
        faults = _faults;
    }

    public int getFaults() {
        return faults;
    }

    public void setFaults(int faults) {
        this.faults = faults;
    }
}
