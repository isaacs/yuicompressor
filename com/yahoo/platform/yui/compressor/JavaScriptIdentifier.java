/*
 * YUI Compressor
 * Author: Julien Lecomte <jlecomte@yahoo-inc.com>
 * Copyright (c) 2007, Yahoo! Inc. All rights reserved.
 * Code licensed under the BSD License:
 *     http://developer.yahoo.net/yui/license.txt
 */

package com.yahoo.platform.yui.compressor;

import java.util.ArrayList;
import java.util.HashMap;

import org.mozilla.javascript.Token;

/**
 * JavaScriptIdentifier represents a variable/function identifier.
 */
class JavaScriptIdentifier extends JavaScriptToken {

    private int refcount = 0;
    private String mungedValue;
    private ScriptOrFnScope declaredScope;
    private boolean markedForMunging = true;
    private boolean declareAsVar;
    private JavaScriptIdentifier parentIdentifier = null;
    private HashMap<String, JavaScriptIdentifier> properties = new HashMap<String, JavaScriptIdentifier>();

    JavaScriptIdentifier(String value, ScriptOrFnScope declaredScope, boolean declareAsVar) {
        super(Token.NAME, value);
        this.declaredScope = declaredScope;
        this.declareAsVar = declareAsVar;
    }

    JavaScriptIdentifier(String value, ScriptOrFnScope declaredScope, JavaScriptIdentifier parentIdentifier) {
        super(Token.NAME, value);
        this.declaredScope = declaredScope;
        this.parentIdentifier = parentIdentifier;
        this.declareAsVar = false;
    }

    ScriptOrFnScope getDeclaredScope() {
        return declaredScope;
    }

    void setMungedValue(String value) {
        mungedValue = value;
    }

    String getMungedValue() {
        return mungedValue;
    }

    void preventMunging() {
        markedForMunging = false;
    }

    boolean isMarkedForMunging() {
        return markedForMunging;
    }

    boolean declareAsVar() {
        return declareAsVar;
    }

    JavaScriptIdentifier getParent() {
    	return parentIdentifier;
    }

    String getFullName() {
    	StringBuilder sb = new StringBuilder();
    	if (getParent() != null) {
    		sb.append(getParent().getValue());
    	}
    	sb.append(getValue());
    	return sb.toString();
    }

    JavaScriptIdentifier getProperty(String name) {
    	return properties.get(name);
    }

    void addProperty(String name) {
    	properties.put(name, new JavaScriptIdentifier(name, declaredScope, this));
    }

    void incrementRefcount() {
        refcount++;
    }

    int getRefcount() {
        return refcount;
    }
}
