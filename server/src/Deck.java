import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * classe genertica di tipo Card (<E extends Card>) che contiene tutte le carte presenti in una partita
 */
public class Deck <E extends Card>  //<E extends Card> --> indica che la classe è generica di tipo Card
{
    /*
     * COSTANTI
     * 
     * 19 carte rosse da 1 a 9 (x2 volte) e uno 0
     * 19 carte verdi da 1 a 9 (x2 volte) e uno 0
     * 19 carte blu da 1 a 9 (x2 volte) e uno 0
     * 19 carte gialle da 1 a 9 (x2 volte) e uno 0
     * 8 carte cambia giro
     * 8 carte blocca
     * 8 carte aggiungi carte (+2)
     * 4 carte aggiungi carte (+4)
     * 4 carte cambia colore
    */
    /**
     * numero di carte per colore
     */
    private final static int NUMBER_CARD_NUMBERED = 19;

    /**
     * numero di carte per colore
     */
    private final static int NUMBER_CARD_CHANGE_TURN = 2;

    /**
     * numero di carte per colore
     */
    private final static int NUMBER_BLOCK_CARD = 2;

    /**
     * numero di carte per colore
     */
    private final static int NUMBER_ADD_2_CARDS_CARD = 2;

    /**
     * numero di carte speciali (non hanno colore)
     */
    private final static int NUMBER_ADD_4_CARDS_CARD = 4;

    /**
     * numero di carte speciali (non hanno colore)
     */
    private final static int NUMBER_CHANGE_COLOR_CARD = 4;

    //attributi della classe Deck
    public List<Card> deck;

    /**
     * costruttore non parametrico del Deck
     * 
     * crea il Deck generico
     */
    public Deck()
    {
        //creo il Deck generico
        deck = new ArrayList<Card>();

        //riempio il Deck con le carte
        fillDeck();
    }

    /** 
     * metodo per sapere quante carte rimangono nel Deck
     * 
     * @return int con numero di carte rimaste nel Deck
    */
    public int getSizeDeck()
    {
        return deck.size();
    }

    /**
     * metodo per riempire il Deck con tutti i tipi di carte del gioco
     */
    private void fillDeck()
    {
        //aggiungo al Deck le carte numerate di tutti i colore
        addNumberCardsToDeck();

        //aggiungo al Deck le carte cambia giro di tutti i colori
        addChangeTurnCardsToDeck();

        //aggiungo al Deck le carte cambia colore
        addChangeColorCardsToDeck();

        //aggiungo al Deck le carte blocca
        addBlockCardsToDeck();

        //aggiugno al Deck le carte aggiungi 2 carte
        add_Add2Cards_CardsToDeck();

        //aggiugno al Deck le carte aggiungi 4 carte
        add_Add4Cards_CardsToDeck();
        
        /*
         * ho riempito il Deck
         */

        //mescolo il Deck in ordine casuale
        Collections.shuffle(deck);
        Collections.shuffle(deck);
        Collections.shuffle(deck);
        Collections.shuffle(deck);
        Collections.shuffle(deck);
    }

    /**
     * metodo per aggiungere al Deck le carte numerate di tutti i colori
     */
    private void addNumberCardsToDeck()
    {
        /*
         * 19 carte ( da 1 a 9 (x2 volte) e uno 0 ) rosse
         * 19 carte ( da 1 a 9 (x2 volte) e uno 0 ) verdi
         * 19 carte ( da 1 a 9 (x2 volte) e uno 0 ) blu
         * 19 carte ( da 1 a 9 (x2 volte) e uno 0 ) gialle
         */

        //carte rosse
        for(int i = 0; i < NUMBER_CARD_NUMBERED; i++)
        {
            //creo una CardNumber
            Card card = new CardNumber();

            //metto il colore rosso alla Card
            card.setColor("rosso");

            //se i >= 10 
            if(i >= 10)
            {
                //aggiungo i-9 così da inserire al posto di 10 1, al posto di 11 2, e così via
                card.setNumber(i - 9);
            }
            else    //i < 10 (da 0 1 9)
                card.setNumber(i);   //aggiugno da 0 a 9

            //aggiungo la Card al Deck
            deck.add(card);
        }


        //carte verdi
        for(int i = 0; i < NUMBER_CARD_NUMBERED; i++)
        {
            //creo una CardNumber
            Card card = new CardNumber();

            //metto il colore verde alla Card
            card.setColor("verde");

            //se i >= 10 
            if(i >= 10)
            {
                //aggiungo i-9 così da inserire al posto di 10 1, al posto di 11 2, e così via
                card.setNumber(i - 9);
            }
            else    //i < 10 (da 0 1 9)
                card.setNumber(i);   //aggiugno da 0 a 9

            //aggiungo la Card al Deck
            deck.add(card);
        }

        //carte gialle
        for(int i = 0; i < NUMBER_CARD_NUMBERED; i++)
        {
            //creo una CardNumber
            Card card = new CardNumber();

            //metto il colore giallo alla Card
            card.setColor("giallo");

            //se i >= 10 
            if(i >= 10)
            {
                //aggiungo i-9 così da inserire al posto di 10 1, al posto di 11 2, e così via
                card.setNumber(i - 9);
            }
            else    //i < 10 (da 0 1 9)
                card.setNumber(i);   //aggiugno da 0 a 9

            //aggiungo la Card al Deck
            deck.add(card);
        }

        //carte blu
        for(int i = 0; i < NUMBER_CARD_NUMBERED; i++)
        {
            //creo una CardNumber
            Card card = new CardNumber();

            //metto il colore blu alla Card
            card.setColor("blu");

            //se i >= 10 
            if(i >= 10)
            {
                //aggiungo i-9 così da inserire al posto di 10 1, al posto di 11 2, e così via
                card.setNumber(i - 9);
            }
            else    //i < 10 (da 0 1 9)
                card.setNumber(i);   //aggiugno da 0 a 9

            //aggiungo la Card al Deck
            deck.add(card);
        }
    }

    /**
     * metodo per aggiungere al Deck le carte cambia giro di tutti i colori
     */
    private void addChangeTurnCardsToDeck()
    {
        /*
         * 2 carte cambia giro rosse
         * 2 carte cambia giro verdi
         * 2 carte cambia giro blu
         * 2 carte cambia giro gialle
         */

        //carte rosse
        for(int i = 0; i < NUMBER_CARD_CHANGE_TURN; i++)
        {
            //creo una CardNumber
            Card card = new CardChangeTurn();

            //metto il colore rosso alla Card
            card.setColor("rosso");

            //aggiungo la Card al Deck
            deck.add(card);
        }


        //carte verdi
        for(int i = 0; i < NUMBER_CARD_CHANGE_TURN; i++)
        {
            //creo una CardNumber
            Card card = new CardChangeTurn();

            //metto il colore verde alla Card
            card.setColor("verde");

            //aggiungo la Card al Deck
            deck.add(card);
        }

        //carte gialle
        for(int i = 0; i < NUMBER_CARD_CHANGE_TURN; i++)
        {
            //creo una CardNumber
            Card card = new CardChangeTurn();

            //metto il colore giallo alla Card
            card.setColor("giallo");

            //aggiungo la Card al Deck
            deck.add(card);
        }

        //carte blu
        for(int i = 0; i < NUMBER_CARD_CHANGE_TURN; i++)
        {
            //creo una CardNumber
            Card card = new CardChangeTurn();

            //metto il colore blu alla Card
            card.setColor("blu");

            //aggiungo la Card al Deck
            deck.add(card);
        }
    }

    /**
     * metodo per aggiungere al Deck le carte cambia colore
     */
    private void addChangeColorCardsToDeck()
    {
        /*
         * 4 carte cambia colore
         */

        for(int i = 0; i < NUMBER_CHANGE_COLOR_CARD; i++)
        {
            //creo una CardChangeColor
            Card card = new CardChangeColor();

            //metto il colore rosso alla Card
            card.setColor("rosso");

            //aggiungo la Card al Deck
            deck.add(card);
        }
    }

    /**
     * metodo per aggiungere al Deck le carte blocca di tutti i colori
     */
    private void addBlockCardsToDeck()
    {
        /*
         * 2 carte blocca rosse
         * 2 carte blocca verdi
         * 2 carte blocca blu
         * 2 carte blocca gialle
         */

        //carte rosse
        for(int i = 0; i < NUMBER_BLOCK_CARD; i++)
        {
            //creo una CardNumber
            Card card = new CardBlock();

            //metto il colore rosso alla Card
            card.setColor("rosso");

            //aggiungo la Card al Deck
            deck.add(card);
        }


        //carte verdi
        for(int i = 0; i < NUMBER_BLOCK_CARD; i++)
        {
            //creo una CardNumber
            Card card = new CardBlock();

            //metto il colore verde alla Card
            card.setColor("verde");

            //aggiungo la Card al Deck
            deck.add(card);
        }

        //carte gialle
        for(int i = 0; i < NUMBER_BLOCK_CARD; i++)
        {
            //creo una CardNumber
            Card card = new CardBlock();

            //metto il colore giallo alla Card
            card.setColor("giallo");

            //aggiungo la Card al Deck
            deck.add(card);
        }

        //carte blu
        for(int i = 0; i < NUMBER_BLOCK_CARD; i++)
        {
            //creo una CardNumber
            Card card = new CardBlock();

            //metto il colore blu alla Card
            card.setColor("blu");

            //aggiungo la Card al Deck
            deck.add(card);
        }
    }

    /**
     * metodo per aggiungere al Deck le carte aggiungi 2 carte di tutti i colori
     */
    private void add_Add2Cards_CardsToDeck()
    {
        /*
         * 2 carte aggiugni 2 carte rosse
         * 2 carte aggiugni 2 carte verdi
         * 2 carte aggiugni 2 carte blu
         * 2 carte aggiugni 2 carte gialle
         */

        //carte rosse
        for(int i = 0; i < NUMBER_ADD_2_CARDS_CARD; i++)
        {
            //creo una CardNumber
            Card card = new CardAdd2Cards();

            //metto il colore rosso alla Card
            card.setColor("rosso");

            //aggiungo la Card al Deck
            deck.add(card);
        }


        //carte verdi
        for(int i = 0; i < NUMBER_ADD_2_CARDS_CARD; i++)
        {
            //creo una CardNumber
            Card card = new CardAdd2Cards();

            //metto il colore verde alla Card
            card.setColor("verde");

            //aggiungo la Card al Deck
            deck.add(card);
        }

        //carte gialle
        for(int i = 0; i < NUMBER_ADD_2_CARDS_CARD; i++)
        {
            //creo una CardNumber
            Card card = new CardAdd2Cards();

            //metto il colore giallo alla Card
            card.setColor("giallo");

            //aggiungo la Card al Deck
            deck.add(card);
        }

        //carte blu
        for(int i = 0; i < NUMBER_ADD_2_CARDS_CARD; i++)
        {
            //creo una CardNumber
            Card card = new CardAdd2Cards();

            //metto il colore blu alla Card
            card.setColor("blu");

            //aggiungo la Card al Deck
            deck.add(card);
        }
    }

    /**
     * metodo per aggiungere al Deck le carte aggiungi 4 carte
     */
    private void add_Add4Cards_CardsToDeck()
    {
        /*
         * 4 carte aggiugni 4 carte
         */

        for(int i = 0; i < NUMBER_ADD_4_CARDS_CARD; i++)
        {
            //creo una CardChangeColor
            Card card = new CardAdd4Cards();

            //metto il colore rosso alla Card
            card.setColor("rosso");

            //aggiungo la Card al Deck
            deck.add(card);
        }
    }

    /**
     * metodo per ripopolare il Deck vuoto con le carte nel Deck degli scarti
     * 
     * @param DiscardDeck nuovo Deck da mescolare e usare
     */
    public void repopulateDeck(List<Card> DiscardDeck)
    {
        /*
         * richiamato solo quando non ci sono più carte quindi Deck.size() = 0 --> già controllato nel metodo che richiama questo metodo
         *
         * metto le carte degli scarti tranne l'ultima che rimane sul tavolo
        */
        
        //.............
    }










    //INIZIO METODI PER MAZZO DEGLI SCARTI

    /**
     * metodo per ottenere il colore della carta in cima al mazzo degli scarti
     * @return colore della carta in cima al mazzo degli scarti
     */
    public String getColorCardOnTop()
    {
        //la carta in cima al mazzo si trova all'ultima posizione della lista
        return deck.get(deck.size() - 1).getColor();
    }

    /**
     * metodo per ottenere il numero della carta in cima al mazzo degli scarti
     * @return numero della carta in cima al mazzo degli scarti
     */
    public int getNumberCardOnTop()
    {
        //la carta in cima al mazzo si trova all'ultima posizione della lista
        return deck.get(deck.size() - 1).getNumber();
    }

    //FINE METODI PER MAZZO DEGLI SCARTI










    //INIZIO METODI PER MAZZO DEGLI UTENTI (carte che ha in mano)

    /**
     * metodo che ordina le carte in mano dell'utente per colore (rosso, blu, verde, giallo, carte speciali)
     */
    public void sortCardsByColor()
    {
        //metto le carte rosse

        //metto le carte blu

        //metto le carte verdi

        //metto le carte gialle

        //metto le carte speciali
    }

    /**
     * metodo che ordina le carte in mano dell'utente per numero (1 --> 9, carte senza numero e speciali)
     */
    public void sortCardsByNumber()
    {
        //ordino i numeri

        //metto le carte senza numero e speciali
    }

    //FINE METODI PER MAZZO DEGLI UTENTI (carte che ha in mano)
}