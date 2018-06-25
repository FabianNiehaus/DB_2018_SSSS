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

}
