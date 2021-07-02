import com.sanityinc.jargs.CmdLineParser;
import javafx.application.Application;
import model.DataBase;
import viewer.StageController;
import viewer.menu.RegisterMenu;

import java.io.IOException;

public class Main{
    public static void main(String[] args) throws CmdLineParser.OptionException, IOException {
        DataBase dataBase = DataBase.getInstance();
        dataBase.restoreDate();
        StageController.run(args);
    }
}
