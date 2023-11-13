/**
 * classe generica che contiene i metodi che deve avere ogni Card
 */
public interface Card
{
    /**
     * metodo per impostare il colore alla carta
     * @param color colore da impostare
     */
    void setColor(String color);

    /**
     * metodo per ottenere il colore della carta
     * @return colore della carta
     */
    String getColor();
    
    /**
     * metodo per impostare il numero della carta
     * @param number numero da impostare
     */
    void setNumber(int number);

    /**
     * metodo per ottenere il numero della carta
     * @return numero della carta
     */
    int getNumber();

    /**
     * metodo per controllare se una carta è giocabile oppure no
     * @param color colore in cima al mazzo
     * @param number numero in cima al mazzo
     * @return true: carta giocabile --- false: carta non giocabile
     */
    boolean isPlayable(String color, int number);
}
