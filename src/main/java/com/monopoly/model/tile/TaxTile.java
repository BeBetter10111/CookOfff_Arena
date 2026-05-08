public class TaxTile extends Title {
    private int taxAmount;

    public TaxTile(String name, int position, int taxAmount) {
        super(name, position, false);
        this.taxAmount = taxAmount;
    }

    @Override
    public void onLand(Player player, GameContext context) {
        super.onLand(player, context);
        System.out.println(player.getName() + " must pay tax of $" + taxAmount);
        player.deductMoney(taxAmount);
        context.getBank().addMoney(taxAmount);
    }

    public int getTaxAmount() {
        return taxAmount;
    }
}