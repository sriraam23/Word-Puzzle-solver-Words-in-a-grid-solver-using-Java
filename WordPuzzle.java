import java.util.Scanner;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.String;

public class WordPuzzle {

	// isPrime function to check if the number passed is prime or not.
	public static boolean isPrime(int n) {
		if (n == 2 || n == 3)
			return true;
		if (n == 1 || n % 2 == 0)
			return false;
		for (int i = 3; i * i <= n; i += 2)
			if (n % i == 0)
				return false;
		return true;
	}

	// createallwords function that creates all possible combinations of words
	// from the grid generated.
	public static String[] createallwords(char grid[][], int rows, int columns) {
		String wordlist[] = new String[10000000];
		int k, l;
		int count = 0;
		String temp = "";
		String temp2 = "";

		for (int i = 0; i < 10000000; i++) {
			wordlist[i] = "";
		}

		// Directions 1 & 2: Horizontal and reverse
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				temp += String.valueOf(grid[i][j]);
			}
			for (int m = 0; m < temp.length(); m++) {
				for (int n = m + 1; n <= temp.length(); n++) {
					wordlist[count++] = temp.substring(m, n);
					temp2 = new StringBuilder(temp.substring(m, n)).reverse()
							.toString();
					if (!temp2.equals(temp.substring(m, n)))
						wordlist[count++] = temp2;
				}
			}
			temp = "";
			temp2 = "";
		}

		// Directions 3 & 4: Vertical and reverse
		for (int i = 0; i < columns; i++) {
			for (int j = 0; j < rows; j++) {
				temp += String.valueOf(grid[j][i]);
			}
			for (int m = 0; m < temp.length(); m++) {
				for (int n = m + 2; n <= temp.length(); n++) {
					wordlist[count++] = temp.substring(m, n);
					temp2 = new StringBuilder(temp.substring(m, n)).reverse()
							.toString();
					if (!temp2.equals(temp.substring(m, n)))
						wordlist[count++] = temp2;
				}
			}
			temp = "";
			temp2 = "";
		}

		// Directions 5 & 6: Main diagonal and reverse
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				temp = String.valueOf(grid[i][j]);
				k = i + 1;
				l = j + 1;
				while (k < rows && l < columns) {
					temp += String.valueOf(grid[k][l]);
					k++;
					l++;
				}
				for (int m = 0; m < temp.length(); m++) {
					for (int n = m + 2; n <= temp.length(); n++) {
						wordlist[count++] = temp.substring(m, n);
						temp2 = new StringBuilder(temp.substring(m, n))
								.reverse().toString();
						if (!temp2.equals(temp.substring(m, n)))
							wordlist[count++] = temp2;
					}
				}
			}
			temp = "";
			temp2 = "";
		}

		// Directions 7 & 8: Other diagonal and reverse
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				temp = String.valueOf(grid[i][j]);
				k = i + 1;
				l = j - 1;
				while (k < rows && l >= 0 && l < columns) {
					temp += String.valueOf(grid[k][l]);
					k++;
					l--;
				}
				for (int m = 0; m < temp.length(); m++) {
					for (int n = m + 2; n <= temp.length(); n++) {
						wordlist[count++] = temp.substring(m, n);
						temp2 = new StringBuilder(temp.substring(m, n))
								.reverse().toString();
						if (!temp2.equals(temp.substring(m, n)))
							wordlist[count++] = temp2;
					}
				}
			}
			temp = "";
			temp2 = "";
		}
		return wordlist;
	}

	// Main function - creates the grid, reads the dictionary and checks for
	// words against Hash table, AVL tree and a Linked List.
	public static void main(String args[]) {

		int rows, columns, i;
		String wordlist[] = new String[10000000];
		Random r = new Random();

		System.out.println("Word Puzzle:");
		System.out.println(" ");
		Scanner s = new Scanner(System.in);

		System.out.println("Enter the number of rows in the grid:");
		rows = s.nextInt();

		System.out.println("Enter the number of columns in the grid:");
		columns = s.nextInt();
		System.out.println(" ");

		char grid[][] = new char[rows][columns];

		System.out.println("Number of rows in the grid is: " + rows);
		System.out.println("Number of columns in the grid is: " + columns);

		System.out.println(" ");
		System.out.println("The grid generated is:");

		// Creating the grid
		for (i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++) {
				grid[i][j] = (char) (r.nextInt(26) + 97);
			}
		for (i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				System.out.print(grid[i][j] + " ");
			}
			System.out.println("");
		}

		wordlist = createallwords(grid, rows, columns);

		System.out.println("");

		for (i = 0; !(wordlist[i].isEmpty()); i++)
			;
		System.out
				.println("The total number of words formed from the grid is: "
						+ i);

		FileInputStream file;
		String word;
		int tablesize;
		int hashcount = 0, avlcount = 0, linkedlistcount = 0;

		// Using the fact that the total words in the dictionary is 109616.
		// Finding a prime number greater than 109616*2
		for (int q = 219233;; q += 2) {
			if (isPrime(q)) {
				tablesize = q;
				break;
			}
		}

		System.out.println(" ");
		System.out.println("Table size of the Hash Table is: " + tablesize);

		MyHashTable<String> hash = new MyHashTable<String>(tablesize);
		AvlTree<String> avl = new AvlTree<String>();
		MyLinkedList<String> lst = new MyLinkedList<String>();

		// Reading the dictionary and performing inserts into a Hash table, AVL
		// tree and a Linked List.
		try {
			file = new FileInputStream("dictionary.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(file));
			try {
				while ((word = br.readLine()) != null) {
					// System.out.println(word);
					avl.insert(word);
					lst.add(word);
					hash.insert(word);
				}
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println("");
		System.out.println("");
		System.out.println("*****");
		System.out.println("");
		System.out.println("");

		/********** Hash Table check **************/
		// Uncomment the print statement below to print the list of words.
		System.out.println("Dictionary words - found using a Hash table:");
		// Checking against a Hash Table
		long startTime = System.currentTimeMillis();
		for (i = 0; !(wordlist[i].isEmpty()); i++) {
			if (hash.contains(wordlist[i])) {
				// System.out.println(wordlist[i]);
				hashcount++;
			}
		}
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(" ");
		System.out.println("Number of dictionary words found: " + hashcount);
		System.out
				.println("Time taken to check when dictionary is stored in a Hash table: "
						+ totalTime + " milliseconds");
		/****************************************/

		System.out.println("");
		System.out.println("");
		System.out.println("*****");
		System.out.println("");
		System.out.println("");

		/********** AVL Tree check **************/
		// Uncomment the print statement below to print the list of words.
		System.out.println("Dictionary words - found using an AVL Tree:");
		// Checking against an AVL Tree
		startTime = System.currentTimeMillis();
		for (i = 0; !(wordlist[i].isEmpty()); i++) {
			if (avl.contains(wordlist[i])) {
				// System.out.println(wordlist[i]);
				avlcount++;
			}
		}
		endTime = System.currentTimeMillis();
		totalTime = endTime - startTime;
		System.out.println(" ");
		System.out.println("Number of dictionary words found: " + avlcount);
		System.out
				.println("Time taken to check when dictionary is stored in an AVL Tree: "
						+ totalTime + " milliseconds");
		/****************************************/

		System.out.println("");
		System.out.println("");
		System.out.println("*****");
		System.out.println("");
		System.out.println("");

		/********** Linked List check **************/
		// Uncomment the print statement below to print the list of words.
		System.out.println("Dictionary words - found using a Linked List:");
		// Checking against a Linked List
		startTime = System.currentTimeMillis();
		for (i = 0; !(wordlist[i].isEmpty()); i++) {
			if (lst.contains(wordlist[i])) { //
				// System.out.println(wordlist[i]);
				linkedlistcount++;
			}
		}
		endTime = System.currentTimeMillis();
		totalTime = endTime - startTime;
		System.out.println(" ");
		System.out.println("Number of dictionary words found: "
				+ linkedlistcount);
		System.out
				.println("Time taken to check when dictionary is stored in a Linked List: "
						+ totalTime + " milliseconds");
		/****************************************/
	}
}
