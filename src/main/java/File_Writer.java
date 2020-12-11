import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class File_Writer {

    public static void SubInFile(String city, String chat_id) {

        File file = new File("data.txt");
        HashMap<String, String> map = new HashMap<>();


        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (Integer.parseInt(chat_id) != Integer.parseInt(line.split("-")[0])) {
                    map.put(line.split("-")[0], line.split("-")[1]);
                }
            }
            bufferedReader.close();
            map.put(chat_id, city);
        } catch (IOException e) {
            e.printStackTrace();
        }


        ArrayList<String> val = new ArrayList<>(map.values());
        ArrayList<String> key = new ArrayList<>(map.keySet());


        try {
            // Запись ID и City в data.txt
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(key.get(0) + "-" + val.get(0));
            for (int i = 1; i < map.size(); i++){
                fileWriter.append("\n");
                fileWriter.write(key.get(i) + "-" + val.get(i));
                fileWriter.flush();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void UnsubInFile(String chat_id) {

        File file = new File("data.txt");
        HashMap<String, String> map = new HashMap<>();


        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (Integer.parseInt(chat_id) != Integer.parseInt(line.split("-")[0])) {
                    map.put(line.split("-")[0], line.split("-")[1]);
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        ArrayList<String> val = new ArrayList<>(map.values());
        ArrayList<String> key = new ArrayList<>(map.keySet());


        try {
            // Запись ID и City в data.txt
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(key.get(0) + "-" + val.get(0));
            for (int i = 1; i < map.size(); i++){
                fileWriter.append("\n");
                fileWriter.write(key.get(i) + "-" + val.get(i));
                fileWriter.flush();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String Check(String chat_id){

        File file = new File("data.txt");
        String txt = null;

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (Objects.equals(chat_id, line.split("-")[0])) {
                    txt = "Ты подписан на город: " + line.split("-")[1] + " \uD83D\uDE0F";
                    return txt;
                } else {
                    txt = "У тебя нет подписок \uD83D\uDE12";
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return txt;
    }


    public static HashMap Read() {
        File file = new File("data.txt");
        HashMap<String, String> map = new HashMap<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                map.put(line.split("-")[0], line.split("-")[1]);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;

    }
}
