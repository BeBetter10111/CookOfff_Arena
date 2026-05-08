public class PropertyTile extends Title {
    private int price;
    private int rent;
    private Player owner;

    public PropertyTile(String name, int position, int price, int rent) {
        super(name, position, true);
        this.price = price;
        this.rent = rent;
        this.owner = null; 
    }

    @Override
    public void onLand(Player player, GameContext context) {
        super.onLand(player, context);
        if (owner == null) {
            System.out.println(player.getName() + " landed on " + getName() + " which is unowned. Price: $" + price);
            // Here you would implement logic to allow the player to buy the property
        } else if (owner != player) {
            System.out.println(player.getName() + " landed on " + getName() + " owned by " + owner.getName() + ". Rent: $" + rent);
            // Here you would implement logic to pay rent to the owner
        } else {
            System.out.println(player.getName() + " landed on their own property: " + getName());
        }
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }
}