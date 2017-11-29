package st.bmunit.extension;

import org.jboss.byteman.rule.helper.Helper;

public class BMUnitHelperWithoutRuleReference extends Helper{
    protected BMUnitHelperWithoutRuleReference() {
        super(null);
    }

    @Override
    public String toString() {
        return "BMUnitHelperWithoutRuleReference";
    }
}
