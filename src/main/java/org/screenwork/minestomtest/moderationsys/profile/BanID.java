package org.screenwork.minestomtest.moderationsys.profile;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class BanID {

    UUID player = null;
    UUID moderator = null;

    String reason = "The ban hammer has spoken.";
    String duration = null;
    String timeIssued = null;

}
