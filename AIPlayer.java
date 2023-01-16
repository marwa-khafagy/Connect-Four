import java.util.Random;
public class AIPlayer extends Player {

    public AIPlayer(char symbol, Board board, String name) {
        super(symbol, board, name);
    }

    @Override
    public void makeMove(Board board) {
        int winningMove, block;
        if(!board.getIsEmpty()){
            block = WinningMoveOrBlock(' ');
            winningMove = WinningMoveOrBlock(symbol);
        }
        else{
            block = -1;
            winningMove = -1;
        }

        if(winningMove != -1 ){
            board.add(winningMove, symbol);
        }
        else if (block != -1){
                board.add(block, symbol);
        }
        else{
            while(!board.add(randomPlay(), symbol)) continue;
        }
    }

    public int randomPlay(){
        Random r = new Random();
        return r.nextInt(board.getColumns()) + 1;
    }

    public int WinningMoveOrBlock(char temp){
        int[] rowChec = new int[]{0,1};
        int[] colChec = new int[]{1,0};
        int[] posDChec = new int[]{1,1};
        int[] negDChec = new int[]{1,-1};

        for(int i = 0 ; i < board.getRows() ; i++){
            int row, negD, posD;
            row = AlmostWin(i, 0, temp, rowChec);
            posD = AlmostWin(i, 0, temp, posDChec);
            negD = AlmostWin(i, 0, temp, negDChec);
            
            if (row != -1){
                return row;
            }
                
            else if(posD != -1){
                return posD;
            }

            else if(negD != -1){
                return negD;
            }
        }

        for(int i = 0 ; i < board.getColumns() ; i++){;
            int col,negD, posD;
            col = AlmostWin(0, i, temp, colChec);
            posD = AlmostWin(0, i, temp, posDChec);
            negD = AlmostWin(0, i, temp, negDChec);

            if (col != -1){
                return col;
            }
            else if (negD != -1){
                return negD;
            }
            else if(posD != -1){
                return posD;
            }
        }
        return -1;
    }

    public int AlmostWin(int row, int col, char temp, int[] incOrDec){
        
        int shift = board.diagonalShift(incOrDec, row, col);
        row -= (incOrDec[0] * shift);
        col -= (incOrDec[1] * shift);
        int conseq = 0;

        while(board.getUpperBound(row, col, incOrDec) && board.extraBound(incOrDec[1], col, 0)){
            boolean correctFormat = false;
            boolean checkFst = false;
            boolean equal = board.isEqual(row, col, temp);
            int[] nextElements = board.getNextElement(incOrDec, row, col);
            int[] nextNextElements = board.getNextElement(incOrDec, nextElements[0], nextElements[1]);
            
            if(equal){
                conseq++;
            }

            if(conseq == 3) {
                row = nextElements[0];
                col = nextElements[1];
                correctFormat = true; checkFst = true;}
            else if(conseq == 2 && !equal && board.getUpperBound(nextElements[0], nextElements[1], incOrDec) && board.extraBound(incOrDec[1], nextElements[1], 0)){
                if(board.isEqual(nextElements[0], nextElements[1], temp)){
                    correctFormat = true;
                }
                else conseq = 0;     
            }
            else if(conseq == 1 && !equal && board.getUpperBound(nextNextElements[0], nextNextElements[1], incOrDec) && board.extraBound(incOrDec[1], nextNextElements[1], 0)){
                if(board.isEqual(nextElements[0], nextElements[1], temp) && (board.isEqual(nextNextElements[0], nextNextElements[1], temp))){
                    correctFormat = true;
                }
                else conseq = 0;     
            }
            else if( !equal && temp == ' ' && incOrDec[0] == 0) {

            }
            else if (!equal) conseq = 0; 

            if(correctFormat){
                int i = board.checkPoints(row, col, incOrDec, checkFst);
                if (i != -1) return i;
                else conseq = 0;
            }
                    
            row = nextElements[0];
            col = nextElements[1];
        }
        return -1;

    }
    
}
