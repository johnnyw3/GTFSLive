class Trip{

    private String ID, routeID, serviceID, headsign, blockID;

    public Trip(String ID, String service, String route, String headsign,
                String block){
        this.ID        = ID;
        this.routeID   = route;
        this.serviceID = service;
        this.headsign  = headsign;
        this.blockID   = block;
    }

    public String toString(){
        return routeID + " " + headsign;
    }
}    
