package ua.kh.lual.sqlcmd.view;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Console implements View {
    @Override
    public void write(String message) {
        String toPrint = "\033[0;37m" +
                         message.replaceAll("<", "\033[1;33m").
                                 replaceAll(">", "\033[0;37m");
        System.out.println(toPrint);
    }

    @Override
    public String read() {
        try {
            Scanner scanner = new Scanner(System.in);
            return scanner.nextLine();
        } catch (NoSuchElementException e) {
            return "";
        }
    }
}

