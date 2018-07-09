package data;

public class Player {

    private int id;
    private String username;
    private String loginname;
    private String password;
    private int score;
    private boolean isAdmin;



    public Player(int id, String username, String loginname, String password) {
        this.id = id;
        this.username = username;
        this.loginname = loginname;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getLoginname() {
        return loginname;
    }

    public String getPassword() {
        return password;
    }

    public int getScore() {
        return score;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", loginname='" + loginname + '\'' +
                ", password='" + password + '\'' +
                ", score=" + score +
                ", isAdmin=" + isAdmin +
                '}';
    }

}
