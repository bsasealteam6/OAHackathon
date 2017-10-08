import org.json.JSONObject;

public class Main {

    public static void main(String[] args) {
        APIConnector OALM = new APIConnector("https://hack2017.oa-bsa.org/api", "Bearer 15LhdPUSc3y2Rf-lmM9QJ5XI8PF8gxGCyR5-qTAidOpYliPXthe579IeY-2gk5L0mSoNA_hiGxp3BrJMKCIryodw1suRg6VgU8uPBZ13XAKSrOvNuOHhe85KI4jeXGsfig7Bz_FW2vyFO4_GZ4ONpXjo3GVq_GijWZsMrHcLSMz-UhxNwzcpH-_3hCFAu08oPthyU-YtdAdyRr7hqwDoEXVD3KpZ2NVoR9iZ34OFYHC1g_GusyS5eS67Hs3fIebdm3XxsklDIePOsMdvzKnV8BIvMSYQd9PU4xyh4De317zSdch7qSoMr-_Wh3cz1H-N2a981KiRcYscKIapPaX5DA");

        JSONObject oalm = OALM.getJSON("/members/3501");

        System.out.println(oalm.get(oalm.toString()));

    }

}
