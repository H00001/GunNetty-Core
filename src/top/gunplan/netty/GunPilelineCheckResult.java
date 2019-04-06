package top.gunplan.netty;

/**
 * @author dosdrtt
 * Thread safe
 */
public class GunPilelineCheckResult {
    public  enum CheckResult {
        /**
         * ERROR    : do not have any filters or handls
         * SAFE     : have been sat
         * WARNNING :
         * UNSAFE   :
         */
        ERROR, SAFE, WARNNING, UNSAFE
    }

    public GunPilelineCheckResult(CheckResult result) {
        this.result = result;
    }

    private CheckResult result;
    private String resaon;

    public GunPilelineCheckResult(CheckResult result, String resaon) {
        this.result = result;
        this.resaon = resaon;
    }

    public GunPilelineCheckResult() {
    }

    public CheckResult getResult() {
        return result;
    }

    public void setResult(CheckResult result) {
        this.result = result;
    }

    public String getResaon() {
        return resaon;
    }

    public void setResaon(String resaon) {
        this.resaon = resaon;
    }
}
