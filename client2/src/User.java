public class User {
    Boolean turno;
    Boolean vinto;
    String userName;
    /**
     * costruttore parametrico
     * @param turno
     * @param vinto
     * @param userName
     */
    public User(Boolean turno, Boolean vinto, String userName) {
        this.turno = turno;
        this.vinto = vinto;
        this.userName = userName;
    }
    /**
     * costruttore non parametrico
     */
    public User() {
        this.turno = false;
        this.vinto = false;
        this.userName = "userName";
    }

    
}
