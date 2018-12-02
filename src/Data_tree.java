import java.util.*;

public class Data_tree {
    private Selection filter;
    private int last_filter_id;
    private Map<Vector<Integer>, Double> assoc_map;

    private ArrayList<Data_tree> children;
    Vocabulary voc;
    FlightDatasetLoader fdl = new FlightDatasetLoader("Data/testData");
    public static final int MAX_DEEP_NODE = 1;
    private int deep;

    public Data_tree(Vocabulary voc, int deep, Selection oldBase, int last_filter_id) {
        this.voc = voc;
        this.deep = deep;
        this.last_filter_id = last_filter_id;
        try {
            filter = (Selection) oldBase.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        filter.addCondition(last_filter_id, Operator.SUP, 0.0);
        children = new ArrayList<>();


    }

    public void addChild(Data_tree child) {
        children.add(child);
    }

    public void setFilter(Selection filter) {
        this.filter = filter;
    }

    public void setLast_filter_id(int last_filter_id) {
        this.last_filter_id = last_filter_id;
    }

    public void setAssoc_map(Map<Vector<Integer>, Double> assoc_map) {
        this.assoc_map = assoc_map;
    }

    public Selection getFilter() {
        return filter;
    }

    public int getLast_filter_id() {
        return last_filter_id;
    }

    public Map<Vector<Integer>, Double> getAssoc_map() {
        return assoc_map;
    }

    public ArrayList<Data_tree> getChildren() {
        return children;
    }


    public Map<Vector<Integer>, Double> extractPartOfKnowledge(Vocabulary voc, Selection baseFilter) {

        Map<Vector<Integer>, Double> map = new HashMap<>();

        Set<Integer> ids = new HashSet<>();
        for (Attribute att : voc.getAttributesList().values()) {
            for (FuzzySet fuzzySet : att.getPartition().getModalities()) {
                ids.add(fuzzySet.getId());
            }
        }


        for (Integer id1 : ids) {
            for (Integer id2 : ids) {
                Vector<Integer> vect = new Vector<>();
                vect.add(id1);
                vect.add(id2);
                map.put(vect, assoc(id1, id2, baseFilter));
            }
        }
        return map;
    }

    public Double dep(int id1, int id2, Selection baseFilter) {
        Selection Rv = new Selection();
        try {
            Rv = (Selection) baseFilter.clone();
            Rv.addCondition(id1, Operator.SUP, 0.0);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        Resume res1 = new Resume(voc);
        Resume res2 = new Resume(voc);

        buildResume(fdl, res1, baseFilter);
        buildResume(fdl, res2, Rv);
        try {
            return res1.getTrw().getMu(id2) / res2.getTrw().getMu(id2);
        } catch (Exception e){}
        return 0.0;
    }


    static void buildResume(FlightDatasetLoader fdl, Resume res, Selection select) {
        try {
            fdl.loadFile();
            for (Item i : fdl.getItems()) {
                res.addItem(i, select);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }


    public Double assoc(int id1, int id2, Selection baseFiltre) {
        Double d = this.dep(id1, id2, baseFiltre);
        if (d <= 1.0) {
            return 0.0;
        }
        return 1.0 - (1 / d);
    }


    public void extractKnowledge() {

        if (this.deep < MAX_DEEP_NODE) {
            Selection baseFilter = new Selection();
            Set<Integer> ids = new HashSet<>();
            for (Attribute att : voc.getAttributesList().values()) {
                for (FuzzySet fuzzySet : att.getPartition().getModalities()) {
                    ids.add(fuzzySet.getId());
                }
            }

            for (int id : ids) {
                assoc_map = extractPartOfKnowledge(voc, baseFilter);
                for (Vector<Integer> vectors : assoc_map.keySet()) {
                    Data_tree child = new Data_tree(voc, ++deep, baseFilter, vectors.get(0));
                    child.extractKnowledge();
                    children.add(child);
                }

            }
        }
    }

    @Override
    public String toString() {
        return "Data_tree{" +
                "filter=" + filter +
                ", last_filter_id=" + last_filter_id +
                ", assoc_map=" + assoc_map +
                ", children=" + children +
                ", deep=" + deep +
                '}';
    }
}