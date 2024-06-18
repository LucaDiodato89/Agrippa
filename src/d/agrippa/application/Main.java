package d.agrippa.application;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		System.out.println("---BEGIN---");
		
		String zerozerothirty = """
				>>>
				>++++++[<+++++>-]
				""";
		
		
		System.out.println("Inserisci un carattere ascii 255 etc etc");
		Scanner scanner = new Scanner(System.in);
		String inputline = scanner.nextLine();
		char input = inputline.charAt(0);
		
		int memory = (int) input;
		char output = (char) memory;
        System.out.println("In memoria: " + memory);
        System.out.println("In output: " +output);
		
		System.out.println("---END---");

	}

}
