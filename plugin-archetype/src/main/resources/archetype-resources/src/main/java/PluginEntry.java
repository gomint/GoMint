package ${package};

import io.gomint.plugin.Plugin;
import io.gomint.plugin.PluginName;
import io.gomint.plugin.Startup;
import io.gomint.plugin.StartupPriority;
import io.gomint.plugin.Version;

@PluginName("${artifactId}")
@Version(major = 1, minor = 0)
@Startup(StartupPriority.STARTUP)
public class PluginEntry extends Plugin {

    @Override
    public void onInstall() {

    }
    
}
