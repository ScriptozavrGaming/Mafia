package Scriptozavr.Mafia.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Player implements Parcelable {

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
        return String.format("%2d%11s%9s%7s%3d", this.position, this.nick,this.role, this.status, this.faults);
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

    public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>() {
        public Player createFromParcel(Parcel source) {
            return new Player(source);
        }

        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nick);
        dest.writeInt(position);
        dest.writeString(role);
        dest.writeString(status);
        dest.writeInt(faults);
    }

    private Player(Parcel source) {
        nick = source.readString();
        position = source.readInt();
        role = source.readString();
        status = source.readString();
        faults = source.readInt();
    }
}
