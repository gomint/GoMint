package io.gomint.server.addons;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * A context for addons compressed into a zip file.
 *
 * @author Ciel DeVille
 * @version 1.0
 * @stability experimental
 */
class AddonZipFileContext extends AddonContext {

    private final ZipFile zipFile;

    /**
     * Constructs a new zip file context given the zip file the addon is stored in.
     *
     * @param zipFile The zip file to be wrapped
     */
    AddonZipFileContext(ZipFile zipFile) {
        this.zipFile = zipFile;
    }

    @Override
    public InputStream openEntry(String path) throws IOException {
        ZipEntry entry = this.zipFile.getEntry(path);
        if (entry != null) {
            return this.zipFile.getInputStream(entry);
        }
        throw new IOException("Entry not found");
    }

    @Override
    public Stream<? extends String> entries() {
        Enumeration<? extends ZipEntry> e = this.zipFile.entries();
        Stream<? extends ZipEntry> stream = StreamSupport.stream(
                new Spliterators.AbstractSpliterator<>(Long.MAX_VALUE, Spliterator.ORDERED) {
                    public boolean tryAdvance(Consumer<? super ZipEntry> action) {
                        if (e.hasMoreElements()) {
                            action.accept(e.nextElement());
                            return true;
                        }
                        return false;
                    }
                    public void forEachRemaining(Consumer<? super ZipEntry> action) {
                        while (e.hasMoreElements()) action.accept(e.nextElement());
                    }
                }, false
        );

        return stream.filter(entry -> !entry.isDirectory()).map(ZipEntry::getName);
    }

    @Override
    public void close() throws IOException {
        this.zipFile.close();
    }
}
