package com.monopoly.model.finance;

import com.monopoly.model.player.Player;

public class Bank {
    private int treasury;
    private int housePool;
    private int hotelPool;
    private int totalMoney;
    private int lastDiceTotal;

    public Bank(int initialMoney, int houseCount, int hotelCount) {
        this.totalMoney = initialMoney;
        this.treasury = initialMoney;
        this.housePool = houseCount;
        this.hotelPool = hotelCount;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public int getLastDiceTotal() {
        return lastDiceTotal;
    }

    public void setLastDiceTotal(int lastDiceTotal) {
        this.lastDiceTotal = Math.max(2, lastDiceTotal);
    }

    public void addMoney(int amount) {
        if (amount > 0) {
            totalMoney += amount;
        }
    }

    public boolean deductMoney(int amount) {
        if (amount > 0 && totalMoney >= amount) {
            totalMoney -= amount;
            return true;
        }
        return false;
    }

    public void pay(Player player, int amount) {
        if (amount <= 0 || player == null) return;
        if (deductMoney(amount)) {
            player.receive(amount);
        }
    }

    public void collect(int amount) {
        addMoney(amount);
    }

    public void handleDebt(Player debtor, Player creditor, int amount) {
        if (debtor == null || amount <= 0) return;
        debtor.markDebt(3);
        if (creditor != null) {
            creditor.receive(Math.max(0, debtor.getBalance()));
        }
    }

    public void logTransaction(Player from, Player to, int amount) {
        // keep method for compatibility with tile logic
    }

    public void notifyEvent(String eventType, Player player) {
        // hook for observer integration later
    }

    public void useHouse() {
        if (housePool > 0) housePool--;
    }

    public void useHouses(int count) {
        for (int i = 0; i < count; i++) useHouse();
    }

    public void returnHouse() {
        housePool++;
    }

    public void returnHouses(int count) {
        housePool += Math.max(0, count);
    }

    public void useHotel() {
        if (hotelPool > 0) hotelPool--;
    }

    public void returnHotel() {
        hotelPool++;
    }
}