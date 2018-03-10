package aashna.com.aashna.aashna_main;

public class Tips {

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    String desc;
    String Title_Tip;


    public Tips(String title_Tip, String title_desc) {
        Title_Tip = title_Tip;
        desc = title_desc;
    }

    public String getTitle_Tip() {
        return Title_Tip;
    }

    public void setTitle_Tip(String title_Tip) {
        Title_Tip = title_Tip;
    }
}
