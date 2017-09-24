package io.gomint.server.util;

import io.gomint.command.CommandPermission;
import io.gomint.player.PlayerPermission;
import io.gomint.server.entity.CommandPermissionMagicNumbers;
import io.gomint.server.player.PlayerPermissionMagicNumbers;
import io.gomint.server.world.GamemodeMagicNumbers;
import io.gomint.server.world.SoundMagicNumbers;
import io.gomint.world.Gamemode;
import io.gomint.world.Sound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class EnumConnectors {

    public static final EnumConnector<Gamemode, GamemodeMagicNumbers> GAMEMODE_CONNECTOR = new EnumConnector<>( Gamemode.class, GamemodeMagicNumbers.class );
    public static final EnumConnector<Sound, SoundMagicNumbers> SOUND_CONNECTOR = new EnumConnector<>( Sound.class, SoundMagicNumbers.class );
    public static final EnumConnector<CommandPermission, CommandPermissionMagicNumbers> COMMANDPERMISSION_CONNECTOR = new EnumConnector<>( CommandPermission.class, CommandPermissionMagicNumbers.class );
    public static final EnumConnector<PlayerPermission, PlayerPermissionMagicNumbers> PLAYERPERMISSION_CONNECTOR = new EnumConnector<>( PlayerPermission.class, PlayerPermissionMagicNumbers.class );

}
