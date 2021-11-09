package space.impact.api.entity.fx;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class WeightlessParticleFX extends EntityFX {
	public WeightlessParticleFX(World w, double x, double y, double z, double xx, double yy, double zz) {
		super(w, x, y, z, xx, yy, zz);
		this.motionX        = xx + (double) ((float) (Math.random() * 2.0D - 1.0D) * 0.05F);
		this.motionY        = yy + (double) ((float) (Math.random() * 2.0D - 1.0D) * 0.05F);
		this.motionZ        = zz + (double) ((float) (Math.random() * 2.0D - 1.0D) * 0.05F);
		this.particleRed    = this.particleGreen = this.particleBlue = this.rand.nextFloat() * 0.3F + 0.7F;
		this.particleScale  = this.rand.nextFloat() * this.rand.nextFloat() * 6.0F + 1.0F;
		this.particleMaxAge = (int) (16.0D / ((double) this.rand.nextFloat() * 0.8D + 0.2D)) + 2;
		this.noClip         = true;
	}
	
	/**
	 * Вызывается для обновления позиции/логики FX.
	 */
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		if (this.particleAge++ >= this.particleMaxAge) {
			this.setDead();
		}
		this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.8999999761581421D;
		this.motionY *= 0.8999999761581421D;
		this.motionZ *= 0.8999999761581421D;
	}
}