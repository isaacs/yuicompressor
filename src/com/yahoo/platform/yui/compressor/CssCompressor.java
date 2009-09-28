/*
 * YUI Compressor
 * Author: Julien Lecomte - http://www.julienlecomte.net/
 * Copyright (c) 2009 Yahoo! Inc.  All rights reserved.
 * The copyrights embodied in the content of this file are licensed
 * by Yahoo! Inc. under the BSD (revised) open source license.
 */

package com.yahoo.platform.yui.compressor;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.w3c.flute.parser.*;
import org.w3c.css.sac.InputSource;

public class CssCompressor {

    private StringBuffer srcsb = new StringBuffer();
    private CssDocumentHandler cssDoc = null;

    public CssCompressor (Reader in) throws IOException {
        // Read the stream...
        
        // Use the flute implementation of a SAC parser.
        System.setProperty("org.w3c.css.sac.parser", "org.w3c.flute.parser.Parser");
        
        Parser parser = new Parser();
        this.cssDoc = new CssDocumentHandler();
        parser.setDocumentHandler(this.cssDoc);
        parser.parseStyleSheet(new InputSource(in));
    }

    public void compress (Writer out, int linebreakpos) throws IOException {
        out.write(this.cssDoc.toString());
    }
}
