package damas.player;

	public enum PlayerMode {
		CPU,
		HUMAN;

	public PlayerMode opposite() {
		return this == CPU ? HUMAN : CPU;
	}
}
