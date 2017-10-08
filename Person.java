import com.google.gson.*;

public class Person{
    private String guid, yptStatus, firstName, lastName, zipCode;
    private JsonArray addresses;
    private boolean yptActive;
    private String x;
    public String source;

    public boolean getYptActive()
    {
        return yptActive;
    }
    public Person (JsonObject person, String source)
    {

        this.source = source;
        if(this.source.equals("OALM"))
        {

        }
        else
        {
            this.guid = person.getAsJsonPrimitive("personGuid").toString();
            this.yptStatus = person.getAsJsonPrimitive("yptStatus").toString();
            this.yptActive = this.yptStatus.equals("trained");
            APIConnector oalm = new APIConnector("http://proapi.cloudhub.io/api");
            JsonObject jObject = oalm.getJSON("/" + this.guid + "/profile");
            this.firstName = jObject.getAsJsonObject("profile").getAsJsonPrimitive("firstName").toString();
            this.lastName = jObject.getAsJsonObject("profile").getAsJsonPrimitive("lastName").toString();
            this.addresses = jObject.getAsJsonArray("addresses");
        }
}

    /*@Override
    public boolean equals(Object obj)  {
        try{
            Person otherPerson = (Person)obj;
            if(this.firstName.equals(otherPerson.firstName) && this.lastName.equals(otherPerson.lastName))
            {
                for(JsonElement address : this.addresses)
                {
                    this.zipCode = ((JsonObject)address).getAsJsonPrimitive("zipCode").toString();
                    for(JsonElement add : otherPerson.addresses)
                    {
                        otherPerson.zipCode = ((JsonObject)address).getAsJsonPrimitive("zipCode").toString();
                        if(this.zipCode.equals(otherPerson.zipCode))
                        {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }*/
}
