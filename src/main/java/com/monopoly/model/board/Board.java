package com.monopoly.model.board;


import com.monopoly.model.player.Player;
import com.monopoly.model.tile.Tile;


import java.util.ArrayList;
import java.util.List;


public class Board {


    private final List<Tile> tiles;
    private final List<Player> players;


    public Board(List<Tile> tiles) {
        this.tiles = new ArrayList<>(tiles);
        this.players = new ArrayList<>();
    }


    public Tile getTile(int position) {
        return tiles.get(position % tiles.size());
    }


    public int getSize() {
        return tiles.size();
    }


    public void addPlayer(Player player) {
        players.add(player);
    }


    public void removePlayer(Player player) {
        players.remove(player);
    }


    public List<Player> getPlayers() {
        return List.copyOf(players);
    }


    public List<Tile> getTiles() {
        return List.copyOf(tiles);
    }


    public int normalizePosition(int position) {
        return position % tiles.size();
    }


    public boolean didPassGo(int oldPosition, int steps) {
        return (oldPosition + steps) >= tiles.size();
    }
}
