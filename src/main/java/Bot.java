import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.util.*;


public class Bot extends TelegramLongPollingBot {

    // Метод для отправки простого сообщения
    public void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(message.getChatId().toString());
        try {
            setButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // овтет пересланным сообщением
    public void send_Msg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();  // создал объект СендМесседж
        sendMessage.enableMarkdown(true);  // выкл возможность разметки (наверное) (ваще хз что это)
        sendMessage.setChatId(message.getChatId().toString());  //  id чата
        sendMessage.setReplyToMessageId(message.getMessageId());  //  id сообщения
        sendMessage.setText(text);
        // отправка сообщения
        try {
            setButtons(sendMessage);
            sendMessage(sendMessage);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // метод для рассылки погоды
    public void sendWeather() throws IOException {
        Model model = new Model();
        HashMap map = File_Writer.Read();
        ArrayList<String> val = new ArrayList<>(map.values());
        ArrayList<String> key = new ArrayList<>(map.keySet());
        for (int i = 0; i < map.size(); i++){
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText(Weather.getWeatherDay(val.get(i), model));
            sendMessage.setChatId(key.get(i));
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }


    // запись\удаление пользователей, которые подписались\отписались
    public void user_subscribe(String city, String chat_id) {
        File_Writer.SubInFile(city, chat_id);
    }
    public void user_unsubscribe(String chat_id) {
        File_Writer.UnsubInFile(chat_id);
    }


    // Метод приема обновлений (WebHook or *LongHook*)
    public void onUpdateReceived(Update update) {
        Model model = new Model();
        Message message = update.getMessage();
        if (update.hasMessage()) {
            if (message != null && message.hasText()) {
                switch (message.getText()) {
                    case "Помощь":
                        sendMsg(message, "Чем могу помочь?\uD83E\uDDD0\nМожет ты напишешь свой город? М?\uD83E\uDD14");
                        break;
                    case "Старт":
                    case "/start":
                        sendMsg(message, "Привет \uD83D\uDC4B\nНапиши свой город \uD83D\uDE42✍️");
                        break;
                    case "/subscribe":
                    case "/unsubscribe":
                        sendMsg(message, "Лучше напиши свой город ✍️\nИ нажми кнопку под сообщением \uD83D\uDE42");
                        break;
                    case "Проверить подписку":
                        //sendMsg(message, File_Writer.Check(String.valueOf(message.getChatId())));
                        try {
                            if (Objects.equals(File_Writer.Check(String.valueOf(message.getChatId())).split(" ")[0], "Ты")) {
                                execute(sendInlineKeyBoardMessage_2(update.getMessage().getChatId(),
                                        File_Writer.Check(String.valueOf(message.getChatId()))));
                            } else {
                                sendMsg(message, File_Writer.Check(String.valueOf(message.getChatId())));
                            }
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        try {
                            execute(sendInlineKeyBoardMessage(update.getMessage().getChatId(),
                                    Weather.getWeatherDay(message.getText(), model)));
                        } catch (TelegramApiException | IOException e) {
                            sendMsg(message, "ERROR: 404\nГород не найден \uD83E\uDD74\nНапиши \"Помощь\"✍️");
                        }
                }
            }
        } else if (update.hasCallbackQuery()) {
            try {
                // НЕОБЫЧНОЕ РЕШЕНИЕ
                SendMessage mes = new SendMessage();    // mes - объект - отвтетное сообщение
                mes.setText(update.getCallbackQuery().getData()).setChatId(update.
                        getCallbackQuery().getMessage().getChatId());

                // проверяю длинну второго слова обратного сообщения
                if (mes.getText().split(" ")[1].length() == 11) {
                    user_subscribe(mes.getText().split(" ")[4], mes.getChatId());   // вызов метода подписки
                    execute(mes);

                } else if (mes.getText().split(" ")[1].length() == 10){
                    user_unsubscribe(mes.getChatId());   // вызов метода отписки
                    execute(mes);
                }
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }


    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(); // создал клавиатуру
        sendMessage.setReplyMarkup(replyKeyboardMarkup); // разметка клавиатуры
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow(); // 1я строчка клавы
        KeyboardRow keyboardSecndRow = new KeyboardRow(); // 2
        KeyboardRow keyboardThirdRow = new KeyboardRow(); // 3

        keyboardFirstRow.add(new KeyboardButton("Старт"));
        keyboardSecndRow.add(new KeyboardButton("Помощь"));
        keyboardThirdRow.add(new KeyboardButton("Проверить подписку"));

        keyboardRowList.add(keyboardFirstRow); // добавляем все сторчки клавиатуры в список
        keyboardRowList.add(keyboardSecndRow);
        keyboardRowList.add(keyboardThirdRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList); // установка списка на клаве
    }


    public static SendMessage sendInlineKeyBoardMessage_2(long chatId, String text) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();  // объект разметки

        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();  // Кнопка 1

        inlineKeyboardButton1.getSwitchInlineQuery();
        inlineKeyboardButton1.setText("Отписаться");  // txt 2
        inlineKeyboardButton1.setCallbackData("Вы отписались от города " +
                text.split(" ")[4]);

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();  // создал ряды (1 и 2)
        keyboardButtonsRow1.add(inlineKeyboardButton1);  // добавил в них наши по кнопке

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(); // лист с рядами
        rowList.add(keyboardButtonsRow1);  // запихиваем ряды в лимт

        inlineKeyboardMarkup.setKeyboard(rowList);  // установка кнопки в обьект разметки клавиатуры
        return new SendMessage().setChatId(chatId).
                setText(text).setReplyMarkup(inlineKeyboardMarkup);  // отправка сообщения к которому крепятся кнопки
    }


    public static SendMessage sendInlineKeyBoardMessage(long chatId, String text) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();  // объект разметки

        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();  // Кнопка 1
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();  // и 2

        inlineKeyboardButton1.setText("Подписаться");  // Текст кнопок
        inlineKeyboardButton1.setCallbackData("Вы подписались на город " +
                text.split("\n")[0].substring(3, text.split("\n")[0].length() - 2));  //то, что напишет после нажатия
        inlineKeyboardButton1.getSwitchInlineQuery();
        inlineKeyboardButton2.setText("Отписаться");  // txt 2
        inlineKeyboardButton2.setCallbackData("Вы отписались от города " +
                text.split("\n")[0].substring(3, text.split("\n")[0].length() - 2));

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();  // создал ряды (1 и 2)
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);  // добавил в них наши по кнопке
        keyboardButtonsRow2.add(inlineKeyboardButton2);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(); // лист с рядами
        rowList.add(keyboardButtonsRow1);  // запихиваем ряды в лимт
        rowList.add(keyboardButtonsRow2);

        inlineKeyboardMarkup.setKeyboard(rowList);  // установка кнопки в обьект разметки клавиатуры
        return new SendMessage().setChatId(chatId).
                setText(text).setReplyMarkup(inlineKeyboardMarkup);  // отправка сообщения к которому крепятся кнопки
    }


    public String getBotUsername() {
        return "pi19_2_java_weather_bot";
    }

    public String getBotToken() {
        return "1426902506:AAEYq4lp-4X_jq1W1kw3RGN0TskMFMs_75M";
    }
}
