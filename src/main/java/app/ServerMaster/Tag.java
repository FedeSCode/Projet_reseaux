package app.ServerMaster;

import java.util.Objects;

public class Tag {
    private String name;

    public Tag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    @Override
    public boolean equals(Object o){
        if(o instanceof Tag)
            return ((Tag) o).getName().equals(name);
        return false;
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(this.name);
    }

    @Override
    public String toString() {
        return name;
    }
}
