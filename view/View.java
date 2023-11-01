package view;

import java.util.Scanner;

public class View {
    private final Scanner sc;

    public View() {
        this.sc = new Scanner(System.in);
    }

    public void displayMenu() {
        System.out.println("====== Copy Program ========\n");
    }

    public String promptInput(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }

    public void showMessage(String msg) {
        System.err.println(msg);
    }
}
