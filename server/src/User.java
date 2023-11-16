public class User
{
    public Integer port;
    // bolleana che permette di capire se sia il turno di questo utente
    public Boolean round;
    // booleana che permette di capire se ha vinto
    public Boolean win;
    // booleana che permette di capire se ha detto UNO
    public Boolean isUno;

    public String IP;
    public String userName;

    public Deck<Card> cards;

    /**
     * costruttore parametrico
     * @param port
     * @param IP
     * @param round
     * @param win
     * @param isUno
     * @param userName
     */
    public User(Integer port, String IP, Boolean round, Boolean win, Boolean isUno, String userName) {
        this.port = port;
        this.round = round;
        this.win = win;
        this.IP = IP;
        this.userName = userName;
        this.isUno = isUno;

        cards = new Deck<Card>();
    }
    /**
     * costruttore non parametrico
     */
    public User() {
        this.port = null;
        this.round = false;
        this.win = false;
        this.isUno = false;
        this.IP = "127.0.0.1"; // localhost come predefinito
        this.userName = "userName";
    }

    /**
     * metodo per aggiungere una carta alle carte dell'utente
     * 
     * @param card carta da aggiungere alle carte dell'utente
     */
    public void addCard(Card card)
    {
        cards.addCard(card);
    }
}
