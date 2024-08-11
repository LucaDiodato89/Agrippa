package d.agrippa.application;

import java.util.Scanner;

import d.agrippa.Interpreter;
import d.agrippa.exception.AgrippaException;

public class Main {

	public static void main(String[] args) {

		System.out.println("---BEGIN---");
		

		String zerozerothirty = """
				>>>
				>++++++[<+++++>-]
				""";
		String helloWorld = "++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.<-.<.+++.------.--------.>>+.>++.";
		String helloWorldTwo = """
				>++++++++[-<+++++++++>]<.>>+>-[+]++>++>+++[>[->+++<<+++>]<<]>-----.>->
				+++..+++.>-.<<+[>[+>+]>>]<--------------.>>.+++.------.--------.>+.>+.
				""";
		String test = "[]++++++++++[>>+>+>++++++[<<+<+++>>>-]<<<<-].>.>.>.>.>.";
		String debugH = "[]++++++++++[>>+>+>++++++[<<+<+++>>>-]<<<<-]    \"A*$\";?@![#>>+<<]>[>>]<<<<[>++<[-]]>.>.";
		String debugHclean = "[]++++++++++[>>+>+>++++++[<<+<+++>>>-]<<<<-][>>+<<]>[>>]<<<<[>++<[-]]>.>";
		
		Interpreter interpreter = new Interpreter();

		try {
			interpreter.interpret(debugH.toCharArray(), 1, 1);
		} catch (AgrippaException ex) {
			ex.printStackTrace();
		}
		System.out.println("");
		System.out.println("---END---");

	}

}
