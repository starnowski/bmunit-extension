# bmunit-extension

Adding support of junit4 test rule to bmunit framework.

TODO:
- Adding static methods which invokes methods of class org.jboss.byteman.rule.helper.Helper which operate on static collections, like counters, lock etc.
    1. st.bmunit.extension.utils.BMUnitUtils#joinEnlist(java.lang.Object)
    3. st.bmunit.extension.utils.BMUnitUtils#createCounter(Object)
    4. st.bmunit.extension.utils.BMUnitUtils#deleteCounter(Object)
    5. st.bmunit.extension.utils.BMUnitUtils#incrementCounter(Object)
    6. st.bmunit.extension.utils.BMUnitUtils#incrementCounter(Object o, int amount)
    7. st.bmunit.extension.utils.BMUnitUtils#decrementCounter(Object o)
    8. st.bmunit.extension.utils.BMUnitUtils#readCounter(Object, boolean)
    
- Adding SpringBoot application demo which use BMUnitRule in integration tests
