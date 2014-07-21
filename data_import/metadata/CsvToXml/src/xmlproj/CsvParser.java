package xmlproj;

/**
 * A CSV parser
 */
public class CsvParser
{
    private ParserState state = ParserState.INITIAL; /* The parser state */
    private char[] content = null; /* The content of the CSV to be parsed (parser input stream) */
    private int ptr = 0; /* The pointer to the current location on the input stream */
    private int row = 0; /* A row counter */
    private int col = 0; /* A column counter */
    private StringBuilder buffer = null; /* A buffer where portions of the stream are stored */
    
    /**
     * Creates a new CSV parser
     * @param content The contents of the CSV file
     */
    public CsvParser(char[] content)
    {
        this.content = content;
        this.buffer = new StringBuilder();
    }
    
    /**
     * Get the parser state
     * @return The parser state
     */
    public ParserState getState()
    {
        return this.state;
    }
    
    /**
     * Get the row id
     * @return The row id
     */
    public int getRow()
    {
        return this.row;
    }
    
    /**
     * Get the column id
     * @return The column id
     */
    public int getCol()
    {
        return this.col;
    }
    
    /**
     * Get the next item
     * @return The contents of the next item
     */
    public String getNextItem()
    {
        this.buffer.setLength(0); /* Clear the buffer */
        boolean spitItem = false; /* This is set to true when it's time to return a parsed item (value) */
        
        while (!spitItem & (this.state != ParserState.FINAL) & (this.state != ParserState.ERROR))
        {
            
            if (this.ptr >= this.content.length)
                this.state = ParserState.FINAL;
            else
            {
                char c = this.content[this.ptr];
                // System.out.println("State: " + this.state.toString());
                
                switch (this.state)
                {
                    case INITIAL:
                        
                        if (c == '\"')
                            this.state = ParserState.READING_OPENING_QUOTE;
                        else if (c == ',')
                            this.state = ParserState.READING_COMMA;
                        else if (c == '\n')
                            this.state = ParserState.READING_NEWLINE;
                        else
                            this.state = ParserState.READING_TEXT;
                        
                        break;
                    case AFTER_CLOSING_QUOTE:
                        
                        if (c == ',')
                            this.state = ParserState.READING_COMMA;
                        else if (c == '\n')
                            this.state = ParserState.READING_NEWLINE;
                        else if (c == '\"')
                            this.state = ParserState.READING_SECOND_QUOTE;
                        else
                            this.state = ParserState.ERROR;
                        
                        break;
                    case READING_TEXT:
                        
                        if (c == ',')
                            this.state = ParserState.READING_COMMA;
                        else if (c == '\n')
                            this.state = ParserState.READING_NEWLINE;
                        else
                            this.buffer.append(this.content[this.ptr++]);
                        
                        break;
                    case READING_OPENING_QUOTE:
                        
                        if (c == '\"')
                        {
                            this.ptr++;
                            this.state = ParserState.READING_QUOTED_TEXT;
                        }
                        else
                            this.state = ParserState.ERROR;
                        
                        break;
                    case READING_CLOSING_QUOTE:
                        
                        if (c == '\"')
                        {
                            this.ptr++;
                            this.state = ParserState.AFTER_CLOSING_QUOTE;
                        }
                        else
                            this.state = ParserState.ERROR;
                        
                        break;
                    case READING_SECOND_QUOTE:
                        
                        if (c == '\"')
                        {
                            this.buffer.append('\"');
                            this.ptr++;
                            this.state = ParserState.READING_QUOTED_TEXT;
                        }
                        else
                            this.state = ParserState.ERROR;
                        
                        break;
                    case READING_QUOTED_TEXT:
                        
                        if (c == '\"')
                            this.state = ParserState.READING_CLOSING_QUOTE;
                        else
                            this.buffer.append(this.content[this.ptr++]);
                        
                        break;
                    case READING_COMMA:
                        
                        if (c == ',')
                        {
                            spitItem = true;
                            this.col++;
                            this.ptr++;
                            this.state = ParserState.INITIAL;
                        }
                        else
                            this.state = ParserState.ERROR;
                        
                        break;
                    case READING_NEWLINE:
                        
                        if (c == '\n')
                        {
                            spitItem = true;
                            this.col = 0;
                            this.row++;
                            this.ptr++;
                            this.state = ParserState.INITIAL;
                        }
                        else
                            this.state = ParserState.ERROR;
                        
                        break;
                    case FINAL:
                        break;
                    case ERROR:
                        break;
                }
                
            }
            
        }
        
        return this.buffer.toString();
    }

}
