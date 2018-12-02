import java.util.Hashtable;


class RewritingVector {

    protected Hashtable<Integer, Double> vectorI;

    public RewritingVector() {
        vectorI = new Hashtable<Integer, Double>();
    }


    public Hashtable<Integer, Double> getVector() {
        return this.vectorI;
    }

    public void setRW(Integer termid, Double mu) {
        this.vectorI.put(termid, mu);
    }

    public void updateVector(Integer termid, Double mu) {
        if (this.vectorI.containsKey(termid))
            this.vectorI.put(termid, mu + this.vectorI.get(termid));
        else
            this.vectorI.put(termid, mu);

    }

    public void normalize(int i) {
        for (Integer a : this.vectorI.keySet()) {
            this.vectorI.put(a, this.vectorI.get(a) / i);
        }
    }

    public Double getMu(Integer id) {
        return this.vectorI.get(id);
    }

    public String toString() {
        String r = "{";
        for (Integer a : this.vectorI.keySet()) {
            r += a + "=>" + this.vectorI.get(a) + ";";
        }
        return r + "}";
    }

    public void niceDisplay(Vocabulary v) {
        String r = "{";
        FuzzySet s;

        for (Integer a : this.vectorI.keySet()) {
            s = v.getFSById(a);
            r += s.getLabel() + "=>" + this.vectorI.get(a) + ";";
        }
        System.out.println(r);
    }
    
}


class ItemBinaryRewritingVector {

    protected char[] vectorI;

    public ItemBinaryRewritingVector(int size) {
        vectorI = new char[size];
    }

    /**
     * @return
     */
    public char[] getVector() {
        return this.vectorI;
    }

    /**
     * @param termid
     * @param mu
     * @param alpha
     */
    public void updateVector(Integer termid, Double mu, double alpha) {
        if ((alpha == 0.0 && mu > 0.0) || (alpha > 0.0 && mu >= alpha))
            this.vectorI[termid] = '1';
        else
            this.vectorI[termid] = '0';
    }

    /**
     * @param id
     * @return
     */
    public char getMu(Integer id) {
        return this.vectorI[id];
    }

    /**
     * @return
     */
    public String toString() {

        return new String(vectorI);
    }

}

public class Rewriter {

    protected Vocabulary voc;

    protected Rewriter(){
        this.voc = null;
    }

    public Rewriter(Vocabulary v) {
        this.voc = v;
    }


    //Rewrite and item and returns its rewriting vector
    public RewritingVector rewriteItem(Item i) {
        RewritingVector trw = new RewritingVector();
        String v;
        Double m = 0.0;
        for (String a : this.voc.getAttributesList().keySet()) {//For each attribute
            v = i.getRawValue(this.voc.getAttributesList().get(a).getNameOfAttribute());
            if (v != null) {
                for (FuzzySet fs : this.voc.getAttributesList().get(a).getPartition().getModalities()){  //For each FS of the partition associated to the attribute concerned
                    m = fs.getMu(v);
                    trw.setRW(fs.getId(), m);
                }
            }
        }
        return trw;
    }

}