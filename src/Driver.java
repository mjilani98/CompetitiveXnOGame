import java.util.Scanner;

public class Driver {

	public static void main(String[] args) 
	{
		//Scanner object
		Scanner input = new Scanner(System.in);
		
		//get board size
		System.out.println("Enter the size of a board");
		int size = input.nextInt();
		
		//create a game
		Game game = new Game(size);
		game.play();
	}

}
