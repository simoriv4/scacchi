import java.util.ArrayList;
import java.util.List;

/**
 * classe che gestisce le carte scartate
 */
public class CardsDiscarded
{
    //attributi
    public List<Card> listCards;

    /**
     * attributo non parametrico
     * 
     * crea la lista delle carte scartate
     */
    public CardsDiscarded()
    {
        listCards = new ArrayList<Card>();
    }

    /**
     * metodo per aggiungere una carta (scartata da un utente) alla lista di carte scartate 
     * @param card carta, scartata da un utente, da aggiugnere alla lista di carte scartate 
     */
    public void addCard(Card card)
    {
        listCards.add(card);
    }

    /**
     * metodo per ottenere la lista di carte scartate
     * @return lista di carte scartate
     */
    public List<Card> getListCardsDiscarded()
    {
        return listCards;
    }

    /**
     * metodo per ottenere il colore della carta in cima al mazzo degli scarti
     * @return colore della carta in cima al mazzo degli scarti
     */
    public String getColorCardOnTop()
    {
        //la carta in cima al mazzo si trova all'ultima posizione della lista
        return listCards.get(listCards.size() - 1).getColor();
    }

    /**
     * metodo per ottenere il numero della carta in cima al mazzo degli scarti
     * @return numero della carta in cima al mazzo degli scarti
     */
    public int getNumberCardOnTop()
    {
        //la carta in cima al mazzo si trova all'ultima posizione della lista
        return listCards.get(listCards.size() - 1).getNumber();
    }
}
