package me.pincer.namelessmcstoregui.objects;

import lombok.Getter;
import me.pincer.namelessmcstoregui.NamelessMCStoreGUI;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Category {
    public static List<Category> categories = new ArrayList<>();
    @Getter
    private final int id;
    @Getter
    private final String name;
    @Getter
    private final boolean hidden;
    @Getter
    private final boolean disabled;

    public Category(int id, String name, boolean hidden, boolean disabled) {
        this.id = id;
        this.name = name;
        this.hidden = hidden;
        this.disabled = disabled;
    }

    public static void fetchCategories() {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    String APIKey = NamelessMCStoreGUI.configValues.get("api-key");
                    String apiServer = NamelessMCStoreGUI.configValues.get("api-url");
                    String requestUrl = apiServer + "/store/categories";

                    URL url = new URL(requestUrl);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestProperty("Authorization", "Bearer " + APIKey);
                    con.setRequestProperty("Content-Type", "application/json");
                    con.setRequestMethod("GET");

                    BufferedReader in = new BufferedReader(new InputStreamReader((con.getInputStream())));
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    while ((inputLine =in.readLine()) != null){
                        response.append(inputLine);
                    }
                    in.close();

                    JSONObject jsonObject = new JSONObject(response.toString()); //Convert text to object
                    JSONArray catData = jsonObject.getJSONArray("categories"); //Get the array named "categories" only

                    for (int i = 0; i < catData.length(); i++) {
                        JSONObject data = catData.getJSONObject(i);
                        int id = data.getInt("id");
                        String name = data.getString("name");
                        boolean hidden = data.getBoolean("hidden");
                        boolean disabled = data.getBoolean("disabled");

                        categories.add(new Category(id, name, hidden, disabled));

                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }.runTaskAsynchronously(NamelessMCStoreGUI.getInstance());
    }
    public static Category getCatById(int id) {
        Category toReturn = null;
        for (Category cat : categories) {
            if (cat.getId() == id) {
                toReturn = cat;
            }
        }
        return toReturn;
    }
    public static Category getCatByName(String name) {
        Category toReturn = null;
        for (Category cat : categories) {
            if (cat.getName().equals(name)) {
                toReturn = cat;
            }
        }
        return toReturn;
    }
}
