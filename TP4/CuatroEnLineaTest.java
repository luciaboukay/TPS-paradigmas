package linea;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class CuatroEnLineaTest {

    @Test
    public void test01NewGameIsNotFinished() {
        Linea game = game7x6Mode('A');
        assertFalse( game.finished() );
        assertFalse(game.blueWon());
        assertFalse(game.redWon());
    }

    @Test
    public void test02RedMovesFirstByDefault(){
        assertTrue(game7x6Mode('A').goesRed());
    }

    @Test
    public void test03NewGameShowEmptyBoard() {
        assertEquals("Juego: 7x6\nModo: Estrategia seleccionada: Vertical y Horizontal\n| - | - | - | - | - | - | - |\n| - | - | - | - | - | - | - |\n| - | - | - | - | - | - | - |\n| - | - | - | - | - | - | - |\n| - | - | - | - | - | - | - |\n| - | - | - | - | - | - | - |\nPartida en curso: Turno de Rojo", game7x6Mode('A').show());
    }

    @Test
    public void test04RedMakesAMoveAndChangesTurno(){
        assertGoesBlueConditions(gameStartsWithRedPlayingInTheFirstColumn(7, 6, 'A'));
    }

    @Test
    public void test05BlueMakesAMoveAndChangesTurno(){
        Linea game = gameStartsWithRedPlayingInTheFirstColumn(7, 6, 'A');
        game.playBlueAt(2);
        assertGoesRedConditions(game);
    }

    @Test
    public void test06ShowFunctionsCorrectlyAfterRedMakesAMove() {
        assertEquals("Juego: 7x6\nModo: Estrategia seleccionada: Vertical y Horizontal\n| - | - | - | - | - | - | - |\n| - | - | - | - | - | - | - |\n| - | - | - | - | - | - | - |\n| - | - | - | - | - | - | - |\n| - | - | - | - | - | - | - |\n| X | - | - | - | - | - | - |\nPartida en curso: Turno de Azul", gameStartsWithRedPlayingInTheFirstColumn(7, 6, 'A').show());
    }

    @Test
    public void test07RedPlayerCantPlayTwiceInARow(){
        Linea game = gameStartsWithRedPlayingInTheFirstColumn(7, 6, 'A');
        assertGoesBlueConditions(game);
        assertThrowsLike( () -> game.playRedAt(1), StateOfGame.notPlayersTurnError);
    }

    @Test
    public void test08BluePlayerCantPlayTwiceInARow(){
        Linea game = gameStartsWithRedPlayingInTheFirstColumn(7, 6, 'A');
        game.playBlueAt(2);
        assertGoesRedConditions(game);
        assertThrowsLike( () -> game.playBlueAt(1), StateOfGame.notPlayersTurnError);
    }

    @Test
    public void test09GameWithUnsupportedDimensionsEndsJustWhenItsCreated(){
        assertTrue(new Linea( -1, 0, 'A' ).finished());
    }

    @Test
    public void test10ErrorIsThrownIfSelectedGameModeIsNotAvailable(){
        assertThrowsLike( () -> new Linea( 7, 6, 'D' ), GameMode.notAvailableGameModeError);
    }

    @Test
    public void test11PlayerCantPlayInAColumnGreaterThanTheBase(){
        assertThrowsLike( () -> new Linea(6, 1, 'A' ).playRedAt(7), Linea.InvalidPositionError);
    }

    @Test
    public void test12PlayerCantPlayInColumnThatDoesNotExist(){
        assertThrowsLike( () -> new Linea(6, 1, 'A' ).playRedAt(-1), Linea.InvalidPositionError);
    }

    @Test
    public void test13PlayerCantPlayAtFullColumn(){
        assertThrowsLike( () -> gameStartsWithRedPlayingInTheFirstColumn(6,1,'A').playBlueAt(1), Linea.InvalidPositionError);
    }

    @Test
    public void test14GameFinishesWhenThereIsTieInGameModeA(){
        assertTieConditions(game2x2FinishedByTie('A'));
    }

    @Test
    public void test15ShowFunctionsCorrectlyWhenThereIsATieInGameModeA(){
        assertEquals("Juego: 2x2\nModo: Estrategia seleccionada: Vertical y Horizontal\n| X | O |\n| X | O |\nResultado: Empate", game2x2FinishedByTie('A').show());
    }

    @Test
    public void test16gameFinishesWhenPlayerWinsHorizontallyInGameModeA(){
        assertRedWonConditions(gameRedHorizontalResult('A'));
    }

    @Test
    public void test17ShowFunctionsCorrectlyWhenPlayerWinsHorizontallyInGameModeA(){
        assertEquals("Juego: 4x2\nModo: Estrategia seleccionada: Vertical y Horizontal\n| O | O | O | - |\n| X | X | X | X |\nResultado: Gano Rojo", gameRedHorizontalResult('A').show());
    }

    @Test
    public void test18GameFinishesWhenPlayerWinsVerticallyInGameModeA(){
        assertRedWonConditions(gameRedVerticalResult('A'));
    }

    @Test
    public void test19ShowFunctionsCorrectlyWhenPlayerWinsVerticallyInGameModeA(){
        assertEquals("Juego: 2x4\nModo: Estrategia seleccionada: Vertical y Horizontal\n| X | - |\n| X | O |\n| X | O |\n| X | O |\nResultado: Gano Rojo", gameRedVerticalResult('A').show());
    }

    @Test
    public void test20DiagonalWinIsNotRecognisedInGameModeA(){
        Linea game = gameWithRedDiagonalResult(4, 4, 'A');
        assertFalse(game.finished());
        assertFalse(game.redWon());
    }

    @Test
    public void test21GameFinishesWhenThereIsATieInGameModeB(){
        assertTieConditions(game2x2FinishedByTie('B'));
    }

    @Test
    public void test22ShowFunctionsCorrectlyWhenThereIsATieInGameModeB(){
        assertEquals("Juego: 2x2\nModo: Estrategia seleccionada: Diagonal\n| X | O |\n| X | O |\nResultado: Empate", game2x2FinishedByTie('B').show());
    }

    @Test
    public void test23GameFinishesWhenPlayerWinsDiagonallyInGameModeB(){
        assertRedWonConditions( gameWithRedDiagonalResult(4,4,'B'));
    }

    @Test
    public void test24ShowFunctionsCorrectlyWhenPlayerWinsDiagonallyInGameModeB(){
        assertEquals("Juego: 4x4\nModo: Estrategia seleccionada: Diagonal\n| - | - | - | X |\n| - | - | X | X |\n| O | X | X | O |\n| X | O | O | O |\nResultado: Gano Rojo", gameWithRedDiagonalResult(4, 4, 'B').show());
    }

    @Test
    public void test25DiagonalFromRightToLeftIsRecognisedAsWin(){
        Linea game = new Linea(5, 4, 'B');
        game.playRedAt(5);
        game.playBlueAt(4);
        game.playRedAt(4);
        game.playBlueAt(3);
        game.playRedAt(2);
        game.playBlueAt(3);
        game.playRedAt(3);
        game.playBlueAt(2);
        game.playRedAt(1);
        game.playBlueAt(2);
        game.playRedAt(2);
        assertRedWonConditions(game);
    }

    @Test
    public void test26HorizontalWinIsNotRecognisedInGameModeB(){
        Linea game = gameRedHorizontalResult('B');
        assertFalse(game.finished());
        assertFalse(game.redWon());
    }

    @Test
    public void test27VerticalWinIsNotRecognisedInGameModeB() {
        Linea game = gameRedVerticalResult('B');
        assertFalse(game.finished());
        assertFalse(game.redWon());
    }

    @Test
    public void test28GameFinishesWhenThereIsATieInGameModeC(){
        assertTieConditions(game2x2FinishedByTie('C'));
    }

    @Test
    public void test29ShowFunctionsCorrectlyWhenThereIsATieInGameModeC(){
        assertEquals("Juego: 2x2\nModo: Estrategia seleccionada: Vertical, Horizontal y Diagonal\n| X | O |\n| X | O |\nResultado: Empate", game2x2FinishedByTie('C').show());
    }

    @Test
    public void test30gamefinishesWhenPlayerWinsDiagonallyInGameModeC(){
        assertRedWonConditions(gameWithRedDiagonalResult(4,4,'C'));
    }

    @Test
    public void test31ShowFunctionsCorrectlyWhenPlayerWinsDiagonallyInGameModeC(){
        assertEquals("Juego: 4x4\nModo: Estrategia seleccionada: Vertical, Horizontal y Diagonal\n| - | - | - | X |\n| - | - | X | X |\n| O | X | X | O |\n| X | O | O | O |\nResultado: Gano Rojo", gameWithRedDiagonalResult(4, 4, 'C').show());
    }

    @Test
    public void test32gameFinishesWhenPlayerWinsHorizontallyInGameModeC(){
        assertRedWonConditions(gameRedHorizontalResult('C'));
    }

    @Test
    public void test33ShowFunctionsCorrectlyWhenPlayerWinsHorizontallyInGameModeC(){
        assertEquals("Juego: 4x2\nModo: Estrategia seleccionada: Vertical, Horizontal y Diagonal\n| O | O | O | - |\n| X | X | X | X |\nResultado: Gano Rojo", gameRedHorizontalResult('C').show());
    }

    @Test
    public void test34gameFinishesWhenPlayerWinsVerticallyInGameModeC(){
        assertRedWonConditions(gameRedVerticalResult('C'));
    }

    @Test
    public void test35ShowFunctionsCorrectlyWhenPlayerWinsVerticallyInGameModeC(){
        assertEquals("Juego: 2x4\nModo: Estrategia seleccionada: Vertical, Horizontal y Diagonal\n| X | - |\n| X | O |\n| X | O |\n| X | O |\nResultado: Gano Rojo", gameRedVerticalResult('C').show());
    }

    @Test
    public void test36GameFinishesWhenSomePlayerWinsDiagonallyWithADiagonalWhichStartsOverTheFirstRow() {
        Linea game = new Linea(5, 5, 'B');
        game.playRedAt(2);
        game.playBlueAt(1);
        game.playRedAt(1);
        game.playBlueAt(2);
        game.playRedAt(2);
        game.playBlueAt(3);
        game.playRedAt(3);
        game.playBlueAt(3);
        game.playRedAt(3);
        game.playBlueAt(4);
        game.playRedAt(4);
        game.playBlueAt(4);
        game.playRedAt(4);
        game.playBlueAt(5);
        game.playRedAt(4);
        assertTrue(game.finished());
        assertTrue(game.redWon());
    }

    @Test
    public void test37ErrorIsThrownIfPlayerTriesToMakeAMoveInFinishedGame(){
        Linea game = gameStartsWithRedPlayingInTheFirstColumn(1, 1, 'A');
        assertThrowsLike( () -> game.playBlueAt(1), StateOfGame.alreadyFinishedGameError);
        assertTrue(game.finished());
        assertFalse(game.goesRed());
    }

    private void assertThrowsLike(Executable executable, String message){
        assertEquals(message,
                assertThrows(Exception.class, executable).getMessage());
    }

    private static Linea game7x6Mode(char gamemode) {
        return new Linea( 7, 6, gamemode);
    }

    private static Linea gameStartsWithRedPlayingInTheFirstColumn(int base, int altura, char gamemode) {
        Linea game = new Linea(base, altura, gamemode);
        game.playRedAt(1);
        return game;
    }

    private static void assertRedWonConditions(Linea game) {
        assertTrue(game.finished());
        assertTrue(game.redWon());
    }

    private static void assertGoesRedConditions(Linea game) {
        assertTrue(game.goesRed());
        assertFalse(game.goesBlue());
    }

    private static void assertGoesBlueConditions(Linea game) {
        assertTrue(game.goesBlue());
        assertFalse(game.goesRed());
    }

    private static void assertTieConditions(Linea game) {
        assertTrue(game.finished());
        assertFalse(game.redWon());
        assertFalse(game.blueWon());
    }

    private static Linea gameRedHorizontalResult(char gamemode) {
        Linea game = new Linea(4, 2, gamemode );
        game.playRedAt(1);
        game.playBlueAt(1);
        game.playRedAt(2);
        game.playBlueAt(2);
        game.playRedAt(3);
        game.playBlueAt(3);
        game.playRedAt(4);
        return game;
    }


    private static Linea game2x2FinishedByTie(char gamemode) {
        Linea game = new Linea(2, 2, gamemode );
        game.playRedAt(1);
        game.playBlueAt(2);
        game.playRedAt(1);
        game.playBlueAt(2);
        return game;
    }

    private static Linea gameRedVerticalResult(char gamemode) {
        Linea game = new Linea(2, 4, gamemode );
        game.playRedAt(1);
        game.playBlueAt(2);
        game.playRedAt(1);
        game.playBlueAt(2);
        game.playRedAt(1);
        game.playBlueAt(2);
        game.playRedAt(1);
        return game;
    }

    private static Linea gameWithRedDiagonalResult(int base, int altura, char gamemode) {
        Linea game = new Linea(base, altura, gamemode);
        game.playRedAt(1);
        game.playBlueAt(2);
        game.playRedAt(2);
        game.playBlueAt(3);
        game.playRedAt(3);
        game.playBlueAt(4);
        game.playRedAt(3);
        game.playBlueAt(4);
        game.playRedAt(4);
        game.playBlueAt(1);
        game.playRedAt(4);
        return game;
    }

}