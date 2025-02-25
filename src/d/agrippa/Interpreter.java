package d.agrippa;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Stack;

import d.agrippa.exception.AgrippaException;
import d.agrippa.utils.Constants;

public class Interpreter {
	
	int[] memory;
    
    int dataPointer;
    
    int instructionPointer;

    public Interpreter () {
        this.memory = new int[Constants.DEFAULT_MEMORY_SIZE];
        this.dataPointer = 0;
        this.instructionPointer = 0;
    }
    
    public Interpreter (int memorySize) {
        this.memory = new int[memorySize];
        this.dataPointer = 0;
        this.instructionPointer = 0;
    }
    
    public void interpret (char[] code) throws AgrippaException{
    	this.interpret(code, -1, -1);
    }
    
    public void interpret (char[] code, int printMemoryStatusFrequency, int printMemoryStatusSize) throws AgrippaException {
    	
    	boolean printStatusFlag = isStatusToBePrinted(printMemoryStatusFrequency, printMemoryStatusSize);
    	
    	if (printStatusFlag && printMemoryStatusSize > memory.length)
    		throw new AgrippaException(Constants.ATTEMPT_AT_PRINTING_OUT_OF_BOUNDS_ERROR_MESSAGE);
    	
    	int stepCounter = 0;
        
        Map<Integer, Integer> parenthesisMap = mapBrackets(code);
        
        Scanner scanner = new Scanner(System.in);
        
        while (instructionPointer<code.length) {
        	switch (code[instructionPointer]) {
        		case '>' -> {
        			//Increment the data pointer by one (to point to the next cell to the right)
        			dataPointer++;
        		}
        		case '<' -> {
        			//Decrement the data pointer by one (to point to the next cell to the left)
        			dataPointer--;
        		}
        		case '+' -> {
        			//Increment the byte at the data pointer by one
        			memory[dataPointer] = Math.floorMod(memory[dataPointer] + 1, Constants.MODULE);
        		}
        		case '-' -> {
        			//Decrement the byte at the data pointer by one
        			memory[dataPointer] = Math.floorMod(memory[dataPointer] - 1, Constants.MODULE);
        		}
        		case '.' -> {
        			// Output the byte at the data pointer
        			char output = (char) memory[dataPointer];
                    System.out.print( output);
        		}
        		case ',' -> {
        			//Accept one byte of input, storing its value in the byte at the data pointer
        			System.out.print("Insert a byte of input: ");
        			String lineInput = scanner.nextLine();
        			char characterInput = lineInput.charAt(0);
        			int integerInput = (int) characterInput;
        			memory[dataPointer] = integerInput;
        		}
        		case '[' -> {
        			//If the byte at the data pointer is zero, then instead of moving the instruction pointer forward to the next command,
        			//jump it forward to the command after the matching ] command
        			if (memory[dataPointer] == 0) {
        				instructionPointer = skipToClosing(instructionPointer, parenthesisMap);
        			}
        		}
        		case ']' -> {
        			//If the byte at the data pointer is nonzero, then instead of moving the instruction pointer forward to the next command,
        			//jump it back to the command after the matching [ command
        			if (memory[dataPointer] != 0) {
        				instructionPointer = skipToOpening(instructionPointer, parenthesisMap);
        			}
        		}
        		default -> {
        			//Ignores every other character
        		}        		
        	}
        	instructionPointer++;
        	
        	if (printStatusFlag) {
        		stepCounter++;
        		if (stepCounter == printMemoryStatusFrequency) {
        			stepCounter = 0;
        			printMemoryStatus(printMemoryStatusSize);
        		}
        	}
        }
        
        scanner.close();
        machineReset();
        
    }
    
    private void machineReset () {
        
        for (int i = 0; i < memory.length; i++) {
            memory[i] = 0;
        }
        
        dataPointer = 0;
        instructionPointer = 0;
                
    }
    
    private Map<Integer, Integer> mapBrackets (char[] code) throws AgrippaException {
        
        Map<Integer, Integer> result = new HashMap<>();
        Stack<Integer> stack = new Stack<>();
        
        try {
        
            for (int i = 0; i <code.length; i++) {
                if (code[i]=='['){
                    stack.push(i);
                } else if (code[i]==']'){
                    result.put(stack.pop(), i); 
                }
            }
        
            if (!stack.empty())
                throw new AgrippaException(Constants.UNBALANCED_PARENTHESIS_ERROR_MESSAGE);
        
        } catch (EmptyStackException e) {
            throw new AgrippaException(Constants.UNBALANCED_PARENTHESIS_ERROR_MESSAGE);
        }
        return result;
    }
    
    private int skipToClosing (int openingPosition, Map<Integer, Integer> bracketsMap) throws AgrippaException {
    	
    	for  (int value : bracketsMap.keySet()) {
    		if (value == openingPosition)
    			return bracketsMap.get(openingPosition);
    	}
        throw new AgrippaException("Write error message");
    }
    
    
    private int skipToOpening (int closingPosition, Map<Integer, Integer> bracketsMap) throws AgrippaException {
    	
    	for  (Entry<Integer, Integer> entry : bracketsMap.entrySet()) {
    		if (entry.getValue() == closingPosition)
    			return entry.getKey();
    	}
    	throw new AgrippaException("Write error message");
    }
	
	private void printMemoryStatus (int range) {
		System.out.print("|");
		for (int i = 0; i < range; i++) {
			String formatted = String.format(Locale.ENGLISH, "%03d", memory[i]);
			System.out.print(formatted+"|");
		}
		System.out.print("\n");
	}

	private boolean isStatusToBePrinted (int frequency, int size) {
		
		return frequency > 0 && size > 0;
		
	}
	
	
}
