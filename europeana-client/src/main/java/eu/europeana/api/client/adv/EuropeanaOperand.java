package eu.europeana.api.client.adv;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class is used to construct complex operands.
 * Currently, more complex operands cannot be disseminated right by Europeana 
 * query: what: ((eminescu AND mihai AND poezii) OR dune) OR what: eminescu     - a correct query,
 * holds the same results as query: what: ((eminescu AND mihai AND sfgdfgdf) OR dune) OR what: eminescu 
 * Above query is a broken query that should hold less results ("sfgdfgdf" is a broken string)
 *
 * @author Cosmin Coman
 */
public class EuropeanaOperand {

    private String value = null;
    protected String operator = null;
    private ArrayList<EuropeanaOperand> operands = new ArrayList<EuropeanaOperand>();

    /**
     *
     * @param operandValue
     * @param forceQuotes force quotes for the operand value, the 'operandValue'
     * will become '"operandValue"'
     */
    public EuropeanaOperand(String operandValue, boolean forceQuotes) {
        constructOperand(operandValue, forceQuotes);
    }

    /**
     * 
     * @param operand 
     */
    public EuropeanaOperand(EuropeanaOperand operand) {
        //constructOperand(operandValue, forceQuotes);
        this.value = operand.value;
        this.operator = operand.operator;
        Iterator<EuropeanaOperand> operandsIt = operand.operands.iterator();
        while (operandsIt.hasNext()) {
            EuropeanaOperand ope = operandsIt.next();
            EuropeanaOperand newOpe = new EuropeanaOperand(ope);
            this.operands.add(newOpe);
        }
    }

    /**
     * This constructor is not adding quotes to the operand value. It's
     * equivalent to the constructor EuropeanaOperand( operandValue, false)
     *
     * @param operandValue
     */
    public EuropeanaOperand(String operandValue) {
        constructOperand(operandValue, false);
    }
    
    /**
     * Creates a complex operand, composed of 2 operands delimited by a logical rule
     * 
     * @param europeanaOperator the logical rule
     * @param operand1
     * @param operand2 
     */

    public EuropeanaOperand(String europeanaOperator, EuropeanaOperand operand1, EuropeanaOperand operand2) {
        operand1.operator = null;
        this.operands.add(operand1);
        operand2.operator = europeanaOperator;
        this.operands.add(operand2);
    }

    /**
     * Adds a new operand to the current one
     * 
     * @param europeanaOperator
     * @param operand 
     */
    public void addOperand(String europeanaOperator, EuropeanaOperand operand) {
        EuropeanaOperand newOperand = new EuropeanaOperand(operand);
        operand.operator = europeanaOperator;
        if (this.operands.size() > 0) {
            this.operands.add(operand);
        } else {
            this.operator = null;
            EuropeanaOperand currentOperand = new EuropeanaOperand(this);
            this.operands.add(currentOperand);
            this.operands.add(newOperand);
        }
    }

    /**
     * Used to avoid duplicate code in EuropeanaOperand constructors
     *
     * @param operandValue
     * @param forceQuotes
     */
    private void constructOperand(String operandValue, boolean forceQuotes) {
        if (forceQuotes == true) {
            StringBuilder sb = new StringBuilder();
            sb.append("\"").append(operandValue).append("\"");
            this.value = sb.toString();
        } else {
            this.value = operandValue;
        }
    }

    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return toString(false);
    }

    /**
     * Used to void some problems where the logical condition appeared before the first operand
     * @param firstOne
     * @return 
     */
    private String toString(boolean firstOne) {
        StringBuilder sb = new StringBuilder();
        if (this.operands.size() > 0) {
            sb.append("(");
            boolean first = true;
            Iterator<EuropeanaOperand> operandsIt = this.operands.iterator();
            while (operandsIt.hasNext()) {
                EuropeanaOperand operand = operandsIt.next();
                String operandsStr;
                if (first == true) {
                    operandsStr = operand.toString(first);
                    first = false;
                } else {
                    operandsStr = operand.toString();
                }
                sb.append(operandsStr);
                if (operandsIt.hasNext()) {
                    sb.append(" ");
                }
            }
            sb.append(")");
            return sb.toString();
        } else {
            if ((this.operator != null) && (firstOne == false)) {
                sb.append(this.operator).append(" ");
            }
            sb.append(this.value);
            return sb.toString();
        }
    }
}
