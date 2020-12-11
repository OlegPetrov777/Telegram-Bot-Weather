import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        startBot();

        while (true){
            Time();
        }
    }


    public static void startBot(){
        ApiContextInitializer.init();   //иницилизация АПИ
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(); // создаем объект телеграмАПИ

        // Регистариця бота
        try {
            telegramBotsApi.registerBot(new Bot());

        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }


    public static void Time() throws IOException, InterruptedException {
        Date date = new Date();
        if (Objects.equals(date.toString().split(" ")[3], "09:00:00")) {
            Bot bot = new Bot();
            bot.sendWeather();
            TimeUnit.SECONDS.sleep(1);
        } else if (Objects.equals(date.toString().split(" ")[3], "15:00:00")){
            Bot bot = new Bot();
            bot.sendWeather();
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
