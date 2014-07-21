package xmlproj;

/**
 * A parser state
 */
public enum ParserState
{
    INITIAL,
    AFTER_CLOSING_QUOTE,
    READING_TEXT,
    READING_OPENING_QUOTE,
    READING_CLOSING_QUOTE,
    READING_SECOND_QUOTE,
    READING_QUOTED_TEXT,
    READING_COMMA,
    READING_NEWLINE,
    FINAL,
    ERROR
}
