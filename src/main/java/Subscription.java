import javax.persistence.*;

@Entity
@Table(name="subscription")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean sub;
    //    @Column(name = "city") //не обязательно указывать если нэйм совпадает
    private String city;
    private long chat_id;

    public Subscription(){}  // обязательный конструктор для Entity

    public Subscription(boolean sub, String city, long chat_id){
        this.sub = sub;
        this.city = city;
        this.chat_id = chat_id;
    }

    // Гетеры
    public long getChat_id() {
        return chat_id;
    }

    public int getId() {
        return id;
    }

    public String getCity() {
        return city;
    }


    // Сетеры
    public void setChat_id(int chat_id) {
        this.chat_id = chat_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setSub(boolean sub) {
        this.sub = sub;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", sub=" + sub +
                ", city='" + city + '\'' +
                ", chat_id=" + chat_id +
                '}';
    }
}
