package com.corruptedbyte.netherrpg.managers;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import static org.bukkit.ChatColor.*;
import static org.bukkit.enchantments.Enchantment.*;


public class ItemDataBase {

    // TYPE
    static class Types {
        public static int Trash = 0;
        public static int Material = 1;
        public static int Tool = 2;
        public static int Weapon = 3;
        public static int Armour = 4;
        public static int Consumable = 5;
        public static int Amulet = 6;
        public static int Ability = 7;

        public static String getName(int type) {
            return switch (type) {
                case 0 -> "Мусор";
                case 1 -> "Материал";
                case 2 -> "Инструмент";
                case 3 -> "Оружие";
                case 4 -> "Броня";
                case 5 -> "Расходник";
                case 6 -> "Амулет";
                case 7 -> "Способность";
                default -> "> Error, please contact DragonTheByte <";
            };
        }
    }


    // RARITY
    static class Rarities {
        public static int Trash = 0;
        public static int Common = 1;
        public static int Uncommon = 2;
        public static int Rare = 3;
        public static int Epic = 4;
        public static int Legendary = 5;
        public static int Chaotic = 6;
        public static int Void = 7;

        public static String getName(int rarity) {
            return switch (rarity) {
                case 0 -> "Мусор";
                case 1 -> "Обычная";
                case 2 -> "Необычная";
                case 3 -> "Редкая";
                case 4 -> "Эпическая";
                case 5 -> "Легендарная";
                case 6 -> "Хаотическая";
                case 7 -> "Пустотная";
                default -> "> Error, please contact DragonTheByte <";
            };
        }

        public static ChatColor getColor(int rarity) {
            return switch (rarity) {
                case 0 -> DARK_GRAY;
                case 1 -> WHITE;
                case 2 -> AQUA;
                case 3 -> GREEN;
                case 4 -> DARK_PURPLE;
                case 5 -> GOLD;
                case 6 -> DARK_RED;
                case 7 -> BLACK;
                default -> ITALIC;
            };
        }
    }


    // ITEM
    public static class Item {
        public int type;
        public int id;
        public Material material;
        public int rarity;
        public String name;
        public List<String> lore;
        private List<String> descriptions;
        public float damage;
        public HashMap<Enchantment, Integer> enchantments;
        public ItemStack itemStack;

        public Item(int id, Material material, int rarity, int type, String name) {
            this.type = type;
            this.id = id;
            this.material = material;
            this.rarity = rarity;
            this.name = name;
            this.descriptions = new ArrayList<>();
            this.enchantments = new HashMap<>();
            this.lore = generateLore();
            this.damage = 0;
            this.itemStack = new ItemStack(Material.AIR);
        }

        private List<String> generateLore() {
            lore = new ArrayList<>();
            lore.add(GREEN+"Редкость"+GOLD+": "+Rarities.getColor(this.rarity)+Rarities.getName(this.rarity));
            lore.add(GREEN+"Тип"+GOLD+": "+WHITE+Types.getName(this.type));
            if (this.damage > 0) lore.add(GREEN+"Урон"+GOLD+": "+RED+this.damage);
            lore.add(GREEN+"Описание"+GOLD+": ");
            lore.addAll(this.descriptions);
            return lore;
        }

        public Item SetDMG(float d) {this.damage = d; return this;}
        public Item AddLore(String l) {this.descriptions.add(WHITE+"  "+l); return this;}
        public Item AddEnch(Enchantment e, int l) {this.enchantments.put(e, l); return this;}
        public Item Finish(Logger log) {
            this.lore = generateLore();
            this.itemStack.setType(this.material);
            ItemMeta meta = this.itemStack.getItemMeta();
            this.enchantments.forEach((e, l) -> meta.addEnchant(e, l, true));
            meta.setLore(this.lore);
            meta.setDisplayName(this.name);
            meta.setUnbreakable(true);
            meta.setCustomModelData(this.id);
            meta.addItemFlags(ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
            this.itemStack.setItemMeta(meta);
            log.info("- Registered "+this.id+": "+this.name);
            return this;
        }
    }


    // MAIN
    public static HashMap<Integer, Item> items = new HashMap<>();

    // PROPERTIES
    public static HashMap<Integer, Integer> itemDamage = new HashMap<>();
    public static HashMap<Integer, Integer> itemRarity = new HashMap<>();
    public static HashMap<Integer, Integer> itemType = new HashMap<>();
    public static HashMap<Integer, Integer> itemToolPower = new HashMap<>();

    // CRAFTS
    public static HashMap<Integer, HashMap<Integer, Integer>> itemCraftsWorkbench = new HashMap<>();
    // public static HashMap<Integer, HashMap<Integer, Integer>> itemCraftsFurnace = new HashMap<>();
    // public static HashMap<Integer, HashMap<Integer, Integer>> itemCraftsAnvil = new HashMap<>();
    // public static HashMap<Integer, HashMap<Integer, Integer>> itemCraftsStonecutter = new HashMap<>();
    // public static HashMap<Integer, HashMap<Integer, Integer>> itemCraftsEnchantingTable = new HashMap<>();


    public static void Initialize(Logger log) {
        log.warning("Я ненавижу создавать плагины, спасибо за внимание! -DragonTheByte (ака DeYamon)");
        log.info("Registering items...");
        Reg(new Item(0, Material.DIAMOND, Rarities.Epic, Types.Material, AQUA+"Алмаз").AddEnch(LUCK, 1).AddLore("Прочный материал голубого цвета.").SetDMG(100).Finish(log));
        log.info("Items registered!");
    }

    public static void Reg(Item item) {
        items.put(item.id, item);
    }
}
