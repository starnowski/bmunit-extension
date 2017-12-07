package st.bmunit.extension;

public class BMUnitUtils {

    public static boolean createJoin(Object key, int max)
    {
        return new BMUnitHelperWithoutRuleReference().createJoin(key, max);
    }

    public static boolean joinEnlist(Object key) {
        return new BMUnitHelperWithoutRuleReference().joinEnlist(key);
    }

    public static boolean joinWait(Object key, int count) {
        return new BMUnitHelperWithoutRuleReference().joinWait(key, count);
    }

    public static boolean joinWait(Object key, int count, long millis) {
        return new BMUnitHelperWithoutRuleReference().joinWait(key, count, millis);
    }

    public static boolean createCounter(Object o){
        return new BMUnitHelperWithoutRuleReference().createCounter(o);
    }

    public static boolean createCounter(Object o, int count){
        return new BMUnitHelperWithoutRuleReference().createCounter(o, count);
    }

    public static boolean deleteCounter(Object o){
        return new BMUnitHelperWithoutRuleReference().deleteCounter(o);
    }

    public static int incrementCounter(Object o, int amount){
        return new BMUnitHelperWithoutRuleReference().incrementCounter(o, amount);
    }

    public static int incrementCounter(Object o){
        return new BMUnitHelperWithoutRuleReference().incrementCounter(o);
    }

    public static int decrementCounter(Object o){
        return new BMUnitHelperWithoutRuleReference().decrementCounter(o);
    }

    public static int readCounter(Object o){
        return new BMUnitHelperWithoutRuleReference().readCounter(o);
    }

    public static int readCounter(Object o, boolean zero){
        return new BMUnitHelperWithoutRuleReference().readCounter(o, zero);
    }

    public static boolean createRendezvous(Object identifier, int expected) {
        return new BMUnitHelperWithoutRuleReference().createRendezvous(identifier, expected);
    }

    public static int rendezvous(Object identifier)
    {
        return new BMUnitHelperWithoutRuleReference().rendezvous(identifier);
    }
}
