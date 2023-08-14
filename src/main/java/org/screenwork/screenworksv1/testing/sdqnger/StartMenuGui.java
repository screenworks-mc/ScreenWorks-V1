package org.screenwork.screenworksv1.testing.sdqnger;

import net.minestom.server.map.MapColors;
import nl.kiipdevelopment.minescreen.screen.InteractableScreenGui;
import nl.kiipdevelopment.minescreen.screen.ScreenGui;
import nl.kiipdevelopment.minescreen.widget.Widget;
import org.jetbrains.skija.*;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("UnstableApiUsage")
public class StartMenuGui {

    private StartMenuGui() {}

    public static ScreenGui init() {

        final InteractableScreenGui gui = new InteractableScreenGui((short) 1, 2048, 1024, 30);

        // Set background color
        gui.updateBackground(MapColors.COLOR_BLACK);

        // Triangle help
        final Random random = ThreadLocalRandom.current();
        final Point[] triangle = new Point[] {
                new Point(random.nextInt(128), random.nextInt(128)),
                new Point(random.nextInt(128), random.nextInt(128)),
                new Point(random.nextInt(128), random.nextInt(128))
        };

        // Create container
        Widget containerWidget = Widget.Containers.column(
                448,
                448,
                Widget.Containers.row(
                        448,
                        214,
                        Widget.Containers.grid(
                                10,
                                128,
                                128,
                                colorButton(gui, MapColors.COLOR_RED),
                                colorButton(gui, MapColors.COLOR_ORANGE),
                                colorButton(gui, MapColors.COLOR_YELLOW),
                                colorButton(gui, MapColors.COLOR_GREEN),
                                colorButton(gui, MapColors.COLOR_BLUE),
                                colorButton(gui, MapColors.COLOR_PINK),
                                colorButton(gui, MapColors.SNOW),
                                colorButton(gui, MapColors.COLOR_BLACK)
                        ),
                        Widget.Spacers.spacer(10, 10),
                        Widget.Wrappers.wrapper(() -> {
                            Widget[] widget = new Widget[10];
                            for (int i = 0; i < 10; i++) {
                                widget[i] = Widget.Spacers.offset(
                                        random.nextInt(64),
                                        random.nextInt(64),
                                        Widget.Shapes.rectangle(
                                                random.nextInt(64),
                                                random.nextInt(64),
                                                MapColors.values()[random.nextInt(MapColors.values().length)]
                                        )
                                );
                            }

                            return Widget.Containers.stack(
                                    128,
                                    128,
                                    widget
                            );
                        })
                ),
                Widget.Containers.row(
                        448,
                        214,
                        Widget.Wrappers.wrapper(() -> gui.players().isEmpty()
                                ? null
                                : Widget.Players.avatar(128, 128, gui.players().stream().findFirst().get().getUuid())
                        ),
                        Widget.Spacers.spacer(10, 10),
                        Widget.Renders.render(128, 128, graphics -> {
                            Surface surface = Surface.makeRasterN32Premul(128, 128);
                            Canvas canvas = surface.getCanvas();

                            // Draw to canvas
                            Paint paint = new Paint()
                                    .setColor(0xFF1D7AA2)
                                    .setMode(PaintMode.STROKE)
                                    .setStrokeWidth(1f);
                            for (int i = 0; i < triangle.length; i++) {
                                Point point = triangle[i];
                                point = point.offset(random.nextInt(-1, 2), random.nextInt(-1, 2));
                                if (point.getX() < 0) point = new Point(0, point.getY());
                                if (point.getX() > 128) point = new Point(128, point.getY());
                                if (point.getY() < 0) point = new Point(point.getX(), 0);
                                if (point.getY() > 128) point = new Point(point.getX(), 128);

                                triangle[i] = point;
                            }
                            canvas.drawTriangles(
                                    triangle,
                                    new int[] { 0xFFFF0000, 0xFF00FF00, 0xFF0000FF },
                                    paint
                            );

                            // Render
                            surface.flush();
                            Image image = surface.makeImageSnapshot();
                            Bitmap bitmap = new Bitmap();
                            bitmap.allocN32Pixels(128, 128);
                            image.readPixels(bitmap);

                            for (int x = 0; x < 128; x++) {
                                for (int y = 0; y < 128; y++) {
                                    graphics.drawDirectDot(MapColors.closestColor(bitmap.getColor(x, y)).getIndex(), x, y);
                                }
                            }
                        })
                )
        );

        // Display mouse
        final int cursorWidth = 4;
        var mouseWidget = Widget.Buttons.hover(
                Widget.Spacers.spacer(gui.map().width(), gui.map().height()),
                (player1, x, y) -> {
                    for (InteractableScreenGui.Mouse mouse : gui.mice()) {
                        var graphics = gui.mapGraphics();

                        graphics.drawRectangle(MapColors.SNOW,
                                Math.max(mouse.x() - cursorWidth, 0),
                                Math.max(mouse.y(), 0),
                                Math.min(mouse.x() + cursorWidth + 1, gui.map().width() - 1),
                                Math.min(mouse.y() + 1, gui.map().height() - 1)
                        );
                        graphics.drawRectangle(MapColors.SNOW,
                                Math.max(mouse.x(), 0),
                                Math.max(mouse.y() - cursorWidth, 0),
                                Math.min(mouse.x() + 1, gui.map().width() - 1),
                                Math.min(mouse.y() + cursorWidth + 1, gui.map().height() - 1)
                        );
                    }
                }
        );

        // Add the components
        gui.addWidget(containerWidget, 64, 64);
        gui.addWidget(mouseWidget);

        return gui;
    }

    private static Widget colorButton(ScreenGui gui, MapColors color) {
        return Widget.Buttons.click(
                Widget.Containers.stack(
                        36,
                        36,
                        Widget.Shapes.square(36, MapColors.STONE),
                        Widget.Spacers.offset(1, 1, Widget.Shapes.square(34, color))
                ),
                (player, x, y) -> gui.updateBackground(color)
        );
    }
}
