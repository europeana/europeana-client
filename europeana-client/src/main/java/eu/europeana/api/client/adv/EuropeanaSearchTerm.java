package eu.europeana.api.client.adv;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * This class defines a search term.
 * Search terms are defined in Europeana API by the parameter "searchTerms="
 * The simplest search term contains a field and a simple operand, ex:
 *      [...]?searchTerms=what: picasso[...]
 *          -what: is the field
 *          -picasso: is the value (a non complex operand)
 * The more complex search terms can be composed of 2 or more search terms:
 *      [...]?searchTerms=what: ((picasso AND pablo) OR dune) OR what: picasso[...]
 * In the above example, there are two search terms with "OR" logical operator between them:
 *      -what: ((picasso AND pablo) OR dune): the complex search term
 *      -what: picasso: the simple search term
 * 
 * This class can be used to compose search terms and create logical operators
 * between multiple search terms.
 * 
 * @author Cosmin Coman
 */
public class EuropeanaSearchTerm extends EuropeanaOperand {
    
    private String field = null;
    private String operator = null;
    private ArrayList<EuropeanaSearchTerm> searchTerms = new ArrayList<EuropeanaSearchTerm>();
    
    /**
     * Constructs a simple EuropeanaSearchTerm
     * 
     * @param europeanaField
     * @param operand 
     */
    public EuropeanaSearchTerm(String europeanaField, EuropeanaOperand operand) {
        super(operand);
        //given the fact that is the only operator, it should not be involved in complex operations, so, should be null
        super.operator = null;
        this.field = europeanaField;
    }
    
    /**
     * Search term to search for in europeanaField
     * 
     * @param europeanaField
     * @param searchTerm 
     */
    public EuropeanaSearchTerm(String europeanaField, String searchTerm) {
        super(searchTerm);
        //EuropeanaOperand op = new EuropeanaOperand(searchterm);
        //super(operand);
        //given the fact that is the only operator, it should not be involved in complex operations, so, should be null
        super.operator = null;
        this.field = europeanaField;
    }


    /**
     * @param searchTerm 
     */
    public EuropeanaSearchTerm(EuropeanaSearchTerm searchTerm) {
        super(searchTerm);
        this.field = searchTerm.field;
        this.operator = searchTerm.operator;
//        if (searchTerm.searchTerms.size()>0) {
            Iterator<EuropeanaSearchTerm> searchTermsIt = searchTerm.searchTerms.iterator();
            while (searchTermsIt.hasNext()) {
                EuropeanaSearchTerm st = searchTermsIt.next();
                EuropeanaSearchTerm newSt = new EuropeanaSearchTerm(st);
                this.searchTerms.add(newSt);
            }
//        } else {
//            //this eliminates the cases where a condition appears before the first search term
//            this.operator = null;
//        }
    }
    
    /**
     * Concatenates another search term to the current one, transforming the current
     * one in a complex SerachTerm (one that is composed of multiple search terms
     * separated by logical conditions)
     * @param europeanaOperator
     * @param searchTerm 
     */
    public void addSearchTerm(String europeanaOperator, EuropeanaSearchTerm searchTerm) {
        EuropeanaSearchTerm newSearchTerm = new EuropeanaSearchTerm(searchTerm);
        newSearchTerm.operator = europeanaOperator;
        if (this.searchTerms.size()>0) {
            this.searchTerms.add(newSearchTerm);
        } else {
            this.operator = null;
            EuropeanaSearchTerm currentSearchTerm = new EuropeanaSearchTerm(this);
            this.searchTerms.add(currentSearchTerm);
            this.searchTerms.add(newSearchTerm);
        }
    }
    
    /**
     * 
     * @return EuropeanaSearchTerm as String
     */
    @Override
    public String toString() {
        return toString(false);
    }
    
    /**
     * Used to void some problems where the logical condition appeared before the first term
     * 
     * @param firstOne
     * @return 
     */
    private String toString(boolean firstOne) {
        StringBuilder sb = new StringBuilder();
        if (this.searchTerms.size()>0) {
            //in this case, the field will be used as a logical operator because the term is complex
            boolean first = true;
            Iterator<EuropeanaSearchTerm> searchTermsIt = this.searchTerms.iterator();
            while (searchTermsIt.hasNext()) {
                EuropeanaSearchTerm searchTerm = searchTermsIt.next();
                String searchTermStr;
                if (first==true) {
                    searchTermStr = searchTerm.toString(first);
                    first=false;
                } else {
                    searchTermStr = searchTerm.toString();
                }
                sb.append(searchTermStr);
                if (searchTermsIt.hasNext()) {
                    sb.append(" ");
                }
            }
            return sb.toString();
        } else {
            if ((this.operator!=null) && (firstOne==false)) {
                sb.append(this.operator).append(" ");
            }
            String operandStr = super.toString();
            sb.append(this.field).append(": ").append(operandStr);
            return sb.toString();
        }
    }

}
