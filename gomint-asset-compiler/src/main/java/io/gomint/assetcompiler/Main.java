package io.gomint.assetcompiler;

import io.gomint.taglib.NBTReader;
import io.gomint.taglib.NBTTagCompound;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Main {

    private static String PMMP_DUMP_BIOME_DATA = "CgAKDWJhbWJvb19qdW5nbGUFCGRvd25mYWxsZmZmPwULdGVtcGVyYXR1cmUzM3M/AAoTYmFtYm9vX2p1bmdsZV9oaWxscwUIZG93bmZhbGxmZmY/BQt0ZW1wZXJhdHVyZTMzcz8ACgViZWFjaAUIZG93bmZhbGzNzMw+BQt0ZW1wZXJhdHVyZc3MTD8ACgxiaXJjaF9mb3Jlc3QFCGRvd25mYWxsmpkZPwULdGVtcGVyYXR1cmWamRk/AAoSYmlyY2hfZm9yZXN0X2hpbGxzBQhkb3duZmFsbJqZGT8FC3RlbXBlcmF0dXJlmpkZPwAKGmJpcmNoX2ZvcmVzdF9oaWxsc19tdXRhdGVkBQhkb3duZmFsbM3MTD8FC3RlbXBlcmF0dXJlMzMzPwAKFGJpcmNoX2ZvcmVzdF9tdXRhdGVkBQhkb3duZmFsbM3MTD8FC3RlbXBlcmF0dXJlMzMzPwAKCmNvbGRfYmVhY2gFCGRvd25mYWxsmpmZPgULdGVtcGVyYXR1cmXNzEw9AAoKY29sZF9vY2VhbgUIZG93bmZhbGwAAAA/BQt0ZW1wZXJhdHVyZQAAAD8ACgpjb2xkX3RhaWdhBQhkb3duZmFsbM3MzD4FC3RlbXBlcmF0dXJlAAAAvwAKEGNvbGRfdGFpZ2FfaGlsbHMFCGRvd25mYWxszczMPgULdGVtcGVyYXR1cmUAAAC/AAoSY29sZF90YWlnYV9tdXRhdGVkBQhkb3duZmFsbM3MzD4FC3RlbXBlcmF0dXJlAAAAvwAKD2RlZXBfY29sZF9vY2VhbgUIZG93bmZhbGwAAAA/BQt0ZW1wZXJhdHVyZQAAAD8AChFkZWVwX2Zyb3plbl9vY2VhbgUIZG93bmZhbGwAAAA/BQt0ZW1wZXJhdHVyZQAAAAAAChNkZWVwX2x1a2V3YXJtX29jZWFuBQhkb3duZmFsbAAAAD8FC3RlbXBlcmF0dXJlAAAAPwAKCmRlZXBfb2NlYW4FCGRvd25mYWxsAAAAPwULdGVtcGVyYXR1cmUAAAA/AAoPZGVlcF93YXJtX29jZWFuBQhkb3duZmFsbAAAAD8FC3RlbXBlcmF0dXJlAAAAPwAKBmRlc2VydAUIZG93bmZhbGwAAAAABQt0ZW1wZXJhdHVyZQAAAEAACgxkZXNlcnRfaGlsbHMFCGRvd25mYWxsAAAAAAULdGVtcGVyYXR1cmUAAABAAAoOZGVzZXJ0X211dGF0ZWQFCGRvd25mYWxsAAAAAAULdGVtcGVyYXR1cmUAAABAAAoNZXh0cmVtZV9oaWxscwUIZG93bmZhbGyamZk+BQt0ZW1wZXJhdHVyZc3MTD4AChJleHRyZW1lX2hpbGxzX2VkZ2UFCGRvd25mYWxsmpmZPgULdGVtcGVyYXR1cmXNzEw+AAoVZXh0cmVtZV9oaWxsc19tdXRhdGVkBQhkb3duZmFsbJqZmT4FC3RlbXBlcmF0dXJlzcxMPgAKGGV4dHJlbWVfaGlsbHNfcGx1c190cmVlcwUIZG93bmZhbGyamZk+BQt0ZW1wZXJhdHVyZc3MTD4ACiBleHRyZW1lX2hpbGxzX3BsdXNfdHJlZXNfbXV0YXRlZAUIZG93bmZhbGyamZk+BQt0ZW1wZXJhdHVyZc3MTD4ACg1mbG93ZXJfZm9yZXN0BQhkb3duZmFsbM3MTD8FC3RlbXBlcmF0dXJlMzMzPwAKBmZvcmVzdAUIZG93bmZhbGzNzEw/BQt0ZW1wZXJhdHVyZTMzMz8ACgxmb3Jlc3RfaGlsbHMFCGRvd25mYWxszcxMPwULdGVtcGVyYXR1cmUzMzM/AAoMZnJvemVuX29jZWFuBQhkb3duZmFsbAAAAD8FC3RlbXBlcmF0dXJlAAAAAAAKDGZyb3plbl9yaXZlcgUIZG93bmZhbGwAAAA/BQt0ZW1wZXJhdHVyZQAAAAAACgRoZWxsBQhkb3duZmFsbAAAAAAFC3RlbXBlcmF0dXJlAAAAQAAKDWljZV9tb3VudGFpbnMFCGRvd25mYWxsAAAAPwULdGVtcGVyYXR1cmUAAAAAAAoKaWNlX3BsYWlucwUIZG93bmZhbGwAAAA/BQt0ZW1wZXJhdHVyZQAAAAAAChFpY2VfcGxhaW5zX3NwaWtlcwUIZG93bmZhbGwAAIA/BQt0ZW1wZXJhdHVyZQAAAAAACgZqdW5nbGUFCGRvd25mYWxsZmZmPwULdGVtcGVyYXR1cmUzM3M/AAoLanVuZ2xlX2VkZ2UFCGRvd25mYWxszcxMPwULdGVtcGVyYXR1cmUzM3M/AAoTanVuZ2xlX2VkZ2VfbXV0YXRlZAUIZG93bmZhbGzNzEw/BQt0ZW1wZXJhdHVyZTMzcz8ACgxqdW5nbGVfaGlsbHMFCGRvd25mYWxsZmZmPwULdGVtcGVyYXR1cmUzM3M/AAoOanVuZ2xlX211dGF0ZWQFCGRvd25mYWxsZmZmPwULdGVtcGVyYXR1cmUzM3M/AAoTbGVnYWN5X2Zyb3plbl9vY2VhbgUIZG93bmZhbGwAAAA/BQt0ZW1wZXJhdHVyZQAAAAAACg5sdWtld2FybV9vY2VhbgUIZG93bmZhbGwAAAA/BQt0ZW1wZXJhdHVyZQAAAD8ACgptZWdhX3RhaWdhBQhkb3duZmFsbM3MTD8FC3RlbXBlcmF0dXJlmpmZPgAKEG1lZ2FfdGFpZ2FfaGlsbHMFCGRvd25mYWxszcxMPwULdGVtcGVyYXR1cmWamZk+AAoEbWVzYQUIZG93bmZhbGwAAAAABQt0ZW1wZXJhdHVyZQAAAEAACgptZXNhX2JyeWNlBQhkb3duZmFsbAAAAAAFC3RlbXBlcmF0dXJlAAAAQAAKDG1lc2FfcGxhdGVhdQUIZG93bmZhbGwAAAAABQt0ZW1wZXJhdHVyZQAAAEAAChRtZXNhX3BsYXRlYXVfbXV0YXRlZAUIZG93bmZhbGwAAAAABQt0ZW1wZXJhdHVyZQAAAEAAChJtZXNhX3BsYXRlYXVfc3RvbmUFCGRvd25mYWxsAAAAAAULdGVtcGVyYXR1cmUAAABAAAoabWVzYV9wbGF0ZWF1X3N0b25lX211dGF0ZWQFCGRvd25mYWxsAAAAAAULdGVtcGVyYXR1cmUAAABAAAoPbXVzaHJvb21faXNsYW5kBQhkb3duZmFsbAAAgD8FC3RlbXBlcmF0dXJlZmZmPwAKFW11c2hyb29tX2lzbGFuZF9zaG9yZQUIZG93bmZhbGwAAIA/BQt0ZW1wZXJhdHVyZWZmZj8ACgVvY2VhbgUIZG93bmZhbGwAAAA/BQt0ZW1wZXJhdHVyZQAAAD8ACgZwbGFpbnMFCGRvd25mYWxszczMPgULdGVtcGVyYXR1cmXNzEw/AAobcmVkd29vZF90YWlnYV9oaWxsc19tdXRhdGVkBQhkb3duZmFsbM3MTD8FC3RlbXBlcmF0dXJlmpmZPgAKFXJlZHdvb2RfdGFpZ2FfbXV0YXRlZAUIZG93bmZhbGzNzEw/BQt0ZW1wZXJhdHVyZQAAgD4ACgVyaXZlcgUIZG93bmZhbGwAAAA/BQt0ZW1wZXJhdHVyZQAAAD8ACg1yb29mZWRfZm9yZXN0BQhkb3duZmFsbM3MTD8FC3RlbXBlcmF0dXJlMzMzPwAKFXJvb2ZlZF9mb3Jlc3RfbXV0YXRlZAUIZG93bmZhbGzNzEw/BQt0ZW1wZXJhdHVyZTMzMz8ACgdzYXZhbm5hBQhkb3duZmFsbAAAAAAFC3RlbXBlcmF0dXJlmpmZPwAKD3NhdmFubmFfbXV0YXRlZAUIZG93bmZhbGwAAAA/BQt0ZW1wZXJhdHVyZc3MjD8ACg9zYXZhbm5hX3BsYXRlYXUFCGRvd25mYWxsAAAAAAULdGVtcGVyYXR1cmUAAIA/AAoXc2F2YW5uYV9wbGF0ZWF1X211dGF0ZWQFCGRvd25mYWxsAAAAPwULdGVtcGVyYXR1cmUAAIA/AAoLc3RvbmVfYmVhY2gFCGRvd25mYWxsmpmZPgULdGVtcGVyYXR1cmXNzEw+AAoQc3VuZmxvd2VyX3BsYWlucwUIZG93bmZhbGzNzMw+BQt0ZW1wZXJhdHVyZc3MTD8ACglzd2FtcGxhbmQFCGRvd25mYWxsAAAAPwULdGVtcGVyYXR1cmXNzEw/AAoRc3dhbXBsYW5kX211dGF0ZWQFCGRvd25mYWxsAAAAPwULdGVtcGVyYXR1cmXNzEw/AAoFdGFpZ2EFCGRvd25mYWxszcxMPwULdGVtcGVyYXR1cmUAAIA+AAoLdGFpZ2FfaGlsbHMFCGRvd25mYWxszcxMPwULdGVtcGVyYXR1cmUAAIA+AAoNdGFpZ2FfbXV0YXRlZAUIZG93bmZhbGzNzEw/BQt0ZW1wZXJhdHVyZQAAgD4ACgd0aGVfZW5kBQhkb3duZmFsbAAAAD8FC3RlbXBlcmF0dXJlAAAAPwAKCndhcm1fb2NlYW4FCGRvd25mYWxsAAAAPwULdGVtcGVyYXR1cmUAAAA/AAA=";

    public static void main( String[] args ) throws IOException {
        // Load old version
        NBTTagCompound assetsCompound = NBTTagCompound.readFrom( new File( "gomint-asset-compiler/assets.dat" ), true, ByteOrder.BIG_ENDIAN );


        byte[] biomeData = Base64.getDecoder().decode(PMMP_DUMP_BIOME_DATA);
        NBTReader nbtReader = new NBTReader(new ByteArrayInputStream(biomeData), ByteOrder.BIG_ENDIAN);
        nbtReader.setUseVarint(true);
        NBTTagCompound biomeCompound = nbtReader.parse();

        assetsCompound.addValue("biomeDefinitions", biomeCompound.deepClone("biomeDefinitions"));
        assetsCompound.writeTo(new File( "gomint-asset-compiler/assets.dat" ), true, ByteOrder.BIG_ENDIAN);

        // Load the MITM assets
        List<String> knownIDs = new ArrayList<>();
        assetsCompound = null;

        try {
            assetsCompound = NBTTagCompound.readFrom( new File( "gomint-asset-compiler/input-from-mitm.dat" ), true, ByteOrder.BIG_ENDIAN );
            for ( Object palette : assetsCompound.getList( "blockPalette", false ) ) {
                NBTTagCompound entry = (NBTTagCompound) palette;
                knownIDs.add( entry.getString( "id", null ) );
            }
        } catch ( IOException e ) {
            e.printStackTrace();
            System.exit( -1 );
        }

        // Load the JE to PE item id map
        try {
            List<Object> converterItems = assetsCompound.getList( "JEtoPEItems", true );

            List<String> input = Files.readAllLines( Paths.get( "gomint-asset-compiler/item_convert.txt" ) );
            for ( String line : input ) {
                String[] split = line.split( " " );
                String[] split2 = split[1].split( ":" );

                if ( split2.length == 1 ) {
                    String[] temp = new String[2];
                    temp[0] = split2[0];
                    temp[1] = "0";
                    split2 = temp;
                }

                NBTTagCompound idPairCompound = new NBTTagCompound( "" );
                idPairCompound.addValue( "s", Integer.parseInt( split[0] ) );
                idPairCompound.addValue( "t", Integer.parseInt( split2[0] ) );
                idPairCompound.addValue( "tm", Integer.parseInt( split2[1] ) );
                converterItems.add( idPairCompound );
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        // Load 1.8 entity strings to network id (PE)
        try {
            List<Object> converterItems = assetsCompound.getList( "JEtoPEEntityIDs", true );

            List<String> input = Files.readAllLines( Paths.get( "gomint-asset-compiler/entity_ids.txt" ) );
            for ( String line : input ) {
                String[] split = line.split( " " );

                NBTTagCompound idPairCompound = new NBTTagCompound( "" );
                idPairCompound.addValue( "s", split[0] );
                idPairCompound.addValue( "t", Integer.parseInt( split[1] ) );
                converterItems.add( idPairCompound );
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        // Check the in-ids.txt
        try {
            List<Object> converterItems = assetsCompound.getList( "converterItems", true );

            List<String> input = Files.readAllLines( Paths.get( "gomint-asset-compiler/in-ids.txt" ) );
            for ( String line : input ) {
                String[] split = line.split( " " );

                NBTTagCompound idPairCompound = new NBTTagCompound( "" );
                idPairCompound.addValue( "s", split[1] );
                idPairCompound.addValue( "i", Integer.parseInt( split[0] ) );
                converterItems.add( idPairCompound );
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        // Check pe_convert.json
        try {
            List<Object> peConverter = assetsCompound.getList( "PEconverter", true );

            JSONParser parser = new JSONParser();
            JSONArray array = (JSONArray) parser.parse( new InputStreamReader( new FileInputStream( new File( "gomint-asset-compiler/pe_convert.json" ) ) ) );
            for ( Object o : array ) {
                JSONObject obj = (JSONObject) o;

                int id = ((Long) obj.get( "id" )).intValue();
                short data = ((Long) obj.get( "data" )).shortValue();
                String newId = (String) obj.get( "name" );

                NBTTagCompound compound = new NBTTagCompound( "" );
                compound.addValue( "i", id );
                compound.addValue( "d", data );
                compound.addValue( "ni", newId );
                peConverter.add( compound );
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        // First we need to cleanup the id converter map

        /*
         * Short format stuff on how the id converter data works
         *
         * The ID converter takes a anvil 1.8 stored block id and converts it to the correct new string block id.
         * It also converts metadata from one stage to another (like facing which are different in PE)
         *
         * One line represents one converter pair:
         *
         * oldid [oldmeta or -1 for all] newid [newmeta or -1 for direct passthrough (only works when oldmeta is -1)]
         *
         * This compiler takes in the WIP-id-converter-map.txt from planet minecraft (thx for that), strips out the
         * data i don't need, uses the old metadata converted from Gomint to map oldmeta to newmeta then pushes it
         * into a output-id-converter-map.txt for control.
         */
        Map<Integer, String> oldToNewId = new HashMap<>();

        try {
            List<String> input = Files.readAllLines( Paths.get( "gomint-asset-compiler/WIP-id-converter-map.txt" ) );
            for ( String idLine : input ) {
                int oldId;
                String newId;

                String[] split = idLine.split( " " );
                if ( split.length > 2 ) {
                    oldId = Integer.parseInt( split[0] );
                    newId = split[2];
                } else {
                    oldId = Integer.parseInt( split[0] );
                    newId = split[1];
                }

                if ( !knownIDs.contains( newId ) ) {
                    System.out.println( oldId + " -> " + newId );
                }

                oldToNewId.put( oldId, newId );
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }


        // Build up the real converter map
        DataConverter dataConverter = new DataConverter();
        try ( BufferedWriter writer = new BufferedWriter( new FileWriter( "gomint-asset-compiler/output-id-converter-map.txt" ) ) ) {
            for ( Map.Entry<Integer, String> entry : oldToNewId.entrySet() ) {
                // Check if data converter has a entry
                if ( dataConverter.hasConverter( entry.getKey() ) ) {
                    for ( byte i = 0; i < 16; i++ ) {
                        writer.write( entry.getKey() + " " + i + " " + entry.getValue() + " " + dataConverter.convert( entry.getKey(), i ).getSecond() + "\n" );
                    }
                } else {
                    writer.write( entry.getKey() + " -1 " + entry.getValue() + " -1\n" );
                }
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        // Wait for user to control the output id converter
        try {
            System.out.println( "Please check the output-id-converter-map.txt file and correct it if needed. Press enter to continue..." );
            System.in.read();
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        // We read the output again so we know if the user has changed something
        List<Object> converters = assetsCompound.getList( "converter", true );
        try {
            List<String> input = Files.readAllLines( Paths.get( "gomint-asset-compiler/output-id-converter-map.txt" ) );
            for ( String idPair : input ) {
                String[] split = idPair.split( " " );

                NBTTagCompound idPairCompound = new NBTTagCompound( "" );
                idPairCompound.addValue( "oi", Short.parseShort( split[0] ) );
                idPairCompound.addValue( "om", Byte.parseByte( split[1] ) );
                idPairCompound.addValue( "ni", split[2] );
                idPairCompound.addValue( "nm", Byte.parseByte( split[3] ) );

                converters.add( idPairCompound );
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        // Write the final assets.dat
        assetsCompound.writeTo( new File( "gomint-asset-compiler/assets.dat" ), true, ByteOrder.BIG_ENDIAN );
    }

}
