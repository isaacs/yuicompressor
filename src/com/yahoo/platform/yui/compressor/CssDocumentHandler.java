package com.yahoo.platform.yui.compressor;
import org.w3c.css.sac.*;
import org.w3c.flute.parser.*;
import org.w3c.flute.parser.selectors.*;

public class CssDocumentHandler implements DocumentHandler {
    
    private String output = "";
    
    private void put (String token) {
        System.err.println("put("+token+")");
        output += token;
    }
    public String toString () {
        return "Hello ["+this.output+"]";
    }
    
    
    public void startDocument (InputSource source) {
        System.err.println("\nstartDocument "+source);
    }
    public void startFontFace () {
        System.err.println("\nstartFontFace");
    }
    public void startMedia (SACMediaList media) {
        System.err.println("\nstartMedia "+media);
    }
    public void startPage (java.lang.String name, java.lang.String pseudo_page) {
        System.err.println("\nstartPage "+name+", "+pseudo_page);
    }
    
    
    public void ignorableAtRule (java.lang.String atRule) {
        System.err.println("\nignorableAtRule " + atRule);
    }
    public void importStyle (java.lang.String uri, SACMediaList media, java.lang.String defaultNamespaceURI) {
        System.err.println("\nimportStyle "+uri+", "+media+", "+defaultNamespaceURI);
    }
    public void namespaceDeclaration (java.lang.String prefix, java.lang.String uri) {
        System.err.println("\nnamespaceDeclaration "+prefix+", "+uri);
    }
    public void comment (String text) {
        // @TODO Leave in comments with /*!, but strip out all others.
        System.err.println("\ncomment " + text);
    }
    
    private CssRulesetHandler ruleset = null;
    public void startSelector (SelectorList selectors) {
        this.ruleset = new CssRulesetHandler(selectors);
    }
    public void endSelector (SelectorList selectors) {
        this.put(this.ruleset.toString());
        this.ruleset = null;
    }
    public void property (java.lang.String name, LexicalUnit value, boolean important) {
        if (null != this.ruleset) this.ruleset.handleProperty(name, value, important);
    }
    
    
    
    
    public void endDocument (InputSource source) {
        // System.err.println("\nendDocument "+source);
    }
    public void endFontFace () {
        System.err.println("\nendFontFace");
    }
    public void endMedia (SACMediaList media) {
        System.err.println("\nendMedia "+media);
    }
    public void endPage (java.lang.String name, java.lang.String pseudo_page) {
        System.err.println("\nendPage " + name + ", " + pseudo_page);
    }
    
}
