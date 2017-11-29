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
}
