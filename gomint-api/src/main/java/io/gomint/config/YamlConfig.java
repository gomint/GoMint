/*
 * Copyright (c) 2020 GoMint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.config;

import com.google.common.base.Preconditions;
import io.gomint.config.annotation.Comment;
import io.gomint.config.annotation.Comments;
import io.gomint.config.annotation.Path;
import io.gomint.util.Messages;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public class YamlConfig extends ConfigMapper implements Config<YamlConfig> {

    public YamlConfig() {}

    public YamlConfig( String filename ) {
        this.configFile = new File(filename + ( filename.endsWith(".yml" ) ? "" : ".yml" ) );
    }

    @Override
    public YamlConfig save() throws InvalidConfigurationException {
        Preconditions.checkNotNull(this.configFile, "Cannot save config file: Local field 'configFile' is null" );

        if ( this.root == null ) {
            this.root = new ConfigSection();
        }

        this.clearComments();
        this.internalSave( this.getClass() );
        this.saveToYaml();

        return this;
    }

    @Override
    public YamlConfig save( File file ) throws InvalidConfigurationException {
        Preconditions.checkNotNull( file, Messages.paramIsNull( "file" ) );

        this.configFile = file;
        this.save();

        return this;
    }

    @Override
    public YamlConfig init() throws InvalidConfigurationException {
        if ( this.configFile.exists() ) {
            this.load();
            return this;
        }

        File parentFile = this.configFile.getParentFile();

        if ( parentFile != null && !parentFile.exists() ) {
            Preconditions.checkState( parentFile.mkdirs(),
                "Failed creating directory " + parentFile.getAbsolutePath() );
        }

        try {
            Preconditions.checkState( this.configFile.createNewFile(),
                "Failed creating file " + this.configFile.getAbsolutePath() );

            this.save();
        } catch ( IOException cause ) {
            throw new InvalidConfigurationException( "Failed saving new empty config file", cause );
        }

        return this;
    }

    @Override
    public YamlConfig init( File file ) throws InvalidConfigurationException {
        Preconditions.checkNotNull( file, Messages.paramIsNull( "file" ) );

        this.configFile = file;
        this.init();

        return this;
    }

    @Override
    public YamlConfig reload() throws InvalidConfigurationException {
        this.loadFromYaml();
        this.internalLoad( this.getClass() );
        return this;
    }

    @Override
    public YamlConfig load() throws InvalidConfigurationException {
        Preconditions.checkNotNull(this.configFile, "Cannot load config file: Local field 'configFile' is null" );

        this.loadFromYaml();
        this.update( this.root );
        this.internalLoad( this.getClass() );

        return this;
    }

    @Override
    public YamlConfig load( File file ) throws InvalidConfigurationException {
        Preconditions.checkNotNull( file, Messages.paramIsNull( "file" ) );

        this.configFile = file;
        this.load();

        return this;
    }

    private YamlConfig internalSave( Class<?> clazz ) throws InvalidConfigurationException {
        if ( !clazz.getSuperclass().equals( YamlConfig.class ) ) {
            this.internalSave( clazz.getSuperclass() );
        }

        for ( Field field : clazz.getDeclaredFields() ) {
            if ( this.doSkip( field ) ) {
                continue;
            }

            String path;

            switch ( this.configMode) {
                case PATH_BY_UNDERSCORE:
                    path = field.getName().replace( "_", "." );
                    break;
                case FIELD_IS_KEY:
                    path = field.getName();
                    break;
                case DEFAULT:
                default:
                    if ( field.getName().contains( "_" ) ) {
                        path = field.getName().replace( "_", "." );
                    } else {
                        path = field.getName();
                    }

                    break;
            }

            ArrayList<String> comments = new ArrayList<>();

            for ( Annotation annotation : field.getAnnotations() ) {
                if ( annotation instanceof Comment) {
                    comments.add( ( (Comment) annotation ).value() );
                }

                if ( annotation instanceof Comments) {
                    for ( Comment comment : ( (Comments) annotation ).value() ) {
                        comments.add( comment.value() + "\n" );
                    }
                }
            }

            if ( field.isAnnotationPresent( Path.class ) ) {
                path = field.getAnnotation( Path.class ).value();
            }

            if ( comments.size() > 0 ) {
                for ( String comment : comments ) {
                    this.addComment( path, comment );
                }
            }

            if ( Modifier.isPrivate( field.getModifiers() ) ) {
                field.setAccessible( true );
            }

            try {
                this.converter.toConfig( this, field, this.root, path );
                this.converter.fromConfig( this, field, this.root, path );
            } catch ( Exception cause ) {
                if ( !this.skipFailedObjects ) {
                    throw new InvalidConfigurationException( "Failed saving field " +
                        "'" + clazz.getName() + "#" + field.getName() + "'", cause );
                }
            }
        }
        return this;
    }

    private YamlConfig internalLoad( Class<?> clazz ) throws InvalidConfigurationException {
        if ( !clazz.getSuperclass().equals( YamlConfig.class ) ) {
            this.internalLoad( clazz.getSuperclass() );
        }

        boolean save = false;

        for ( Field field : clazz.getDeclaredFields() ) {
            if ( this.doSkip( field ) ) {
                continue;
            }

            String path;

            switch ( this.configMode) {
                case PATH_BY_UNDERSCORE:
                    path = field.getName().replace( "_", "." );
                    break;
                case FIELD_IS_KEY:
                    path = field.getName();
                    break;
                case DEFAULT:
                default:
                    if ( field.getName().contains( "_" ) ) {
                        path = field.getName().replace( "_", "." );
                    } else {
                        path = field.getName();
                    }

                    break;
            }

            if ( field.isAnnotationPresent( Path.class ) ) {
                path = field.getAnnotation( Path.class ).value();
            }

            if ( Modifier.isPrivate( field.getModifiers() ) ) {
                field.setAccessible( true );
            }

            if ( this.root.has( path ) ) {
                try {
                    this.converter.fromConfig( this, field, this.root, path );
                } catch ( Exception cause ) {
                    throw new InvalidConfigurationException( "Failed assigning value to field " +
                        "'" + clazz.getName() + "#" + field.getName() + "'", cause );
                }
            } else {
                try {
                    this.converter.toConfig( this, field, this.root, path );
                    this.converter.fromConfig( this, field, this.root, path );

                    save = true;
                } catch ( Exception cause ) {
                    if ( !this.skipFailedObjects ) {
                        throw new InvalidConfigurationException( "Failed retrieving value of field " +
                            "'" + clazz.getName() + "#" + field.getName() + "'", cause );
                    }
                }
            }
        }

        if ( save ) {
            this.saveToYaml();
        }

        return this;
    }

}
