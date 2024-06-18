package d.agrippa;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

import d.agrippa.exception.AgrippaException;
import d.agrippa.utils.Constants;

public class Interpreter {
	
	int[] memory;
    
    int dataPointer;
    
    int instructionPointer;
    
    //Map<Integer, Integer> brackets;

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
    
    public void interpret (char[] code) {
    	
    }
    
    
    public void interpret (char[] code, int printMemoryStatusFrequency, int printMemoryStatusSize) throws AgrippaException {
    	
    	boolean printStatusFlag = isStatusToBePrinted(printMemoryStatusFrequency, printMemoryStatusSize);
    	int stepCounter = 0;
        
        Map<Integer, Integer> parenthesisMap = mapBrackets(code);
        
        Scanner scanner = new Scanner(System.in);
        
        while (instructionPointer<code.length) {
        	switch (code[instructionPointer]) {
        		case '>' -> {
        			//Increment the data pointer by one (to point to the next cell to the right)
        			dataPointer++;
        			instructionPointer++;
        		}
        		case '<' -> {
        			//Decrement the data pointer by one (to point to the next cell to the left)
        			dataPointer--;
        			instructionPointer++;
        		}
        		case '+' -> {
        			//Increment the byte at the data pointer by one
        			memory[dataPointer] = (memory[dataPointer] + 1) % 255;
        			instructionPointer++;
        		}
        		case '-' -> {
        			//Decrement the byte at the data pointer by one
        			if (memory[dataPointer]==0)
        				memory[dataPointer] = 255;
        			else
        				memory[dataPointer] = memory[dataPointer] - 1;
        			instructionPointer++;
        		}
        		case '.' -> {
        			// 	Output the byte at the data pointer
        			char output = (char) memory[dataPointer];
                    System.out.print(output);
                    instructionPointer++;
        		}
        		case ',' -> {
        			//Accept one byte of input, storing its value in the byte at the data pointer
        			
        			//TODO Implement
        		}
        		case '[' -> {
        			//If the byte at the data pointer is zero, then instead of moving the instruction pointer forward to the next command,
        			//jump it forward to the command after the matching ] command
        		}
        		case ']' -> {
        			//If the byte at the data pointer is nonzero, then instead of moving the instruction pointer forward to the next command,
        			//jump it back to the command after the matching [ command
        		}
        		default -> {
        			//Ignores every other character
        		}
        	}
        	
        	//Check if to print status
        	if (printStatusFlag) {
        		//TODO Implement
        		;
        	}
        	
        }
        
        machineReset();
              
    }
    
    private void machineReset () {
        
        for (int i = 0; i < memory.length; i++) {
            memory[i] = 0;
        }
        
        dataPointer = 0;
        instructionPointer = 0;
                
    }
    
    public Map<Integer, Integer> mapBrackets (char[] code) throws AgrippaException {
        
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
    
  //TODO implement,
    private int skipToClosing () {
        return 1;
    }
    
  //TODO implement,
    private int skipToOpening () {
        return 2;
    }
	
    //TODO implement, print cells and pointers
	private void printMemoryStatus (int range) {
		for (int i = 0; i < range; i++)
			System.out.println();
		
	}

	private boolean isStatusToBePrinted (int frequency, int size) {
		
		return frequency > 0 && size > 0;
		
	}
	
	
}
