package com.monopoly.lobby.model;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class RoomRegistry {

    private static final RoomRegistry INSTANCE = new RoomRegistry();
    private final Map<String, GameRoom> rooms = new ConcurrentHashMap<>();

    private RoomRegistry() {}

    public static RoomRegistry getInstance() { return INSTANCE; }

    public String createRoom(String roomName, GameRoom.Difficulty difficulty, int humanPlayers, GameRoom.BotMode botMode) {
        String id = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        GameRoom room = new GameRoom(id, roomName, difficulty, humanPlayers, botMode);
        rooms.put(id, room);
        return id;
    }

    public GameRoom findRoom(String roomId) {
        if (roomId == null) return null;
        return rooms.get(roomId.trim().toUpperCase());
    }

    public void closeRoom(String roomId) {
        rooms.remove(roomId);
    }
}
