/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geomap;

/**
 *
 * @author Kim Jon Un
 */

import java.net.URI;
import javax.ws.rs.client.Client;  
import javax.ws.rs.client.ClientBuilder;  
import javax.ws.rs.client.WebTarget;  
import javax.ws.rs.core.MediaType;  
import javax.ws.rs.core.UriBuilder;  
import org.glassfish.jersey.client.ClientConfig;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class GeoMap
{
    public static void main(String...aa)
    {
        try
        {
            System.out.println("Finding Address...\n");
            ClientConfig config = new ClientConfig();
            Client client = ClientBuilder.newClient(config);
            WebTarget target = client.target(getBaseURI());

            String jsonaddresss =  target.request().accept(MediaType.APPLICATION_JSON).get(String.class);

            String address = adres(jsonaddresss);


            System.out.println("My Address is : >>"+address);

        }
        catch(Exception e)
        {
            System.out.println("Error"+e);
        }
    }

    private static URI getBaseURI()
    {
        return UriBuilder.fromUri("https://maps.googleapis.com/maps/api/geocode/json?latlng=1.28333,36.81667&key=AIzaSyC-EwAaeu1L6mJRKms91yp0kQpQZXhHJe4").build();
    }
    private static String adres(String jsonaddresss)
    {
        try
        {
            JSONParser parser = new JSONParser();

            Object obj = parser.parse(jsonaddresss);
            JSONObject jsonObject = (JSONObject) obj;

            JSONArray msg = (JSONArray) jsonObject.get("results");

            //System.out.println("Message"+msg.get(1)); //Get Second Line Of Result

            JSONObject jobj = (JSONObject) parser.parse(msg.get(1).toString()); // Parsse it 

            String perfect_address = jobj.get("formatted_address").toString();

            jsonaddresss = perfect_address;
        }
        catch(Exception e)
        {
            jsonaddresss = "Error In Address"+e;
        }
        return jsonaddresss;
        
    }
}