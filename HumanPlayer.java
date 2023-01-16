import java.util.Scanner;
public class HumanPlayer extends Player {

    public HumanPlayer(char symbol, Board board, String name) {
        super(symbol, board, name);
    }

    @Override
    public void makeMove(Board board) {
        Scanner input = new Scanner(System.in);
        int move;
        while(true){
            System.out.print(name + ", please input your move: ");
            move = input.nextInt();
            if(board.add(move, symbol)) break;
            else System.out.println("Invalid move.");
        }
        
    }
    
}
