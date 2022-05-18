public class Resistor extends Component{
    private Spec resistance;

    public Resistor() {
        this.resistance = new Spec();
    }

    public Spec getResistance() {
        return resistance;
    }

    public void setResistance(Spec resistance) {
        this.resistance = resistance;
    }
}
