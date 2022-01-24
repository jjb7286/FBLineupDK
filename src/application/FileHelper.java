package application;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileHelper {
	
	
	// read a csv and store in 2d string array
	public static String[][] storeCSV(String fileName){
		int[] csvRowCol = getCSVRowsCols(fileName);
		String[][] csv = new String[csvRowCol[0]][csvRowCol[1]];
		
		try {
			Scanner sc = new Scanner(new BufferedReader(new FileReader(fileName)));
			int row = 0;
			while (sc.hasNext()) {
				// get next line and split into components
				String[] components = sc.nextLine().replaceAll("\"","").split(",");
				// store each component 
				for (int i = 0; i < components.length; i++) {
					csv[row][i] = components[i];
				}
				// get next row
				row++;
			}
			return csv;
		} catch (FileNotFoundException e) {
			System.out.println(fileName + " not found");
			e.printStackTrace();
			return csv;
		}
		
		
		
		
	}
	// method to add lineups to sheet for final export
	public static void saveExportCSV() throws IOException {
		
		int j = 1;
		// iterate through final lineupList
		for (int i = 0; i < LineupHelper.lineupList.size(); i++) {
			// starting on the second row of the draftkings data array
			
			// add the 9 players to that row
			ListHelper.dkPlayers[j][4] = LineupHelper.lineupList.get(i).getQb().getId();
			ListHelper.dkPlayers[j][5] = LineupHelper.lineupList.get(i).getRb1().getId();
			ListHelper.dkPlayers[j][6] = LineupHelper.lineupList.get(i).getRb2().getId();
			ListHelper.dkPlayers[j][7] = LineupHelper.lineupList.get(i).getWr1().getId();
			ListHelper.dkPlayers[j][8] = LineupHelper.lineupList.get(i).getWr2().getId();
			ListHelper.dkPlayers[j][9] = LineupHelper.lineupList.get(i).getWr3().getId();
			ListHelper.dkPlayers[j][10] = LineupHelper.lineupList.get(i).getTe().getId();
			ListHelper.dkPlayers[j][11] = LineupHelper.lineupList.get(i).getFlex().getId();
			ListHelper.dkPlayers[j][12] = LineupHelper.lineupList.get(i).getDst().getId();
			j++;
		
		}
		
		writeCSV();
		
	}
	
	// method to write draft kings array back to CSV
	public static void writeCSV() throws IOException {
		
		FileWriter csvWriter = new FileWriter("final.csv");
		
		for (int i = 0; i < ListHelper.dkPlayers.length; i++) {
			for (int j = 0; j < ListHelper.dkPlayers[i].length; j++) {
				csvWriter.append(ListHelper.dkPlayers[i][j]);
				csvWriter.append(",");				
			}
			csvWriter.append("\n");
		}

		csvWriter.flush();
		csvWriter.close();
	}
	
	// method to get number of rows in csv
	public static int[] getCSVRowsCols(String fileName) {
		
		try {
			Scanner sc = new Scanner(new BufferedReader(new FileReader(fileName)));
			// break up the lines and populate data into players
			int numRows = 0;
			int numCols = 0;
			while (sc.hasNext()) {
				// break up the csv into components
				String[] components = sc.nextLine().replaceAll("\"","").split(",");
				if (components.length > numCols) {
					numCols = components.length;
				}
				numRows++;
			}
			
			sc.close();
			return new int[] {numRows, numCols};
			
		
		} catch (FileNotFoundException e) {
			System.out.println(fileName + " not found");
			e.printStackTrace();
			return new int[] {-1, -1};
		}
		
		
	}
	
	
}
