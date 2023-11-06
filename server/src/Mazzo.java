import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * classe genertica di tipo Carta (<E extends Carta>) che contiene tutte le carte presenti in una partita
 */
public class Mazzo <E extends Carta>  //<E extends Carta> --> indica che la classe è generica di tipo Carta
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
    private final static int NUMERO_CARTE_NUMERATE = 19;

    /**
     * numero di carte per colore
     */
    private final static int NUMERO_CARTE_CAMBIA_GIRO = 2;

    /**
     * numero di carte per colore
     */
    private final static int NUMERO_CARTE_BLOCCA = 2;

    /**
     * numero di carte per colore
     */
    private final static int NUMERO_CARTE_AGGIUNGI_2_CARTE = 2;

    private final static int NUMERO_CARTE_AGGIUNGI_4_CARTE = 4;
    private final static int NUMERO_CARTE_CAMBIA_COLORE = 4;

    //attributi della classe Mazzo
    public List<Carta> mazzo;

    /**
     * costruttore non parametrico del Mazzo
     * 
     * crea il Mazzo generico
     */
    public Mazzo()
    {
        //creo il Mazzo generico
        mazzo = new ArrayList<Carta>();

        //riempio il mazzo con le carte
        riempiMazzo();
    }

    /** 
     * metodo per sapere quante carte rimangono nel Mazzo
     * 
     * @return int con numero di carte rimaste nel Mazzo
    */
    public int getSizeMazzo()
    {
        return mazzo.size();
    }

    /**
     * metodo per ripopolare il mazzo vuoto con le carte nel mazzo degli scarti
     * 
     * @param mazzoScarti nuovo mazzo da mescolare e usare
     */
    public void ripopolaMazzo(List<Carta> mazzoScarti)
    {
        /*
         * richiamato solo quando non ci sono più carte quindi mazzo.size() = 0 --> già controllato nel metodo che richiama questo metodo
         *
         * metto le carte degli scarti tranne l'ultima che rimane sul tavolo
        */
        
        //.............
    }

    /**
     * metodo per riempire il Mazzo con tutti i tipi di carte del gioco
     */
    private void riempiMazzo()
    {
        //aggiungo al Mazzo le carte numerate di tutti i colore
        aggiungiAlMazzoCarteNumerate();

        //aggiungo al mazzo le carte cambia giro di tutti i colori
        aggiungiAlMazzoCarteCambiaGiro();

        //aggiungo al Mazzo le carte cambia colore
        aggiungiAlMazzoCarteCambiaColore();

        //aggiungo al Mazzo le carte blocca
        aggiungiAlMazzoCarteBlocca();

        //aggiugno al Mazzo le carte aggiungi 2 carte
        aggiungiAlMazzoCarteAggiungi2Carte();

        //aggiugno al Mazzo le carte aggiungi 4 carte
        aggiungiAlMazzoCarteAggiungi4Carte();
        
        /*
         * ho riempito il Mazzo
         */

        //mescolo il mazzo in ordine casuale
        Collections.shuffle(mazzo);
        Collections.shuffle(mazzo);
        Collections.shuffle(mazzo);
        Collections.shuffle(mazzo);
        Collections.shuffle(mazzo);
    }

    /**
     * metodo per aggiungere al Mazzo le carte numerate di tutti i colori
     */
    private void aggiungiAlMazzoCarteNumerate()
    {
        /*
         * 19 carte ( da 1 a 9 (x2 volte) e uno 0 ) rosse
         * 19 carte ( da 1 a 9 (x2 volte) e uno 0 ) verdi
         * 19 carte ( da 1 a 9 (x2 volte) e uno 0 ) blu
         * 19 carte ( da 1 a 9 (x2 volte) e uno 0 ) gialle
         */

        //carte rosse
        for(int i = 0; i < NUMERO_CARTE_NUMERATE; i++)
        {
            //creo una CartaNumerata
            Carta carta = new CartaNumerata();

            //metto il colore rosso alla carta
            carta.setColore("rosso");

            //se i >= 10 
            if(i >= 10)
            {
                //aggiungo i-9 così da inserire al posto di 10 1, al posto di 11 2, e così via
                carta.setNumero(i - 9);
            }
            else    //i < 10 (da 0 1 9)
                carta.setNumero(i);   //aggiugno da 0 a 9

            //aggiungo la carta al mazzo
            mazzo.add(carta);
        }


        //carte verdi
        for(int i = 0; i < NUMERO_CARTE_NUMERATE; i++)
        {
            //creo una CartaNumerata
            Carta carta = new CartaNumerata();

            //metto il colore verde alla carta
            carta.setColore("verde");

            //se i >= 10 
            if(i >= 10)
            {
                //aggiungo i-9 così da inserire al posto di 10 1, al posto di 11 2, e così via
                carta.setNumero(i - 9);
            }
            else    //i < 10 (da 0 1 9)
                carta.setNumero(i);   //aggiugno da 0 a 9

            //aggiungo la carta al mazzo
            mazzo.add(carta);
        }

        //carte gialle
        for(int i = 0; i < NUMERO_CARTE_NUMERATE; i++)
        {
            //creo una CartaNumerata
            Carta carta = new CartaNumerata();

            //metto il colore giallo alla carta
            carta.setColore("giallo");

            //se i >= 10 
            if(i >= 10)
            {
                //aggiungo i-9 così da inserire al posto di 10 1, al posto di 11 2, e così via
                carta.setNumero(i - 9);
            }
            else    //i < 10 (da 0 1 9)
                carta.setNumero(i);   //aggiugno da 0 a 9

            //aggiungo la carta al mazzo
            mazzo.add(carta);
        }

        //carte blu
        for(int i = 0; i < NUMERO_CARTE_NUMERATE; i++)
        {
            //creo una CartaNumerata
            Carta carta = new CartaNumerata();

            //metto il colore blu alla carta
            carta.setColore("blu");

            //se i >= 10 
            if(i >= 10)
            {
                //aggiungo i-9 così da inserire al posto di 10 1, al posto di 11 2, e così via
                carta.setNumero(i - 9);
            }
            else    //i < 10 (da 0 1 9)
                carta.setNumero(i);   //aggiugno da 0 a 9

            //aggiungo la carta al mazzo
            mazzo.add(carta);
        }
    }

    /**
     * metodo per aggiungere al Mazzo le carte cambia giro di tutti i colori
     */
    private void aggiungiAlMazzoCarteCambiaGiro()
    {
        /*
         * 2 carte cambia giro rosse
         * 2 carte cambia giro verdi
         * 2 carte cambia giro blu
         * 2 carte cambia giro gialle
         */

        //carte rosse
        for(int i = 0; i < NUMERO_CARTE_CAMBIA_GIRO; i++)
        {
            //creo una CartaNumerata
            Carta carta = new CartaCambiaGiro();

            //metto il colore rosso alla carta
            carta.setColore("rosso");

            //aggiungo la carta al mazzo
            mazzo.add(carta);
        }


        //carte verdi
        for(int i = 0; i < NUMERO_CARTE_CAMBIA_GIRO; i++)
        {
            //creo una CartaNumerata
            Carta carta = new CartaCambiaGiro();

            //metto il colore verde alla carta
            carta.setColore("verde");

            //aggiungo la carta al mazzo
            mazzo.add(carta);
        }

        //carte gialle
        for(int i = 0; i < NUMERO_CARTE_CAMBIA_GIRO; i++)
        {
            //creo una CartaNumerata
            Carta carta = new CartaCambiaGiro();

            //metto il colore giallo alla carta
            carta.setColore("giallo");

            //aggiungo la carta al mazzo
            mazzo.add(carta);
        }

        //carte blu
        for(int i = 0; i < NUMERO_CARTE_CAMBIA_GIRO; i++)
        {
            //creo una CartaNumerata
            Carta carta = new CartaCambiaGiro();

            //metto il colore blu alla carta
            carta.setColore("blu");

            //aggiungo la carta al mazzo
            mazzo.add(carta);
        }
    }

    /**
     * metodo per aggiungere al Mazzo le carte cambia colore
     */
    private void aggiungiAlMazzoCarteCambiaColore()
    {
        /*
         * 4 carte cambia colore
         */

        for(int i = 0; i < NUMERO_CARTE_CAMBIA_COLORE; i++)
        {
            //creo una CartaCambiaColore
            Carta carta = new CartaCambiaColore();

            //metto il colore rosso alla carta
            carta.setColore("rosso");

            //aggiungo la carta al mazzo
            mazzo.add(carta);
        }
    }

    /**
     * metodo per aggiungere al Mazzo le carte blocca di tutti i colori
     */
    private void aggiungiAlMazzoCarteBlocca()
    {
        /*
         * 2 carte blocca rosse
         * 2 carte blocca verdi
         * 2 carte blocca blu
         * 2 carte blocca gialle
         */

        //carte rosse
        for(int i = 0; i < NUMERO_CARTE_BLOCCA; i++)
        {
            //creo una CartaNumerata
            Carta carta = new CartaBlocca();

            //metto il colore rosso alla carta
            carta.setColore("rosso");

            //aggiungo la carta al mazzo
            mazzo.add(carta);
        }


        //carte verdi
        for(int i = 0; i < NUMERO_CARTE_BLOCCA; i++)
        {
            //creo una CartaNumerata
            Carta carta = new CartaBlocca();

            //metto il colore verde alla carta
            carta.setColore("verde");

            //aggiungo la carta al mazzo
            mazzo.add(carta);
        }

        //carte gialle
        for(int i = 0; i < NUMERO_CARTE_BLOCCA; i++)
        {
            //creo una CartaNumerata
            Carta carta = new CartaBlocca();

            //metto il colore giallo alla carta
            carta.setColore("giallo");

            //aggiungo la carta al mazzo
            mazzo.add(carta);
        }

        //carte blu
        for(int i = 0; i < NUMERO_CARTE_BLOCCA; i++)
        {
            //creo una CartaNumerata
            Carta carta = new CartaBlocca();

            //metto il colore blu alla carta
            carta.setColore("blu");

            //aggiungo la carta al mazzo
            mazzo.add(carta);
        }
    }

    /**
     * metodo per aggiungere al Mazzo le carte aggiungi 2 carte di tutti i colori
     */
    private void aggiungiAlMazzoCarteAggiungi2Carte()
    {
        /*
         * 2 carte aggiugni 2 carte rosse
         * 2 carte aggiugni 2 carte verdi
         * 2 carte aggiugni 2 carte blu
         * 2 carte aggiugni 2 carte gialle
         */

        //carte rosse
        for(int i = 0; i < NUMERO_CARTE_AGGIUNGI_2_CARTE; i++)
        {
            //creo una CartaNumerata
            Carta carta = new CartaAggiungi2Carte();

            //metto il colore rosso alla carta
            carta.setColore("rosso");

            //aggiungo la carta al mazzo
            mazzo.add(carta);
        }


        //carte verdi
        for(int i = 0; i < NUMERO_CARTE_AGGIUNGI_2_CARTE; i++)
        {
            //creo una CartaNumerata
            Carta carta = new CartaAggiungi2Carte();

            //metto il colore verde alla carta
            carta.setColore("verde");

            //aggiungo la carta al mazzo
            mazzo.add(carta);
        }

        //carte gialle
        for(int i = 0; i < NUMERO_CARTE_AGGIUNGI_2_CARTE; i++)
        {
            //creo una CartaNumerata
            Carta carta = new CartaAggiungi2Carte();

            //metto il colore giallo alla carta
            carta.setColore("giallo");

            //aggiungo la carta al mazzo
            mazzo.add(carta);
        }

        //carte blu
        for(int i = 0; i < NUMERO_CARTE_AGGIUNGI_2_CARTE; i++)
        {
            //creo una CartaNumerata
            Carta carta = new CartaAggiungi2Carte();

            //metto il colore blu alla carta
            carta.setColore("blu");

            //aggiungo la carta al mazzo
            mazzo.add(carta);
        }
    }

    /**
     * metodo per aggiungere al Mazzo le carte aggiungi 4 carte
     */
    private void aggiungiAlMazzoCarteAggiungi4Carte()
    {
        /*
         * 4 carte aggiugni 4 carte
         */

        for(int i = 0; i < NUMERO_CARTE_AGGIUNGI_4_CARTE; i++)
        {
            //creo una CartaCambiaColore
            Carta carta = new CartaAggiungi4Carte();

            //metto il colore rosso alla carta
            carta.setColore("rosso");

            //aggiungo la carta al mazzo
            mazzo.add(carta);
        }
    }
}