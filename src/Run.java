import org.json.simple.JSONObject;

public class Run {
    public static void main(String[] args) {
        String fileName = "src/topology.json";

      Topology object= DealWithJsonFiles.readJsonFile(fileName);
      DealWithJsonFiles.  writeJsonFile(object,"test.json");
    }
}