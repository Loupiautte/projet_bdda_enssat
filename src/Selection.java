import java.util.Hashtable;

public class Selection {
    protected Hashtable<Integer, Double> values;
    protected Hashtable<Integer, Operator> operators;


    public Selection() {
        values = new Hashtable<Integer, Double>();
        operators = new Hashtable<Integer, Operator>();
    }

    public void addCondition(Integer id, Operator op, Double val) {
        values.put(id, val);
        operators.put(id, op);
    }

    public void removeCondition(Integer id) {
        values.remove(id);
        operators.remove(id);
    }

    public void reset() {
        values.clear();
        operators.clear();
    }

    public Double getValue(Integer id) {
        return values.get(id);
    }

    public Operator getOperateur(Integer id) {
        return operators.get(id);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Selection selec = new Selection();
        selec.values = (Hashtable<Integer, Double>)values.clone();
        selec.operators = (Hashtable<Integer, Operator>)operators.clone();
        return selec;
    }

    @Override
    public String toString() {
        return "Selection{" +
                "values=" + values +
                ", operators=" + operators +
                '}';
    }
}
