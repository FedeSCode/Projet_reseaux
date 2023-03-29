package app.Server;

import java.util.Objects;

public class User {
    private String username;
    private String password;
    private int id;

    public User(String username) {
        this.username = username;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(Integer currentId,String username) {
        this.id = currentId++;
        this.username = username;
        password = "ok";
    }



    /*getter*/
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    /*setter*/
    public void setUsername(String username) {
        this.username = username;
    }

    /*methods*/


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
