package at.ac.fhcampuswien.newsanalyzer.ui;

//Zusammenarbeit mit Ekaterina


import at.ac.fhcampuswien.newsanalyzer.ctrl.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInterface
{
	private Controller ctrl = new Controller();

	public void getDataFromCtrl1(){
		System.out.println("Looking for articles with word \"Apple\"");

		ctrl.process("apple");
	}

	public void getDataFromCtrl2(){
		// TODO implement me
		System.out.println("Looking for articles with word \"Java\"");

		ctrl.process("Java");
	}

	public void getDataFromCtrl3(){
		// TODO implement me
		System.out.println("Looking for articles with word \"Book\"");

		ctrl.process("Book");
	}

	public void getDataForCustomInput() {
		// TODO implement me
		System.out.println("Enter the input you want to find: ");
		String newCustomLine = readLine();

		System.out.println("Looking for articles with word \"" + newCustomLine + "\"");

		ctrl.process(newCustomLine);

	}


	public void start() {
		Menu<Runnable> menu = new Menu<>("User Interface");
		menu.setTitle("Wählen Sie aus:");
		menu.insert("a", "Choice Apple", this::getDataFromCtrl1);
		menu.insert("b", "Choice Java", this::getDataFromCtrl2);
		menu.insert("c", "Choice Book", this::getDataFromCtrl3);
		menu.insert("d", "Choice User Input:",this::getDataForCustomInput);
		menu.insert("q", "Quit", null);
		Runnable choice;
		while ((choice = menu.exec()) != null) {
			choice.run();
		}
		System.out.println("Program finished");
	}


	protected String readLine() {
		String value = "\0";
		BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			value = inReader.readLine();
		} catch (IOException ignored) {
		}
		return value.trim();
	}

	protected Double readDouble(int lowerlimit, int upperlimit)     {
		Double number = null;
		while (number == null) {
			String str = this.readLine();
			try {
				number = Double.parseDouble(str);
			} catch (NumberFormatException e) {
				number = null;
				System.out.println("Please enter a valid number:");
				continue;
			}
			if (number < lowerlimit) {
				System.out.println("Please enter a higher number:");
				number = null;
			} else if (number > upperlimit) {
				System.out.println("Please enter a lower number:");
				number = null;
			}
		}
		return number;
	}
}