package io.gomint.helper;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author geNAZt
 */
public class ItemCreateCheck {

    private static final String TEMPLATE = "package io.gomint.inventory.item;\n" +
        "\n" +
        "import io.gomint.GoMint;\n" +
        "\n" +
        "/**\n" +
        " * @author geNAZt\n" +
        " * @version 1.0\n" +
        " */\n" +
        "public interface %ITEM_NAME% extends ItemStack {\n" +
        "\n" +
        "    /**\n" +
        "     * Create a new item stack with given class and amount\n" +
        "     *\n" +
        "     * @param amount which is used for the creation\n" +
        "     */\n" +
        "    static %ITEM_NAME% create( int amount ) {\n" +
        "        return GoMint.instance().createItemStack( %ITEM_NAME%.class, amount );\n" +
        "    }\n" +
        "\n" +
        "}\n";

    public static void main( String[] args ) {
        File itemFolder = new File( "gomint-server/src/main/java/io/gomint/server/inventory/item/" );
        for ( File file : itemFolder.listFiles( new FilenameFilter() {
            @Override
            public boolean accept( File dir, String name ) {
                return name.endsWith( ".java" );
            }
        } ) )
            try {
                if ( !file.getAbsolutePath().contains( "Block.java" ) ) {
                    String content = new String( Files.readAllBytes( file.toPath() ) );
                    if ( !content.contains( "implements " ) ) {
                        String name = file.getName().replace( ".java", "" );
                        System.out.println( TEMPLATE.replaceAll( "%ITEM_NAME%", name ) );
                    }
                }
            } catch ( IOException e ) {
                e.printStackTrace();
            }
    }

}
