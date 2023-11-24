public class User {
    public Boolean round;
    public Boolean win;
    public Boolean isUno;
    public String userName;

    public Deck<Card> deck;

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
        this.deck = new Deck<>();
    }
    /**
     * costruttore non parametrico
     */
    public User() {
        this.round = true;  // PER DEBUG
        this.win = false;
        this.isUno = false;
        this.userName = "userName";
        this.deck = new Deck<>();
    }



    
}
