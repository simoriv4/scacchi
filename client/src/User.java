public class User {
    public Boolean round;
    public Boolean win;
    public Boolean isUno;
    public String userName;

    /**
     * costruttore parametrico
     * @param round
     * @param win
     * @param userName
     */
    public User(Boolean round, Boolean win, Boolean isUno, String userName) {
        this.round = round;
        this.win = win;
        this.userName = userName;
        this.isUno = isUno;
    }
    /**
     * costruttore non parametrico
     */
    public User() {
        this.round = false;
        this.win = false;
        this.isUno = false;
        this.userName = "userName";
    }

    
}
