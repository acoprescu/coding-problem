# coding-problem
## Solution for the Coding problem:
Define a class structure to save the following organization charts including basic constructor(s)
and destructor if needed. Each person (and subordinates) has the same set of attributes. (Age,
EmployeeID, Address, current reports and current boss).

## Directory Structure
### Main Directory
**CodingProblem.java** contains the entry point for the problem solution.
The solution can run in interactive mode using the standard console or from/to files.

An example input file *Input.txt* is added in the same directory

**TestResults.java** contains the entry point for running the tests
The results can be displayed inside the standard prompt or in a log file

### base Directory
Contains the data classes (e.g. Person) and the interfaces implemented by the solution

### impl Directory
Contains the actual solution of the problem

### test Directory 
Contains the tests run by **TestRunner** 

## Compile and Run
### Compile and Run the Solution
```console
javac CodingProblem.java

java CodingProblem
    or
java CodingProblem input_file output_file    
```

### Compile and Run the Tests
```console
javac TestRunner.java

java TestRunner
    or
java TestRunner log_file
```

## Notes
- Name should be also an attribute, names are not unique</il>
- Reports is a generic value unrelated to the tree structure



