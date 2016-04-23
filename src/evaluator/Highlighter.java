/*
 * Copyright (c) 2016.
 */

package evaluator;

import javax.swing.text.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Allows syntax highlighting for all the code editor
 * */
public class Highlighter extends DefaultStyledDocument {

    private Style style;
    private Style constantWidthStyle;
    
    public Highlighter(Style style, Style constantWidthStyle) {
        this.style = style;
        this.constantWidthStyle = constantWidthStyle;
    }

    @Override
    public void insertString (int offset, String string, AttributeSet attributeSet) throws BadLocationException {
        super.insertString(offset, string, attributeSet);
        refresh();
    }

    @Override
    public void remove (int offset, int length) throws BadLocationException {
        super.remove(offset, length);
        refresh();
    }

    /**
     * Refreshes the screen everytime a new character is entered, and tells
     * the editor what to coloize
     * @throws BadLocationException
     */
    private synchronized void refresh() throws BadLocationException {
        String text = getText(0, getLength());
        List<Word> keywords = processWords(text);
        setCharacterAttributes(0, text.length(), style, true);
        for (Word keyword : keywords) {
            setCharacterAttributes(keyword.getPosition(),
                    keyword.getWord().length(), constantWidthStyle, true);
        }
    }

    /**
     * Process each word in the text
     * @param text The text to parse and look for highlighting
     * @return The list of words that need to be highlighted
     */
    private static  List<Word> processWords(String text) {
        text = text + " ";
        List<Word> keywords = new ArrayList<>();
        int lastWhitespacePosition;
        StringBuilder wordBuilder = new StringBuilder();
        char[] characters = text.toCharArray();

        for(int i = 0; i < characters.length; i++) {
            char character = characters[i];
            if(Character.isWhitespace(character)) {
                lastWhitespacePosition = i;
                if(wordBuilder.length() > 0) {
                    Word word = new Word(
                            wordBuilder.toString(),
                            lastWhitespacePosition - wordBuilder.length()
                            );
                    if(word.isKeyword()) {
                        keywords.add(word);
                    }
                    wordBuilder = new StringBuilder();
                }
            }
            else {
                wordBuilder.append(character);
            }
        }
        return keywords;
    }

    /**
     * Represents a word in the text to be parsed
     */
    private static class Word {

        private int position;
        private String word;

        public Word(String word, int position) {
            this.word = word;
            this.position = position;
        }

        /**
         * Gets the words position in the text
         * @return the position of the word
         */
        public int getPosition() {
            return position;
        }

        public String getWord() {
            return word;
        }

        /**
         * Returns weather or not this word is a keyword
         * @return whether or not this word is a keyword
         */
        public boolean isKeyword(){
            return(word.trim().equals("private") ||
                    word.trim().equals("public") ||
                    word.trim().equals("int") ||
                    word.trim().equals("char") ||
                    word.trim().equals("protected") ||
                    word.trim().equals("class") ||
                    word.trim().equals("static") ||
                    word.trim().equals("void") ||
                    word.trim().equals("return") ||
                    word.trim().equals("boolean") ||
                    word.trim().equals("double") ||
                    word.trim().equals("byte") ||
                    word.trim().equals("this") ||
                    word.trim().equals("super") ||
                    word.trim().equals("final") ||
                    word.trim().equals("if") ||
                    word.trim().equals("for") ||
                    word.trim().equals("while") ||
                    word.trim().equals("do") ||
                    word.trim().equals("try") ||
                    word.trim().equals("catch") ||
                    word.trim().equals("new") ||
                    word.trim().equals("switch") ||
                    word.trim().equals("case") ||
                    word.trim().equals("break") ||
                    word.trim().equals("goto") ||
                    word.trim().equals("default") ||
                    word.trim().equals("long") ||
                    word.trim().equals("float") ||
                    word.trim().equals("short") ||
                    word.trim().equals("continue") ||
                    word.trim().equals("else") ||
                    word.trim().equals("abstract") ||
                    word.trim().equals("assert") ||
                    word.trim().equals("extends") ||
                    word.trim().equals("implements") ||
                    word.trim().equals("const") ||
                    word.trim().equals("default") ||
                    word.trim().equals("enum") ||
                    word.trim().equals("finally") ||
                    word.trim().equals("import") ||
                    word.trim().equals("instanceof") ||
                    word.trim().equals("interface") ||
                    word.trim().equals("null") ||
                    word.trim().equals("native") ||
                    word.trim().equals("package") ||
                    word.trim().equals("strictfp") ||
                    word.trim().equals("synchronized") ||
                    word.trim().equals("throw") ||
                    word.trim().equals("throws") ||
                    word.trim().equals("transient") ||
                    word.trim().equals("volatile") ||
                    word.trim().equals("false") ||
                    word.trim().equals("true"));
        }
    }
}
