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
    		
    	}
    	
    	
    	
    }
    
    
	//method displays players scores
	private void displayScores(Board board)
	{
		int computerScore = calculateScore(board,COMPUTER); //getting computer score
		int playerScore = calculateScore(board,PLAYER);		//getting player score
		
		System.out.println("Score of X = " + computerScore);
		System.out.println("Score of O = " + playerScore);
	}
	
	//method calculates score for a passed player
	private int calculateScore(Board board,char symbol)
	{
		int score = 0 ; //player score
		
		//checks rows , calculating scores for each row
		for(int x = 0 ; x < size ; x++)
			score += checkRow(board , x , symbol);
		
		
		//check columns , calculating scores for each column
		for(int y = 0 ; y < size ; y++ )
			score += calculateColumn(board, y , symbol);
		
		
		return score;
	}
	
	//method calculates scores of a column
	private int calculateColumn(Board board, int y, char symbol)
	{
		int twoCount = 0 ; 		//two consecutive pieces
		int threeCount = 0 ;	//three consecutive pieces
		int counter = 0 ;		
		
		for(int j = 0 ; j < size ; j++)
		{
			if(board.array[j][y] == symbol)		//if current slot matches passed symbol
			{
				counter +=1;
			}
			else								
			{
				counter = 0;					//reset the counter 
			}
			
			if(counter == 2)					//increment number of two consecutive pieces
				twoCount +=1;
			
			if(counter == 3)					//increment number of three consecutive pieces
			{
				threeCount +=1;
				twoCount +=1;					//also add another two consecutive pieces
			}
		}
		
		int score = (2 * twoCount) + (3 * threeCount); //score = 2p + 3q

		return score;		//return score
	}
	
	private int checkRow(Board board, int x , char symbol)
	{
		int twoCount = 0 ;						//two consecutive pieces
		int threeCount = 0 ;					//three consecutive pieces
		int counter = 0 ;
		
		for(int y = 0 ; y < size ; y++)
		{
			if(board.array[x][y]== symbol)
			{
				counter +=1;
			}
			else
			{
				counter = 0;
			}
			
			if(counter == 2)
				twoCount +=1;
			
			if(counter == 3)
			{
				threeCount +=1;
				twoCount +=1;
			}
		}
		
		int score = (2 * twoCount) + (3 * threeCount);
		
		return score;
	}
	
	
	//method lets the player to make a move
	private Board playerMove(Board board)
	{
		System.out.print("Player move: ");         //prompt player
		
		Scanner scanner = new Scanner(System.in);  //read player's move
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        
        board.array[x][y] = PLAYER;                //place player symbol
        
        displayBoard(board);                       //display board
        
        displayScores(board);					   //display scores

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
	
	
	
}
