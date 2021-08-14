package example;

public class Connector {

    public void connectOracle(Credential credential){

        System.out.println("Connecting Oracle with" + credential.getUsername());
    }
    public void connectMongo(Credential credential){

        System.out.println("Connecting Mongo with" + credential.getUsername());
    }
    public void connectRedis(Credential credential){

        System.out.println("Connecting Redis with" + credential.getUsername());
    }
}
