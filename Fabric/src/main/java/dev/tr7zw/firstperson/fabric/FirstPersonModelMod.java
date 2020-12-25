package dev.tr7zw.firstperson.fabric;

import javax.annotation.Nullable;

import dev.tr7zw.firstperson.FirstPersonModelCore;
import dev.tr7zw.velvet.api.Velvet;
import dev.tr7zw.velvet.fabric.VelvetImpl;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public class FirstPersonModelMod extends FirstPersonModelCore implements ModInitializer {
	
	@Nullable
	public static MatrixStack hideHeadWithMatrixStack = null;
	@Nullable
	public static MatrixStack paperDollStack = null; //Make force compatibility not hide paper doll

	public FirstPersonModelMod() {
		instance = this;
	}

	public static boolean fixBodyShadow(MatrixStack matrixStack){
		return (enabled && (config.firstPerson.forceActive || hideHeadWithMatrixStack == matrixStack));
	}


	public boolean isFixActive(Object player, Object matrices){
		return MinecraftClient.getInstance() != null && MinecraftClient.getInstance().getCameraEntity() == player && (matrices == hideHeadWithMatrixStack || config.firstPerson.forceActive && matrices != paperDollStack);
	}
	
	@Override
	public void onInitialize() {
		wrapper = new FabricWrapper(MinecraftClient.getInstance());
		Velvet.velvet = new VelvetImpl();
	    ClientTickEvents.END_CLIENT_TICK.register(e ->
	    {
	    	onTick();
	    });
	    sharedSetup();
	}
	  
}