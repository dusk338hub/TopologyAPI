import org.apiguardian.api.API;

import static org.junit.jupiter.api.Assertions.*;

/*
    Main API tests
 */
class TestProject {

    /* this testcase checks  if read an element
        from a file it will be the right data */
    @org.junit.jupiter.api.Test
    void readJSON() {
        String fileName = "src/topology.json";

        assertEquals("top1", DealWithJsonFiles.readJsonFile(fileName).getId());
    }


    /* this testcase checks if write in two different files
       the data that has been written will stay the same */
    @org.junit.jupiter.api.Test
    void writeJson() {
        String fileName = "src/topology.json";

       Topology newTopology= DealWithJsonFiles.readJsonFile(fileName);
        DealWithJsonFiles.  writeJsonFile(newTopology,"test.json");
        fileName = "test.json";
        Topology newTopology2= DealWithJsonFiles.readJsonFile(fileName);


        assertEquals(newTopology2.getId(), newTopology.getId());
    }

    /* this testcase checks if the topology element will be stored in the arrayList
       we should run this testcase independently */
    @org.junit.jupiter.api.Test
    void queryTopologies() {
        String fileName = "src/topology.json";

        Topology newTopology= DealWithJsonFiles.readJsonFile(fileName);

        assertEquals(1, DealWithJsonFiles.getData().size());
    }

    /* this testcase checks that if we delete topology element and the size of the array list will be zero
    we should run this testcase independently */
    @org.junit.jupiter.api.Test
    void deleteTopology() {
        String fileName = "src/topology.json";

        Topology newTopology= DealWithJsonFiles.readJsonFile(fileName);
      DealWithJsonFiles.deleteTopology("top1");
        assertEquals(0, DealWithJsonFiles.queryTopologies().size());
    }



// this testcase checks if try to get a data from the topology element it will be correct
    @org.junit.jupiter.api.Test
    void queryDevices() {
        String fileName = "src/topology.json";

        Topology newTopology= DealWithJsonFiles.readJsonFile(fileName);

        assertEquals(2, DealWithJsonFiles.queryDevices("top1").size());
    }


    // this testcase checks if we want to get a specific  component be a  netlist data
    @org.junit.jupiter.api.Test
    void queyDeviceWithNetlistNode() {
        String fileName = "src/topology.json";

        Topology newTopology= DealWithJsonFiles.readJsonFile(fileName);
        assertEquals(2, DealWithJsonFiles.queryDevicesWithNetlistNode("top1", "n1").size());
    }
}