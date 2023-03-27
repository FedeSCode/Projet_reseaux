package app.Server;

import java.util.Objects;

public class User {
    private static int currentId = 0;
    private String username;
    private final int id;

    public User(String username) {
        this.id = ++currentId;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof User)
            return ((User) o).getUsername().equals(username);
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.username);
    }

    @Override
    public String toString() {
        return username;
    }
}
