package linea;
public class Tie extends StateOfGame {

    public StateOfGame nextState(Linea partida){
        return new Tie();
    }

    public void moveBlue(){
        throw new RuntimeException(alreadyFinishedGameError);
    }
    public void moveRed(){
        throw new RuntimeException(alreadyFinishedGameError);
    }

    public boolean isFinished(Linea partida){
        return true;
    }

    public String show(){
        return "Resultado: Empate";
    }

    public boolean isBlueWin(){
        return false;
    }

    public boolean isRedWin(){
        return false;
    }

    public boolean isBlueTurn(){
        return false;
    }

    public boolean isRedTurn(){
        return false;
    }
}

