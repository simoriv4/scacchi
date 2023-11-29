# Server del progetto UNO-GAME di Riva, Gobbetto e Monti

## CARTELLE

### SRC
Contiene tutte le classi del progetto lato client

#### CLASSI LATO CLIENT
- App.java: contiene il main che mette in esecuzione il server.

- Card.java: è un'interfaccia che viene ereditata dai diversi tipi di carta del gioco. Contiene tutte le funzioni comuni a tutti i tipi di carta.

- CardAdd2Cards.java: è un tipo di carta normale, ha colore ma non numero. Una volta giocata fa aggiungere due carte al giocatore successivo.

- CardAdd4Cards.java: è un tipo di carta speciale, non ha colore nè numero. Una volta giocata fa aggiungere quattro carte al giocatore successivo e fa cambiare il colore in tavola al giocatore che ha giocato la carta.

- CardBlock.java: è un tipo di carta normale, ha colore ma non numero. Una volta giocata blocca il giocatore successivo che quindi salta il turno.

- CardChangeColor.java:è un tipo di carta speciale, non ha colore nè numero. Una volta giocata fa a cambiare il colore in tavola al giocatore che ha giocato la carta.

- CardChangeTurn.java: è un tipo di carta normale, ha colore ma non numero. Una volta giocata fa cambiare il giro delle giocate (da destra a sinitra o viceversa).

- CardNumber.java: è un tipo di carta normale, ha colore e numero. Quando viene giocata non succede nulla.

- Communication.java: è la classe che gestisce la comunicazione tra client e server.

- Constants.java: è la classe che contiene tutte le costanti utili lato client.

- Deck.java: è la classe che contiene il mazzo di carte.

- Game.java: è la classe che gestisce il gioco. Aspetta le richieste del client, le elabora e invia una risposta.

- Listeningthread.java: è la classe che ascolta la socket di comunicazione per vedere se arriva wualche richiesta da qualche client.

- Message.java: è la classe che gestisce il messaggio che sarà poi inviato al server.

- MyTimerTask.java: è la classe che gestisce il timer, tempo entro il quale devono avvenire certi eventi. Il tempo del timer è diverso per ogni evento.

- StarGameThread.java: è la classe che fa partire il gioco per ogni client che entra nella partita.

- User.java: è la classe che gestisce l'utente del gioco.

- Users.java: è la classe che gestisce tutti gli utenti del gioco.