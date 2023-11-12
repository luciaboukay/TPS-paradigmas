package linea;
public class VerticalAndHorizontalMode extends GameMode {

    public VerticalAndHorizontalMode() {
        super('A');
    }

    public boolean azulGanaPartida(Linea partida) {
        return partida.jugadorganoHorizontal(Linea.BluePlayed) || partida.jugadorganoVertical(Linea.BluePlayed);
    }

    public boolean rojoGanaPartida(Linea partida) {
        return partida.jugadorganoHorizontal(Linea.RedPlayed) || partida.jugadorganoVertical(Linea.RedPlayed);
    }

    public String show(){
        return "Estrategia seleccionada: Vertical y Horizontal";
    }

}
