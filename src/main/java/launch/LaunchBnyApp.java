package launch;

import com.pluralsight.FileManager;
import javafx.application.Application;
import javafx.stage.Stage;


public class LaunchBnyApp extends Application {

    public void start(Stage stage) {
        // loading csv data into database on startup because it wasn't saving them into the database
        DatabaseConnection db = new DatabaseConnection();
        //Makes sure the transactions database is empty before loading, so no duplicates are made
        if (db.getTransactionsFromDB().isEmpty()) {
            db.saveToDataBase(FileManager.loadTransactions());
        }

        Model.getInstance().getViewSwap().showLoginView();
    }
}
