package com.monopoly.model.player;

import com.monopoly.interfaces.IPlayer;
import com.monopoly.model.tile.PropertyTile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Player implements IPlayer { /* đóng vai trò là lớp cha, abstract dùng để đảm bảo không thể khời
 tạo trực tiếp mà thông qua lớp con*/
    protected String name;
    protected int balance;
    protected int position;
    protected static final int GoBonus = 200; //đảm bảo player nhận đc $200 if đi qua hoặc ừng tại ô Go
    protected int frozenTurns;
    protected boolean inJail;
    protected int debtTurnsRemaining;
    protected boolean debtFlag;
    protected int jailFreeCards;
    protected int hardModeSwapCards;
    protected final List<PropertyTile> properties;

    public Player(String name, int initialBalance) {
        this.name = name;
        this.balance = initialBalance; // lưu số tiền ban đầu
        this.position = 0; // lưu địa chỉ ban đầu
        this.frozenTurns = 0;
        this.inJail = false;
        this.debtTurnsRemaining = 0;
        this.debtFlag = false;
        this.jailFreeCards = 0;
        this.hardModeSwapCards = 0;
        this.properties = new ArrayList<>();
    }

    public abstract void takeTurn(int dice1, int dice2); /* dựa vào cách hành động của từng loại người chơi, subclass
    bắt buộc phải override cái method này */

    public abstract void onLandDecision(PropertyTile property, boolean wantToBuy);

    @Override
    public int getBalance() {

        return balance; //trả về giá trị của balance
    }

    @Override
    public void move(int step) {
        int oldPosition = position; // kiểm tra vị trí cũ
        int newPosition = (position + step) % 40; // tính vị trí mới, nếu vị trí vượt quá 40 thì lấy số dư
        boolean passGo = (position + step) >= 40; // kiểm tra có vượt qua ô Go không
        this.position = newPosition; // cập nhật vi trí mới
        System.out.println(name + "move " + step + "steps to position: " + position);
        if (passGo) {
            this.balance += GoBonus;
            System.out.println("Player " + name + "passed Go \nReceive $" + GoBonus + "\nBalance: " + balance);
        }
    }

    public void addBalance(int amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Player: " + name + "added a new balance of " + amount + "\nBalance: " + balance);
        }
    }

    public boolean deductBalance(int amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            System.out.println("Player: " + name + "deducted a balance of " + amount + "\nBalance: " + balance);
            return true;
        }
        System.out.println("Player: " + name + "does not have enough money to deduct");
        return false;
    }

    public boolean pay(int amount) {
        return deductBalance(amount);
    }

    public void receive(int amount) {
        addBalance(amount);
    }

    public String getName() {

        return name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isBankrupt() {

        return balance < 0;
    }

    public void addProperty(PropertyTile property) {
        if (property != null && !properties.contains(property)) {
            properties.add(property);
        }
    }

    public void removeProperty(PropertyTile property) {
        properties.remove(property);
    }

    public List<PropertyTile> getProperties() {
        return Collections.unmodifiableList(properties);
    }

    public void notifyLandedOnUnowned(Object tile) {
        // UI/controller can intercept this later
    }

    public int getFrozenTurns() {
        return frozenTurns;
    }

    public void setFrozenTurns(int turns) {
        this.frozenTurns = Math.max(0, turns);
    }

    public void decrementFrozenTurns() {
        if (frozenTurns > 0) frozenTurns--;
    }

    public boolean isFrozen() {
        return frozenTurns > 0;
    }

    public boolean isInJail() {
        return inJail;
    }

    public void setInJail(boolean inJail) {
        this.inJail = inJail;
    }

    public void addJaillFreeCard() {
        jailFreeCards++;
    }

    public int getJailFreeCards() {
        return jailFreeCards;
    }

    public boolean useJailFreeCard() {
        if (jailFreeCards <= 0) return false;
        jailFreeCards--;
        return true;
    }

    public void addHardModeSwapCard() {
        hardModeSwapCards++;
    }

    public int getHardModeSwapCards() {
        return hardModeSwapCards;
    }

    public boolean useHardModeSwapCard() {
        if (hardModeSwapCards <= 0) return false;
        hardModeSwapCards--;
        return true;
    }

    public void markDebt(int turns) {
        debtFlag = true;
        debtTurnsRemaining = Math.max(0, turns);
    }

    public void clearDebt() {
        debtFlag = false;
        debtTurnsRemaining = 0;
    }

    public boolean isInDebt() {
        return debtFlag;
    }

    public int getDebtTurnsRemaining() {
        return debtTurnsRemaining;
    }

    public void decreaseDebtTurn() {
        if (!debtFlag) return;
        if (debtTurnsRemaining > 0) debtTurnsRemaining--;
        if (debtTurnsRemaining == 0) debtFlag = false;
    }

    @Override
    public String toString() {
        return "Player: " + name + "\nBalance: " + balance + "\nPosition: " + position;
    }
}

