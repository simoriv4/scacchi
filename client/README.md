# Client del progetto UNO-GAME di Riva, Gobbetto e Monti


## CLASSI LATO CLIENT
- App.java: contiene il main che mette in esecuzione il client.

- Card.java: è un'interfaccia che viene ereditata dai diversi tipi di carta del gioco. Contiene tutte le funzioni comuni a tutti i tipi di carta.ù

- CardAdd2Cards.java: è un tipo di carta normale, ha colore ma non numero. Una volta giocata fa aggiungere due carte al giocatore successivo.

- CardAdd4Cards.java: è un tipo di carta speciale, non ha colore nè numero. Una volta giocata fa aggiungere quattro carte al giocatore successivo e fa cambiare il colore in tavola al giocatore che ha giocato la carta.

- CardBlock.java: è un tipo di carta normale, ha colore ma non numero. Una volta giocata blocca il giocatore successivo che quindi salta il turno.

- CardChangeColor.java:è un tipo di carta speciale, non ha colore nè numero. Una volta giocata fa a cambiare il colore in tavola al giocatore che ha giocato la carta.

- CardChangeTurn.java: è un tipo di carta normale, ha colore ma non numero. Una volta giocata fa cambiare il giro delle giocate (da destra a sinitra o viceversa).

- CardNumber.java: è un tipo di carta normale, ha colore e numero. Quando viene giocata non succede nulla.

- Communication.java: è la classe che gestisce la comunicazione tra client e server.

- Constants.java: è la classe che contiene tutte le costanti utili lato client.

- Deck.java: è la classe che contiene il mazzo di carte.

- gamepage.java: è la classe che gestiche la pagina del gioco sviluppata con la libreria java Swing.

- homepage.java: è la classe che gestiche la pagina home dove l'utente inserisce lo username. Anche questa è sviluppata con la libreria java Swing.

- Message.java: è la classe che gestisce il messaggio che sarà poi inviato al server.

- MyLabel.java: è la classe usata per creare le Label personalizzte di java Swing. La classe eredita la classe Label di java Swing.

- MyMouseAdapter.java: è la classe che gestisce il click del mouse.

- Roules.java: è la classe delle regole che venogno impostare nei vari pulsanti creati con java Swing.

- Server.java: è la classe che gestisce il server. Prende le informazioni del server dal file xml "configServer.xml".

- User.java: è la classe che gestisce l'utente del gioco.