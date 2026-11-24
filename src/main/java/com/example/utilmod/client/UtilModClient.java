package com.example.utilmod.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public final class UtilModClient implements ClientModInitializer {

    private static final String MOD_ID = "utilmod";
        private static KeyMapping guiKey;

            @Override
                public void onInitializeClient() {
                        Features.init();

                                KeyMapping.Category category =
                                                KeyMapping.Category.register(Identifier.fromNamespaceAndPath(MOD_ID, "main"));

                                                        guiKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                                                                        "key.utilmod.gui",
                                                                                        InputConstants.Type.KEYSYM,
                                                                                                        GLFW.GLFW_KEY_RIGHT_SHIFT,
                                                                                                                        category));

                                                                                                                                ClientTickEvents.END_CLIENT_TICK.register(client -> {
                                                                                                                                            Features.tick();
                                                                                                                                                        while (guiKey.consumeClick()) {
                                                                                                                                                                        if (client.screen == null) {
                                                                                                                                                                                            client.setScreen(new ModuleScreen());
                                                                                                                                                                                                            }
                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                });

                                                                                                                                                                                                                                        HudElementRegistry.addLast(
                                                                                                                                                                                                                                                        Identifier.fromNamespaceAndPath(MOD_ID, "overlay"),
                                                                                                                                                                                                                                                                        (graphics, deltaTracker) -> Features.renderHud(graphics, deltaTracker));
                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                            }