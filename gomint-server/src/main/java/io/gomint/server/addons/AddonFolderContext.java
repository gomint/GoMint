package io.gomint.server.addons;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * A context for addons laid out in a regular folder-structure.
 *
 * @author Ciel DeVille
 * @version 1.0
 * @stability experimental
 */
class AddonFolderContext extends AddonContext {

    private final File addonFolder;

    AddonFolderContext(File addonFolder) {
        this.addonFolder = addonFolder;
    }

    @Override
    public InputStream openEntry(String path) throws IOException {
        File entryFile = new File(this.addonFolder.getCanonicalPath() + File.separator + path.replaceAll("/", Matcher.quoteReplacement(File.separator)));
        if (entryFile.exists() && entryFile.isFile()) {
            return new FileInputStream(entryFile);
        }
        throw new IOException("Entry not found");
    }

    @Override
    public Stream<? extends String> entries() throws IOException {
        final Path addonPath = Paths.get(this.addonFolder.getAbsolutePath());
        return Files.walk(addonPath)
                .filter(Files::isRegularFile)
                .map(addonPath::relativize)
                .map(Path::toString)
                .map(entry -> entry.replaceAll(Pattern.quote(File.separator), "/"));
    }

    @Override
    public void close() throws IOException {
        // NOP
    }
}
