import java.util.LinkedList;
import java.util.Scanner;

public class Game 
{
	private final char EMPTY = ' ';                //empty slot
    private final char COMPUTER = 'X';             //computer
    private final char PLAYER = '0';               //player
    private final int MIN = 0;                     //min level
    private final int MAX = 1;                     //max level
    private final int LIMIT = 7;                   //depth limit

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
    private int computerScore;
    private int playerScore;
    
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
    		
    		if(full(board))				//if board is full
    		{
    			displayBoard(board);	//display board
    			displayScores(board);	//display scores
    			displayWinner();		//display winner
    			break;
    		}
    		
    		//computer makes a move
    		board = computerMove(board);
    		
    		if(full(board))				//if board is full
    		{
    			displayBoard(board);	//display board
    			displayScores(board);	//display scores
    			displayWinner();		//display winner
    			break;
    		}
    		
    		
    	}
    	
    	
    	
    }
    
    //method returns a computer move
    private Board computerMove(Board board)		//decide a move
    {
    	//create a list of children boards
    	LinkedList<Board> children = generate(board,COMPUTER);
    	
    	int maxIndex = -1;
        int maxValue = Integer.MIN_VALUE;
        
        //find the child with
        //largest minmax value
        
        for(int x = 0 ; x < children.size() ; x++)
        {
        	int currentValue = minmax(children.get(x),MIN,1,Integer.MIN_VALUE, Integer.MAX_VALUE);
        	if(currentValue > maxValue)
        	{
        		maxIndex = x ;
        		maxValue = currentValue;
        	}
        }
        
        Board result = children.get(maxIndex); //choose child as next move
        
        System.out.println("Computer move");
        
        displayBoard(result);	//print next move
        
        displayScores(result);	//print scores
        
    	return result;			//return updated board
    }
    
    
    
    //method computes minmax value of a board
    private int minmax(Board board, int level, int depth, int alpha, int beta)
    {
    	if( full(board) || depth >= LIMIT ) // if board is terminal or depth limit is reached
    	{
    		return evaluate(board,depth);			//evaluate board
    	}
    	else
    	{
    		if(level == MAX) //if board at a MAX level	
    		{
    			//generate children of board
    			LinkedList<Board> children = generate(board, COMPUTER);	
    			
    			int maxValue = Integer.MIN_VALUE;
    			
    			//for each child of board
    			for(int x = 0 ; x < children.size() ; x++)
    			{
    				int currentValue = minmax(children.get(x), MIN, depth+1, alpha, beta);
    				
    				if (currentValue > maxValue)  //find maximum of minmax values
                        maxValue = currentValue;
    				
    				if (maxValue >= beta)         //if maximum exceeds beta stop
                        return maxValue;
    				
    				if (maxValue > alpha)         //if maximum exceeds alpha update alpha
                        alpha = maxValue;	
    			}
    			
    			return maxValue;		//return maximum value
    		}
    		else			//if board at a MIN level
    		{
    			//generate children of board
    			LinkedList<Board> children = generate(board, PLAYER);	
    			
    			int minValue = Integer.MAX_VALUE;
    			
    			//for each child of board
    			for(int x = 0 ; x < children.size() ; x++)
    			{
    				int currentValue = minmax(children.get(x), MAX, depth+1, alpha, beta);
    				
    				if(currentValue < minValue) //find minimum of minmax values
    					minValue = currentValue;
    				
    				if(minValue <= alpha)		//if minimum is less than alpha stop
    					return minValue;
    				
    				if(minValue < beta)			//if minimum is less than beta update beta
    					beta = minValue;

    			}
    			
    			return minValue;			//return minimumValue
    		}
    		
    	}
    	
    	
    }
    
    //method evaluates a board
    private int evaluate(Board board,int depth)
    {
    	int computer = calculateScore(board,COMPUTER); //calculate computer score
    	int player = calculateScore(board,PLAYER);	   //calculate player score 
    	int difference = computer = player;			   //difference between scores
    	
    	// Constant for large terminal value
    	final int WIN_VALUE = 1000000;
    	
    	
    	// If board is full, it's a terminal state
        if (full(board))
        {
            if (difference > 0)
            {
            	return WIN_VALUE - depth;      // computer wins (sooner is better)
            }
            else if (difference < 0)
            {
            	return -WIN_VALUE + depth;     // player wins (later is better)
            }
            else
                return 0;                      // tie
        }

        // Non-terminal board: return heuristic score difference
        return difference;
    	
    }
    
    //method generates list of children boards
    private LinkedList<Board> generate(Board board,char symbol)
    {
    	//empty list of children
    	LinkedList<Board> children = new LinkedList<Board>();
    	
    	//go through board
    	for(int x = 0 ; x < size ; x++)
    	{
    		for(int y = 0 ; y < size ; y++)
    		{
    			if(board.array[x][y]==EMPTY)	//if slot is empty
    			{
    				Board child = copy(board);	//create child
    				child.array[x][y] = symbol;	//put symbol
    				children.addLast(child); 	//add to list
    			}
    		}
    	}
    	
    	
    	return children;		//return list of children
    }
    
    //method display the winner
    private void displayWinner()
    {
    	computerScore = calculateScore(board,COMPUTER);
    	playerScore = calculateScore(board,PLAYER);
    	
    	if(computerScore == playerScore)	//if draw
    		System.out.println("Draw");
    	else if(computerScore > playerScore)		//if computer wins
    		System.out.println("X wins");
    	else								//else player wins
    		System.out.println("O wins");
    }   
    
	//method displays players scores
	private void displayScores(Board board)
	{
		computerScore = calculateScore(board,COMPUTER); //getting computer score
		playerScore = calculateScore(board,PLAYER);		//getting player score
		
		System.out.println("Score of X = " + computerScore);
		System.out.println("Score of O = " + playerScore);
	}
	
	//method calculates score for a passed player
	private int calculateScore(Board board,char symbol)
	{
		int score = 0 ; //player score
		
		//checks rows , calculating scores for each row
		for(int x = 0 ; x < size ; x++)
			score += calculateRow(board , x , symbol);
		
		
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
		
		 for (int x = 0; x < size; x++)
	    {
	        if (board.array[x][y] == symbol)   //if current slot matches passed symbol
	        {
	            counter++;
	        }
	        else
	        {
	            if (counter >= 2)
	                twoCount += (counter - 1);
	            
	            if (counter >= 3)
	                threeCount += (counter - 2);
	            
	            counter = 0;
	        }
	    }

	    //flush last run if at column end
	    if (counter >= 2)
	        twoCount += (counter - 1);
	    
	    if (counter >= 3)
	        threeCount += (counter - 2);
		
		int score = (2 * twoCount) + (3 * threeCount); //score = 2p + 3q

		return score;		//return score
	}
	
	//method calculates scores of a row
	private int calculateRow(Board board, int x , char symbol)
	{
		int twoCount = 0 ;						//two consecutive pieces
		int threeCount = 0 ;					//three consecutive pieces
		int counter = 0 ;
		
		for (int y = 0; y < size; y++)
	    {
	        if (board.array[x][y] == symbol)   //if current slot matches passed symbol
	        {
	            counter++;
	        }
	        else
	        {
	            if (counter >= 2) 
	                twoCount += (counter - 1); //count all overlapping 2s
	            
	            if (counter >= 3) 
	                threeCount += (counter - 2); //count all overlapping 3s
	            
	            counter = 0;
	        }
	    }

	    //flush last run if at row end
	    if (counter >= 2)
	        twoCount += (counter - 1);
	    
	    if (counter >= 3)
	        threeCount += (counter - 2);
		
		int score = (2 * twoCount) + (3 * threeCount); //score = 2p + 3q
		
		return score;		//return score
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
	
	//Method checks whether a board is full
    private boolean full(Board board)
    {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (board.array[i][j] == EMPTY)
                   return false;

        return true;
    }
	
    //Method makes copy of a board
    private Board copy(Board board)
    {
        Board result = new Board(size);      

        for (int i = 0; i < size; i++)       
            for (int j = 0; j < size; j++)
                result.array[i][j] = board.array[i][j];

        return result;                       
    }
    
}
