package org.screenwork.screenworksv1.UIEx;

import imgui.ImGui;
import imgui.app.Application;
import imgui.type.ImDouble;
import imgui.type.ImFloat;
import imgui.type.ImInt;
import imgui.type.ImString;
import net.minestom.server.snapshot.EntitySnapshot;
import net.minestom.server.snapshot.InstanceSnapshot;
import net.minestom.server.snapshot.ServerSnapshot;
import net.minestom.server.world.DimensionType;

/* TODO: Input reading and relay
 * This class needs to be able to take input data and relay changes back to the server.
 */
final class ServerUI extends Application {

    volatile ServerSnapshot snapshotReference;

    @Override
    public void process() {
        var snapshot = this.snapshotReference;
        if (snapshot == null) return;

        ImGui.begin("Instances!");
        snapshot.instances().forEach(instance -> {
            DimensionType dimension = instance.dimensionType();
            if (ImGui.treeNode(dimension.toString())) {
                instance(instance, snapshot);
                ImGui.treePop();
            }
        });
        ImGui.end();
    }

    private void instance(InstanceSnapshot instance, ServerSnapshot snapshot) {
        // World age
        ImGui.inputInt("World age", new ImInt(Math.toIntExact(instance.worldAge())));

        // Dimension type
        dimensionType(instance.dimensionType());

        // Entities
        instance.entities().forEach(entity -> entity(entity, snapshot));
    }

    private void entity(EntitySnapshot entity, ServerSnapshot snapshot) {
        if (ImGui.treeNode(entity.id() + " " + entity.type().name())) {

            // Position
            if (ImGui.treeNode("Position")) {
                ImGui.inputDouble("x", new ImDouble(entity.position().x()));
                ImGui.inputDouble("y", new ImDouble(entity.position().y()));
                ImGui.inputDouble("z", new ImDouble(entity.position().z()));
                ImGui.inputDouble("pitch", new ImDouble(entity.position().pitch()));
                ImGui.inputDouble("yaw", new ImDouble(entity.position().yaw()));
                ImGui.treePop();
            }

            // Instance
            if (ImGui.treeNode("Instance")) {
                instance(entity.instance(), snapshot);
                ImGui.treePop();
            }

            // Passengers
            if (entity.passengers().size() > 0 && ImGui.treeNode("Passengers")) {
                entity.passengers().forEach(passenger -> entity(passenger, snapshot));
            }

            ImGui.treePop();
        }
    }

    private void dimensionType(DimensionType dimension) {
        if (ImGui.treeNode("Dimension type")) {
            ImGui.checkbox("ultrawarm", dimension.isUltrawarm());
            ImGui.checkbox("natural", dimension.isNatural());
            ImGui.checkbox("piglin safe", dimension.isPiglinSafe());
            ImGui.checkbox("respawn anchor safe", dimension.isRespawnAnchorSafe());
            ImGui.checkbox("bed safe", dimension.isBedSafe());
            ImGui.checkbox("raid capable", dimension.isRaidCapable());
            ImGui.checkbox("skylight", dimension.isSkylightEnabled());
            ImGui.checkbox("ceiling", dimension.isCeilingEnabled());
            if (ImGui.checkbox("fixed time", dimension.getFixedTime() != null)) {
                Long boxedFixedTime = dimension.getFixedTime();
                // TODO: Properly support longs
                ImInt fixedTime = new ImInt(Math.toIntExact(boxedFixedTime == null ? 0 : boxedFixedTime));
                ImGui.inputInt("fixed time", fixedTime);
            }
            ImGui.inputFloat("ambient light", new ImFloat(dimension.getAmbientLight()));
            ImGui.inputInt("height", new ImInt(dimension.getHeight()));
            ImGui.inputInt("min y", new ImInt(dimension.getMinY()));
            ImGui.inputInt("logical height", new ImInt(dimension.getLogicalHeight()));
            ImGui.inputText("infiniburn", new ImString(dimension.getInfiniburn().toString()));
            ImGui.treePop();
        }
    }
}

