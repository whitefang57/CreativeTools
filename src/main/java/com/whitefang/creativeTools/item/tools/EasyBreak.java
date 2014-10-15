package com.whitefang.creativetools.item.tools;

import com.google.common.collect.Sets;
import com.whitefang.creativetools.CreativeTools;
import com.whitefang.creativetools.handler.ConfigurationHandler;
import com.whitefang.creativetools.item.ItemCreativeTool;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;
import java.util.Set;

public class EasyBreak extends ItemCreativeTool {
	private static final Set effectiveBlocks = Sets.newHashSet();
	private int xRange = ConfigurationHandler.xRange;
	private int yRange = ConfigurationHandler.yRange;
	private int zRange = ConfigurationHandler.zRange;

	public EasyBreak() {
		super(effectiveBlocks);
		this.setUnlocalizedName("easyBreak");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack equipped, World world, EntityPlayer player) {
		if (world.isRemote) {
			MovingObjectPosition mop = raytraceFromEntity(world, player, false, 4.5D);
			if (mop == null)
				return equipped;

			if (!player.isSneaking()) {
				switch (mop.sideHit) {
					case 0:
						changeRange(ForgeDirection.UP);
						break;
					case 1:
						changeRange(ForgeDirection.UP);
						break;
					case 2:
						changeRange(ForgeDirection.WEST);
						break;
					case 3:
						changeRange(ForgeDirection.WEST);
						break;
					case 4:
						changeRange(ForgeDirection.NORTH);
						break;
					case 5:
						changeRange(ForgeDirection.NORTH);
						break;
				}
			}

			if (player.isSneaking()) {
				switch (mop.sideHit) {
					case 0:
						changeRange(ForgeDirection.DOWN);
						break;
					case 1:
						changeRange(ForgeDirection.DOWN);
						break;
					case 2:
						changeRange(ForgeDirection.EAST);
						break;
					case 3:
						changeRange(ForgeDirection.EAST);
						break;
					case 4:
						changeRange(ForgeDirection.SOUTH);
						break;
					case 5:
						changeRange(ForgeDirection.SOUTH);
						break;
				}
			}
			player.addChatMessage(new ChatComponentText("xRange:" + xRange));
			player.addChatMessage(new ChatComponentText("yRange:" + yRange));
			player.addChatMessage(new ChatComponentText("zRange:" + zRange));
		}
		return equipped;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		list.add("Right-Click on a block to increase range");
		list.add("Shift + Right-Click on a block to decrease range");
		list.add("xRange:" + xRange);
		list.add("yRange:" + yRange);
		list.add("zRange:" + zRange);
	}

	@Override
	public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
		World world = player.worldObj;
		final Block block = world.getBlock(x, y, z);
		final int meta = world.getBlockMetadata(x, y, z);

		if (block == null)
			return super.onBlockStartBreak(stack, x, y, z, player);

		MovingObjectPosition mop = raytraceFromEntity(world, player, false, 4.5D);
		if (mop == null)
			return super.onBlockStartBreak(stack, x, y, z, player);

		int xRange = this.xRange;
		int yRange = this.yRange;
		int zRange = this.zRange;

		switch (mop.sideHit) {
			case 0:
			case 1:
				yRange = 0;
				break;
			case 2:
			case 3:
				zRange = 0;
				break;
			case 4:
			case 5:
				xRange = 0;
				break;
		}

		for (int xPos = x - xRange; xPos <= x + xRange; xPos++) {
			for (int yPos = y - yRange; yPos <= y + yRange; yPos++) {
				for (int zPos = z - zRange; zPos <= z + zRange; zPos++) {
					world.setBlockToAir(xPos, yPos, zPos);
					world.func_147479_m(x, y, z);
				}
			}
		}

		if (!world.isRemote)
			world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + (meta << 12));
		return true;
	}

	private static MovingObjectPosition raytraceFromEntity(World world, Entity player, boolean par3, double range) {
		float f = 1.0F;
		float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
		float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
		double d0 = player.prevPosX + (player.posX - player.prevPosX) * (double) f;
		double d1 = player.prevPosY + (player.posY - player.prevPosY) * (double) f;
		if (!world.isRemote && player instanceof EntityPlayer)
			d1 += 1.62D;
		double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double) f;
		Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
		float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
		float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
		float f5 = -MathHelper.cos(-f1 * 0.017453292F);
		float f6 = MathHelper.sin(-f1 * 0.017453292F);
		float f7 = f4 * f5;
		float f8 = f3 * f5;
		double d3 = range;
		if (player instanceof EntityPlayerMP) {
			d3 = ((EntityPlayerMP) player).theItemInWorldManager.getBlockReachDistance();
		}
		Vec3 vec31 = vec3.addVector((double) f7 * d3, (double) f6 * d3, (double) f8 * d3);
		return world.func_147447_a(vec3, vec31, par3, !par3, par3);
	}

	private void changeRange(ForgeDirection orientation) {
		switch (orientation) {
			case EAST:
				if (xRange > 0)
					xRange--;
				break;
			case WEST:
				if (xRange < 4)
					xRange++;
				break;

			case SOUTH:
				if (zRange > 0)
					zRange--;
				break;
			case NORTH:
				if (zRange < 4)
					zRange++;
				break;

			case DOWN:
				if (yRange > 0)
					yRange--;
				break;
			case UP:
				if (yRange < 4)
					yRange++;
				break;

			default:
				return;
		}
	}

	@Override
	public float func_150893_a(ItemStack p_150893_1_, Block p_150893_2_) {
		return CreativeTools.CREATIVE_MATERIAL.getEfficiencyOnProperMaterial();
	}
}
