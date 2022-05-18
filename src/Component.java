
import java.util.ArrayList;
public class Component {
    private String id;
    private String type;
    private ArrayList<Pair>netList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Pair> getNetList() {
        return netList;
    }

    public void setNetList(ArrayList<Pair> netList) {
        this.netList = netList;
    }
}
