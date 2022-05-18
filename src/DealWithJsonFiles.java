import netscape.javascript.JSObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class DealWithJsonFiles {
    public static ArrayList<Topology> getData() {
        return data;
    }// our holder for the topology element

    public static void setData(ArrayList<Topology> data) {
        DealWithJsonFiles.data = data;
    }

    private  static ArrayList<Topology>data=new ArrayList<>();

    // this function reads the topology element and save to the topology array
    public static Topology readJsonFile(String filename)
    {
        JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader(filename)) {

            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            Topology newTopology=new Topology();
            newTopology.setId((String) jsonObject.get("id"));
            JSONArray holderForComponents= (JSONArray) jsonObject.get("components");
            ArrayList<Component>components=new ArrayList<>();
            for(int i=0;i<holderForComponents.size();i++)
            {
                JSONObject component=(JSONObject)holderForComponents.get(i);
                if(component.get("type").equals("resistor"))
                {
                    Resistor resistor=new Resistor();
                    resistor.setId((String)component.get("id"));
                    resistor.setType("resistor");
                    JSONObject holderForResistance= (JSONObject) component.get("resistance");
                    resistor.getResistance().setDefaultValue(Double.parseDouble (String.valueOf( holderForResistance.get("default"))));
                    resistor.getResistance().setMinValue(Double.parseDouble (String.valueOf( holderForResistance.get("min"))));
                    resistor.getResistance().setMaxValue(Double.parseDouble(String.valueOf( holderForResistance.get("max"))));
                    JSONObject holderForNetList= (JSONObject) component.get("netlist");
                    ArrayList<Pair>netlist=new ArrayList<Pair>();
                    for(Object it: holderForNetList.keySet())
                    {
                        String element=(String)it;
                        Pair p=new Pair();
                        p.first=element;
                        netlist.add(p);
                    }
                    int index=0;
                    for(Object it: holderForNetList.values())
                    {
                        String element=(String)it;
                       netlist.get(index).second=element;
                       index++;

                    }
                    resistor.setNetList(netlist);
                    components.add(resistor);

                }
                else {
                    Nmos nMos=new Nmos();
                    nMos.setId((String)component.get("id"));
                    nMos.setType("nmos");
                    JSONObject holderForm1= (JSONObject) component.get("m(l)");
                    nMos.getM1().setDefaultValue(Double.parseDouble (String.valueOf( holderForm1.get("default"))));
                    nMos.getM1().setMinValue(Double.parseDouble (String.valueOf( holderForm1.get("min"))));
                    nMos.getM1().setMaxValue(Double.parseDouble(String.valueOf( holderForm1.get("max"))));
                    JSONObject holderForNetList= (JSONObject) component.get("netlist");
                    ArrayList<Pair>netlist=new ArrayList<Pair>();
                    for(Object it: holderForNetList.keySet())
                    {

                        String element=(String)it;

                        Pair p=new Pair();
                        p.first=element;
                        netlist.add(p);
                    }
                    int index=0;
                    for(Object it: holderForNetList.values())
                    {
                        String element=(String)it;
                        netlist.get(index).second=element;
                        index++;

                    }

                    nMos.setNetList(netlist);
                    components.add(nMos);
                }

            }
            newTopology.setComponents(components);
            data.add(newTopology);
            return  newTopology;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }

    /* this function writes the topology element by  giving  it topology element
       and the file that it will write in it */

    public static  void writeJsonFile(Topology element, String fileName)
    {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("id",element.getId());
        JSONArray holderForComponents=new JSONArray();
        for(int i=0;i<element.getComponents().size();i++)
        {
            if (element.getComponents().get(i).getType().equals("resistor"))
            {  Resistor resistor=(Resistor)element.getComponents().get(i);
                JSONObject resistorObject=new JSONObject();
                resistorObject.put("type","resistor");
                resistorObject.put("id",resistor.getId());
                JSONObject resistanceObject=new JSONObject();
                resistanceObject.put("default",resistor.getResistance().getDefaultValue());
                resistanceObject.put("min",resistor.getResistance().getMinValue());
                resistanceObject.put("max",resistor.getResistance().getMaxValue());
                JSONObject holdNetList=new JSONObject();
                for(int j=0;j<resistor.getNetList().size();j++)
                {
                    holdNetList.put(resistor.getNetList().get(j).first,resistor.getNetList().get(j).second);
                }
                resistorObject.put("resistance",resistanceObject);
                resistorObject.put("netlist",holdNetList);
                holderForComponents.add(resistorObject);

            }
            else {
               Nmos nMos=(Nmos)element.getComponents().get(i);
                JSONObject nMosObject=new JSONObject();
                nMosObject.put("type","nmos");
                nMosObject.put("id",nMos.getId());
                JSONObject m1Object=new JSONObject();
                m1Object.put("default",nMos.getM1().getDefaultValue());
                m1Object.put("min",nMos.getM1().getMinValue());
                m1Object.put("max",nMos.getM1().getMaxValue());
                JSONObject holdNetList=new JSONObject();
                for(int j=0;j<nMos.getNetList().size();j++)
                {
                    holdNetList.put(nMos.getNetList().get(j).first,nMos.getNetList().get(j).second);

                }
                nMosObject.put("m(l)",m1Object);
                nMosObject.put("netlist",holdNetList);
                holderForComponents.add(nMosObject);
            }

        }
        jsonObject.put("components",holderForComponents);
        try (FileWriter file = new FileWriter(fileName)) {
            file.write(jsonObject.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



  static   ArrayList<Topology> queryTopologies()
    {
        return data;
    }


    //this function  deletes topology element by its id
    static void deleteTopology(String id)
   {
       for(int i=0;i<data.size();i++)
       {
           if(data.get(i).getId().equals(id))
           {
               data.remove(i);
               return;
           }
       }

   }

// this function returns the component array that the  topology element have  by its id
    static ArrayList<Component> queryDevices(String id)
    {
        for(int i=0;i<data.size();i++)
        {
            if(data.get(i).getId().equals(id))
            {
                return data.get(i).getComponents();
            }
        }
        return null;
    }


    /* this function returns the component array that the  topology element have  by its id
       but we also check if the netlist of the component has the NetlistNodeID string as a second string
       in the pair that the netlist has
    */
    static ArrayList<Component> queryDevicesWithNetlistNode(String id, String  NetlistNodeID)
    {
        Topology newTopology=new Topology();
        for(int i=0;i<data.size();i++)
        {
            if(data.get(i).getId().equals(id))
            {
                newTopology= data.get(i);
                break;
            }
        }
        ArrayList<Component>answer=new ArrayList<Component>();
        for(int i=0;i<newTopology.getComponents().size();i++)
        {
            for(int j=0;j<newTopology.getComponents().get(i).getNetList().size(); j++)
            {
                if(newTopology.getComponents().get(i).getNetList().get(j).second.equals(NetlistNodeID))
                {
                    answer.add(newTopology.getComponents().get(i));
                }
            }

        }
        return answer;
    }
}
