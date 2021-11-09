package space.impact.api;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import space.impact.api.multiblocks.alignment.IAlignment;
import space.impact.api.multiblocks.alignment.IAlignmentProvider;
import space.impact.api.multiblocks.alignment.constructable.ConstructableUtility;
import space.impact.api.multiblocks.alignment.enumerable.ExtendedFacing;
import space.impact.api.net.AlignmentMessage;

import static space.impact.api.Main.proxy;

/**
 * Стабильный интерфейс к внутренним компонентам Impact API. Обратная совместимость поддерживается в максимально возможной степени.
 */
public class ImpactAPI {
	public static final String MOD_ID = "impactapi";
	public static final int WHITE = 0, ORANGE = 1, MAGENTA = 2, L_BLUE = 3, YELLOW = 4, LIME = 5, PINK = 6, GRAY = 7, L_GRAY = 8, CYAN = 9, PURPLE = 10, BLUE = 11, BROWN = 12, GREEN = 13, RED = 14, BLACK = 15;
	
	/**
	 * Запуск процесса подсказок. Все частицы подсказок, сгенерированные в течение одного процесса, будут считаться принадлежащими одной голограмме.
	 * <p>
	 * Вам не нужно вызывать эту функцию, если только ваш триггер инструмент не вызвал функцию {@link ConstructableUtility#handle(ItemStack, EntityPlayer, World, int, int, int, int)}
	 */
	public static void startHinting(World w) {
		proxy.startHinting(w);
	}
	
	/**
	 * Запуск текущего процесса подсказок. Все частицы подсказок, сгенерированные в течение одного процесса, будут считаться принадлежащими одной голограмме.
	 * <p>
	 * Вам не нужно вызывать эту функцию, если только ваш триггер инструмент не вызвал функцию {@link ConstructableUtility#handle(ItemStack, EntityPlayer, World, int, int, int, int)}
	 */
	public static void endHinting(World w) {
		proxy.endHinting(w);
	}
	
	public static void hintParticleTinted(World w, int x, int y, int z, IIcon[] icons, short[] RGBa) {
		proxy.hintParticleTinted(w, x, y, z, icons, RGBa);
	}
	
	public static void hintParticleTinted(World w, int x, int y, int z, Block block, int meta, short[] RGBa) {
		proxy.hintParticleTinted(w, x, y, z, block, meta, RGBa);
	}
	
	public static void hintParticle(World w, int x, int y, int z, IIcon[] icons) {
		proxy.hintParticle(w, x, y, z, icons);
	}
	
	public static void hintParticle(World w, int x, int y, int z, Block block, int meta) {
		proxy.hintParticle(w, x, y, z, block, meta);
	}
	
	/**
	 * Запрос ExtendedFacing данного TileEntity.
	 * В дальнейшем ExtendedFacing будет установлен для данного TileEntity через {@link IAlignment#setExtendedFacing(ExtendedFacing)} после получения ответа от сервера.
	 *
	 * @throws IllegalArgumentException если не является TileEntity или предоставил null.
	 */
	public static void queryAlignment(IAlignmentProvider provider) {
		Main.net.sendToServer(new AlignmentMessage.AlignmentQuery(provider));
	}
	
	/**
	 * Отправляет ExtendedFacing TileEntity всем игрокам. Может вызываться только на стороне сервера.
	 *
	 * @throws IllegalArgumentException если не является TileEntity или предоставил null.
	 */
	public static void sendAlignment(IAlignmentProvider provider) {
		Main.net.sendToAll(new AlignmentMessage.AlignmentData(provider));
	}
	
	/**
	 * Отправляет ExtendedFacing TileEntity игроку. Может вызываться только на стороне сервера.
	 *
	 * @throws IllegalArgumentException если не является TileEntity или предоставил null.
	 */
	public static void sendAlignment(IAlignmentProvider provider, EntityPlayerMP player) {
		Main.net.sendTo(new AlignmentMessage.AlignmentData(provider), player);
	}
	
	/**
	 * Отправляет ExtendedFacing TileEntity всем игрокам вокруг целевой точки. Может вызываться только на стороне сервера.
	 *
	 * @throws IllegalArgumentException если не является TileEntity или предоставил null.
	 */
	public static void sendAlignment(IAlignmentProvider provider, NetworkRegistry.TargetPoint targetPoint) {
		Main.net.sendToAllAround(new AlignmentMessage.AlignmentData(provider), targetPoint);
	}
	
	
	/**
	 * Отправляет ExtendedFacing TileEntity всем игрокам в измерении. Может вызываться только на стороне сервера.
	 *
	 * @throws IllegalArgumentException если не является TileEntity или предоставил null.
	 */
	public static void sendAlignment(IAlignmentProvider provider, World dimension) {
		Main.net.sendToDimension(new AlignmentMessage.AlignmentData(provider), dimension.provider.dimensionId);
	}
	
	public static Block getBlockHint() {
		return Main.blockHint;
	}
	
	public static Item getItemBlockHint() {
		return Main.itemBlockHint;
	}
}