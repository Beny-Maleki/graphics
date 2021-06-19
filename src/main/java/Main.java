import com.sanityinc.jargs.CmdLineParser;
import model.DataBase;
import viewer.menu.RegisterMenu;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws CmdLineParser.OptionException, IOException {
        DataBase dataBase = DataBase.getInstance();
        dataBase.restoreDate();
        RegisterMenu registerMenu = RegisterMenu.getInstance();
        registerMenu.run();
    }
}
