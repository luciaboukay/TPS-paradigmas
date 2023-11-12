package linea;
public class DiagonalMode extends GameMode {

    public DiagonalMode() {
        super('B');
    }

    public boolean rojoGanaPartida(Linea partida) {
        return partida.triunfoDiagonalDeJugador(Linea.RedPlayed);
    }

    public boolean azulGanaPartida(Linea partida) {
        return partida.triunfoDiagonalDeJugador(Linea.BluePlayed);
    }

    public String show(){
        return "Estrategia seleccionada: Diagonal";
    }
}