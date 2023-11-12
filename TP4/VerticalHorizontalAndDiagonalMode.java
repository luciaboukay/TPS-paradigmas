package linea;
public class VerticalHorizontalAndDiagonalMode extends GameMode {

    public VerticalHorizontalAndDiagonalMode() {
        super('C');
    }

    public boolean rojoGanaPartida(Linea partida) {
        return partida.triunfoDiagonalDeJugador(Linea.RedPlayed) || partida.jugadorganoHorizontal(Linea.RedPlayed) || partida.jugadorganoVertical(Linea.RedPlayed);
    }

    public boolean azulGanaPartida(Linea partida) {
        return partida.triunfoDiagonalDeJugador(Linea.BluePlayed) || partida.jugadorganoHorizontal(Linea.BluePlayed) || partida.jugadorganoVertical(Linea.BluePlayed);
    }

    public String show(){
        return "Estrategia seleccionada: Vertical, Horizontal y Diagonal";
    }
}
