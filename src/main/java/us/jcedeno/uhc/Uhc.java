package us.jcedeno.uhc;

import fr.mrmicky.fastinv.FastInvManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.PaperCommandManager;
import org.incendo.cloud.paper.util.sender.PaperSimpleSenderMapper;
import org.incendo.cloud.paper.util.sender.Source;
import org.incendo.cloud.setting.ManagerSetting;

import static org.incendo.cloud.parser.standard.IntegerParser.integerParser;
import static org.incendo.cloud.parser.standard.StringParser.stringParser;

public final class Uhc extends JavaPlugin {

    private PaperCommandManager<Source> commandManager;


    @Override
    public void onEnable() {
        // Plugin startup logic
        FastInvManager.register(this);

        // Set cloud command manager using modern cmd mngr
        commandManager = PaperCommandManager.builder(PaperSimpleSenderMapper.simpleSenderMapper())
                .executionCoordinator(ExecutionCoordinator.asyncCoordinator())
                .buildOnEnable(this);


        commandManager.settings().set(ManagerSetting.ALLOW_UNSAFE_REGISTRATION, true);

        commandManager.command(
                commandManager.commandBuilder("new_command")
                        .required("name", stringParser())
                        .handler(ctx -> {
                            final String name = ctx.get("name");
                            commandManager.command(
                                    commandManager.commandBuilder(name).handler(ctx1 -> {
                                        ctx1.sender().source().sendMessage("HI");
                                    })
                            );
                        })
        );

        commandManager.command(
                commandManager.commandBuilder("command_test")
                        .required("amount", integerParser(0, 10))
                        .required("times", integerParser(10, 20))
                        .handler(ctx->{
                            final Integer amount = ctx.get("amount");
                            final Integer times = ctx.get("times");
                            ctx.sender().source().sendMessage(MiniMessage.miniMessage().deserialize(String.format("<rainbow>Hello %s, your amount <white>%s</white> times <yellow>%s</yellow> is equals to <red>%s</red>!!!</rainbow>", ctx.sender().source().getName(), amount, times, amount * times)));
                        })
        );





    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
