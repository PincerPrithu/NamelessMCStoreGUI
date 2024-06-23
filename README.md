# NamelessMCStoreGUI Plugin

## Overview

NamelessMCStoreGUI is a Minecraft plugin that provides a customizable graphical user interface (GUI) for Partydragen's [NamelessStore](https://github.com/partydragen/Nameless-Store) module. The plugin uses configuration files to customize various aspects of the GUI, such as icons and messages, and interacts with the NamelessMC API to fetch store categories and products.

## Features

- Customizable GUI
- Integration with an external API to fetch and display store categories and products, no need to create them yourself
- Configurable messages
- Easy-to-use commands for players to open the store GUI and browse your webstore ingame

## Installation

1. **Download the Plugin:**
   - Download the latest version of the NamelessMCStoreGUI plugin from the [releases](https://github.com/your-repo/releases](https://github.com/PincerPrithu/NamelessMCStoreGUI/releases) page.

2. **Install the Plugin:**
   - Place the downloaded JAR file into your server's `plugins` directory.

3. **Start the Server:**
   - Start or restart your Minecraft server to generate the default configuration files.

4. **Add API details:**
   - Edit the `config.yml` file in the `plugins/NamelessMCStoreGUI` directory. You must set API key, API URL and Store URL.
   - API Key & URL can be found by going to your NamelessMC website panel > Configuration > API
   - Store URL is the home page of your NamelessMC store. Ex: https://yourstore.com/**store**
     
5. **Reload the Plugin:**
   - Use `/webstore reload` in your console or ingame (permission: namelessmcgui.admin)

## Configuration

### `config.yml`

```yaml
api-credentials:
  api-url: "https://mywebsite.com/api/v2"
  api-key: "supersecret"
  timeout-seconds: 15

settings:
  store-url: "https://myminecraftserver.com/store"
  primary-color: "GREEN"

# The following are the default settings for the GUI
gui-icons:
  # What fills the edges of the GUI
  border-item:
    display-name: ""
    lore: [ ]
    material: "GRAY_STAINED_GLASS_PANE"
    quantity: 1
  # Back Button
  back-item:
    display-name: "&c&lBack to Categories"
    lore: [ ]
    material: "RED_STAINED_GLASS_PANE"
    quantity: 1
  # Exit Button
  close-item:
    display-name: "&c&lClose Menu"
    lore: [ ]
    material: "RED_STAINED_GLASS_PANE"
    quantity: 1
  # Category Item (Variables: {category_name}, {category_id}, {category_description}, {number_of_products})
  category-item:
    display-name: "&e&l{category_name}"
    lore: [ "&7{number_of_products} Products" ]
    material: "CHEST"
    quantity: 1
  # Product Item (Variables: {product_name}, {product_price}, {product_description}, {product_id}, {product_url})
  product-item:
    display-name: "&a&l{product_name} &e(${product_price})"
    lore:
      - "&7Click to view this item on our web store"
    material: "CHEST_MINECART"
    quantity: 1
    on-click-message: "&aView {product_name} on our webstore: {product_url}"

messages:
  no-permission: "&cYou do not have permission to use this command."
  no-products: "&cThere are no products in this category."
  no-webstore: "&cThe webstore is currently unavailable."
  no-store-url: "&cThe store URL is not set up correctly."
  no-gui-icons: "&cThe GUI icons are not set up correctly."
```

### Registering CustomItem Class

Ensure `CustomItem` is registered for serialization in your main plugin class:

```java
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

public class NamelessMCStoreGUI extends JavaPlugin {
    @Override
    public void onEnable() {
        // Register CustomItem for configuration serialization
        ConfigurationSerialization.registerClass(CustomItem.class);

        // Other initialization code...
    }

    // Other methods...
}
```

## Usage

### Commands

- **/openstore**: Opens the store GUI for the player.

### GUI Navigation

- **Border Item**: Items that fill the edges of the GUI for visual separation.
- **Back Button**: A button to navigate back to the categories.
- **Close Button**: A button to close the GUI.
- **Category Item**: Represents a store category. Clicking it will show the products in that category.
- **Product Item**: Represents a product. Clicking it will provide more information and a link to view the product on the webstore.

## Development

### Setting Up the Development Environment

1. **Clone the Repository**:
   ```sh
   git clone https://github.com/your-repo/NamelessMCStoreGUI.git
   cd NamelessMCStoreGUI
   ```

2. **Import the Project**:
   - Open the project in your preferred IDE (e.g., IntelliJ IDEA).
   - Ensure you have the Spigot API and other dependencies set up in your development environment.

3. **Build the Plugin**:
   - Use Maven or Gradle to build the project and generate the JAR file.

### Contributions

Contributions are welcome! Please open an issue or submit a pull request with your improvements.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

Thank you for using NamelessMCStoreGUI! If you have any questions or need further assistance, feel free to open an issue on GitHub.
