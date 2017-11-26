package io.gomint.server.util;

import javassist.*;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author geNAZt
 * @version 1.0
 */
public class EnumConnector<E1 extends Enum<E1>, E2 extends Enum<E2>> {

    private static final AtomicLong ID_COUNTER = new AtomicLong( 0 );

    private EnumConverter converter;
    private EnumConverter reverter;

    public EnumConnector( Class<E1> enumOne, Class<E2> enumTwo ) {
        // Get enum constants of both
        E1[] enumOneConstants = enumOne.getEnumConstants();
        E2[] enumTwoConstants = enumTwo.getEnumConstants();

        // Generate converter
        this.converter = generateConverter( enumOneConstants, enumTwo );
        this.reverter = generateConverter( enumTwoConstants, enumOne );
    }

    private EnumConverter generateConverter( Enum[] enumOneConstants, Class<?> otherClass ) {
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.makeClass( "io.gomint.server.util.EnumConverterFrom" + ID_COUNTER.incrementAndGet() );

        // Add converter interface
        try {
            ctClass.addInterface( classPool.get( "io.gomint.server.util.EnumConverter" ) );
        } catch ( NotFoundException e ) {
            e.printStackTrace();
        }

        // Generate convert method
        StringBuilder methodBody = new StringBuilder( "public Enum convert( Enum value ) {\n" +
            "    int id = value.ordinal();\n" +
            "    switch ( id ) {\n" );

        for ( Enum constant : enumOneConstants ) {
            methodBody.append( "        case " ).append( constant.ordinal() ).append( ":" );
            methodBody.append( "             return " ).append( otherClass.getName() ).append( "." ).append( constant.name() ).append( ";\n" );
        }

        methodBody.append( "    }\n\n    return null;\n}" );

        try {
            ctClass.addMethod( CtMethod.make( methodBody.toString(), ctClass ) );
        } catch ( CannotCompileException e ) {
            e.printStackTrace();
        }

        try {
            return (EnumConverter) ctClass.toClass().newInstance();
        } catch ( InstantiationException | IllegalAccessException | CannotCompileException e ) {
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
