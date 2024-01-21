//Abo Rabia Rami 
//Stav Zysblatt
import java.util.Scanner;

public class Runner {

	public static Process[] processes;// Array to store the processes
	public static int availableMemory;// Available memory size
	public static int[] memoryBlocks;// Array to represent memory blocks

	public static int allocationAlgorithm;// Selected allocation algorithm

	public static int lastIndex = 0;

	public static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {

		//checking if the input is power of two
		System.out.print("Enter memory size - should be power of 2: ");
		int memorySize = input.nextInt();

		if (!isPowerOfTwo(memorySize)) {
			System.out.println("Invalid size - should be a power of 2.");
			return;
		}

		memoryBlocks = new int[memorySize];
		processes = new Process[memorySize];
		availableMemory = memorySize;

		int choice = -1;

		while(choice != 4) {

			System.out.println("1. Enter process");
			System.out.println("2. Exit process");
			System.out.println("3. Print status");
			System.out.println("4. Exit");
			System.out.print("Enter your choice: ");
			choice = input.nextInt();

			if(choice == 1) {

				System.out.println("Choose allocation algorithm:");
				System.out.println("1. firstFit");
				System.out.println("2. bestFit");
				System.out.println("3. nextFit");
				allocationAlgorithm = input.nextInt();// Selecting the allocation algorithm

				enterProcess();

			}
			if(choice == 2)
				exitProcess();
			if(choice == 3)
				printStatus();
			if(choice == 4)
				System.out.println("Bye Bye");
			if(choice > 4 || choice < 1)
				System.out.println("Invalid choice");


		}

	}
	// Print the status of processes and available memory
	private static void printStatus() {

		for (Process entry : processes) {
			if (entry != null) {

				System.out.println("process: "+ entry.processNumber);
				System.out.println("startIndex: "+ entry.startIndex);
				System.out.println("allocated memory: "+ entry.size);
				System.out.println("endIndex: "+ entry.endIndex);

			}

		}

		System.out.println("Available Memory: " + availableMemory);

		System.out.println("_________________");


	}
	// Exit a process and release its allocated memory
	private static void exitProcess() {

		System.out.print("Enter the process number that you want to release: "); 
		int processNumber = input.nextInt();

		if (processes[processNumber] != null) {

			int startIndex = processes[processNumber].startIndex;
			int endIndex = processes[processNumber].endIndex;

			int processSize = processes[processNumber].size;



			availableMemory += processSize;
			processes[processNumber] = null;

			for (int i = startIndex; i <= endIndex; i++)
				memoryBlocks[i] = 0;

			System.out.println("The memory has been released for process " + processNumber);
		}

		else 
			System.out.println("cant find process " + processNumber);

		System.out.println("_________________");

	}
	// Enter a new process and allocate memory for it
	private static void enterProcess() {

		System.out.print("Enter a process number: ");
		int processNumber = input.nextInt();

		System.out.print("Enter memory size - should be power of 2: ");
		int processSize = input.nextInt();
		if (!isPowerOfTwo(processSize)) {
			System.out.println("Invalid size - should be a power of 2.");
			return;
		}

		if (allocateMemory(processNumber, processSize)) 
			System.out.println("The memory allocated for the process is:" + processNumber);

		else 
			System.out.println("Couldn't allocate memory because of external fragmentation: " + sumOfFragmentation());


		System.out.println("_________________");

	}
	// Calculate the sum of fragmentation (number of contiguous empty blocks)
	private static int sumOfFragmentation() {

		int sum = 0;

		for(int i = 0; i < memoryBlocks.length; i++)
			if(memoryBlocks[i] == 0)
				sum++;

		return sum;
	}
	// Allocate memory for a process based on the selected allocation algorithm
	private static boolean allocateMemory(int processNumber, int processSize) {

		int startIndex = -1;

		if(allocationAlgorithm == 1)
			startIndex = firstFitIndex(processSize);
		if(allocationAlgorithm == 2)
			startIndex = bestFitIndex(processSize);
		if(allocationAlgorithm == 3)
			startIndex = nextFitIndex(processSize);

		if (startIndex != -1) {

			for (int i = startIndex; i < startIndex + processSize; i++) {
				memoryBlocks[i] = processNumber;
			}

			processes[processNumber] = new Process(processNumber, processSize, startIndex, startIndex + processSize - 1);
			availableMemory -= processSize;

			return true;

		}
		return false;
	}
	// Find the index of the best-fit block for the given process size
	private static int bestFitIndex(int processSize) {

		int blockSize;
		int[] blockSizes = new int[memoryBlocks.length];// Array to store the sizes of available blocks

		for (int i = 0; i < memoryBlocks.length; i++) {

			if(memoryBlocks[i] == 0) {// Check if the current byte is empty (indicated by 0)


				blockSize = 0;
				for (int j = i; j < memoryBlocks.length; j++) {// Start a new loop to calculate the size of the available block
					if(memoryBlocks[j] != 0){// Check if the next byte is occupied (not 0)
						break;// Exit the inner loop since the available block ends
					}
					blockSize++;// Increment the blockSize for each empty byte
				}

				if(blockSize >= processSize)// Check if the available block size is sufficient for the process
					blockSizes[i] = blockSize;// Store the block size in the blockSizes array

				i = i + blockSize;// Skip to the next block after the current available block

			}
		}
		return minIndex(blockSizes);// Find the index of the minimum block size and return it
	}
	// Find the index of the smallest value in the array
	private static int minIndex(int[] blockSize) {

		int minValue = Integer.MAX_VALUE;

		int index = -1;

		for (int i = 0; i < blockSize.length; i++) {
			if (blockSize[i] > 0 && blockSize[i] < minValue) {// Check if the current value is positive and smaller than the current minimum
				minValue = blockSize[i];
				index = i;
			}
		}
		return index;

	}
	// Find the index for the next-fit algorithm
	private static int nextFitIndex(int processSize) {

		int blockSize = 0;
		for (int i = lastIndex; i < memoryBlocks.length; i++) {

			if (memoryBlocks[i] == 0) {

				blockSize++;

				if (blockSize == processSize) {
					lastIndex = i;
					return i - blockSize + 1;
				}

			}

			else
				blockSize = 0;// Reset blockSize to 0 if a non-empty position is encountered

		}
		return firstFitIndex(processSize);// If no suitable block is found, invoke firstFitIndex method to search from the beginning

	}
	// Find the index for the first-fit algorithm
	private static int firstFitIndex(int processSize) {

		int blockSize = 0;//Variable to track the size of empty positions 
		for (int i = 0; i < memoryBlocks.length; i++) {

			if (memoryBlocks[i] == 0) {// Check if the current position is empty

				blockSize++;

				if (blockSize == processSize) // Check if blockSize matches the required process size
					return i - blockSize + 1;// Return the index of the starting position of the block

			}
			else
				blockSize = 0;

		}
		return -1;// Return -1 to indicate that memory allocation failed

	}

	public static boolean isPowerOfTwo(int number) {

		if (number <= 0) {
			return false; // 0 and negative numbers are not power of 2
		}

		while (number % 2 == 0)
			number /= 2;

		if(number == 1)
			return true;

		return false;
	}




}
