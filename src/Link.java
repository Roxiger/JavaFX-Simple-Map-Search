
public class Link {

    public Node relatedNode;
    public int roadType;
    public double pathLenght;
    public double price;

    public Link(Node n, double l, int roadType){
        this.relatedNode = n;
        this.pathLenght = l;
        this.roadType = roadType;
        this.price = 0;
    }

    public void setPrice(double price){
        this.price = price;
    }
}
