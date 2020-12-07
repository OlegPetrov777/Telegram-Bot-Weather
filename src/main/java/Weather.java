import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Weather {
    public static String getWeatherNow(String message, Model model) throws IOException {
        String API_ID = "dc633a261da8fc3210fde781160bf996";
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + message +
                "&units=metric&appid=" + API_ID + "&lang=ru");

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }

        JSONObject object = new JSONObject(result);
        model.setName(object.getString("name"));

        JSONObject main = object.getJSONObject("main");
        model.setTemp(main.getDouble("temp"));
        model.setHumidity(main.getDouble("humidity"));

        JSONArray getArray = object.getJSONArray("weather");
        for (int i = 0; i < getArray.length(); i++) {
            JSONObject obj = getArray.getJSONObject(i);
            model.setMain((String) obj.get("description"));
        }


        return "Город: " + model.getName() + "\n" +
                "Температура: " + model.getTemp() + " C° \n" +
                "Влажность: " + model.getHumidity() + " % \n" +
                "Погода: " + model.getMain() + "\n";
        //"http://openweathermap.org/img/w/" + model.getIcon() + ".png";
    }

    public static String getWeatherDay(String message, Model model) throws IOException {
        String name;
        String[] time = new String[8];
        Double[] temp_min = new Double[8];
        Double[] temp_max = new Double[8];
        Double[] humidity = new Double[8];
        String[] weather = new String[8];

        String API_ID = "dc633a261da8fc3210fde781160bf996";
        URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?q=" + message +
                "&units=metric&cnt=8&appid=" + API_ID + "&lang=ru");

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }

        JSONObject object = new JSONObject(result);

        JSONObject city = object.getJSONObject("city");
        name = city.getString("name");

        JSONArray getArray = object.getJSONArray("list");
        for (int i = 0; i < getArray.length(); i++) {
            JSONObject obj = getArray.getJSONObject(i);
            time[i] = (String) obj.get("dt_txt");

            JSONArray getArray_2 = obj.getJSONArray("weather");
            for (int j = 0; j < getArray_2.length(); j++) {
                JSONObject objj = getArray_2.getJSONObject(j);
                weather[i] = (String) objj.get("description");
            }

            JSONObject temp = obj.getJSONObject("main");
            temp_min[i] = temp.getDouble("temp_min");
            temp_max[i] = temp.getDouble("temp_max");
            humidity[i] = temp.getDouble("humidity");
        }


        return "\uD83C\uDFD8 " + name + " \uD83C\uDFD8\n" +
                "\uD83D\uDCC6 " +
                time[0].substring(8, 10) + "." + time[0].substring(5, 7) + "." + time[0].substring(0, 4) +
                " \uD83D\uDCC6\n\n   " +

                "⏰ " + time[0].substring(10, 16) + "  ⏰\n" +
                "Температура: " + temp_min[0] + "°С  —  " + temp_max[0] + "°С\n" +
                "Влажнось: " + humidity[0] + "%\n" +
                "Погода: " + weather[0] + "\n\n   " +

                "⏰ " + time[1].substring(10, 16) + "  ⏰\n" +
                "Температура: " + temp_min[1] + "°С  —  " + temp_max[1] + "°С\n" +
                "Влажнось: " + humidity[1] + "%\n" +
                "Погода: " + weather[1] + "\n\n   " +

                "⏰ " + time[2].substring(10, 16) + "  ⏰\n" +
                "Температура: " + temp_min[2] + "°С  —  " + temp_max[2] + "°С\n" +
                "Влажнось: " + humidity[2] + "%\n" +
                "Погода: " + weather[2] + "\n\n   " +

                "⏰ " + time[3].substring(10, 16) + "  ⏰\n" +
                "Температура: " + temp_min[3] + "°С  —  " + temp_max[3] + "°С\n" +
                "Влажнось: " + humidity[3] + "%\n" +
                "Погода: " + weather[3] + "\n\n   " +

                "⏰ " + time[4].substring(10, 16) + "  ⏰\n" +
                "Температура: " + temp_min[4] + "°С  —  " + temp_max[4] + "°С\n" +
                "Влажнось: " + humidity[4] + "%\n" +
                "Погода: " + weather[4] + "\n\n   " +

                "⏰ " + time[5].substring(10, 16) + "  ⏰\n" +
                "Температура: " + temp_min[5] + "°С  —  " + temp_max[5] + "°С\n" +
                "Влажнось: " + humidity[5] + "%\n" +
                "Погода: " + weather[5] + "\n\n   " +

                "⏰ " + time[6].substring(10, 16) + "  ⏰\n" +
                "Температура: " + temp_min[6] + "°С  —  " + temp_max[6] + "°С\n" +
                "Влажнось: " + humidity[6] + "%\n" +
                "Погода: " + weather[6] + "\n\n   " +

                "⏰ " + time[7].substring(10, 16) + "  ⏰\n" +
                "Температура: " + temp_min[7] + "°С  —  " + temp_max[7] + "°С\n" +
                "Влажнось: " + humidity[7] + "%\n" +
                "Погода: " + weather[7];
    }
}
