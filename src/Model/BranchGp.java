package Model;
//BGid,GpId,BranchId,WorkingTime

public class BranchGp {
    private int bgId;
    private int gpId;
    private int branchId;
    private String workingTime;
    private int workingBegin;
    private int workingEnd;


    public BranchGp(){

    }

    public int getWorkingBegin() {
        return workingBegin;
    }

    public void setWorkingBegin(int workingBegin) {
        this.workingBegin = workingBegin;
    }

    public int getWorkingEnd() {
        return workingEnd;
    }

    public void setWorkingEnd(int workingEnd) {
        this.workingEnd = workingEnd;
    }

    public BranchGp(int bgId, int gpId, int branchId, String workingTime, int workingBegin, int workingEnd) {
        this.bgId = bgId;
        this.gpId = gpId;
        this.branchId = branchId;
        this.workingTime = workingTime;
        this.workingBegin = workingBegin;
        this.workingEnd = workingEnd;
    }

    public int getBgId() {
        return bgId;
    }

    public void setBgId(int bgId) {
        this.bgId = bgId;
    }

    public int getGpId() {
        return gpId;
    }

    public void setGpId(int gpId) {
        this.gpId = gpId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(String workingTime) {
        this.workingTime = workingTime;
    }
}


