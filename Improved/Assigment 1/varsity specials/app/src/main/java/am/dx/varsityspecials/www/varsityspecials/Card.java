package am.dx.varsityspecials.www.varsityspecials;

/**
 * Created by 15009851 on 2017-07-18.
 */

public class Card {
    private String line1;
    private String line2;
    private String line3;
    private  String image;





    public Card(String name, String des, String time) { //sets data
        line1 = name;
        line2 = des;
        line3 = time;
    }
    public Card(String line1) {
        this.line1 = line1;
    } //sets data

    public Card(String location, String des, String img, int n) { //sets data
        line1 = location;
        line2 = des;
        image = img;


    }

    public String getImage() {
        return image;
    } //returns iamge name

    public String getLine1() {
        return line1;
    } //returns line 1

    public String getLine2() {
        return line2;
    } //returns line 2

    public String getLine3(){return line3;} //returns line 3

}