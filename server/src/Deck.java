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
    public void fillDeck()
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
            card.setColor("red");

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
            card.setColor("green");

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
            card.setColor("yellow");

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
            card.setColor("blue");

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
            card.setColor("red");

            //aggiungo la Card al Deck
            deck.add(card);
        }


        //carte verdi
        for(int i = 0; i < NUMBER_CARD_CHANGE_TURN; i++)
        {
            //creo una CardNumber
            Card card = new CardChangeTurn();

            //metto il colore verde alla Card
            card.setColor("green");

            //aggiungo la Card al Deck
            deck.add(card);
        }

        //carte gialle
        for(int i = 0; i < NUMBER_CARD_CHANGE_TURN; i++)
        {
            //creo una CardNumber
            Card card = new CardChangeTurn();

            //metto il colore giallo alla Card
            card.setColor("yellow");

            //aggiungo la Card al Deck
            deck.add(card);
        }

        //carte blu
        for(int i = 0; i < NUMBER_CARD_CHANGE_TURN; i++)
        {
            //creo una CardNumber
            Card card = new CardChangeTurn();

            //metto il colore blu alla Card
            card.setColor("blue");

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
            card.setColor("red");

            //aggiungo la Card al Deck
            deck.add(card);
        }


        //carte verdi
        for(int i = 0; i < NUMBER_BLOCK_CARD; i++)
        {
            //creo una CardNumber
            Card card = new CardBlock();

            //metto il colore verde alla Card
            card.setColor("green");

            //aggiungo la Card al Deck
            deck.add(card);
        }

        //carte gialle
        for(int i = 0; i < NUMBER_BLOCK_CARD; i++)
        {
            //creo una CardNumber
            Card card = new CardBlock();

            //metto il colore giallo alla Card
            card.setColor("yellow");

            //aggiungo la Card al Deck
            deck.add(card);
        }

        //carte blu
        for(int i = 0; i < NUMBER_BLOCK_CARD; i++)
        {
            //creo una CardNumber
            Card card = new CardBlock();

            //metto il colore blu alla Card
            card.setColor("blue");

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
            card.setColor("red");

            //aggiungo la Card al Deck
            deck.add(card);
        }


        //carte verdi
        for(int i = 0; i < NUMBER_ADD_2_CARDS_CARD; i++)
        {
            //creo una CardNumber
            Card card = new CardAdd2Cards();

            //metto il colore verde alla Card
            card.setColor("green");

            //aggiungo la Card al Deck
            deck.add(card);
        }

        //carte gialle
        for(int i = 0; i < NUMBER_ADD_2_CARDS_CARD; i++)
        {
            //creo una CardNumber
            Card card = new CardAdd2Cards();

            //metto il colore giallo alla Card
            card.setColor("yellow");

            //aggiungo la Card al Deck
            deck.add(card);
        }

        //carte blu
        for(int i = 0; i < NUMBER_ADD_2_CARDS_CARD; i++)
        {
            //creo una CardNumber
            Card card = new CardAdd2Cards();

            //metto il colore blu alla Card
            card.setColor("blue");

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

            //aggiungo la Card al Deck
            deck.add(card);
        }
    }

    /**
     * metodo per ripopolare il Deck vuoto con le carte nel Deck degli scarti
     * 
     * @param DiscardDeck nuovo Deck da mescolare e usare
     */
    public void repopulateDeck(List<Card> discardDeck)
    {
        /*
         * richiamato solo quando non ci sono più carte quindi Deck.size() = 0 --> già controllato nel metodo che richiama questo metodo
         *
         * metto le carte degli scarti, tranne l'ultima che rimane sul tavolo, nel mazzo
        */
        
        //scorro le carte scartate tranne l'ultima che rimarrà all'interno del mazzo degli scarti
        for(int i = 0; i < deck.size() - 1; i++)
        {
            //tolgo la carta dal mazzo degli scarti (discardDeck) e la metto nel mazzo da gioco (deck)
            deck.add(discardDeck.remove(i));
        }

        /*
         * mazzo degli scarti con solo l'ultima carta e mazzo da gioco rifatto
        */

        //mescolo il nuovo Deck in ordine casuale
        Collections.shuffle(deck);
        Collections.shuffle(deck);
        Collections.shuffle(deck);
        Collections.shuffle(deck);
        Collections.shuffle(deck);
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
     * metodo che ordina le carte in mano dell'utente per colore (red, blue, green, yellow, carte speciali)
     */
    public void sortCardsByColor()
    {
        //creo un mazzo temporaneo
        List<Card> lst = new ArrayList<Card>();

        //metto le carte rosse
        lst.addAll(pickCardsByColor(deck, "red"));

        //metto le carte blu
        lst.addAll(pickCardsByColor(deck, "blue"));

        //metto le carte verdi
        lst.addAll(pickCardsByColor(deck, "green"));

        //metto le carte gialle
        lst.addAll(pickCardsByColor(deck, "yellow"));

        //metto le carte speciali
        //le carti rimanenti nel mazzo sono tutte speciali quindi le inserisco nella lista senza controlli
        for (Card card : deck)
        {
            //aggiugno la carta alla lista
            lst.add(card);

            //rimuovo la carta dal mazzo così da fare meno controlli dopo
            deck.remove(card);
        }

        //il nuovo mazzo diventa la lista appena creata
        deck = lst;
    }

    /**
     * metodo che prende tutte le carte di un colore specifico (yellow, green, red, blue)
     * @param lst lista di carte da cui prendere le carte
     * @param color colore della carta da prendere
     * @return lista contentente le carte di quel colore
     */
    private List<Card> pickCardsByColor(List<Card> lst, String color)
    {
        //creo un mazzo temporaneo
        List<Card> tmp = new ArrayList<Card>();

        //per ogni carta nella lista
        for (Card card : lst)
        {
            //controllo il colore
            if(card.getColor().equals(color))
            {
                //inserisco nella lista tmp la carta
                tmp.add(card);

                //elimino la carta dalla lista lst
                lst.remove(card);
            }    
        }

        //ritorno le carte con quel colore
        return tmp;
    }

    /**
     * metodo che ordina le carte in mano dell'utente per numero (1 --> 9, carte senza numero e speciali)
     */
    public void sortCardsByNumber()
    {
        //creo un mazzo temporaneo
        List<Card> lst = new ArrayList<Card>();

        //metto gli 0
        lst.addAll(pickCardsByNumber(deck, 0));

        //metto gli 1
        lst.addAll(pickCardsByNumber(deck, 1));

        //metto gli 2
        lst.addAll(pickCardsByNumber(deck, 2));

        //metto gli 3
        lst.addAll(pickCardsByNumber(deck, 3));

        //metto gli 4
        lst.addAll(pickCardsByNumber(deck, 4));

        //metto gli 5
        lst.addAll(pickCardsByNumber(deck, 5));

        //metto gli 6
        lst.addAll(pickCardsByNumber(deck, 6));

        //metto gli 7
        lst.addAll(pickCardsByNumber(deck, 7));

        //metto gli 8
        lst.addAll(pickCardsByNumber(deck, 8));

        //metto gli 9
        lst.addAll(pickCardsByNumber(deck, 9));

        //metto le carte senza numero e speciali speciali
        //le carti rimanenti nel mazzo sono tutte senza numero e speciali quindi le inserisco nella lista senza controlli
        for (Card card : deck)
        {
            //aggiugno la carta alla lista
            lst.add(card);

            //rimuovo la carta dal mazzo così da fare meno controlli dopo
            deck.remove(card);
        }

        //il nuovo mazzo diventa la lista appena creata
        deck = lst;
    }

    /**
     * metodo che prende tutte le carte di un numero specifico (1 -> 9) 
     * @param lst lista di carte da cui prendere le carte
     * @param number numero della carta da prendere
     * @return lista contentente le carte di quel numero
     */
    private List<Card> pickCardsByNumber(List<Card> lst, int number)
    {
        //creo un mazzo temporaneo
        List<Card> tmp = new ArrayList<Card>();

        //per ogni carta nella lista
        for (Card card : lst)
        {
            //controllo il numero
            if(card.getNumber() == number)
            {
                //inserisco nella lista tmp la carta
                tmp.add(card);

                //elimino la carta dalla lista lst
                lst.remove(card);
            }    
        }

        //ritorno le carte con quel numero
        return tmp;
    }

    //FINE METODI PER MAZZO DEGLI UTENTI (carte che ha in mano)
}