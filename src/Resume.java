public class Resume extends Rewriter {


    private RewritingVector trw;

    public Resume(Vocabulary v) {
        super(v);
        trw = new RewritingVector();
    }

    public void addItem(Item i) {
        String v;
        Double m = 0.0;
        for (String a : this.voc.getAttributesList().keySet()) {//For each attribute
            v = i.getRawValue(this.voc.getAttributesList().get(a).getNameOfAttribute());
            if (v != null) {
                for (FuzzySet fs : this.voc.getAttributesList().get(a).getPartition().getModalities()) {  //For each FS of the partition associated to the attribute concerned
                    m = fs.getMu(v);
                    System.out.println(fs.getId());
                    double oldValue = trw.getMu(fs.getId()) == null ? 0 : trw.getMu(fs.getId());
                    trw.setRW(fs.getId(), oldValue + m);

                }
            }
        }
    }

    public void addItem(Item i, Selection select) {
        String v;
        Double m = 0.0;
        for (String a : this.voc.getAttributesList().keySet()) {//For each attribute
            v = i.getRawValue(this.voc.getAttributesList().get(a).getNameOfAttribute());
            if (v != null) {
                boolean ok = true;
                for (FuzzySet fs : this.voc.getAttributesList().get(a).getPartition().getModalities()) {  //For each FS of the partition associated to the attribute concerned
                    m = fs.getMu(v);
                    if ( ! Operator.compare(select.getOperateur(fs.getId()), m, select.getValue(fs.getId()))) {
                        ok = false;
                        break;
                    }

                }

                if(ok) {
                    for (FuzzySet fs : this.voc.getAttributesList().get(a).getPartition().getModalities()) {  //For each FS of the partition associated to the attribute concerned
                        m = fs.getMu(v);
                        double oldValue = trw.getMu(fs.getId()) == null ? 0 : trw.getMu(fs.getId());
                        trw.setRW(fs.getId(), oldValue + m);

                    }
                }
            }
        }
    }

    public void niceDisplay() {
        trw.niceDisplay(voc);
    }
}
