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

    public void setAdmin(boolean admin, String pasword) {
        String functionPassword = "i3ßfnzr984jf02!";
        if (password.equals(functionPassword)) {
            isAdmin = admin;
        }
    }

}
