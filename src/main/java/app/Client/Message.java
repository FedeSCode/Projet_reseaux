package app.Client;

public class Message {
    private static Long currentId = 0L;
    String message;
    User username;
    Long id;

    public Message(String message, User username) {
        this.id = ++currentId;
        this.message = message;
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUsername() {
        return username;
    }

    public void setUsername(User username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
