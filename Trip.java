import java.util.GregorianCalendar;

class Trip{

    private String ID, routeID, headsign, blockID;
    private Service serviceType;

    public Trip(String ID, Service service, String route, String headsign,
                String block){
        this.ID           = ID;
        this.routeID      = route;
        this.serviceType  = service;
        this.headsign     = headsign;
        this.blockID      = block;
    }

    public boolean hasService(GregorianCalendar date){
        return serviceType.hasService(date);
    }

    public String toString(){
        return routeID + " " + headsign;
    }

}    
