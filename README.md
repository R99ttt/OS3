# Memory Management System

## Overview

This Java program is developed as part of an Operating Systems (OS) course to simulate a simple memory management system. The system allows the user to perform basic memory management operations, such as allocating memory for processes, releasing memory, and viewing the status of memory and processes.

## Contributors

- Abo Rabia Rami
- Stav Zysblatt

## How to Use

1. **Compile the Code:** Ensure you have Java installed on your system. Compile the `Runner.java` file using a Java compiler.

   ```bash
   javac Runner.java
   ```

2. **Run the Program:** Execute the compiled program.

   ```bash
   java Runner
   ```

3. **Menu Options:**
   - **Enter process (Option 1):** Allocates memory for a new process based on the selected allocation algorithm.
   - **Exit process (Option 2):** Releases memory allocated to a specific process.
   - **Print status (Option 3):** Displays the status of processes and available memory.
   - **Exit (Option 4):** Terminates the program.

## Memory Allocation Algorithms

The program supports three memory allocation algorithms:

1. **First Fit:** Allocates memory to the first available block that is large enough to accommodate the process.

2. **Best Fit:** Allocates memory to the smallest available block that can accommodate the process.

3. **Next Fit:** Similar to the first-fit algorithm but starts searching for an available block from the last allocated block.

## Input Requirements

- **Memory Size:** The program prompts the user to enter the total memory size, which should be a power of 2.

- **Process Size:** When entering a new process, the user needs to provide the process number and memory size, which should also be a power of 2.

## Notes

- The program checks if the entered memory size is a power of 2 before proceeding.
- Exiting a process releases the allocated memory and updates the available memory.
- The status option displays information about each allocated process, including its process number, allocated memory range, and available memory.

## Contribution

Feel free to contribute to the project by submitting bug reports, feature requests, or pull requests. Your feedback and contributions are valuable to improve the functionality and usability of the memory management system.

## License

This project is licensed under the [MIT License](LICENSE).
