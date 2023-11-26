import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
/**
 * classe genertica di tipo Card (<E extends Card>) che contiene tutte le carte presenti in una partita
 */
public class Deck <E extends Card>  //<E extends Card> --> indica che la classe è generica di tipo Card
{
    //COSTANTI
    /**
     * numero di carte, per colore, da 1 a 9 (x2 volte) e uno 0
     */
    private final static int NUMBER_CARD_NUMBERED = 19;

    /**
     * numero di carte, per colore, cambia giro
     */
    private final static int NUMBER_CARD_CHANGE_TURN = 2; 

    /**
     * numero di carte, per colore, blocca turno
     */
    private final static int NUMBER_BLOCK_CARD = 2;

    /**
     * numero di carte, per colore, aggiungi 2 carte
     */
    private final static int NUMBER_ADD_2_CARDS_CARD = 2;  

    /**
     * numero di carte speciali (non hanno colore), aggiungi 4 carte
     */
    private final static int NUMBER_ADD_4_CARDS_CARD = 4;

    /**
     * numero di carte speciali (non hanno colore), cambia colore
     */
    private final static int NUMBER_CHANGE_COLOR_CARD = 4;

    /*
     * numero di carte da distribuire all'inizio all'utente
     */
    private final static int MAX_CARD_USER = 7;

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
     * metodo per aggiungere una carta al Deck
     * 
     * @param card carta da aggiungere al deck
     */
    public void addCard(Card card)
    {
        deck.add(card);
    }

    /**
     * metodo per ottenere la prima carta del mazzo
     * @return prima carta del mazzo
     */
    public Card getCard()
    {
        return deck.remove(0);
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
        //carte red
        addNumberCardsToDeck("red");

        //carte green
        addNumberCardsToDeck("green");

        //carte yellow
        addNumberCardsToDeck("yellow");

        //carte blue
        addNumberCardsToDeck("blue");
    }

    /**
     * metodo per aggiungere al Deck le carte CardNumber del colore passato come parametro
     */
    private void addNumberCardsToDeck(String color)
    {
        //per tutte le volte che devo aggiungere una carta CardNumber
        for(int i = 0; i < NUMBER_CARD_NUMBERED; i++)
        {
            //creo una CardNumber
            Card card = new CardNumber();

            //metto il colore rosso alla Card
            card.setColor(color);

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
     * metodo che crea il mazzo da dare all'utente appena entra in partita
     * @return l'oggetto deck
     */
    public Deck<Card> initUserDeck()
    {
        Deck<Card> tmp = new Deck<>();
        for(int i = 0; i < MAX_CARD_USER; i++)
            tmp.addCard(getCard());
        
        return tmp;
    }

    public String serializeDeck() throws ParserConfigurationException, TransformerException
    {
        // istanzio il documento per creare la stringa XML
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        DocumentBuilder b = f.newDocumentBuilder();
        Document d = b.newDocument();

        // creo la root dell'XML
        Element root = d.createElement("root_message");
        // aggiungo alla root i nodi 
        for(Card c: this.deck)
        {
            root.appendChild(c.serialize(d));
        }

        // aggiungo la root al documento
        d.appendChild(root);

        // SALVA SU STRINGA
        // Creare un oggetto Transformer per la trasformazione in stringa
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(d), new StreamResult(writer));
        String xmlString = writer.toString();

        return xmlString;
    }


    /**
     * metodo per aggiungere al Deck le carte cambia giro di tutti i colori
     */
    private void addChangeTurnCardsToDeck()
    {
        //carte red
        addChangeTurnCardsToDeck("red");

        //carte green
        addChangeTurnCardsToDeck("green");

        //carte yellow
        addChangeTurnCardsToDeck("yellow");

        //carte blue
        addChangeTurnCardsToDeck("blue");
    }

    /**
     * metodo per aggiungere al Deck le carte CardChangeTurn del colore passato come parametro
     */
    private void addChangeTurnCardsToDeck(String color)
    {
        //per tutte le volte che devo aggiungere una carta CardChangeTurn
        for(int i = 0; i < NUMBER_CARD_CHANGE_TURN; i++)
        {
            //creo una CardChangeTurn
            Card card = new CardChangeTurn();

            //metto il colore alla Card
            card.setColor(color);

            //aggiungo la Card al Deck
            deck.add(card);
        }
    }



    /**
     * metodo per aggiungere al Deck le carte blocca di tutti i colori
     */
    private void addBlockCardsToDeck()
    {
        //carte red
        addBlockCardsToDeck("red");

        //carte green
        addBlockCardsToDeck("green");

        //carte yellow
        addBlockCardsToDeck("yellow");

        //carte blue
        addBlockCardsToDeck("blue");
    }

    /**
     * metodo per aggiungere al Deck le carte CardBlock del colore passato come parametro
     */
    private void addBlockCardsToDeck(String color)
    {
        //per tutte le volte che devo aggiungere una carta CardBlock
        for(int i = 0; i < NUMBER_BLOCK_CARD; i++)
        {
            //creo una CardBlock
            Card card = new CardBlock();

            //metto il colore alla Card
            card.setColor(color);

            //aggiungo la Card al Deck
            deck.add(card);
        }
    }



    /**
     * metodo per aggiungere al Deck le carte aggiungi 2 carte di tutti i colori
     */
    private void add_Add2Cards_CardsToDeck()
    {
        //carte red
        add_Add2Cards_CardsToDeck("red");

        //carte green
        add_Add2Cards_CardsToDeck("green");

        //carte yellow
        add_Add2Cards_CardsToDeck("yellow");

        //carte blue
        add_Add2Cards_CardsToDeck("blue");
    }

    /**
     * metodo per aggiungere al Deck le carte Add2Cards del colore passato come parametro
     */
    private void add_Add2Cards_CardsToDeck(String color)
    {
        //per tutte le volte che devo aggiungere una carta Add2Cards
        for(int i = 0; i < NUMBER_ADD_2_CARDS_CARD; i++)
        {
            //creo una CardAdd2Cards
            Card card = new CardAdd2Cards();

            //metto il colore alla Card
            card.setColor(color);

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
     * metodo per aggiungere al Deck le carte cambia colore
     */
    private void addChangeColorCardsToDeck()
    {
        //per tutte le volte che devo aggiungere una carta CardChangeColor
        for(int i = 0; i < NUMBER_CHANGE_COLOR_CARD; i++)
        {
            //creo una CardChangeColor
            Card card = new CardChangeColor();

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
    /**
     * metodo per ottenere la carta in cima al mazzo degli scarti
     * @return la carta in cima al mazzo degli scarti
     */
    public Card getCardOnTop()
    {
        return deck.get(deck.size() - 1);
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
        // clono il mazzo per non avere problemi con l'eliminazione della carte
        List<Card> tmp2 = new ArrayList<Card>();
        for (Card card : lst)
        {
            tmp2.add(card);
        }

        //per ogni carta nella lista
        for (Card card : tmp2)
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