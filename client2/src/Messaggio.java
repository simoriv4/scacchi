class Messsaggio{
    // comando
    // messsaggio-->
                // username
                // carte
    String comando;
    String messaggio;

    /**
     * costruttore parametrico
     * @param comando
     * @param messaggio
     */
    public Messsaggio(String comando, String messaggio) {
        this.comando = comando;
        this.messaggio = messaggio;
    }

    /**
     * costruttore non parametrico
     */
    public Messsaggio() {
        this.comando = "";
        this.messaggio = "";
    }

    public String serializeMessaggio()
    {
        return null;
    }
    
}