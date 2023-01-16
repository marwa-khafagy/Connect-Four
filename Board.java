public class Board {

	private final int NUM_OF_COLUMNS = 7;
	private final int NUM_OF_ROW = 6;
	private int[] lastPlay= new int[2];
	private boolean isEmpty;
	private char[][] board = new char[NUM_OF_ROW][NUM_OF_COLUMNS];
	
	/* 
	 * The board object must contain the board state in some manner.
	 * You must decide how you will do this.
	 * 
	 * You may add addition private/public methods to this class is you wish.
	 * However, you should use best OO practices. That is, you should not expose
	 * how the board is being implemented to other classes. Specifically, the
	 * Player classes.
	 * 
	 * You may add private and public methods if you wish. In fact, to achieve
	 * what the assignment is asking, you'll have to
	 * 
	 */
	
	public Board() {
		//this.board = board;
		reset();
	}
	
	public void printBoard() {
		for (int i = NUM_OF_ROW - 1; i >= 0 ; i--){
			System.out.print("|");
			for (int j = 0 ; j < NUM_OF_COLUMNS ; j++){
				System.out.print(this.board[i][j] + "|");
			}
			System.out.println();
		}
	}
	
	public boolean containsWin() {
		if (isEmpty)
			return false;
		else{
			int[] rowChec = new int[]{0,1};
			int[] colChec = new int[]{1,0};
			int[] posDChec = new int[]{1,1};
			int[] negDChec = new int[]{1,-1};

			if(win(lastPlay[0], lastPlay[1], symbol(), rowChec)) return true;
			else if(win(lastPlay[0], lastPlay[1], symbol(), colChec)) return true;
			else if(win(lastPlay[0], lastPlay[1], symbol(), posDChec)) return true;
			else if(win(lastPlay[0], lastPlay[1], symbol(), negDChec)) return true;
			else return false;
		}

	}

	public boolean isEqual(int row, int col, char temp){
		if(temp == ' ') temp = symbol();
		if (board[row][col] == temp)
			return true;
		return false;
	}

	public boolean isCellEmpty(int row, int col){
		if (board[row][col] == ' ' || board[row][col] == '_')
			return true;
		return false;
	}

	public int[] getNextElement(int inc[], int row, int col){
		return new int[]{row + inc[0] , col + inc[1]};
	}

	public boolean getUpperBound(int row, int col, int[] inc){
		if (inc[0] > 0) return row < NUM_OF_ROW;
		else return col < NUM_OF_COLUMNS;
	}

	public int diagonalShift(int[] incOrDec, int row, int col){
		if (incOrDec[0] == 1){
            if(incOrDec[1] == 1){
                if (row > col) return col;
                else return row;
            }
            else if(incOrDec[1] == -1){
                if (row <= NUM_OF_COLUMNS - col - 1) return row;
				else return NUM_OF_COLUMNS - col - 1;
            }
			else{
				return row;
			}
        }
		else{
			return col;
		}
	}
	
	public boolean win(int row, int col, char temp, int[] incOrDec){
        int shift = diagonalShift(incOrDec, row, col);
        row -= (incOrDec[0] * shift);
        col -= (incOrDec[1] * shift);
        int conseq = 0;

        while(getUpperBound(row, col, incOrDec) && extraBound(incOrDec[1], col, 0)){
			if(isEqual(row, col, temp)){
				conseq ++;
				if (conseq >= 4) return true;
			}
                
            else if(conseq >= 4)
				return true;
			else conseq = 0;

			int[] nextElements = getNextElement(incOrDec, row, col);
			row = nextElements[0];
			col = nextElements[1];   
        }
        return false;

    }

	public boolean extraBound(int intOrDec, int col, int shift){
		if(intOrDec == 0) return true;
		else if(intOrDec < 0){
			return col >=0 + shift;
		}
		else return col < NUM_OF_COLUMNS - shift;
	}

	public boolean isTie() {
		for (int i = 0; i < NUM_OF_COLUMNS; i++){
			if(this.board[NUM_OF_ROW - 1][i] == ' ') return false;
		}
		return true;
	}
	
	public void reset() {
		for (int i = 0; i < NUM_OF_ROW ; i++){
			for (int j = 0 ; j < NUM_OF_COLUMNS ; j++){
				if(i == 0){
					this.board[i][j] = '_';
				}
				else this.board[i][j] = ' ';
			}
		}
		this.isEmpty = true;
	}

	public boolean add(int i, char c){
		if (c == ' ') c = symbol();

		if(this.board[NUM_OF_ROW - 1][--i] != ' ')
			return false;
		
		for (int j = 0; j < NUM_OF_ROW; j++){
			if(this.board[j][i] == ' ' || board[j][i] == '_'){
				this.board[j][i] = c;
				lastPlay[0] = j;
				lastPlay[1] = i;
				break;
			}
		}
		isEmpty = false;
		return true;
	}

	public int getColumns(){
		return NUM_OF_COLUMNS;
	}

	public int getRows(){
		return NUM_OF_ROW;
	}

	public boolean getIsEmpty(){
		return isEmpty;
	}

	private char symbol(){
		return board[lastPlay[0]][lastPlay[1]];
	}

	public int checkPoints(int row, int col, int[] incOrDec, boolean checkfst){
		
		int rowShift, colShift;
		rowShift = 4 * incOrDec[0];
		colShift = 4 * incOrDec[1];

		boolean thisElement = row < NUM_OF_ROW && extraBound(incOrDec[1], col, 0) && isCellEmpty(row, col) && (row == 0 || !isCellEmpty(row - 1, col));
		boolean firstElement = checkfst && !(incOrDec[0] == 1 && incOrDec[1] == 0) && extraBound( -1 * incOrDec[1], col, colShift) && row >= rowShift && isCellEmpty(row - rowShift, col - colShift) && (row - rowShift == 0 || !isCellEmpty(row - rowShift - 1, col - colShift));

		if(firstElement){
			return col - colShift + 1;
		}
		
		if(thisElement){
			return col + 1;
		} 

		else {
			return -1;}
		
	}

}