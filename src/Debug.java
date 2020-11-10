import com.google.gson.Gson;

import java.util.ArrayList;

public class Debug {
    public static void main(String[] args) {
        Gson gson = new Gson();
        Card lastCard = null;
        String jsonLastCard = gson.toJson(lastCard);

        Card jCard = gson.fromJson(jsonLastCard, Card.class);

        int a;
    }
}
