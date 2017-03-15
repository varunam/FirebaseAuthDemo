package app.firebaseauthdemo.com.firebaseauthdemo;

/**
 * Created by vaam on 15-03-2017.
 */
public class UserInformation {

    public String fullName;
    public String Address;

    public UserInformation(){

    }

    public UserInformation(String fullName, String address) {
        Address = address;
        this.fullName = fullName;
    }
}
