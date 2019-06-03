package top.gunplan.netty;

/**
 * @author dosdrtt
 * Thread safe
 */
public class GunPipelineCheckResult {
    private String reason;

    public GunPipelineCheckResult(CheckResult result) {
        this.result = result;
    }

    private CheckResult result;

    public GunPipelineCheckResult(CheckResult result, String reason) {

        this.result = result;
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public GunPipelineCheckResult() {
    }

    public CheckResult getResult() {
        return result;
    }

    public void setResult(CheckResult result) {
        this.result = result;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public enum CheckResult {
        /**
         * ERROR    : do not have any filter or handls
         * SAFE     : have been sat
         * WARNING  : you should notice it
         * UNSAFE   : it is unsafe
         */
        ERROR, SAFE, WARNING, UNSAFE
    }
}
