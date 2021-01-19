package backend;
public class User implements Clonable{
    private String email;
    private String password;
    private String userType;
    private String name;
    private String address;
    private int phoneNumber;

    public User(String email, String password, String userType, String name, String address, int phoneNumber) {
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public Object copy() {
        return new User(this.email, this.password, this.userType, this.name, this.address, this.phoneNumber);
    }
}
