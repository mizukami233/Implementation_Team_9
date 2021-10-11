package Model;

public class Reason {
    private int reasonId;
    private String type;
    private String des;

    public Reason(int reasonId, String type, String des) {
        this.reasonId = reasonId;
        this.type = type;
        this.des = des;
    }

    public int getReasonId() {
        return reasonId;
    }

    public void setReasonId(int reasonId) {
        this.reasonId = reasonId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
