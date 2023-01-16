// This an example of a human player playing with a computer opponent
// It can also be set for a computer player to play with a computer player, or a human player to play with a human player
public class runner {
    public static void main(String[] args) {
        Board board = new Board();
        board.reset();
        ConnectFour game = new ConnectFour(board);
        game.setPlayer1(new HumanPlayer('X', board, "Xan"));
        game.setPlayer2(new AIPlayer('0', board, "Oct"));
        game.playGame();

    }

}
