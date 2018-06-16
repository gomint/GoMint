package io.gomint.config;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.representer.Representer;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BaseConfigMapper extends BaseConfig {

    private transient Yaml yaml;
    protected transient ConfigSection root;
    private transient Map<String, ArrayList<String>> comments = new LinkedHashMap<>();
    private transient Representer yamlRepresenter = new Representer();
    private transient String commentPrefix = "";

    protected BaseConfigMapper() {
        DumperOptions yamlOptions = new DumperOptions();
        yamlOptions.setIndent( 2 );
        yamlOptions.setDefaultFlowStyle( DumperOptions.FlowStyle.BLOCK );

        this.yamlRepresenter.setDefaultFlowStyle( DumperOptions.FlowStyle.BLOCK );
        this.yaml = new Yaml( new CustomClassLoaderConstructor( BaseConfigMapper.class.getClassLoader() ), this.yamlRepresenter, yamlOptions );

        /*
        Configure the settings for serializing via the annotations present.
         */
        configureFromSerializeOptionsAnnotation();
    }

    protected void loadFromYaml() throws InvalidConfigurationException {
        this.root = new ConfigSection();

        try ( InputStreamReader fileReader = new InputStreamReader( new FileInputStream( CONFIG_FILE ), Charset.forName( "UTF-8" ) ) ) {
            Object object = this.yaml.load( fileReader );

            if ( object != null ) {
                convertMapsToSections( (Map<?, ?>) object, this.root );
            }
        } catch ( IOException | ClassCastException | YAMLException e ) {
            throw new InvalidConfigurationException( "Could not load YML", e );
        }
    }

    private void convertMapsToSections( Map<?, ?> input, ConfigSection section ) {
        if ( input == null ) {
            return;
        }

        for ( Map.Entry<?, ?> entry : input.entrySet() ) {
            String key = entry.getKey().toString();
            Object value = entry.getValue();

            if ( value instanceof Map ) {
                convertMapsToSections( (Map<?, ?>) value, section.create( key ) );
            } else {
                section.set( key, value, false );
            }
        }
    }

    protected void saveToYaml() throws InvalidConfigurationException {
        try ( OutputStreamWriter fileWriter = new OutputStreamWriter( new FileOutputStream( CONFIG_FILE ), Charset.forName( "UTF-8" ) ) ) {
            if ( CONFIG_HEADER != null ) {
                for ( String line : CONFIG_HEADER ) {
                    fileWriter.write( "# " + line + "\n" );
                }

                fileWriter.write( "\n" );
            }

            Integer depth = 0;
            List<String> keyChain = new ArrayList<>();

            String yamlString = this.yaml.dump( this.root.getValues( true ) );
            StringBuilder writeLines = new StringBuilder();

            for ( String line : yamlString.split( "\n" ) ) {
                if ( line.startsWith( new String( new char[depth] ).replace( "\0", " " ) ) ) {
                    keyChain.add( line.split( ":" )[0].trim() );
                    depth = depth + 2;
                } else {
                    if ( line.startsWith( new String( new char[depth - 2] ).replace( "\0", " " ) ) ) {
                        if ( !line.startsWith( new String( new char[depth - 2] ).replace( "\0", " " ) + "-" ) ) {
                            keyChain.remove( keyChain.size() - 1 );
                        } else {
                            keyChain.add( "-" );
                        }
                    } else {
                        // Check how much spaces are in front of the line
                        int spaces = 0;
                        for ( int i = 0; i < line.length(); i++ ) {
                            if ( line.charAt( i ) == ' ' ) {
                                spaces++;
                            } else {
                                break;
                            }
                        }

                        depth = spaces;

                        if ( spaces == 0 ) {
                            keyChain = new ArrayList<>();
                            depth = 2;
                        } else {
                            List<String> temp = new ArrayList<>();
                            int index = 0;
                            for ( int i = 0; i < spaces; i = i + 2, index++ ) {
                                temp.add( keyChain.get( index ) );
                            }

                            keyChain = temp;

                            depth = depth + 2;
                        }
                    }

                    if ( !keyChain.isEmpty() && keyChain.get( keyChain.size() - 1 ).equals( "-" ) && line.trim().startsWith( "-" ) ) {
                        keyChain.add( line.split( ":" )[0].trim().substring( 1 ).trim() );
                    } else {
                        keyChain.add( line.split( ":" )[0].trim() );
                    }
                }

                String search;
                if ( !keyChain.isEmpty() ) {
                    search = String.join( ".", keyChain );
                } else {
                    search = "";
                }

                int useDepth = depth - 2;
                if ( line.trim().startsWith( "-" ) ) {
                    keyChain.remove( keyChain.size() - 1 );
                    useDepth += 2;
                }

                if ( this.comments.containsKey( search ) ) {
                    for ( String comment : this.comments.get( search ) ) {
                        writeLines.append( new String( new char[useDepth] ).replace( "\0", " " ) );
                        writeLines.append( "# " );
                        writeLines.append( comment );
                        writeLines.append( "\n" );
                    }
                }

                writeLines.append( line );
                writeLines.append( "\n" );
            }

            fileWriter.write( writeLines.toString() );
        } catch ( IOException e ) {
            throw new InvalidConfigurationException( "Could not save YML", e );
        }
    }

    public void addComment( String key, String value ) {
        if ( !this.comments.containsKey( key ) ) {
            this.comments.put( key, new ArrayList<>() );
        }

        for ( String s : value.split( "\n" ) ) {
            this.comments.get( key ).add( s );
        }
    }

    public void clearComments() {
        this.comments.clear();
    }

    public void mergeComments( Map<String, String> comments ) {
        for ( Map.Entry<String, String> entry : comments.entrySet() ) {
            String commentPath = this.commentPrefix + "." + entry.getKey();
            if ( !this.comments.containsKey( commentPath ) ) {
                this.addComment( commentPath, entry.getValue() );
            }
        }
    }

    public void resetCommentPrefix( String path ) {
        this.commentPrefix = path;
    }

    public void addCommentPrefix( String path ) {
        this.commentPrefix += "." + path;
    }

    public void removeCommentPrefix( String path ) {
        if ( this.commentPrefix.endsWith( path ) ) {
            this.commentPrefix = this.commentPrefix.substring( 0, this.commentPrefix.length() - ( 1 + path.length() ) );
        }
    }
}
