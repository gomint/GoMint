warning: LF will be replaced by CRLF in gomint-api/src/main/java/io/gomint/world/Gamemode.java.
The file will have its original line endings in your working directory.
warning: LF will be replaced by CRLF in gomint-server/src/main/java/io/gomint/server/GoMintServer.java.
The file will have its original line endings in your working directory.
warning: LF will be replaced by CRLF in gomint-server/src/main/java/io/gomint/server/command/CommandManager.java.
The file will have its original line endings in your working directory.
warning: LF will be replaced by CRLF in gomint-server/src/main/java/io/gomint/server/command/internal/EffectCommand.java.
The file will have its original line endings in your working directory.
warning: LF will be replaced by CRLF in gomint-server/src/main/java/io/gomint/server/command/internal/GamemodeCommand.java.
The file will have its original line endings in your working directory.
warning: LF will be replaced by CRLF in gomint-server/src/main/java/io/gomint/server/command/internal/TitleCommand.java.
The file will have its original line endings in your working directory.
warning: LF will be replaced by CRLF in gomint-server/src/main/java/io/gomint/server/inventory/item/Items.java.
The file will have its original line endings in your working directory.
[1mdiff --git a/gomint-api/src/main/java/io/gomint/world/Gamemode.java b/gomint-api/src/main/java/io/gomint/world/Gamemode.java[m
[1mindex 7a923c20..6e101446 100644[m
[1m--- a/gomint-api/src/main/java/io/gomint/world/Gamemode.java[m
[1m+++ b/gomint-api/src/main/java/io/gomint/world/Gamemode.java[m
[36m@@ -10,24 +10,24 @@[m [mpublic enum Gamemode {[m
      * Survival mode can destroy blocks, attack mobs and other players like normal. They also can't fly or clip through[m
      * blocks[m
      */[m
[31m-    SURVIVAL("Survival"),[m
[32m+[m[32m    SURVIVAL( "Survival" ),[m
 [m
     /**[m
      * Creative mode allows flying and instant breaking blocks[m
      */[m
[31m-    CREATIVE("Creative"),[m
[32m+[m[32m    CREATIVE( "Creative" ),[m
 [m
     /**[m
      * Adventure removes hitboxes so you can't hit mobs / players or interact with certain blocks. They also can't fly[m
      * or clip through blocks[m
      */[m
[31m-    ADVENTURE("Adventure"),[m
[32m+[m[32m    ADVENTURE( "Adventure" ),[m
 [m
     /**[m
      * Spectator removed the ability to break blocks or hit mobs / players. This also allows flying and clipping through[m
      * blocks[m
      */[m
[31m-    SPECTATOR("Spectator");[m
[32m+[m[32m    SPECTATOR( "Spectator" );[m
 [m
     private String name;[m
 [m
[1mdiff --git a/gomint-server/src/main/java/io/gomint/server/GoMintServer.java b/gomint-server/src/main/java/io/gomint/server/GoMintServer.java[m
[1mindex ccf93666..5d946d80 100644[m
[1m--- a/gomint-server/src/main/java/io/gomint/server/GoMintServer.java[m
[1m+++ b/gomint-server/src/main/java/io/gomint/server/GoMintServer.java[m
[36m@@ -7,6 +7,7 @@[m
 [m
 package io.gomint.server;[m
 [m
[32m+[m[32mimport com.google.common.util.concurrent.Futures;[m
 import com.google.common.util.concurrent.Futures;[m
 import com.google.common.util.concurrent.ListenableFuture;[m
 import com.google.common.util.concurrent.ListeningExecutorService;[m
[1mdiff --git a/gomint-server/src/main/java/io/gomint/server/command/CommandManager.java b/gomint-server/src/main/java/io/gomint/server/command/CommandManager.java[m
[1mindex e2afb32f..8b781ffc 100644[m
[1m--- a/gomint-server/src/main/java/io/gomint/server/command/CommandManager.java[m
[1m+++ b/gomint-server/src/main/java/io/gomint/server/command/CommandManager.java[m
[36m@@ -7,6 +7,7 @@[m [mimport io.gomint.server.command.internal.DeopCommand;[m
 import io.gomint.server.command.internal.DifficultyCommand;[m
 import io.gomint.server.command.internal.EffectCommand;[m
 import io.gomint.server.command.internal.GamemodeCommand;[m
[32m+[m[32mimport io.gomint.server.command.internal.GiveCommand;[m
 import io.gomint.server.command.internal.ListCommand;[m
 import io.gomint.server.command.internal.OpCommand;[m
 import io.gomint.server.command.internal.StopCommand;[m
[36m@@ -45,6 +46,7 @@[m [mpublic class CommandManager {[m
                 DifficultyCommand.class,[m
                 EffectCommand.class,[m
                 GamemodeCommand.class,[m
[32m+[m[32m                GiveCommand.class,[m
                 ListCommand.class,[m
                 OpCommand.class,[m
                 StopCommand.class,[m
[1mdiff --git a/gomint-server/src/main/java/io/gomint/server/command/internal/EffectCommand.java b/gomint-server/src/main/java/io/gomint/server/command/internal/EffectCommand.java[m
[1mindex 18e6a69c..2ae62ef1 100644[m
[1m--- a/gomint-server/src/main/java/io/gomint/server/command/internal/EffectCommand.java[m
[1m+++ b/gomint-server/src/main/java/io/gomint/server/command/internal/EffectCommand.java[m
[36m@@ -45,7 +45,7 @@[m [mpublic class EffectCommand extends Command {[m
     @Override[m
     public CommandOutput execute( CommandSender player, String alias, Map<String, Object> arguments ) {[m
         CommandOutput output = new CommandOutput();[m
[31m-        EntityPlayer target = arguments.get( "player" ) == null ? (EntityPlayer) player : (EntityPlayer) arguments.get( "player" );[m
[32m+[m[32m        EntityPlayer target = (EntityPlayer) arguments.get( "player" );[m
 [m
         if( target == null ) {[m
             return output.fail( "No targets matched selector" );[m
[1mdiff --git a/gomint-server/src/main/java/io/gomint/server/command/internal/GamemodeCommand.java b/gomint-server/src/main/java/io/gomint/server/command/internal/GamemodeCommand.java[m
[1mindex 3d69dfb0..75f6ec64 100644[m
[1m--- a/gomint-server/src/main/java/io/gomint/server/command/internal/GamemodeCommand.java[m
[1m+++ b/gomint-server/src/main/java/io/gomint/server/command/internal/GamemodeCommand.java[m
[36m@@ -21,7 +21,6 @@[m [mimport java.util.Map;[m
  * @version 1.0[m
  */[m
 @Name( "gamemode" )[m
[31m-@Alias( "gm" )[m
 @Description( "Sets a player's game mode." )[m
 @Permission( "gomint.command.gamemode" )[m
 @Overload( {[m
[1mdiff --git a/gomint-server/src/main/java/io/gomint/server/command/internal/TitleCommand.java b/gomint-server/src/main/java/io/gomint/server/command/internal/TitleCommand.java[m
[1mindex ca73d874..7219fa07 100644[m
[1m--- a/gomint-server/src/main/java/io/gomint/server/command/internal/TitleCommand.java[m
[1m+++ b/gomint-server/src/main/java/io/gomint/server/command/internal/TitleCommand.java[m
[36m@@ -49,7 +49,7 @@[m [mpublic class TitleCommand extends Command {[m
     @Override[m
     public CommandOutput execute( CommandSender player, String alias, Map<String, Object> arguments ) {[m
         CommandOutput output = new CommandOutput();[m
[31m-        EntityPlayer target = arguments.get( "player" ) == null ? (EntityPlayer) player : (EntityPlayer) arguments.get( "player" );[m
[32m+[m[32m        EntityPlayer target = (EntityPlayer) arguments.get( "player" );[m
 [m
         if( target == null ) {[m
             return output.fail( "No targets matched selector" );[m
[36m@@ -73,6 +73,7 @@[m [mpublic class TitleCommand extends Command {[m
                     target.sendActionBar( (String) arguments.get( "text" ) );[m
                     break;[m
                 case "times":[m
[32m+[m[32m                    // Doesn't work yet[m
                     target.sendTitle( null, null, (int) arguments.get( "fadeIn" ), (int) arguments.get( "stay" ), (int) arguments.get( "fadeOut" ), TimeUnit.SECONDS );[m
                     break;[m
             }[m
