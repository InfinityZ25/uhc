package us.jcedeno.uhc.game.commands;


import net.kyori.adventure.text.minimessage.MiniMessage;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.Default;
import org.incendo.cloud.annotations.processing.CommandContainer;
import org.incendo.cloud.paper.util.sender.Source;

@CommandContainer
public class ConfigCommand {


    @Command("config-command literalArgument <number> [string]")
    public void testCommand(Source sender, @Argument("number") Integer number, @Argument("string") @Default("string!") String str) {
        sender.source()
                .sendMessage(MiniMessage.miniMessage().deserialize(
                        "The command you sent has the number argument " + number + " and the string value " + str)
                );

    }



}
