public enum Operator {
    SUP,
    INF,
    INFEQ,
    SUPEQ,
    EQ,
    DIFF;

    public static boolean compare(Operator op, double op1, double op2) {
        if(op == Operator.SUP)
            return op1 > op2;
        if(op == Operator.INF)
            return op1 < op2;
        if(op == Operator.INFEQ)
            return op1 <= op2;
        if(op == Operator.EQ)
            return op1 == op2;
        if(op == Operator.DIFF)
            return op1 != op2;
        if(op == Operator.SUPEQ)
            return op1 >= op2;
        return false;
    }
}
