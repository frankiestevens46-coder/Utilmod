package com.example.utilmod.client;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

final class Setting {
    final String name;
        final boolean isToggle;
            boolean on;
                double value, min, max, step;

                    private Setting(String name, boolean isToggle) {
                            this.name = name;
                                    this.isToggle = isToggle;
                                        }

                                            static Setting toggle(String name, boolean def) {
                                                    Setting s = new Setting(name, true);
                                                            s.on = def;
                                                                    return s;
                                                                        }

                                                                            static Setting slider(String name, double def, double min, double max, double step) {
                                                                                    Setting s = new Setting(name, false);
                                                                                            s.value = def;
                                                                                                    s.min = min;
                                                                                                            s.max = max;
                                                                                                                    s.step = step;
                                                                                                                            return s;
                                                                                                                                }

                                                                                                                                    void flip() {
                                                                                                                                            on = !on;
                                                                                                                                                }

                                                                                                                                                    void adjust(int dir) {
                                                                                                                                                            double v = Math.round((value + dir * step) / step) * step;
                                                                                                                                                                    value = Math.max(min, Math.min(max, v));
                                                                                                                                                                        }

                                                                                                                                                                            String display() {
                                                                                                                                                                                    return isToggle ? (on ? "ON" : "OFF") : String.format(Locale.ROOT, "%.0f", value);
                                                                                                                                                                                        }
                                                                                                                                                                                        }

                                                                                                                                                                                        abstract class Feature {
                                                                                                                                                                                            final String name;
                                                                                                                                                                                                final String category;
                                                                                                                                                                                                    final Minecraft mc = Minecraft.getInstance();
                                                                                                                                                                                                        final List<Setting> settings = new ArrayList<>();
                                                                                                                                                                                                            boolean enabled;

                                                                                                                                                                                                                Feature(String name, String category) {
                                                                                                                                                                                                                        this.name = name;
                                                                                                                                                                                                                                this.category = category;
                                                                                                                                                                                                                                    }

                                                                                                                                                                                                                                        Setting addToggle(String n, boolean def) {
                                                                                                                                                                                                                                                Setting s = Setting.toggle(n, def);
                                                                                                                                                                                                                                                        settings.add(s);
                                                                                                                                                                                                                                                                return s;
                                                                                                                                                                                                                                                                    }

                                                                                                                                                                                                                                                                        Setting addSlider(String n, double def, double min, double max, double step) {
                                                                                                                                                                                                                                                                                Setting s = Setting.slider(n, def, min, max, step);
                                                                                                                                                                                                                                                                                        settings.add(s);
                                                                                                                                                                                                                                                                                                return s;
                                                                                                                                                                                                                                                                                                    }

                                                                                                                                                                                                                                                                                                        void tick() {}

                                                                                                                                                                                                                                                                                                            void hud(GuiGraphics g) {}
                                                                                                                                                                                                                                                                                                            }

                                                                                                                                                                                                                                                                                                            final class Features {
                                                                                                                                                                                                                                                                                                                static final List<Feature> ALL = new ArrayList<>();

                                                                                                                                                                                                                                                                                                                    static void init() {
                                                                                                                                                                                                                                                                                                                            ALL.add(new Fps());
                                                                                                                                                                                                                                                                                                                                    ALL.add(new Coordinates());
                                                                                                                                                                                                                                                                                                                                            ALL.add(new AttackCooldown());
                                                                                                                                                                                                                                                                                                                                                    ALL.add(new AutoSprint());
                                                                                                                                                                                                                                                                                                                                                            ALL.add(new Clock());
                                                                                                                                                                                                                                                                                                                                                                }

                                                                                                                                                                                                                                                                                                                                                                    static void tick() {
                                                                                                                                                                                                                                                                                                                                                                            for (Feature f : ALL) {
                                                                                                                                                                                                                                                                                                                                                                                        if (f.enabled) f.tick();
                                                                                                                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                                                                                                                    }

                                                                                                                                                                                                                                                                                                                                                                                                        static void renderHud(GuiGraphics g, DeltaTracker d) {
                                                                                                                                                                                                                                                                                                                                                                                                                for (Feature f : ALL) {
                                                                                                                                                                                                                                                                                                                                                                                                                            if (f.enabled) f.hud(g);
                                                                                                                                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                                                                                                                                                        }