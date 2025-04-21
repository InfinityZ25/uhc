package us.jcedeno.uhc;

import fr.mrmicky.fastinv.FastInvManager;
import lombok.extern.log4j.Log4j2;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.PaperCommandManager;
import org.incendo.cloud.paper.util.sender.PaperSimpleSenderMapper;
import org.incendo.cloud.paper.util.sender.Source;

import static org.incendo.cloud.parser.standard.IntegerParser.integerParser;


@Log4j2
public final class Uhc extends JavaPlugin {

    private PaperCommandManager<Source> commandManager;
    private AnnotationParser<PaperCommandManager<Source>> annotationParser;


    @Override
    @SuppressWarnings("all")
    public void onEnable() {
        // Register fastInv
        FastInvManager.register(this);

        // Set cloud command manager using modern cmd mngr
        commandManager = PaperCommandManager.builder(PaperSimpleSenderMapper.simpleSenderMapper())
                .executionCoordinator(ExecutionCoordinator.asyncCoordinator())
                .buildOnEnable(this);

        // Instantiate the annotation parser
        annotationParser = new AnnotationParser(commandManager, Source.class);

        try {
            annotationParser.parseContainers();
        } catch (Exception e) {
            log.info(e.getMessage());
        }


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
