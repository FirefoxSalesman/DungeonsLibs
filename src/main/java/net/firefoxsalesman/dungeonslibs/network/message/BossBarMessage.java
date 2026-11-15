package net.firefoxsalesman.dungeonslibs.network.message;

import dev.obscuria.fragmentum.api.network.PacketPayload;
import net.firefoxsalesman.dungeonslibs.client.gui.BossBarRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class BossBarMessage implements PacketPayload {
	private final UUID bar;
	private final int boss;
	private final boolean remove;

	public static final PacketPayload.Info<BossBarMessage> INFO = new PacketPayload.Info<>(
		BossBarMessage.class,
		BossBarMessage::encode,
		BossBarMessage::decode,
		BossBarMessage::handle
	);

	public BossBarMessage(UUID bar, int boss, boolean remove) {
		this.bar = bar;
		this.boss = boss;
		this.remove = remove;
	}

	public BossBarMessage(UUID bar, Mob boss, boolean remove) {
		this(bar, boss.getId(), remove);
	}

	private static void encode(BossBarMessage packet, FriendlyByteBuf buffer) {
		buffer.writeUUID(packet.bar);
		buffer.writeInt(packet.boss);
		buffer.writeBoolean(packet.remove);
	}

	private static BossBarMessage decode(FriendlyByteBuf buffer) {
		return new BossBarMessage(buffer.readUUID(), buffer.readInt(), buffer.readBoolean());
	}

	private static void handle(Player player, BossBarMessage packet) {
		if (player != null) {
			Entity bossEntity = player.level().getEntity(packet.boss);
			if (bossEntity instanceof Mob mob) {
				if (packet.remove) {
					BossBarRenderer.removeBossBar(packet.bar, mob);
				} else {
					BossBarRenderer.addBossBar(packet.bar, mob);
				}
			}
		}
	}

	@Override
	public Info<?> info() {
		return INFO;
	}
}
