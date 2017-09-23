package io.gomint.server;

import com.google.common.reflect.ClassPath;
import io.gomint.server.registry.RegisterInfo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BlockItemGenerator {

    public static void main( String[] args ) {
        try {
            for ( ClassPath.ClassInfo info : ClassPath.from( ClassLoader.getSystemClassLoader() ).getTopLevelClasses( "io.gomint.server.world.block" ) ) {
                Class<?> cl = info.load();
                if ( cl.isAnnotationPresent( RegisterInfo.class ) ) {
                    // API first
                    String apiInterface = "package io.gomint.inventory.item;\n\n" +
                            "import io.gomint.GoMint;\n\n" +
                            "/**\n" +
                            " * @author geNAZt\n" +
                            " * @version 1.0\n" +
                            " */\n" +
                            "public interface Item" + info.getSimpleName() + " extends ItemStack {\n\n" +
                            "    /**\n" +
                            "     * Create a new item stack with given class and amount\n" +
                            "     *\n" +
                            "     * @param amount which is used for the creation\n" +
                            "     */\n" +
                            "    static Item" + info.getSimpleName() + " create( int amount ) {\n" +
                            "        return GoMint.instance().createItemStack( Item" + info.getSimpleName() + ".class, amount );\n" +
                            "    }\n\n" +
                            "}";

                    Files.write( Paths.get( "generated/api/Item" + info.getSimpleName() + ".java" ), apiInterface.getBytes(), StandardOpenOption.CREATE );

                    // Implementation
                    /*int id = cl.getAnnotation( RegisterInfo.class ).id();
                    String implementation = "package io.gomint.server.inventory.item;\n" +
                            "\n" +
                            "import io.gomint.server.registry.RegisterInfo;\n" +
                            "import io.gomint.taglib.NBTTagCompound;\n" +
                            "\n" +
                            "/**\n" +
                            " * @author geNAZt\n" +
                            " * @version 1.0\n" +
                            " \n" +
                            "@RegisterInfo( id = " + id + " )\n " +
                            "public class Item" + info.getSimpleName() + " extends ItemStack implements io.gomint.inventory.item.Item" + info.getSimpleName() +" {\n" +
                            "\n" +
                            "    // CHECKSTYLE:OFF\n" +
                            "    public Item" + info.getSimpleName() + "( short data, int amount ) {\n" +
                            "        super( " + id + ", data, amount );\n" +
                            "    }\n" +
                            "\n" +
                            "    public Item" + info.getSimpleName() + "( short data, int amount, NBTTagCompound nbt ) {\n" +
                            "        super( " + id + ", data, amount, nbt );\n" +
                            "    }\n" +
                            "    // CHECKSTYLE:ON\n" +
                            "\n" +
                            "}\n";

                    Files.write( Paths.get( "generated/impl/Item" + info.getSimpleName() + ".java" ), implementation.getBytes(), StandardOpenOption.CREATE );*/
                    System.out.println( info.getName() );
                }
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

}
