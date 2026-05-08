package com.monopoly.strategy;

import com.monopoly.interfaces.IBuyStrategy;
import com.monopoly.model.player.Player;
import com.monopoly.model.tile.PropertyTile;
import com.monopoly.model.tile.RailroadTile;
import com.monopoly.model.tile.UtilityTile;
import com.monopoly.model.board.Tile;

/**
 * ConservativeBuyStrategy — Strategy Pattern
 *
 * AI chơi thủ, ưu tiên giữ tiền mặt.
 * Chỉ mua khi đủ an toàn, không xây nhà khi gần cạn tiền.
 *
 * SOLID:
 *  - SRP: chỉ chứa logic quyết định mua/xây
 *  - OCP: thêm rule mới không cần sửa class khác
 *  - LSP: thay thế hoàn toàn cho IBuyStrategy
 *  - DIP: AIPlayer inject interface, không biết class cụ thể này
 */
public class ConservativeBuyStrategy implements IBuyStrategy {

    // Luôn giữ ít nhất ngưỡng này sau khi mua
    private static final int SAFETY_BUFFER = 500;

    // Chỉ xây nhà khi balance vượt ngưỡng này
    private static final int BUILD_THRESHOLD = 800;

    // Không mua đất nếu giá > tỉ lệ này so với balance hiện tại
    private static final double MAX_SPEND_RATIO = 0.4;

    /**
     * Quyết định có nên mua đất/railroad/utility không.
     *
     * Logic:
     *  1. Sau khi mua, balance phải còn >= SAFETY_BUFFER
     *  2. Giá mua không được vượt quá 40% balance hiện tại
     *  3. Ưu tiên mua nếu đã sở hữu đất cùng màu (gần monopoly)
     */
    @Override
    public boolean shouldBuy(Tile tile, Player player) {
        int price = getTilePrice(tile);
        if (price <= 0) return false;

        int balanceAfterBuy = player.getBalance() - price;

        // Rule 1: Phải còn đủ buffer sau khi mua
        if (balanceAfterBuy < SAFETY_BUFFER) return false;

        // Rule 2: Không dốc quá 40% tiền vào một lần mua
        if (price > player.getBalance() * MAX_SPEND_RATIO) return false;

        // Rule 3: Nếu đã gần monopoly (sở hữu đất cùng màu) → ưu tiên mua
        if (tile instanceof PropertyTile pt) {
            boolean nearMonopoly = player.getProperties().stream()
                    .anyMatch(p -> p.getColorGroup().equals(pt.getColorGroup()));
            if (nearMonopoly) return balanceAfterBuy >= SAFETY_BUFFER;
        }

        // Rule 4: Railroad — mua nếu an toàn (railroad có giá trị tích lũy)
        if (tile instanceof RailroadTile) {
            long ownedRailroads = player.getProperties().stream()
                    .filter(p -> p instanceof RailroadTile)
                    .count();
            return balanceAfterBuy >= SAFETY_BUFFER && ownedRailroads < 3;
        }

        // Mặc định: chỉ mua khi đủ an toàn
        return balanceAfterBuy >= SAFETY_BUFFER;
    }

    /**
     * Quyết định có nên xây nhà trên đất không.
     *
     * Logic:
     *  1. Balance phải > BUILD_THRESHOLD
     *  2. Phải có monopoly (sở hữu đủ cả nhóm màu)
     *  3. Chỉ xây tối đa 2 nhà — không đầu tư quá mức
     *  4. Phải còn đủ buffer sau khi trả tiền xây
     */
    @Override
    public boolean shouldBuildHouse(PropertyTile tile, Player player) {
        // Rule 1: Đủ tiền
        if (player.getBalance() <= BUILD_THRESHOLD) return false;

        // Rule 2: Phải có monopoly trên nhóm màu này
        boolean hasMonopoly = hasFullColorGroup(tile, player);
        if (!hasMonopoly) return false;

        // Rule 3: Không xây quá 2 nhà (conservative giữ tiền)
        if (tile.getHouses() >= 2) return false;

        // Rule 4: Sau khi xây vẫn còn đủ buffer
        int buildCost = tile.getHouseCost();
        int balanceAfter = player.getBalance() - buildCost;
        return balanceAfter >= SAFETY_BUFFER;
    }

    // ─── helpers ────────────────────────────────────────────────

    /**
     * Kiểm tra player có sở hữu toàn bộ nhóm màu của tile không.
     */
    private boolean hasFullColorGroup(PropertyTile tile, Player player) {
        String color = tile.getColorGroup();
        long totalInGroup = tile.getTotalInColorGroup();
        long owned = player.getProperties().stream()
                .filter(p -> p.getColorGroup().equals(color))
                .count();
        return owned == totalInGroup;
    }

    /**
     * Lấy giá mua của tile (PropertyTile, RailroadTile, UtilityTile).
     * Trả 0 nếu không phải loại mua được.
     */
    private int getTilePrice(Tile tile) {
        if (tile instanceof PropertyTile pt) return pt.getPrice();
        if (tile instanceof RailroadTile rt) return rt.getPrice();
        if (tile instanceof UtilityTile ut) return ut.getPrice();
        return 0;
    }

    @Override
    public String toString() {
        return "ConservativeBuyStrategy [buffer=$" + SAFETY_BUFFER
                + ", buildThreshold=$" + BUILD_THRESHOLD
                + ", maxSpendRatio=" + (int)(MAX_SPEND_RATIO * 100) + "%]";
    }
}