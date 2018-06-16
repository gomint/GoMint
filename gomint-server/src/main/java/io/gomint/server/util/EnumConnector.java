package io.gomint.server.util;

/**
 * @author geNAZt
 * @version 1.0
 */
public class EnumConnector<E1 extends Enum<E1>, E2 extends Enum<E2>> {

    private EnumConverter converter;
    private EnumConverter reverter;

    public EnumConnector( Class<E1> enumOne, Class<E2> enumTwo ) {
        // Generate converter
        this.converter = generateConverter( enumTwo );
        this.reverter = generateConverter( enumOne );
    }

    private EnumConverter generateConverter(  Class<?> otherClass ) {
        String converterClass = "EnumConverterFrom" + otherClass.getSimpleName();

        try {
            return (EnumConverter) EnumConverter.class.getClassLoader().loadClass( "io.gomint.server.util." + converterClass ).newInstance();
        } catch ( InstantiationException | IllegalAccessException | ClassNotFoundException e ) {
            e.printStackTrace();
        }

        return null;
    }

    public E2 convert( E1 e1 ) {
        // Null safety
        if ( e1 == null ) return null;
        return (E2) this.converter.convert( e1 );
    }

    public E1 revert( E2 e2 ) {
        // Null safety
        if ( e2 == null ) return null;
        return (E1) this.reverter.convert( e2 );
    }

}
