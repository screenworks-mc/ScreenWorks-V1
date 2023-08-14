package org.screenwork.screenworksv1.testing.sdqnger;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.instance.Instance;

@Data
@RequiredArgsConstructor
public class VerificationData {

    public Entity cursor;
    public Pos targetPos = new Pos(0, 0, 0);
    public Instance playerInstance;

}