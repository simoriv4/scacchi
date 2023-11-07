public class Utente {
    Boolean turno;
    Boolean vinto;
    String userName;
    /**
     * costruttore parametrico
     * @param turno
     * @param vinto
     * @param userName
     */
    public Utente(Boolean turno, Boolean vinto, String userName) {
        this.turno = turno;
        this.vinto = vinto;
        this.userName = userName;
    }
    /**
     * costruttore non parametrico
     */
    public Utente() {
        this.turno = false;
        this.vinto = false;
        this.userName = "userName";
    }

    
}
