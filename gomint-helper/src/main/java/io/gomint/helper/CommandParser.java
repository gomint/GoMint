package io.gomint.helper;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * @author geNAZt
 * @version 1.0
 */
public class CommandParser {

    public static void main( String[] args ) {
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod( "https://gist.githubusercontent.com/geNAZt/d23423657e8f600e7faa43b5b6128590/raw/ce30e7e0bb4f515c1f2b95431df2371406963e4d/gistfile1.txt" );

        Set<String> types = new HashSet<>();

        try {
            client.executeMethod( method );
            byte[] responseBody = method.getResponseBody();

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse( new String( responseBody ) );

            jsonObject.forEach( new BiConsumer() {
                @Override
                public void accept( Object o, Object o2 ) {
                    JSONObject commandObject = (JSONObject) o2;
                    JSONArray versions = (JSONArray) commandObject.get( "versions" );
                    for ( Object version : versions ) {
                        JSONObject versionObject = (JSONObject) version;
                        JSONObject overloadsObject = (JSONObject) versionObject.get( "overloads" );

                        overloadsObject.forEach( new BiConsumer() {
                            @Override
                            public void accept( Object o, Object o2 ) {
                                JSONObject overload = (JSONObject) o2;
                                JSONObject output = (JSONObject) overload.get( "output" );

                                if ( output != null && output.containsKey( "parameters" ) ) {
                                    JSONArray strings = (JSONArray) output.get( "parameters" );
                                    for ( Object string : strings ) {
                                        JSONObject param = (JSONObject) string;
                                        types.add( (String) param.get( "type" ) );
                                    }
                                }

                                JSONObject input = (JSONObject) overload.get( "input" );

                                if ( input != null && input.containsKey( "parameters" ) ) {
                                    JSONArray strings = (JSONArray) input.get( "parameters" );
                                    for ( Object string : strings ) {
                                        JSONObject param = (JSONObject) string;
                                        types.add( (String) param.get( "type" ) );
                                    }
                                }
                            }
                        } );
                    }
                }
            } );

            for ( String type : types ) {
                System.out.println( type );
            }
        } catch ( IOException | ParseException e ) {
            e.printStackTrace();
        }
    }

}
