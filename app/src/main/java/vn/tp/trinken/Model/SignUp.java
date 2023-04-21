package vn.tp.trinken.Model;

public class SignUp {
    private String userName;
    private String email;
    private String password;
    private String repassword;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public SignUp(String userName, String email, String password, String repassword) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.repassword = repassword;
    }

    public SignUp(){

    }

}
