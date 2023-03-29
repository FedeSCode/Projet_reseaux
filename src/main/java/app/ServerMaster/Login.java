package app.ServerMaster;

public class Login {

    public Login(String username, String password, String checkPassword) {
    }

    public boolean isAuth(String username, String password, String checkPassword){
        boolean isAuth=false;
        if(username != null){
            if(password.equals(checkPassword)){
                isAuth=true;
            }
        }
        return isAuth;
    }

}
