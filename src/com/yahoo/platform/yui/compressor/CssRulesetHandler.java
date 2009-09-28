package com.yahoo.platform.yui.compressor;
import org.w3c.css.sac.*;
import org.w3c.flute.parser.*;
import org.w3c.flute.parser.selectors.*;

public class CssRulesetHandler {
    private String output = "";
    private void put (String token) {
        System.err.println("put("+token+")");
        this.output += token;
    }
    public String toString () {
        if (this.propertyCount == 0) return "";
        this.put("}");
        return this.output;
    }
    private int propertyCount = 0;
    public CssRulesetHandler (SelectorList selectors) {
        for (int i = 0; i < selectors.getLength(); i ++) {
            System.err.println("\n--------");
            this.handleSelector(selectors.item(i));
        }
        this.put("{");
    }
    
    public void handleProperty (java.lang.String name, LexicalUnit value, boolean important) {
        System.err.println("\nproperty "+name+", "+value+", "+important);
        if (this.propertyCount > 0) this.put(";");
        this.put(name);
        this.put(":");
        this.put(this.handlePropertyValue(value));
        if (important) this.put("!important");
        this.propertyCount ++;
    }
    
    
    private String handlePropertyValue (LexicalUnit value) {
        String t = "";
        switch (value.getLexicalUnitType()) {
            case LexicalUnit.SAC_IDENT: // any identifier except inherit.
                t = value.getStringValue(); break;
            case LexicalUnit.SAC_ATTR: // Attribute: attr(...).
                t = "attr(" + value.getStringValue() + ")"; break;
            case LexicalUnit.SAC_RGBCOLOR: // RGB Colors.
                // @TODO: Convert to hex here.
                t = "rgb("+(value.getParameters())+")"; break;
            default:
                return value.toString();
        }
        if (value.getNextLexicalUnit() != null) t += " " + this.handlePropertyValue(value.getNextLexicalUnit());
        return t;
    }
    
    
    private void handleCondition (Condition c) {
        switch (c.getConditionType()) {
            case Condition.SAC_AND_CONDITION:
                System.err.println("Condition.SAC_AND_CONDITION");
                AndConditionImpl aci = (AndConditionImpl)c;
                this.handleCondition(aci.getSecondCondition());
                this.handleCondition(aci.getFirstCondition());
                break;
            case Condition.SAC_ATTRIBUTE_CONDITION:
                System.err.println("Condition.SAC_ATTRIBUTE_CONDITION");
                AttributeConditionImpl attrCond = (AttributeConditionImpl)c;
                this.put("[");
                this.put(attrCond.getLocalName());
                String attrValue = attrCond.getValue();
                if (null != attrValue) this.put("=" + attrValue);
                this.put("]");
                break;
            case Condition.SAC_BEGIN_HYPHEN_ATTRIBUTE_CONDITION:
                System.err.println("Condition.SAC_BEGIN_HYPHEN_ATTRIBUTE_CONDITION");
                break;
            case Condition.SAC_CLASS_CONDITION:
                ClassConditionImpl cci = (ClassConditionImpl)c;
                this.put("."+cci.getValue());
                break;
            case Condition.SAC_CONTENT_CONDITION:
                System.err.println("Condition.SAC_CONTENT_CONDITION");
                break;
            case Condition.SAC_ID_CONDITION:
                IdConditionImpl ici = (IdConditionImpl)c;
                this.put("#"+ici.getValue());
                break;
            case Condition.SAC_LANG_CONDITION:
                System.err.println("Condition.SAC_LANG_CONDITION");
                
                break;
            case Condition.SAC_NEGATIVE_CONDITION:
                System.err.println("Condition.SAC_NEGATIVE_CONDITION");
                break;
            case Condition.SAC_ONE_OF_ATTRIBUTE_CONDITION:
                System.err.println("Condition.SAC_ONE_OF_ATTRIBUTE_CONDITION");
                break;
            case Condition.SAC_ONLY_CHILD_CONDITION:
                System.err.println("Condition.SAC_ONLY_CHILD_CONDITION");
                break;
            case Condition.SAC_ONLY_TYPE_CONDITION:
                System.err.println("Condition.SAC_ONLY_TYPE_CONDITION");
                break;
            case Condition.SAC_OR_CONDITION:
                System.err.println("Condition.SAC_OR_CONDITION");
                break;
            case Condition.SAC_POSITIONAL_CONDITION:
                System.err.println("Condition.SAC_POSITIONAL_CONDITION");
                break;
            case Condition.SAC_PSEUDO_CLASS_CONDITION:
                System.err.println("Condition.SAC_PSEUDO_CLASS_CONDITION");
                break;
            default:
                System.err.println("Condition.hrm?");
                break;
        }
    }
    
    private void handleSelector (Selector s) {
        switch (s.getSelectorType()) {
            case Selector.SAC_ANY_NODE_SELECTOR                   :
                System.err.println("SAC_ANY_NODE_SELECTOR");
                this.put("*");
                break;
            case Selector.SAC_CDATA_SECTION_NODE_SELECTOR         :
                System.err.println("SAC_CDATA_SECTION_NODE_SELECTOR");
                break;
            case Selector.SAC_COMMENT_NODE_SELECTOR               :
                System.err.println("SAC_COMMENT_NODE_SELECTOR");
                break;
            case Selector.SAC_CONDITIONAL_SELECTOR                :
                System.err.println("SAC_CONDITIONAL_SELECTOR");
                ConditionalSelectorImpl cond = (ConditionalSelectorImpl)s;
                this.handleSelector(cond.getSimpleSelector());
                this.handleCondition(cond.getCondition());
                break;
            case Selector.SAC_CHILD_SELECTOR                      :
                System.err.println("SAC_CHILD_SELECTOR");
                ChildSelectorImpl child = (ChildSelectorImpl)s;
                this.handleSelector(child.getAncestorSelector());
                this.put(">");
                this.handleSelector(child.getSimpleSelector());
                break;
            case Selector.SAC_DESCENDANT_SELECTOR                 :
                System.err.println("SAC_DESCENDANT_SELECTOR");
                DescendantSelectorImpl desc = (DescendantSelectorImpl)s;
                this.handleSelector(desc.getAncestorSelector());
                this.put(" ");
                this.handleSelector(desc.getSimpleSelector());
                break;
            case Selector.SAC_DIRECT_ADJACENT_SELECTOR            :
                System.err.println("SAC_DIRECT_ADJACENT_SELECTOR");
                DirectAdjacentSelectorImpl dir = (DirectAdjacentSelectorImpl)s;
                this.handleSelector(dir.getSelector());
                this.put("+");
                this.handleSelector(dir.getSiblingSelector());
                break;
            case Selector.SAC_ELEMENT_NODE_SELECTOR               :
                System.err.println("SAC_ELEMENT_NODE_SELECTOR");
                ElementSelectorImpl esi = (ElementSelectorImpl)s;
                if (null != esi.getLocalName()) this.put(esi.getLocalName());
                break;
            case Selector.SAC_NEGATIVE_SELECTOR                   :
                System.err.println("SAC_NEGATIVE_SELECTOR");
                break;
            case Selector.SAC_PROCESSING_INSTRUCTION_NODE_SELECTOR:
                System.err.println("SAC_PROCESSING_INSTRUCTION_NODE_SELECTOR");
                break;
            case Selector.SAC_PSEUDO_ELEMENT_SELECTOR             :
                System.err.println("SAC_PSEUDO_ELEMENT_SELECTOR");
                break;
            case Selector.SAC_ROOT_NODE_SELECTOR                  :
                System.err.println("SAC_ROOT_NODE_SELECTOR");
                break;
            case Selector.SAC_TEXT_NODE_SELECTOR                  :
                System.err.println("SAC_TEXT_NODE_SELECTOR");
                break;
            default                                                               :
                System.err.println(s.getSelectorType() + " hrm?");
                break;
        }
        
    }
    
    
}