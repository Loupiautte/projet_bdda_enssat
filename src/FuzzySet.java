

/**
 * Created by
 */
abstract public class FuzzySet {
    protected String label;
    protected int id;

    public FuzzySet(int i, String l) {
        this.id = i;
        this.label = l;

    }

    /**
     * @return
     */
    public String getLabel() {

        return this.label;
    }


    public int getId() {
        return this.id;
    }


    /**
     * @param n
     */
    public void setLabel(String n) {

        this.label = n;
    }

    /**
     * @param s
     * @return
     */
    abstract public Double getMu(String s);
}
