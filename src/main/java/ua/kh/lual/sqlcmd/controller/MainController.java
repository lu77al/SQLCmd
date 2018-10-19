package ua.kh.lual.sqlcmd.controller;

import ua.kh.lual.sqlcmd.model.DatabaseManager;
import ua.kh.lual.sqlcmd.model.JDBCManager;
import ua.kh.lual.sqlcmd.view.Console;
import ua.kh.lual.sqlcmd.view.View;

public class MainController {
    public static void main(String[] args) {
        DatabaseManager db = new JDBCManager();
        View view = new Console();
        view.write("Hello. Your are using SQLcmd application");
        while (true) {
            view.write("Enter database name, user name and password (format: database|user|password)");
            try {
                String userInput = view.read();
                String[] chunk = userInput.split("\\|");
                if (chunk.length != 3) {
                    throw new Exception("Wrong input format");
                }
                String database = chunk[0];
                String user = chunk[1];
                String password = chunk[2];
                db.connect(database, user, password);
                view.write(String.format("User [%s] successfully connected to database [%s]", user, database));
                break;
            } catch (Exception e) {
                view.write("Connection failed");
                view.write("Reason: " + e.getMessage());
                if (e.getCause() != null) {
                    view.write("       " + e.getCause().getMessage());
                }
                view.write("Try again");
                view.write("");
            }
        }
    }
}
