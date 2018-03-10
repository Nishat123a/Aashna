package aashna.com.aashna.S_SelfSafety;

/**
 * Created by srisai1 on 10/5/2016.
 */
public class Map {
    private String RowId;
    private String Name;
    private String Number;




    public Map(String mRowId, String mName, String mNumber) {

        RowId = mRowId;
        Name = mName;
        Number = mNumber;


    }
//Getters for latitude,longitude,address and time
    public String getRowId(){return RowId;};
    public String getName() {
        return Name;
    }

    public String getNumber() {
        return Number;
    }

    }




