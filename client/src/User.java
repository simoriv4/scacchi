public class User {
    public Boolean turno;
    public Boolean vinto;
    public Boolean isUno;
    public String userName;

    /**
     * costruttore parametrico
     * @param turno
     * @param vinto
     * @param userName
     */
    public User(Boolean turno, Boolean vinto, Boolean isUno, String userName) {
        this.turno = turno;
        this.vinto = vinto;
        this.userName = userName;
        this.isUno = isUno;
    }
    /**
     * costruttore non parametrico
     */
    public User() {
        this.turno = false;
        this.vinto = false;
        this.isUno = false;
        this.userName = "userName";
    }

    
}
