package zpi.flattery.controllers.model;

import zpi.flattery.models.User;

public class UserRequest {

    private User user;

    private String password;

    public UserRequest() {

    }

    public UserRequest(User user, String password){
        this.user = user;
        this.password = password;
    }

    public User getUser() {
        return this.user;
    }

    public String getPassword(){
        return this.password;
    }
}
