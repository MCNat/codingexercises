import java.io.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class app
{
    private static String GetWebpageContent(String url)
        throws MalformedURLException, IOException
    {
        URLConnection connection = 
            new URL(url)
            .openConnection();

        BufferedReader reader = 
            new BufferedReader(
                new InputStreamReader(
                    connection
                    .getInputStream()));

        StringBuilder response = new StringBuilder();

        String inputLine;

        while ((inputLine = reader.readLine()) != null) 
        {
            response.append(inputLine);
            response.append("\r\n");
        }

        reader.close();

        return response.toString();
    }

    private static void ProcessContent(String text)
    {
        if (
            text == null ||
            text.trim() == "")
        {
            System.out.println("no content to process");
            return;
        }

        Pattern pattern = 
            Pattern
            .compile("<a.*href\\s?=\\s?['\\\"]([^\\\"\\']*)[\'\\\"][^>]*\\>(.*)<\\/a>"); 

        Matcher matcher = 
            pattern
            .matcher(text);

        while (matcher.find())
            System.out.println(
                matcher.group(2) + " -> " + matcher.group(1));
    }

    public static void main(String[] args)
        throws MalformedURLException, IOException
    {
        ProcessContent(
            GetWebpageContent("http://www.spacex.com/"));
    }
} 