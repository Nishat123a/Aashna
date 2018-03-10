package aashna.com.aashna.S_ContributeToNGO;


public class C_ngo_pojo {

    public C_ngo_pojo(String ngo_name, String account) {
        this.ngo_name = ngo_name;
        this.account = account;
    }

    String ngo_name;

    public String getNgo_name() {
        return ngo_name;
    }

    public void setNgo_name(String ngo_name) {
        this.ngo_name = ngo_name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    String account;
}
