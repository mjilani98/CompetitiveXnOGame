import java.util.Scanner;

public class Game 
{
	private final char EMPTY = ' ';                //empty slot
    private final char COMPUTER = 'X';             //computer
    private final char PLAYER = '0';               //player
    private final int MIN = 0;                     //min level
    private final int MAX = 1;                     //max level
    private final int LIMIT = 6;                   //depth limit

	//Board class (inner class)
    private class Board
    {
        private char[][] array;                    //board array

        //Constructor of Board class
        private Board(int size)
        {
            array = new char[size][size];          //create array
                                             
            for (int i = 0; i < size; i++)         //fill array with empty slots   
                for (int j = 0; j < size; j++)
                    array[i][j] = EMPTY;
        }
    }
    
    //
    private int size;
    private Board board;
    
    
    //public constructor
    public Game(int size)
    {
    	this.size = size;
    	board = new Board(size);
    }
    
    
    
    
    
    //method starts the game 
    public void play()
    {
    	//display initial board
		displayBoard(board);
		
    	while(true)
    	{
    		//take player mover
    		board = playerMove(board);
    		displayScores(board);
    	}
    }
    
    

    //method lets the player to make a move
	private Board playerMove(Board board)
	{
		System.out.print("Player move: ");         //prompt player
		
		Scanner scanner = new Scanner(System.in);  //read player's move
        int i = scanner.nextInt();
        int j = scanner.nextInt();
        
        board.array[i][j] = PLAYER;                //place player symbol
        
        displayBoard(board);                       //diplay board

        return board;                              //return updated board

		
	}

	//method display a board
	private void displayBoard(Board board) 
	{
		int n = board.array.length;

	    for (int i = 0; i < n; i++)
	    {
	    	
	    	
	        // print row with vertical lines
	        for (int j = 0; j < n; j++) 
	        {
	            char val = board.array[i][j];
	            
	            if (val == '\0' || val == ' ') val = '-';  // empty spot
	            System.out.print(" " + val + " ");
	            
	            if (j < n - 1) System.out.print("|");
	        }
	        System.out.println();

	        // print horizontal line
	        if (i < n - 1) 
	        {
	            for (int j = 0; j < n; j++)
	            {
	                System.out.print("---");
	                if (j < n - 1) System.out.print("+");
	            }
	            System.out.println();
	        }
	        
	        
	    }
	    
	    System.out.println();
		
	}

	//method displays players scores
	private void displayScores(Board board)
	{
		
	}
	
}
