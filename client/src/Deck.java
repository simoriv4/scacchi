import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
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

    private final static String CARD_ADD_2_CARDS = "cardAdd2Cards"; 
    private final static String CARD_ADD_4_CARDS = "cardAdd4Cards"; 
    private final static String CARD_BLOCK = "cardBlock"; 
    private final static String CARD_CHANGE_COLOR = "cardChangeColor"; 
    private final static String CARD_CHANGE_TURN = "cardChangeTurn"; 
    private final static String CARD_NUMBER = "cardNumber"; 

    // comandi
    private final static String SORT_BY_NUMBER = "sortByNumber";
    private final static String SORT_BY_COLOR = "sortByColor"; 

    // messaggi di risposta
    private final static String CORRECT = "200";
    private final static String ERROR_USERNAME = "400";
    private final static String ERROR_CARD_PALYED = "406";
    private final static String WINNER = "201";
    private final static String ERROR_SKIP = "409";
    private final static String ERROR_EXIT = "500";



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

    //FINE METODI PER MAZZO DEGLI SCARTI










    //INIZIO METODI PER MAZZO DEGLI UTENTI (carte che ha in mano)

    /**
     * metodo che ordina le carte in mano dell'utente per colore (red, blue, green, yellow, carte speciali)
     * @throws TransformerException
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public void sortCardsByColor(Communication c, User u) throws IOException, ParserConfigurationException, TransformerException, SAXException
    {
        // creo un messaggio che invierò al server
        Message m = new Message(u.isUno, SORT_BY_COLOR, u.userName, "");
        // invio il messaggio
        c.sendMessage(m);

        // attendo risposta
        String reply = c.listening();
        m.InitMessageFromStringXML(reply);
        // controllo che l'operazione sia avvenuta correttamente
        if(m.command == CORRECT)
            unserializeDeck(m.message);
    }

    /**
     * metodo che ordina le carte in mano dell'utente per numero (1 --> 9, carte senza numero e speciali)
     * @throws TransformerException
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public String sortCardsByNumber(Communication c, User u) throws IOException, ParserConfigurationException, TransformerException, SAXException
    {
        // creo un messaggio che invierò al server
        Message m = new Message(u.isUno, SORT_BY_NUMBER, u.userName, "");
        // invio il messaggio
        c.sendMessage(m);

        // attendo risposta
        String reply = c.listening();
        m.InitMessageFromStringXML(reply);
        // controllo che l'operazione sia avvenuta correttamente
        if(m.command == CORRECT)
        {
            unserializeDeck(m.message);
            return CORRECT; // ritorno la correttezza del messaggio
        }
        else
            return m.message; // ritorno il messaggio di errore che stamperò con una message box        
    }

    /**
     * metodo che permette di trasformare la lista di carte passate in stringa XML in una lista di carte
     * @param message
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public void unserializeDeck(String message) throws SAXException, IOException, ParserConfigurationException {
        // istanzio il documento per creare la stringa XML
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        DocumentBuilder b = f.newDocumentBuilder();
        // creo un documento XML dalla stringa in formato XML passata
        Document d = b.parse(new InputSource(new StringReader(message)));

        // prendo tutte le carte del mazzo
        NodeList cards = d.getElementsByTagName("card");

        // svuoto il mazzo
        this.deck = new ArrayList<Card>();

        // per ogni carta controllo il tipo e poi unserializzo l'oggetto e lo aggiungo alla lista temporanea 
        for (int i = 0; i < cards.getLength(); i++)
        {
            Element element = (Element) cards.item(i);

            NamedNodeMap attributes = element.getAttributes();
            // salvo il tipo di carta da unserializzare
            String type = attributes.item(0).getTextContent();
            switch (type) {
                case CARD_ADD_2_CARDS:
                    // prendo le carte cardAdd2Cards
                    NodeList add2Cards = d.getElementsByTagName("cardAdd2Cards");

                    CardAdd2Cards c = new CardAdd2Cards();
                    c.unserialize(add2Cards.item(i));
                    // aggiungo alla lista temporanea di carta l'oggetto unserializzato
                    this.deck.add(c);
                    break;
                case CARD_ADD_4_CARDS:
                    // prendo le carte cardAdd4Cards
                    NodeList add4Cards = d.getElementsByTagName("cardAdd4Cards");
                    CardAdd4Cards c2 = new CardAdd4Cards();

                    c2.unserialize(add4Cards.item(i));
                    // aggiungo alla lista temporanea di carta l'oggetto unserializzato
                    this.deck.add(c2);
                    break;
                case CARD_BLOCK:
                    // prendo le carte addBlock
                    NodeList cardBlock = d.getElementsByTagName("cardBlock");

                    CardBlock c3 = new CardBlock();

                    c3.unserialize(cardBlock.item(i));
                    // aggiungo alla lista temporanea di carta l'oggetto unserializzato
                    this.deck.add(c3);
                    break;
                case CARD_NUMBER:
                    // prendo le carte cardChangeTurn
                    NodeList cardChangeTurn = d.getElementsByTagName("cardChangeTurn");

                    CardChangeTurn c4 = new CardChangeTurn();

                    c4.unserialize(cardChangeTurn.item(i));
                    // aggiungo alla lista temporanea di carta l'oggetto unserializzato
                    this.deck.add(c4);
                    break;
                case CARD_CHANGE_COLOR:
                    // prendo le carte cardChangeColor
                    NodeList cardChangeColor = d.getElementsByTagName("cardChangeColor");

                    CardChangeColor c5 = new CardChangeColor();

                    c5.unserialize(cardChangeColor.item(i));
                    // aggiungo alla lista temporanea di carta l'oggetto unserializzato
                    this.deck.add(c5);                    
                    break;
                case CARD_CHANGE_TURN:
                    // prendo le carte cardAdd2Cards
                    NodeList cardNumber = d.getElementsByTagName("cardNumber");

                    CardNumber c6 = new CardNumber();

                    c6.unserialize(cardNumber.item(i));
                    // aggiungo alla lista temporanea di carta l'oggetto unserializzato
                    this.deck.add(c6); 
                    break;
            }
        }
    }

    //FINE METODI PER MAZZO DEGLI UTENTI (carte che ha in mano)
}