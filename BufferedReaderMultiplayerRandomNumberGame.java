
// Name: James Lee
// Course Section:  Intro to Computer Concepts {04:547:01}
// Semester:  Spring 2020
// Assignment:  Individual Assignment # 6 - Multi-Player Random Number Game + Printed Log
// Summary: Design the infamous Random Number Game w/ specific conditions
// but make it multiplayer for any amount of users
// and also create a log file for each player + the game itself (per round)
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BufferedReaderMultiplayerRandomNumberGame {

	public static void main(String[] args) {
		int round = 1;

		String answer = gameStart(round);

		while(answer.equalsIgnoreCase("yes")) {
			round++;
			answer = gameStart(round);

			// outfile.createTempFile("MP_RNG_");
		}

		System.out.println("Thanks for playing – have a great day!!");
	}

	static String gameStart(int round) {

		Random random = new Random();
		Scanner keyboard = new Scanner(System.in);

		int n = 0;
		int guess = 0;
		int random_num = 0;
		int counter = 0;


		boolean won = false;

		ArrayList<String> playerNames = new ArrayList<String>();
		ArrayList<Integer> randNums = new ArrayList<Integer>();
		ArrayList<String> winners = new ArrayList<String>();
		ArrayList<Integer> winnersNumOfGuesses = new ArrayList<Integer>();
		ArrayList<String> winnersWrongGuesses = new ArrayList<String>();
		ArrayList<File> playerStats = new ArrayList<File>();
		try {
			// game start
			System.out.println("How many users are playing?: "); // ask user for number of players
			n = getAPosInt();

			String[] wrongGuesses = new String[n]; // {"5" , null, null}

			boolean[] list = new boolean[n]; // fills boolean arraylist with false

			int numOfGuesses[] = new int[n];
			for (int i = 0; i < n; i++) { // fills numOfGuesses with all zeroes.
				numOfGuesses[i] = 0;
			}
			System.out.println();
			for (int i = 0; i < n; i++) { // getting name of each player
				System.out.println("What is the name of Player #" + (i + 1) + " ?");
				String name = keyboard.next();
				playerNames.add(name);

				File player = new File("C:\\Users\\Jole\\Desktop\\" + name + ".txt"); 
				// delete?
				player.createNewFile();
				playerStats.add(player);
			}


			for (int i = 0; i < n; i++) { // picking random number for each player
				random_num = random.nextInt(100) + 1;
				randNums.add(i, random_num);
			}

			System.out.println();
			System.out.println("Ok...I’ve picked a random number for each of you.");
			System.out.println("From 1-100.");
			System.out.println("Time to Play the game!");
			System.out.println();

			for (int i = 0; i < n; i++) { 												// remove later
				System.out.println(randNums.get(i));
			}

			do { // the game
				for (int i = 0; i < n; i++) {
					if (list[i] == false && counter != n) {
						System.out.println(playerNames.get(i) + ", please guess the random number: ");
						guess = getAPosInt();
						numOfGuesses[i]++;
						if (guess > randNums.get(i)) {
							System.out.println("** TOO HIGH **");
							System.out.println();
							String wrongGuess = makeItAString(guess);

							if (wrongGuesses[i] != null) {
								wrongGuesses[i] = wrongGuesses[i] + ", " + wrongGuess;
							} else {
								wrongGuesses[i] = wrongGuess;
							}
						} else if (guess == randNums.get(i)) {
							System.out.println();
							System.out.println("** CORRECT **");
							System.out.println();
							list[i] = true;
							counter++;
							winners.add(playerNames.get(i));
							winnersNumOfGuesses.add(numOfGuesses[i]);
							winnersWrongGuesses.add(wrongGuesses[i]);

							// start here
							File playerFile = playerStats.get(i);
							BufferedReader br = new BufferedReader(new FileReader(playerFile.getAbsoluteFile()));
							String line =  "";
							String totalRounds = "";
							String totalGuesses = "";

							if((line = br.readLine()) != null) {
								totalRounds = line.substring(line.lastIndexOf(" ") +1);
							}
							if((line = br.readLine()) != null) {
								totalGuesses = line.substring(line.lastIndexOf(" ") +1);
							}

							FileWriter fw = new FileWriter(playerFile.getAbsoluteFile()); 
							BufferedWriter bw = new BufferedWriter(fw);
							// delete?

							playerFile.createNewFile(); 

							bw.write("Total Number of Rounds: " + Integer.toString(round));
							bw.newLine();
							bw.flush();
							if(totalGuesses.isEmpty()) {
								bw.write("Total Number of Guesses: " + Integer.toString(numOfGuesses[i]));
							}
							else {
								bw.write("Total Number of Guesses: " + Integer.toString(numOfGuesses[i] + Integer.parseInt(totalGuesses)));
							}
							bw.flush();
							bw.close();
							// ends here
						} else {
							System.out.println("** TOO LOW **");
							System.out.println();
							String wrongGuess = makeItAString(guess);
							if (wrongGuesses[i] != null) {
								wrongGuesses[i] = wrongGuesses[i] + ", " + wrongGuess;
							} else {
								wrongGuesses[i] = wrongGuess;
							}
						}
					} else if (counter == n) {
						won = true;
					}
				}
			} while (won == false);
			// GAME ENDS


			// WINNERS TABLE STARTS HERE

			System.out.println("WINNERS TABLE: ");
			System.out.println("PLACE		NAME		NUMBER OF GUESSES		WRONG GUESSES");

			for (int i = 0; i < n; i++) {
				if (winnersWrongGuesses.get(i) == null) {
					System.out.println((i + 1) + "		" + winners.get(i).trim() + "		" + winnersNumOfGuesses.get(i));
				} else {
					System.out.println((i + 1) + "		" + winners.get(i).trim() + "		" + winnersNumOfGuesses.get(i)
					+ "				" + winnersWrongGuesses.get(i).trim());
				}
				//winners table ends here ROUND ENDS HERE


			}
			// WINNERS TABLE ENDS HERE


			//MP_RNG_ Writer 

			File outfile = new File("C:\\Users\\Jole\\Desktop\\MP_RNG_" + round + ".txt"); 
			if(!outfile.exists()) { 
				outfile.createNewFile(); 
			} 
			FileWriter fw = new FileWriter(outfile.getAbsoluteFile()); 
			BufferedWriter bw = new BufferedWriter(fw);

			String content = "This is the LOG FILE for Round #" + (round) +".";
			bw.write(content); 
			bw.newLine();
			bw.newLine(); 
			bw.flush();

			for(int i = 0; i < n; i++) { 
				String content2 = winners.get(i) + " came in Place #" + (i+1) + " and won this round with " + winnersNumOfGuesses.get(i) + " guesses.";
				bw.write(content2);
				bw.flush();
				bw.newLine();
				bw.newLine(); 
				if(winnersWrongGuesses.get(i) == null) {
					bw.write(winners.get(i) + "'s guesses were: " + randNums.get(i));
				}
				else {
					bw.write(winners.get(i) + "'s guesses were: " + winnersWrongGuesses.get(i) + ", " + randNums.get(i));
				}
				bw.newLine();
				bw.newLine(); 
				bw.flush();

			} 
			bw.newLine();

			bw.close();


		} catch (IOException e) { 
			e.printStackTrace(); 
		}
		//Win Rate (i.e. Number of rounds played / number of guesses) for each player.
		for(int i = 0; i < n; i++) {
			double x = round/winnersNumOfGuesses.get(i);
			System.out.println();
			System.out.println();
			System.out.println(winners.get(i) + "'s Win Rate is: " + x);
		}
		System.out.println();
		String answer = getAYesOrNo();

		return answer;


	}

	static int getAPosInt() { // get a positive INT!!!!!!
		int enteredNumber = 0;
		Scanner myScanner = new Scanner(System.in);
		boolean numberError = false;
		String enteredString = "";

		do {
			try {
				enteredString = myScanner.next();
				enteredNumber = Integer.parseInt(enteredString.trim()); // then cast as a integer
				if (enteredNumber > 0) {
					numberError = false;
				} else {
					numberError = true;
					System.out.println("Your entry: " + enteredNumber + " is invalid...Please try again..");
				}
			} catch (Exception e) {
				System.out.println("Your entry: " + enteredString + " is invalid...Please try again..");
				numberError = true; // Uh-Oh...We have a problem.
			}

		} while (numberError == true); // Keep asking the user until the correct number is entered.

		System.out.println("You entered " + enteredNumber + "!");

		return enteredNumber;

	}

	static String getAYesOrNo() {
		Scanner myScanner = new Scanner(System.in);
		boolean badAnswer = true;
		String enteredString = "";
		System.out.println();

		do {
			System.out.println("Do you want to play again? Please enter Yes or No: ");
			enteredString = myScanner.next(); // Read into a string

			if (enteredString.equalsIgnoreCase("yes")) {
				badAnswer = false;
			} else if (enteredString.equalsIgnoreCase("no")) {
				badAnswer = false;
			} else {
				System.out.println("Your entry: " + enteredString + " is invalid... Please try again..");
				badAnswer = true;
			}

		} while (badAnswer == true); // Keep asking the user until the correct number is entered.

		System.out.println();
		return enteredString;
	}

	static String makeItAString(int x) {
		String theNumber = "";
		theNumber = theNumber + x;
		return theNumber;
	}
	
	static int makeItAnInt(String s) {
		String enteredString = s;
		int enteredNumber = 0;
		boolean numberError = false;
		do {
			try {
				enteredNumber = Integer.parseInt(s.trim());
				numberError = false;
			}
			catch(Exception e){
				System.out.println("String " + s + " is an invalid Integer ");
				numberError = true;

			}
		}while(numberError = true);
		System.out.println("String " + s + " has been made into an Integer " + enteredNumber);
		return enteredNumber;
	}

}
