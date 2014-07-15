package TDDSessionsLibrary;

/**
 * Created with IntelliJ IDEA.
 * Created by nelsonni@onid.oregonstate.edu on 7/14/14.
 */
public class Phase {

    protected String type;
    protected int start;
    protected int end;

    public Phase(String phaseType, int phaseStart, int phaseEnd) {
        type = phaseType;
        start = phaseStart;
        end = phaseEnd;
    }

}
