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
                   // System.out.println(fs.getId());
                    double oldValue = trw.getMu(fs.getId()) == null ? 0 : trw.getMu(fs.getId());
                    trw.setRW(fs.getId(), oldValue + m);

                }
            }
        }
    }

    public void addItem(Item i, Selection select) {
        String v;
        Double m = 0.0;
        Double m1 = 0.0;
        boolean ok = true;
        for (String a : this.voc.getAttributesList().keySet()) {//For each attribute
            v = i.getRawValue(this.voc.getAttributesList().get(a).getNameOfAttribute());
//            System.out.println(i.getRawValue("DepDelay"));
            if (v != null) {

                for (FuzzySet fs : this.voc.getAttributesList().get(a).getPartition().getModalities()) {  //For each FS of the partition associated to the attribute concerned
                    m = fs.getMu(v);
                   // System.out.println(m);
                  //  System.out.println(m);
                    if ( select.getValue(fs.getId()) != null && ! Operator.compare(select.getOperateur(fs.getId()), m, select.getValue(fs.getId()))) {
//                        System.out.println( " : Ajout : " + select.getValue(fs.getId()));
                        System.out.println("non ajouté " + a + " : "+ fs.getLabel() + " => " + m);
                        ok = false;
                        break;
                    }
                }


            }
        }



        if(ok) {




            for (String a : this.voc.getAttributesList().keySet()) {//For each attribute
                v = i.getRawValue(this.voc.getAttributesList().get(a).getNameOfAttribute());
//            System.out.println(i.getRawValue("DepDelay"));
                if (v != null) {

                    for (FuzzySet fs : this.voc.getAttributesList().get(a).getPartition().getModalities()) {  //For each FS of the partition associated to the attribute concerned
                        m = fs.getMu(v);
                        System.out.println(a + " : Ajouté " + fs.getLabel() + " => " + m);
                        double oldValue = trw.getMu(fs.getId()) == null ? 0 : trw.getMu(fs.getId());
                        trw.setRW(fs.getId(), oldValue + m);

                    }


                }
            }






        }
        System.out.println("");

    }

    public void niceDisplay() {
        trw.niceDisplay(voc);
    }
}
