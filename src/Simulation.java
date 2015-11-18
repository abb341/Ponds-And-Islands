import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Simulation {

	private static String FILE_NAME = "map.txt";
	private static char ISLAND = 'X';
	private static char POND = '.';

	public static void main(String[] args) {
		Scanner file = openTheFile();
		final int NUM_ROWS = getNumRows(file);
		final int NUM_COLS = getNumCols(file);
		System.out.println(NUM_ROWS + " " + NUM_COLS);

		char[][] pondsIslands = new char[NUM_ROWS][NUM_COLS];
		fillPondsIslandsArray(pondsIslands, NUM_ROWS, NUM_COLS, file);

		int landCounter = 0;
		int waterCounter = 0;
		char landLabel = 'a' - 1;
		char waterLabel = '0';

		for (int row = 0; row < NUM_ROWS; row++)
		{
			for (int col = 0; col < NUM_COLS; col++)
			{
				if (pondsIslands[row][col] == ISLAND)
				{
					landCounter++;
					landLabel++;
					floodFillLand(pondsIslands, row, col, landLabel);
				}
				else if (pondsIslands[row][col] == POND)
				{
					waterCounter++;
					String temp = waterCounter + "";
					waterLabel = temp.charAt(0);
					floodFillWater(pondsIslands, row, col, waterLabel);
				}
			}
		}

		display(pondsIslands, waterCounter, landCounter);
	}

	/**
	 * Open the request file. Leave file null if the open fails.
	 *
	 * @param the newly opened file, or null if opening failed
	 */
	private static Scanner openTheFile()
	{
		Scanner file = null;

		try
		{
			file = new Scanner(new File(FILE_NAME));
		}
		catch (IOException e)
		{
			System.out.println("File error - file not found");
		}

		return file;
	}

	/**
	 * Finds the number of rows in the file
	 * @param file
	 * @return
	 */
	private static int getNumRows(Scanner file)
	{
		int numRows = 0;

		if (file != null)
		{
			numRows = file.nextInt();
		}

		return numRows;
	}

	/**
	 * Finds the number of columns in the file
	 * @param file
	 * @return
	 */
	private static int getNumCols(Scanner file)
	{
		int numCols = 0;

		if (file != null)
		{
			numCols = file.nextInt();
		}

		return numCols;
	}

	/**
	 * Fills the pondsIslands array with the info from the file
	 * @param pondsIslands
	 * @param NUM_ROWS
	 * @param NUM_COLS
	 * @param file
	 * @return
	 */
	private static void fillPondsIslandsArray(char[][] pondsIslands, int NUM_ROWS,
			int NUM_COLS, Scanner file)
	{
		// Start filling from the nextLine
		file.nextLine();

		for (int row = 0; row < NUM_ROWS; row++)
		{
			String lineAtRow = file.nextLine();
			for (int col = 0; col < NUM_COLS; col++)
			{
				pondsIslands[row][col] = lineAtRow.charAt(col);
				System.out.print(pondsIslands[row][col]);
			}
			System.out.println();
		}
		
		System.out.println();
	}

	/**
	 * Turns an island full of 'X's into an island characterized by a more specific label
	 * @param pondsIslands
	 * @param row
	 * @param col
	 * @param label
	 */
	private static void floodFillLand(char[][] pondsIslands, int row, int col, char label)
	{
		if (isValid(pondsIslands, row, col))
		{
			if (pondsIslands[row][col] == ISLAND)
			{
				pondsIslands[row][col] = label;

				for (int rowIndex = row - 1; rowIndex <= row + 1; rowIndex++)
				{
					for (int colIndex = col - 1; colIndex <= col + 1; colIndex++)
					{
						floodFillLand(pondsIslands, rowIndex, colIndex, label);
					}
				}

			}
		}
	}

	/**
	 * Checks to see if a certain row and col is inside the pondsIslands array
	 * @param pondsIslands
	 * @param row
	 * @param col
	 * @return
	 */
	private static boolean isValid(char[][] pondsIslands, int row, int col)
	{
		if (row < 0)
		{
			return false;
		}
		else if (col < 0)
		{
			return false;
		}
		else if (row > pondsIslands.length - 1)
		{
			return false;
		}
		else if (col > pondsIslands[0].length - 1)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	/**
	 * Turns an pond full of '.'s into an pond characterized by a more specific label
	 * @param pondsIslands
	 * @param row
	 * @param col
	 * @param label
	 */
	private static void floodFillWater(char[][] pondsIslands, int row, int col, char label)
	{
		if (isValid(pondsIslands, row, col))
		{
			if (pondsIslands[row][col] == POND)
			{
				pondsIslands[row][col] = label;

				floodFillWater(pondsIslands, row - 1, col, label);
				floodFillWater(pondsIslands, row, col - 1, label);
				floodFillWater(pondsIslands, row + 1, col, label);
				floodFillWater(pondsIslands, row, col + 1, label);
			}
		}
	}

	/**
	 * Prints out useful information about the number of ponds and islands
	 * @param pondsIslands
	 * @param waterCounter
	 * @param landCounter
	 */
	private static void display(char[][] pondsIslands, int waterCounter, int landCounter)
	{
		for (int row = 0; row < pondsIslands.length; row++)
		{
			for (int col = 0; col < pondsIslands[0].length; col++)
			{
				System.out.print(pondsIslands[row][col]);
			}
			System.out.println();
		}
		System.out.println();
		
		System.out.println("Ponds: " + waterCounter);
		System.out.println("Islands: " + landCounter);
	}


}
