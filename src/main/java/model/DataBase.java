package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import model.cards.cardsProp.MagicCard;
import model.cards.cardsProp.MonsterCard;
import model.userProp.Deck;
import model.userProp.User;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class DataBase {
    private static DataBase instance;

    private DataBase() {
    }

    public static DataBase getInstance() {
        if (instance == null) instance = new DataBase();
        return instance;
    }

    public void restoreDate() {
        ArrayList<MonsterCard> monsterCards = new ArrayList<>();
        ArrayList<MagicCard> magicCards = new ArrayList<>();
        try {
            String json = new String(Files.readAllBytes(Paths.get("jsonResources\\MagicCard.json")));
            magicCards = new Gson().fromJson(json,
                    new TypeToken<List<MagicCard>>() {
                    }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String json = new String(Files.readAllBytes(Paths.get("jsonResources\\MonsterCard.json")));
            monsterCards = new Gson().fromJson(json,
                    new TypeToken<List<MonsterCard>>() {
                    }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (CSVReader reader = new CSVReader(new FileReader("csvFile\\SpellTrap.csv"))) {
            String[] lineInArray;
            int counter = 0;
            while ((lineInArray = reader.readNext()) != null) {
                MagicCard magicCard = magicCards.get(counter);
                magicCard.setDetails(lineInArray[0], lineInArray[1],
                        lineInArray[2], lineInArray[3],
                        lineInArray[4], lineInArray[5]);
                counter++;
//              magicCards.add(new MagicCard(lineInArray[0], lineInArray[1],
//                       lineInArray[2], lineInArray[3],
//                       lineInArray[4], lineInArray[5]));
            }
            MagicCard.setMagicCards(magicCards);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (CSVReader reader = new CSVReader(new FileReader("csvFile\\Monster.csv"))) {
            String[] infoRow;
            int counter = 0;
            reader.readNext(); // dummy read to skip the title row
            while ((infoRow = reader.readNext()) != null) {
                MonsterCard monsterCard = monsterCards.get(counter);
                monsterCard.setDetails(infoRow[0], infoRow[1], infoRow[2],
                        infoRow[3], infoRow[4], infoRow[5],
                        infoRow[6], infoRow[7], infoRow[8]);
                counter++;
//                MonsterCard monsterCard = new MonsterCard(infoRow[0], infoRow[1], infoRow[2],
//                        infoRow[3], infoRow[4], infoRow[5],
//                        infoRow[6], infoRow[7], infoRow[8]);
//                monsterCards.add(monsterCard);
            }
            MonsterCard.setMonsterCards(monsterCards);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //card Json
//        String json = new Gson().toJson(magicCards);
//        Writer writer = null;
//        try {
//            writer = new BufferedWriter(new OutputStreamWriter(
//                    new FileOutputStream("jsonResources\\MagicCard.json"), StandardCharsets.UTF_8));
//            writer.write(json);
//        } catch (IOException ex) {
//            // Report
//        } finally {
//            try {
//                assert writer != null;
//                writer.close();
//            } catch (Exception ex) {//*ignore*//*}
//            }
//            json = new Gson().toJson(monsterCards);
//            try {
//                writer = new BufferedWriter(new OutputStreamWriter(
//                        new FileOutputStream("jsonResources\\MonsterCard.json"), StandardCharsets.UTF_8));
//                writer.write(json);
//            } catch (IOException ex) {
//                // Report
//            } finally {
//                try {
//                    writer.close();
//                } catch (Exception ex) {//*ignore*//*}
//                }
//
//
//                //User Json
//                try {
//                    json = new String(Files.readAllBytes(Paths.get("jsonResources\\Decks.Json")));
//                    ArrayList<Deck> decks;
//                    decks = new Gson().fromJson(json,
//                            new TypeToken<List<Deck>>() {
//                            }.getType()
//                    );
//                    Deck.setAllDecks(decks);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                try {
//                    json = new String(Files.readAllBytes(Paths.get("jsonResources\\Users.Json")));
//                    ArrayList<User> decks;
//                    decks = new Gson().fromJson(json,
//                            new TypeToken<List<User>>() {
//                            }.getType()
//                    );
//                    User.setAllUsers(decks);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }
}