package am.dx.varsityspecials.www.varsityspecials;

/**
 * Created by dylanmatthews on 2017/08/11.
 */

public class blog {

    private String title,desc,image;

    public blog(String titl,String des, String im) {

        title= titl;
        desc = des;
        image = im;

    }

    public blog(String location, String des) {
        title = location;
        desc = des;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
