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

import static me.pincer.namelessmcstoregui.NamelessMCStoreGUI.log;

public class Product {
    @Getter
    static List<Product> uncategorizedProducts = new ArrayList<>();
    @Getter
    private final int id;
    @Getter
    private final int categoryId;
    @Getter
    Category category;
    @Getter
    private final float price;
    @Getter
    private final String name;
    @Getter
    private final boolean hidden;
    @Getter
    private final boolean disabled;

    public Product(int id, int categoryId, float price, String name, boolean hidden, boolean disabled) {
        this.id = id;
        this.name = name;
        this.hidden = hidden;
        this.disabled = disabled;
        this.categoryId = categoryId;
        this.price = price;

        try {
            Category.getCatById(categoryId).addProduct(this);
        } catch (Exception e) {
            uncategorizedProducts.add(this);
        }
    }

    public static void fetchProducts() {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    String APIKey = NamelessMCStoreGUI.configValues.get("api-key");
                    String apiServer = NamelessMCStoreGUI.configValues.get("api-url");
                    String requestUrl = apiServer + "/store/products";

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
                    JSONArray proData = jsonObject.getJSONArray("products"); //Get the array named "categories" only

                    for (int i = 0; i < proData.length(); i++) {
                        JSONObject data = proData.getJSONObject(i);
                        int id = data.getInt("id");
                        int catid = data.getInt("category_id");
                        String name = data.getString("name");
                        float price = data.getFloat("price");
                        boolean hidden = data.getBoolean("hidden");
                        boolean disabled = data.getBoolean("disabled");

                        Product product = new Product(id, catid, price, name, hidden, disabled);
                    }
                } catch (Exception e) {
                    log("Failed to fetch Products from API:" + e.getMessage());
                    log("Please check your API credentials in the config.yml file and use /webstore reload to try again.");
                }
            }
        }.runTaskAsynchronously(NamelessMCStoreGUI.getInstance());
    }
}