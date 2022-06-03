package project.com.icarus.db;

public class User {
    // fields
    private int userID;
    private String userName;
    private String createdDate;
    private String userRole;
    private int createdBy;
    private int isActive;
    private String password;

    // constructors
    public User() {}
    public User(String username,  String userrole, String  createddate, int createdBy ) {

        this.userName = username;
        this.userRole = userrole;
        this.createdBy = createdBy;
        this.createdDate = createddate;
        this.isActive = 1;
    }
    // properties
    public void setUserID(int userid) {
        this.userID = userid;
    }
    public int getUserID() {
        return this.userID;
    }

    public void setUserName(String username) {
        this.userName = username;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
    public String getUserRole() {
        return this.userRole;
    }

    public void setCreatedDate(String createddate) {
        this.createdDate = createddate;
    }
    public String getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedBy(int createdby) {
        this.createdBy = createdBy;
    }
    public int getCreatedBy() {
        return this.createdBy;
    }

    public void setActive(int isactive) {
        this.isActive = isactive;
    }
    public int getActive() {
        return this.isActive;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return this.password;
    }

}