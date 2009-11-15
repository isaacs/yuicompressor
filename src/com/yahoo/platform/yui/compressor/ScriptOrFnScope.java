/*
 * YUI Compressor
 * Author: Julien Lecomte - http://www.julienlecomte.net/
 * Copyright (c) 2009 Yahoo! Inc.  All rights reserved.
 * The copyrights embodied in the content of this file are licensed
 * by Yahoo! Inc. under the BSD (revised) open source license.
 */

package com.yahoo.platform.yui.compressor;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

class ScriptOrFnScope {

    private int braceNesting;
    private ScriptOrFnScope parentScope;
    private ArrayList subScopes;
    private Hashtable identifiers = new Hashtable();
    private Hashtable hints = new Hashtable();
    private Hashtable strings = new Hashtable();
    private boolean markedForMunging = true;
    private int thisCount = 0;
    private JavaScriptIdentifier thisIdentifier;

    ScriptOrFnScope(int braceNesting, ScriptOrFnScope parentScope) {
        this.braceNesting = braceNesting;
        this.parentScope = parentScope;
        this.subScopes = new ArrayList();
        if (parentScope != null) {
            parentScope.subScopes.add(this);
        }
    }

    int getBraceNesting() {
        return braceNesting;
    }

    ScriptOrFnScope getParentScope() {
        return parentScope;
    }

    JavaScriptIdentifier declareIdentifier(String symbol, boolean declareAsVar) {
        JavaScriptIdentifier identifier = (JavaScriptIdentifier) identifiers.get(symbol);
        if (identifier == null) {
            identifier = new JavaScriptIdentifier(symbol, this, declareAsVar);
            identifiers.put(symbol, identifier);
        }
        return identifier;
    }

    JavaScriptIdentifier getIdentifier(String symbol) {
        return (JavaScriptIdentifier) identifiers.get(symbol);
    }
    
    boolean hasIdentifier(String symbol) {
        return getIdentifier(symbol) != null;
    }
    
    void declareSymbolAsThis(String symbol) {
        thisIdentifier = declareIdentifier(symbol, false);
    }
    
    JavaScriptIdentifier getThisIdentifier() {
        return thisIdentifier;
    }
    
    void addHint(String variableName, String variableType) {
        hints.put(variableName, variableType);
    }

    void preventMunging() {
        if (parentScope != null) {
            // The symbols in the global scope don't get munged,
            // but the sub-scopes it contains do get munged.
            markedForMunging = false;
        }
    }
    
    ArrayList<JavaScriptIdentifier> getIdentifiers() {
        return getIdentifiers(true); 
    }
    
    ArrayList<JavaScriptIdentifier> getIdentifiers(boolean onlyDeclaredAsVar) {
        ArrayList<JavaScriptIdentifier> result = new ArrayList<JavaScriptIdentifier>();
        Enumeration elements = identifiers.elements();
        while (elements.hasMoreElements()) {
            JavaScriptIdentifier i = (JavaScriptIdentifier) elements.nextElement();
            if (!onlyDeclaredAsVar || i.declaredAsVar()) {
                result.add(i);
            }
        }
        return result;
    }
    
    int getVarIdentifiersSize() {
        int size = 0;
        Enumeration elements = identifiers.elements();
        while (elements.hasMoreElements()) {
            JavaScriptIdentifier i = (JavaScriptIdentifier) elements.nextElement();
            if (i.declaredAsVar()) {
                size++;
            }
        }
        return size;
    }


    int incrementConstValueCount(String value) {
        if (parentScope == null) {
            return 0;
        }
        Integer count = (Integer)strings.get(value);
        if (count == null) {
            count = new Integer(0);
        }
        int c = count.intValue() + 1;
        strings.put(value, c);
        parentScope.incrementConstValueCount(value);
        return c;
    }

    JavaScriptIdentifier getConstValueIdentifier(String value) {
        return getConstValueIdentifier(value, false);
    }
    
    private JavaScriptIdentifier getConstValueIdentifier(String value, boolean onlyCurrentScope) {
        JavaScriptIdentifier identifier;
        ScriptOrFnScope scope = this;
        do {
            identifier = scope.getIdentifier("const_" + value);
            if (identifier != null) {
                return identifier;
            }
            scope = scope.parentScope;
        } while (!onlyCurrentScope && scope != null);
        return null;
    }
    
    Enumeration getConstValues() {
        return strings.keys();
    }
    
    void buildConstTable() {
        Enumeration elements = strings.keys();
        Hashtable symbols = getFreeSymbols();
        int pickFromSet = ((Integer) symbols.get("set")).intValue();
        ArrayList freeSymbols = (ArrayList) symbols.get("symbols");
        String mungedValue = (String)freeSymbols.remove(0);
        JavaScriptIdentifier identifier;
        while (elements.hasMoreElements()) {
            String value = (String) elements.nextElement();
            int count = ((Integer)strings.get(value)).intValue();
            if (count > 1 && getConstValueIdentifier(value) == null && mungedValue.length() < value.length()) {
                identifier = declareIdentifier("const_" + value, false);
                identifier.incrementRefcount();
                identifier.setMungedValue(mungedValue);
                mungedValue = (String)freeSymbols.remove(0);
                if (freeSymbols.size() == 0) {
                    pickFromSet++;
                    if (pickFromSet == 2) {
                        freeSymbols.addAll(JavaScriptCompressor.twos);
                    } else if (pickFromSet == 3) {
                        freeSymbols.addAll(JavaScriptCompressor.threes);
                    } else {
                        throw new IllegalStateException("The YUI Compressor ran out of symbols. Aborting...");
                    }
                    // It is essential to remove the symbols already used in
                    // the containing scopes, or some of the variables declared
                    // in the containing scopes will be redeclared, which can
                    // lead to errors.
                    freeSymbols.removeAll(getAllUsedSymbols());
                }
            } else {
                strings.remove(value);
            }
        }
        for (int i = 0; i < subScopes.size(); i++) {
            ((ScriptOrFnScope) subScopes.get(i)).buildConstTable();
        }
    }

    int incrementThisCount() {
        return ++thisCount;
    }
    
    int getThisCount() {
        return thisCount;
    }
    
    private ArrayList getUsedSymbols() {
        ArrayList result = new ArrayList();
        Enumeration elements = identifiers.elements();
        while (elements.hasMoreElements()) {
            JavaScriptIdentifier identifier = (JavaScriptIdentifier) elements.nextElement();
            String mungedValue = identifier.getMungedValue();
            if (mungedValue == null) {
                mungedValue = identifier.getValue();
            }
            result.add(mungedValue);
        }
        return result;
    }

    private ArrayList getAllUsedSymbols() {
        ArrayList result = new ArrayList();
        ScriptOrFnScope scope = this;
        while (scope != null) {
            result.addAll(scope.getUsedSymbols());
            scope = scope.parentScope;
        }
        return result;
    }

    boolean isMarkedForMunging() {
        return markedForMunging;
    }

    Hashtable getFreeSymbols() {
        int pickFromSet = 1;
        ArrayList freeSymbols = new ArrayList();
        freeSymbols.addAll(JavaScriptCompressor.ones);
        freeSymbols.removeAll(getAllUsedSymbols());
        if (freeSymbols.size() == 0) {
            freeSymbols.addAll(JavaScriptCompressor.twos);
            pickFromSet = 2;
            freeSymbols.removeAll(getAllUsedSymbols());
            if (freeSymbols.size() == 0) {
                freeSymbols.addAll(JavaScriptCompressor.threes);
                pickFromSet = 3;
                freeSymbols.removeAll(getAllUsedSymbols());
            }
            if (freeSymbols.size() == 0) {
                throw new IllegalStateException("The YUI Compressor ran out of symbols. Aborting...");
            }
        }
        Hashtable result = new Hashtable();
        result.put("set", pickFromSet);
        result.put("symbols", freeSymbols);
        return result;
    }

    void munge() {

        if (!markedForMunging) {
            // Stop right here if this scope was flagged as unsafe for munging.
            return;
        }

        // Do not munge symbols in the global scope!
        if (parentScope != null) {

            Hashtable symbols = getFreeSymbols();
            int pickFromSet = ((Integer) symbols.get("set")).intValue();
            ArrayList freeSymbols = (ArrayList) symbols.get("symbols");

            Enumeration elements = identifiers.elements();
            while (elements.hasMoreElements()) {
                if (freeSymbols.size() == 0) {
                    pickFromSet++;
                    if (pickFromSet == 2) {
                        freeSymbols.addAll(JavaScriptCompressor.twos);
                    } else if (pickFromSet == 3) {
                        freeSymbols.addAll(JavaScriptCompressor.threes);
                    } else {
                        throw new IllegalStateException("The YUI Compressor ran out of symbols. Aborting...");
                    }
                    // It is essential to remove the symbols already used in
                    // the containing scopes, or some of the variables declared
                    // in the containing scopes will be redeclared, which can
                    // lead to errors.
                    freeSymbols.removeAll(getAllUsedSymbols());
                }

                String mungedValue;
                JavaScriptIdentifier identifier = (JavaScriptIdentifier) elements.nextElement();
                if (identifier.getMungedValue() == null) {
                    if (identifier.isMarkedForMunging()) {
                        mungedValue = (String) freeSymbols.remove(0);
                    } else {
                        mungedValue = identifier.getValue();
                    }
                    identifier.setMungedValue(mungedValue);
                }
            }
        }

        for (int i = 0; i < subScopes.size(); i++) {
            ((ScriptOrFnScope) subScopes.get(i)).munge();
        }
    }
}
