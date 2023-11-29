public interface Constants {

    // COSTANTI
    public final String COVER_CARD_PATH = "src\\assets\\card-back.png";
    public final String COVER_CARD_LEFT_PATH = "src\\assets\\card-back-left.png";
    public final String COVER_CARD_RIGHT_PATH = "src\\assets\\card-back-right.png";

    public final String UNO_PATH = "src\\assets\\logo.png";
    public final String BACKGROUND_IMAGE_PATH = "src\\assets\\backgrounds\\bgG.png";
    public final String MUTE_PATH = "src\\assets\\mute.png";
    public final String MUSIC_PATH = "audio\\UNO_track.wav";

    // percorsi carte

    public final String CARDS_PATH = "src\\assets\\cards-front";
    public final String ADD_2_CARDS_NAME = "D2";
    public final String ADD_4_CARDS_NAME = "D4W";
    public final String CHANGE_COLOR_NAME = "W";
    public final String BLOCK_CARD_NAME = "skip";
    public final String CHANGE_TURN_CARD_NAME = "_";

    public final String ROULES_COMBOBOX = "Regolamento";
    public final String QUIT_COMBOBOX = "Esci";
    public final String MUSIC_OFF_COMBOBOX = "Disattiva audio";
    public final String MUSIC_ON_COMBOBOX = "Attiva audio";

    // lista comandi
    public final String SKIP = "skip";
    public final String UNO = "uno";
    public final String PLAY = "play";
    public final String DRAW = "draw";
    public final String QUIT = "quit";
    public final String INIT_DECK = "init";
    public final String START = "start";
    public final static String SORT_BY_NUMBER = "sortByNumber";
    public final static String SORT_BY_COLOR = "sortByColor";
    public final static String DISCARDED_CARD = "discarded_card";
    public final static String LOSE = "lose";
    public final static String DRAW_USER = "draw_user";

    public final static String CARD_ADD_2_CARDS = "CardAdd2Cards";
    public final static String CARD_ADD_4_CARDS = "CardAdd4Cards";
    public final static String CARD_BLOCK = "CardBlock";
    public final static String CARD_CHANGE_COLOR = "CardChangeColor";
    public final static String CARD_CHANGE_TURN = "CardChangeTurn";
    public final static String CARD_NUMBER = "CardNumber";

    public final static String RED = "red";
    public final static String YELLOW = "yellow";
    public final static String BLUE = "blue";
    public final static String GREEN = "green";

    // messaggi di risposta
    public final static String CORRECT = "200";
    public final static String ERROR_USERNAME = "400";
    public final static String ERROR_CARD_PALYED = "406";
    public final static String WINNER = "201";
    public final static String ERROR_SKIP = "409";
    public final static String ERROR_EXIT = "500";

    // misure carte
    public final static Integer WIDTH_UNO_IMAGE = 150;
    public final static Integer HEIGHT_UNO_IMAGE = 150;

    public final static Integer WIDTH_CARDS = 100;
    public final static Integer HEIGHT_CARDS = 150;
}
