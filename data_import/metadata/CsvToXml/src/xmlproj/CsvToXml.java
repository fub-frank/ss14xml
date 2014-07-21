package xmlproj;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 * A CSV to XML converter
 */
public class CsvToXml
{
    
    /**
     * Escapes a string
     * @param s The string to be escaped
     * @return The escaped string
     */
    private static String escape(String s)
    {
        s = s.replace("&", "&amp;");
        s = s.replace("<", "&gt;");
        s = s.replace(">", "&lt;");
        s = s.replace("\"", "&quot;");
        return s;
    }
    
    /**
     * Entry point for CSV to XML converter
     * @param args Command line arguments
     */
    public static void main(String[] args)
    {
        if (args.length != 5)
            System.out.println("Usage: java -jar CsvToXml.jar [source file (CSV)] [destination file (XML)] [root tag name] [item tag name] [CSV col tags]");
        else
        {
            String[] tags = args[4].split(",");

            try (BufferedReader reader = new BufferedReader(new FileReader(args[0])))
            {
                LinkedList<Character> list = new LinkedList<>();
                int ic;

                while ((ic = reader.read()) != -1)
                    list.addLast((char)ic);

                char[] arr = new char[list.size()];

                for (int i = 0; i < arr.length; i++)
                    arr[i] = list.pollFirst();

                try (PrintWriter writer = new PrintWriter(args[1]))
                {
                    writer.print("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
                    CsvParser parser = new CsvParser(arr);
                    int old_row = parser.getRow();
                    int old_col = parser.getCol();
                    writer.print("<" + CsvToXml.escape(args[2]) + "><" + CsvToXml.escape(args[3]) + " id=\"" + old_row + "\">");

                    while ((parser.getState() != ParserState.FINAL) & (parser.getState() != ParserState.ERROR))
                    {
                        String content = parser.getNextItem();
                        int row = parser.getRow();
                        int col = parser.getCol();
                        String tagname = "";

                        if (old_col < tags.length)
                            tagname = tags[old_col];
                        
                        if (tagname.length() > 0)
                            writer.print("<" + tagname + ">" + CsvToXml.escape(content) + "</" + tagname + ">");

                        if (row != old_row)
                            writer.print("</" + args[3] + "><" + args[3] + " id=\"" + row + "\">");

                        old_row = row;
                        old_col = col;
                    }

                    writer.print("</" + args[3] + "></" + CsvToXml.escape(args[2]) + ">");
                    writer.println();
                    
                    if (parser.getState() == ParserState.ERROR)
                        System.err.println("Parse error in input file on line " + parser.getRow() + ", col " + parser.getCol() + ". Output file may not be valid/complete.");
                    
                }
                catch (IOException ex)
                {
                    ex.getStackTrace();
                    System.err.println("Cannot open output file: " + args[1]);
                }

            }
            catch (IOException ex)
            {
                ex.getStackTrace();
                System.err.println("I/O error on: " + args[0]);
            }
            
        }
        
    }
    
}
