package classes;

public class PlayerStatsData {
    private static PlayerStats playerStats = new PlayerStats();

    public static void set(PlayerStats playerStats) {
        PlayerStatsData.playerStats = playerStats;
    }

    public static PlayerStats getPlayerStats() {
        return playerStats;
    }
}
