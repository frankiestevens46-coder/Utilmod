package com.example.utilmod.client;

import net.minecraft.client.gui.GuiGraphics;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

final class Fps extends Feature {
    final Setting shadow = addToggle("Text Shadow", true);

        Fps() {
                super("FPS Counter", "Render");
                    }

                        @Override
                            void hud(GuiGraphics g) {
                                    g.drawString(mc.font, "FPS: " + mc.getFps(), 4, 4, 0xFFFFFFFF, shadow.on);
                                        }
                                        }

                                        final class Coordinates extends Feature {
                                            final Setting shadow = addToggle("Text Shadow", true);

                                                Coordinates() {
                                                        super("Coordinates", "Render");
                                                            }

                                                                @Override
                                                                    void hud(GuiGraphics g) {
                                                                            if (mc.player == null) return;
                                                                                    String t = String.format(Locale.ROOT, "XYZ: %.1f / %.1f / %.1f",
                                                                                                    mc.player.getX(), mc.player.getY(), mc.player.getZ());
                                                                                                            g.drawString(mc.font, t, 4, 16, 0xFFFFFFFF, shadow.on);
                                                                                                                }
                                                                                                                }

                                                                                                                final class AttackCooldown extends Feature {
                                                                                                                    final Setting yOffset = addSlider("Y Offset", 14, 0, 60, 1);

                                                                                                                        AttackCooldown() {
                                                                                                                                super("Attack Cooldown", "Combat");
                                                                                                                                    }

                                                                                                                                        @Override
                                                                                                                                            void hud(GuiGraphics g) {
                                                                                                                                                    if (mc.player == null) return;
                                                                                                                                                            float charge = Math.min(1.0F, mc.player.getAttackStrengthScale(0.0F));
                                                                                                                                                                    int screenW = mc.getWindow().getGuiScaledWidth();
                                                                                                                                                                            int screenH = mc.getWindow().getGuiScaledHeight();
                                                                                                                                                                                    int barW = 40;
                                                                                                                                                                                            int x = screenW / 2 - barW / 2;
                                                                                                                                                                                                    int y = screenH / 2 + (int) yOffset.value;
                                                                                                                                                                                                            g.fill(x - 1, y - 1, x + barW + 1, y + 4, 0xA0000000);
                                                                                                                                                                                                                    g.fill(x, y, x + (int) (barW * charge), y + 3,
                                                                                                                                                                                                                                    charge >= 1.0F ? 0xFF5CE07A : 0xFFE0B05C);
                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                        }

                                                                                                                                                                                                                                        final class AutoSprint extends Feature {
                                                                                                                                                                                                                                            final Setting minFood = addSlider("Min Food Level", 6, 0, 20, 1);

                                                                                                                                                                                                                                                AutoSprint() {
                                                                                                                                                                                                                                                        super("Auto Sprint", "Movement");
                                                                                                                                                                                                                                                            }

                                                                                                                                                                                                                                                                @Override
                                                                                                                                                                                                                                                                    void tick() {
                                                                                                                                                                                                                                                                            var p = mc.player;
                                                                                                                                                                                                                                                                                    if (p == null) return;
                                                                                                                                                                                                                                                                                            boolean can = mc.options.keyUp.isDown()
                                                                                                                                                                                                                                                                                                            && !p.isShiftKeyDown()
                                                                                                                                                                                                                                                                                                                            && !p.isUsingItem()
                                                                                                                                                                                                                                                                                                                                            && p.getFoodData().getFoodLevel() > minFood.value;
                                                                                                                                                                                                                                                                                                                                                    if (can) {
                                                                                                                                                                                                                                                                                                                                                                mc.options.keySprint.setDown(true);
                                                                                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                                                                                            }

                                                                                                                                                                                                                                                                                                                                                                            final class Clock extends Feature {
                                                                                                                                                                                                                                                                                                                                                                                static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
                                                                                                                                                                                                                                                                                                                                                                                    final Setting shadow = addToggle("Text Shadow", true);

                                                                                                                                                                                                                                                                                                                                                                                        Clock() {
                                                                                                                                                                                                                                                                                                                                                                                                super("Clock", "Misc");
                                                                                                                                                                                                                                                                                                                                                                                                    }

                                                                                                                                                                                                                                                                                                                                                                                                        @Override
                                                                                                                                                                                                                                                                                                                                                                                                            void hud(GuiGraphics g) {
                                                                                                                                                                                                                                                                                                                                                                                                                    String t = LocalTime.now().format(FORMAT);
                                                                                                                                                                                                                                                                                                                                                                                                                            int x = mc.getWindow().getGuiScaledWidth() - mc.font.width(t) - 4;
                                                                                                                                                                                                                                                                                                                                                                                                                                    g.drawString(mc.font, t, x, 4, 0xFFFFFFFF, shadow.on);
                                                                                                                                                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                                                                                                                                                        }