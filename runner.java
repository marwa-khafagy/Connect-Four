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
