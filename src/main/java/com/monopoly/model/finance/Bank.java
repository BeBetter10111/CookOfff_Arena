public class Bank {
    private int treasury;
    private int housePool;
    private int hotelPool;
    private int totalMoney;

    public Bank(int initialMoney, int houseCount, int hotelCount) {
        this.totalMoney = initialMoney;
        this.treasury = initialMoney;
        this.housePool = houseCount;
        this.hotelPool = hotelCount;
    }

    public int getTotalMoney() {
        return totalMoney;
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
}