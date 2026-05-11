package com.monopoly.model.tile;

import com.monopoly.context.GameContext;
import com.monopoly.model.player.Player;

public class JailTile extends Tile {

    public static final int JAIL_POSITION = 10;

    public JailTile(int position) {
        super(position, "Jail / Just Visiting");
    }

    @Override
    public void onLand(Player player, GameContext context) {
        // Nếu bị gửi vào tù từ GoToJailTile thì player.setInJail(true) đã được gọi rồi
        // Nếu đi bộ qua hoặc đặt chân thì chỉ là "Just Visiting" — không làm gì
    }

    public static void sendToJail(Player player) {
        player.setPosition(JAIL_POSITION);
        player.setInJail(true);
        player.setJailTurns(0);
    }
}
