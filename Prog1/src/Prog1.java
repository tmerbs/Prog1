
/**
 * Name: Todd Merbeth
 * Course: Csc460
 * Assignment: Prog1.java
 * Instructor: Lester McCann
 * TA: Shou Yang
 * Due Date: 1/29/2016
 * 
 * Description: This program's purpose is to copy a text file containing a
 * large number of formatted data into a new binary file. The text file path
 * is given as a command line argument. That file is read, and all lines are 
 * tested for validity. The valid lines are then sorted based off of their
 * Object Identifier before being written as a binary file top "objects.bin".
 * The binary file is closed after writing before being opened again for 
 * reading. The first five records and last five records of data are printed
 * in the order they were found. If there were less than 5 records, however 
 * many valid lines there were will be printed for the fire and last values. 
 * As a result values can be printed more than once. 
 * The total number of records written to the binary file are printed after.
 * A search bar is then shown for the user to search for a specific Object 
 * Identifier from the binary file. If the path, input, or search is invalid
 * or not found, the user will be informed why. If the user types "quit" into
 * the search bar the program will end.
 * 
 * Version: Java 1.7
 * 
 * Bugs: The order of the record being output is in the order of the binary 
 * file instead of the text file. I didn't notice that we were 
 * supposed to print in that order until too late.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Prog1 {
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("No path specified"); // Let user know if path
														// hasn't been specified
														// and end program
			System.exit(-1);
		}
		Path file = Paths.get(args[0]); // Get path from args[0]
		if (Files.isReadable(file) == false) { // If the path is not readable,
												// let user know and end program
			System.out.println("File is not readable");
			System.exit(-1);
		}
		List<String> lines = null; // List to hold all of the text file lines
		List<String> validLines = new ArrayList<String>(); // List holding all
															// validLines to be
															// added to binary
															// file
		File fileRef = new File("objects.bin"); // Used to create file
		RandomAccessFile dataStream = null; // Specializes the file I/O
		long lineCount = 0; // Total text file line count
		long validLineCount = 0; // Valid line count
		try {
			lines = Files.readAllLines(file, Charset.forName("US-ASCII")); // Reads
																			// all
																			// of
																			// the
																			// text
																			// file
																			// lines
		} catch (IOException e) {
			System.out.println("Error reading text file lines");
			System.exit(-1);
		}
		/*
		 * For each string line in lines check the validity of the line and
		 * values. If there is an error found, let the user know of the error
		 * and do not add the line to the validLines or increase validLineCount.
		 * If not errors are found, add the line to validLines and increase
		 * validLineCount.
		 */
		for (String line : lines) {
			long record = lineCount + 1; // Record # will be 1 above the line
											// count since record 1 in line 0
			if (line.length() != 129) {
				System.out.println("Record #" + record + " excluded: Invalid line length");

			} else if (isInteger(line.substring(0, 8).trim()) == false) {
				System.out.println("Record #" + record + " excluded: Invalid SPM Value");

			} else if (isInteger(line.substring(9, 11).trim()) == false) {
				System.out.println("Record #" + record + " excluded: Invalid RAh Value");

			} else if (isInteger(line.substring(12, 14).trim()) == false) {
				System.out.println("Record #" + record + " excluded: Invalid RAm Value");

			} else if (isDouble(line.substring(15, 21).trim()) == false) {
				System.out.println("Record #" + record + " excluded: Invalid RAs Value");

			} else if (isValidDE(line.substring(22, 23).trim()) == false) {
				System.out.println("Record #" + record + " excluded: Invalid DE- Value");

			} else if (isInteger(line.substring(23, 25).trim()) == false) {
				System.out.println("Record #" + record + " excluded: Invalid DEd Value");

			} else if (isInteger(line.substring(26, 28).trim()) == false) {
				System.out.println("Record #" + record + " excluded: Invalid DEm Value");

			} else if (isDouble(line.substring(29, 34).trim()) == false) {
				System.out.println("Record #" + record + " excluded: Invalid DEs Value");

			} else if (isDouble(line.substring(35, 45).trim()) == false) {
				System.out.println("Record #" + record + " excluded: Invalid RAdeg Value");

			} else if (isDouble(line.substring(46, 56).trim()) == false) {
				System.out.println("Record #" + record + " excluded: Invalid DEdeg Value");

			} else if (isInteger(line.substring(56, 60).trim()) == false) {
				System.out.println("Record #" + record + " excluded: Invalid e_RAdeg Value");

			} else if (isInteger(line.substring(60, 64).trim()) == false) {
				System.out.println("Record #" + record + " excluded: Invalid e_DEdeg Value");

			} else if (isDouble(line.substring(65, 72).trim()) == false) {
				System.out.println("Record #" + record + " excluded: Invalid pmRA Value");

			} else if (isDouble(line.substring(73, 80).trim()) == false) {
				System.out.println("Record #" + record + " excluded: Invalid pmDE Value");

			} else if (isDouble(line.substring(80, 85).trim()) == false) {
				System.out.println("Record #" + record + " excluded: Invalid e_pmRA Value");

			} else if (isDouble(line.substring(85, 90).trim()) == false) {
				System.out.println("Record #" + record + " excluded: Invalid e_pmDE Value");

			} else if (isDouble(line.substring(91, 96).trim()) == false) {
				System.out.println("Record #" + record + " excluded: Invalid Vmag Value");

			} else if (isDouble(line.substring(97, 102).trim()) == false) {
				System.out.println("Record #" + record + " excluded: Invalid Bmag Value");

			} else if (isDouble(line.substring(103, 108).trim()) == false) {
				System.out.println("Record #" + record + " excluded: Invalid B-V Value");

			} else if (isInteger(line.substring(108, 111).trim()) == false) {
				System.out.println("Record #" + record + " excluded: Invalid e_Vmag Value");

			} else if (isInteger(line.substring(111, 114).trim()) == false) {
				System.out.println("Record #" + record + " excluded: Invalid e_Bmag Value");

			} else if (isInteger(line.substring(117, 118).trim()) == false) {
				System.out.println("Record #" + record + " excluded: Invalid Nfie Value");

			} else if (isInteger(line.substring(119, 121).trim()) == false) {
				System.out.println("Record #" + record + " excluded: Invalid o_Bmag Value");

			} else if (isInteger(line.substring(122, 124).trim()) == false) {
				System.out.println("Record #" + record + " excluded: Invalid o_Vmag Value");

			} else if (isValidF1(line.substring(125, 126).trim()) == false) {
				System.out.println("Record #" + record + " excluded: Invalid F1 Value");

			} else if (isValidF2(line.substring(126, 127).trim()) == false) {
				System.out.println("Record #" + record + " excluded: Invalid F2 Value");

			} else if (isValidF3(line.substring(127, 128).trim()) == false) {
				System.out.println("Record #" + record + " excluded: Invalid F3 Value");

			} else {
				validLines.add(line);
				validLineCount++;
			}
			lineCount++;
		}
		if (validLineCount > 1) { // Don't need to sort in there is only one or
									// 0 lines
			/*
			 * Sort valid lines by using a comparator to check the values of the
			 * Object Identifier of the line. This value is found in the first 8
			 * bytes of the line so use substring to get the first 8 and
			 * compare. Trim the lines to remove white space and use
			 * Integer.valueOf to be sure that you are comparing int values and
			 * not str values.
			 */
			Collections.sort(validLines, new Comparator<String>() {
				public int compare(String line1, String line2) {
					String SPM1 = line1.substring(0, 8).trim();
					String SPM2 = line2.substring(0, 8).trim();

					return Integer.valueOf(SPM1).compareTo(Integer.valueOf(SPM2));
				}
			});
		}
		try {
			// Create randomAccessFile dataStream to write binary line data
			dataStream = new RandomAccessFile(fileRef, "rw");
		} catch (IOException e) {
			System.out.println("Error creating RandomAccessFile object");
			System.exit(-1);
		}
		try {
			/*
			 * Read all lines in validLines and write the values in binary to
			 * the file.
			 */
			for (String line : validLines) {
				dataStream.writeInt(Integer.parseInt(line.substring(0, 8).trim()));
				dataStream.writeInt(Integer.parseInt(line.substring(9, 11).trim()));
				dataStream.writeInt(Integer.parseInt(line.substring(12, 14).trim()));
				dataStream.writeDouble(Double.parseDouble(line.substring(15, 21).trim()));
				StringBuffer DE = new StringBuffer((line.substring(22, 23).trim()));
				dataStream.writeBytes(DE.toString());
				dataStream.writeInt(Integer.parseInt(line.substring(23, 25).trim()));
				dataStream.writeInt(Integer.parseInt(line.substring(26, 28).trim()));
				dataStream.writeDouble(Double.parseDouble(line.substring(29, 34).trim()));
				dataStream.writeDouble(Double.parseDouble(line.substring(35, 45).trim()));
				dataStream.writeDouble(Double.parseDouble(line.substring(46, 56).trim()));
				dataStream.writeInt(Integer.parseInt(line.substring(56, 60).trim()));
				dataStream.writeInt(Integer.parseInt(line.substring(60, 64).trim()));
				dataStream.writeDouble(Double.parseDouble(line.substring(65, 72).trim()));
				dataStream.writeDouble(Double.parseDouble(line.substring(73, 80).trim()));
				dataStream.writeDouble(Double.parseDouble(line.substring(80, 85).trim()));
				dataStream.writeDouble(Double.parseDouble(line.substring(85, 90).trim()));
				dataStream.writeDouble(Double.parseDouble(line.substring(91, 96).trim()));
				dataStream.writeDouble(Double.parseDouble(line.substring(97, 102).trim()));
				dataStream.writeDouble(Double.parseDouble(line.substring(103, 108).trim()));
				dataStream.writeInt(Integer.parseInt(line.substring(108, 111).trim()));
				dataStream.writeInt(Integer.parseInt(line.substring(111, 114).trim()));
				dataStream.writeInt(Integer.parseInt(line.substring(117, 118).trim()));
				dataStream.writeInt(Integer.parseInt(line.substring(119, 121).trim()));
				dataStream.writeInt(Integer.parseInt(line.substring(122, 124).trim()));
				StringBuffer F1 = new StringBuffer((line.substring(125, 126).trim()));
				F1.setLength(1);
				dataStream.writeBytes(F1.toString());
				StringBuffer F2 = new StringBuffer((line.substring(126, 127).trim()));
				F2.setLength(1);
				dataStream.writeBytes(F2.toString());
				StringBuffer F3 = new StringBuffer((line.substring(127, 128).trim()));
				F3.setLength(1);
				dataStream.writeBytes(F3.toString());
			}
			dataStream.close(); // Close RandomAccessFile

		} catch (IOException e) {
			System.out.println("Error writing to binary file");
			System.exit(-1);
		}
		try {
			dataStream = new RandomAccessFile(fileRef, "r"); // RandomAccessFile
																// for read only
																// this time
		} catch (FileNotFoundException e1) {
			System.out.println("Error creating RandomAccessFile object");
			System.exit(-1);
		}
		// Keep list of printed lines to search for in the text file
		// (not done yet)
		List<String> printedLines = new ArrayList<String>();
		// Get first five lines one by one and read out the values. Break if
		// less than 5 valid lines.
		for (int i = 0; i < 5; i++) {
			if (i >= validLineCount) {
				break;
			}
			try {
				// String line = "dataStream.readInt()";
				System.out.print(dataStream.readInt() + ",");
				System.out.print(dataStream.readInt() + ",");
				System.out.print(dataStream.readInt() + ",");
				System.out.print(dataStream.readDouble() + ",");
				byte[] DEread = new byte[1]; // file->byte[]
				dataStream.readFully(DEread);
				String DE = new String(DEread); // byte[]->String
				System.out.print(DE + ",");
				System.out.print(dataStream.readInt() + ",");
				System.out.print(dataStream.readInt() + ",");
				System.out.print(dataStream.readDouble() + ",");
				System.out.print(dataStream.readDouble() + ",");
				System.out.print(dataStream.readDouble() + ",");
				System.out.print(dataStream.readInt() + ",");
				System.out.print(dataStream.readInt() + ",");
				System.out.print(dataStream.readDouble() + ",");
				System.out.print(dataStream.readDouble() + ",");
				System.out.print(dataStream.readDouble() + ",");
				System.out.print(dataStream.readDouble() + ",");
				System.out.print(dataStream.readDouble() + ",");
				System.out.print(dataStream.readDouble() + ",");
				System.out.print(dataStream.readDouble() + ",");
				System.out.print(dataStream.readInt() + ",");
				System.out.print(dataStream.readInt() + ",");
				System.out.print(dataStream.readInt() + ",");
				System.out.print(dataStream.readInt() + ",");
				System.out.print(dataStream.readInt());
				byte[] F1read = new byte[1]; // file->byte[]->String
				dataStream.readFully(F1read);
				String F1 = new String(F1read);
				byte[] F2read = new byte[1]; // file->byte[]->String
				dataStream.readFully(F2read);
				String F2 = new String(F2read);
				byte[] F3read = new byte[1]; // file->byte[]->String
				dataStream.readFully(F3read);
				String F3 = new String(F3read);
				System.out.print("," + F1);
				System.out.print("," + F2);
				System.out.print("," + F3);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Error reading from binaryFile");
				System.exit(-1);
			}
		}
		try {
			/*
			 * Seek the last 5 lines of data. 140 bytes per line (4 per int, 8
			 * per double, 1 per char) multiplied by the valid line count - 5 to
			 * get to the 5th from last. If <5 records, just start from 0 and
			 * read up lines
			 */
			if (validLineCount >= 5) {
				dataStream.seek(140 * (validLineCount - 5));
			} else {
				dataStream.seek(0);
			}
		} catch (IOException e1) {
			System.out.println("Error seeking for last 5 in binary file");
			System.exit(-1);
		}
		// Read/print the 5 lines' values. If <5 lines break.
		for (int i = 0; i < 5; i++) {
			if (i >= validLineCount) {
				break;
			}
			try {
				System.out.print(dataStream.readInt() + ",");
				System.out.print(dataStream.readInt() + ",");
				System.out.print(dataStream.readInt() + ",");
				System.out.print(dataStream.readDouble() + ",");
				byte[] DEread = new byte[1]; // file -> byte[] -> String
				dataStream.readFully(DEread);
				String DE = new String(DEread);
				System.out.print(DE + ",");
				System.out.print(dataStream.readInt() + ",");
				System.out.print(dataStream.readInt() + ",");
				System.out.print(dataStream.readDouble() + ",");
				System.out.print(dataStream.readDouble() + ",");
				System.out.print(dataStream.readDouble() + ",");
				System.out.print(dataStream.readInt() + ",");
				System.out.print(dataStream.readInt() + ",");
				System.out.print(dataStream.readDouble() + ",");
				System.out.print(dataStream.readDouble() + ",");
				System.out.print(dataStream.readDouble() + ",");
				System.out.print(dataStream.readDouble() + ",");
				System.out.print(dataStream.readDouble() + ",");
				System.out.print(dataStream.readDouble() + ",");
				System.out.print(dataStream.readDouble() + ",");
				System.out.print(dataStream.readInt() + ",");
				System.out.print(dataStream.readInt() + ",");
				System.out.print(dataStream.readInt() + ",");
				System.out.print(dataStream.readInt() + ",");
				System.out.print(dataStream.readInt());
				byte[] F1read = new byte[1]; // file -> byte[] -> String
				dataStream.readFully(F1read);
				String F1 = new String(F1read);
				byte[] F2read = new byte[1]; // file -> byte[] -> String
				dataStream.readFully(F2read);
				String F2 = new String(F2read);
				byte[] F3read = new byte[1]; // file -> byte[] -> String
				dataStream.readFully(F3read);
				String F3 = new String(F3read);
				System.out.print("," + F1);
				System.out.print("," + F2);
				System.out.print("," + F3);
			} catch (IOException e) {
				System.out.println("Error reading from binary file");
				System.exit(-1);
			}
		}
		// Print the line count after the first and last 5 lines print
		System.out.println(validLineCount);
		Scanner input = new Scanner(System.in); // Use scanner for user input
		while (true) { // Put in endless loop so multiple searches can be done
			System.out.print("Search: ");
			String s = input.next(); // String the user said to search for
			if (s.equals("quit")) { // If "quit" was typed, end the program
				return;
			}
			if (s.length() < 9 && isInteger(s)) {
				/*
				 * If search was <=8 bytes and an integer use the search
				 * function with validLines list and int value of the search
				 */
				int index = search(validLines, Integer.valueOf(s));
				if (index != -1) { // If search found something
					try {
						/*
						 * Find the correct line given the index that was the
						 * search result. Then read and print all of the values
						 * for the line.
						 */
						dataStream.seek(140 * index);
						System.out.print(dataStream.readInt() + ",");
						System.out.print(dataStream.readInt() + ",");
						System.out.print(dataStream.readInt() + ",");
						System.out.print(dataStream.readDouble() + ",");
						byte[] DEread = new byte[1]; // file -> byte[] -> String
						dataStream.readFully(DEread);
						String DE = new String(DEread);
						System.out.print(DE + ",");
						System.out.print(dataStream.readInt() + ",");
						System.out.print(dataStream.readInt() + ",");
						System.out.print(dataStream.readDouble() + ",");
						System.out.print(dataStream.readDouble() + ",");
						System.out.print(dataStream.readDouble() + ",");
						System.out.print(dataStream.readInt() + ",");
						System.out.print(dataStream.readInt() + ",");
						System.out.print(dataStream.readDouble() + ",");
						System.out.print(dataStream.readDouble() + ",");
						System.out.print(dataStream.readDouble() + ",");
						System.out.print(dataStream.readDouble() + ",");
						System.out.print(dataStream.readDouble() + ",");
						System.out.print(dataStream.readDouble() + ",");
						System.out.print(dataStream.readDouble() + ",");
						System.out.print(dataStream.readInt() + ",");
						System.out.print(dataStream.readInt() + ",");
						System.out.print(dataStream.readInt() + ",");
						System.out.print(dataStream.readInt() + ",");
						System.out.print(dataStream.readInt());
						byte[] F1read = new byte[1]; // file -> byte[] -> String
						dataStream.readFully(F1read);
						String F1 = new String(F1read);
						byte[] F2read = new byte[1]; // file -> byte[] -> String
						dataStream.readFully(F2read);
						String F2 = new String(F2read);
						byte[] F3read = new byte[1]; // file -> byte[] -> String
						dataStream.readFully(F3read);
						String F3 = new String(F3read);
						System.out.print("," + F1);
						System.out.print("," + F2);
						System.out.print("," + F3);
						System.out.print("\n");
					} catch (IOException e) {
						System.out.println("Error in search function seek or read in binary file");
						System.exit(-1);
					}
				} else {// The search found no results
					System.out.println("Object Identifier not found");
				}
			} else {// The search was invalid
				System.out.println("Invalid Object Identifier value");
			}
		}

	}

	/*
	 * This function checks if a string is an integer. The only valid non-digit
	 * char is at 0 where a "-" is allowed. Checks each char to make sure they
	 * are a digit 0-9.
	 */
	public static boolean isInteger(String str) {
		if (str == null) {
			return false;
		}
		if (str.length() == 0) {
			return false;
		}
		int i = 0; // Used to check each char
		if (str.charAt(0) == '-') { // Check sign
			if (str.length() == 1) {
				return false;
			}
			i++;
		}
		while (i < str.length()) {
			char c = str.charAt(i); // Character currently being observed
			if (c < '0' || c > '9') {
				return false;
			}
			i++;
		}
		return true;
	}

	/*
	 * This function checks if a string is an double. The only valid non-digit
	 * char is at 0 where a "-" is allowed, or a "." somewhere in the string for
	 * decimal values. Only one decimal is allowed. Checks each other char to
	 * make sure they are a digit 0-9.
	 */
	public static boolean isDouble(String str) {
		int decimal = 0; // Decimal counter, cannot have more than 1
		if (str == null) {
			return false;
		}
		if (str.length() == 0) {
			return false;
		}
		int i = 0;
		if (str.charAt(0) == '-') { // Check sign
			if (str.length() == 1) {
				return false;
			}
			i++;
		}
		while (i < str.length()) {
			char c = str.charAt(i); // Character currently being observed
			if (c == '.') { // Check for decimal
				decimal++;
			} else if (c < '0' || c > '9') {
				return false;
			}
			if (decimal > 1) { // If more that one decimal, it is invalid
				return false;
			}
			i++;
		}
		return true;
	}

	/*
	 * Returns whether the DE value provided is valid. Only "+", "-", and " "
	 * are accepted. Anything else returns false.
	 */
	public static boolean isValidDE(String str) {
		if (str.equals(" ") || str.equals("-") || str.equals("+") || str.equals("")) {
			return true;
		} else
			return false;
	}

	/*
	 * Returns whether the F1 value provided is valid. Only "H", "T", and " "
	 * are accepted. Anything else returns false.
	 */
	public static boolean isValidF1(String str) {
		if (str.equals(" ") || str.equals("H") || str.equals("T") || str.equals("")) {
			return true;
		} else
			return false;
	}

	/*
	 * Returns whether the F2 value provided is valid. Only "G", "K", "Q" and
	 * " " are accepted. Anything else returns false.
	 */
	public static boolean isValidF2(String str) {
		if (str.equals(" ") || str.equals("G") || str.equals("K") || str.equals("Q") || str.equals("")) {
			return true;
		} else
			return false;
	}

	/*
	 * Returns whether the F3 value provided is valid. Only "A", "S", and " "
	 * are accepted. Anything else returns false.
	 */
	public static boolean isValidF3(String str) {
		if (str.equals(" ") || str.equals("A") || str.equals("S") || str.equals("")) {
			return true;
		} else
			return false;
	}

	/*
	 * Search function used to find the object identifier being searched for. It
	 * takes the list of validLines and the target the the user specified and
	 * performs an interpolation search through the data for the value.
	 */
	public static int search(List<String> allLines, int target) {
		// If the are no lines, the target will not be found.
		if (allLines.size() == 0) {
			return -1;
		}
		int[] lines = new int[allLines.size()];// Array to hold all of the
												// lines' object identifier
												// values
		// Put the line object identifier int values into the array and trim to
		// get rid of the white space.
		for (int i = 0; i < allLines.size(); i++) {
			lines[i] = Integer.valueOf(allLines.get(i).substring(0, 8).trim());
		}
		// Used for search
		int low_index = 0; // Starts at 0.
		int high_index = allLines.size() - 1; // Starts at number of lines - 1.
		int probe_index; // Probe value not set yet.

		// Interpolation Search
		while (lines[low_index] <= target && lines[high_index] >= target) {
			if (lines[high_index] - lines[low_index] == 0) {
				return (low_index + high_index) / 2; // low and high = target,
														// return the value
			}
			// Probe index calculated here
			probe_index = low_index
					+ (target - lines[low_index]) / (lines[high_index] - lines[low_index]) * (high_index - low_index);

			if (lines[probe_index] < target) { // If probe is less that target,
												// move low up.
				low_index = probe_index + 1;
			} else if (lines[probe_index] > target) { // If probe is greater
														// than target, high
														// down.
				high_index = probe_index - 1;
			} else {
				return probe_index; // Else the probe is the target, return the
									// value
			}
		}
		if (lines[low_index] == target) { // If low_index is the target, return
											// value
			return low_index;
		} else {
			return -1; // Else the value is not there, return -1 and let user
						// know
		}
	}
}
