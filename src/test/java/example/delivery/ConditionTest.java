package example.delivery;

import declarativex.Filter;
import example.AppLog;
import example.Connector;
import example.Credential;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(MockitoExtension.class)
public class ConditionTest {



    private String whatCanBeDoneFor(int age) {

        return Filter.If(()-> (age > 60))
                .then("Can Retire")
                .elseIf(()->(age > 40))
                .then("Can Go World Tour")
                .elseIf(()->(age > 32))
                .then("Can Go for Job")
                .elseIf(()->(age > 18))
                .then("Can Go To College")
                .orElseGet("Can Play");

//
//        String result;
//
//
//        if (age > 60) {
//            result = "Can Retire";
//        } else if (age > 40) {
//            result = "Can Go World Tour";
//        } else if (age > 32) {
//            result = "Can Go for Job";
//        } else if (age > 18) {
//            result = "Can Go To College";
//        } else {
//            result = "Can Play";
//        }
//        return result;
    }



    @Spy
    Connector connector;
    Credential credential= new Credential("x","y");

    AppLog actualLog = new AppLog(ConditionTest.class);

    AppLog log = null;

    @BeforeEach
    public void before() {
        log = Mockito.spy(actualLog);
    }


    @ParameterizedTest
    @CsvSource({"65,Can Retire",
            "41,Can Go World Tour",
            "34,Can Go for Job",
            "19,Can Go To College",
            "8,Can Play"})
    public void ageandActivity(int age, String expected) {


        String result = null;


        result = whatCanBeDoneFor(age);

        assertThat(result, equalTo(expected));
    }


    @ParameterizedTest
    @CsvSource({"Oracle,1,0,0",
            "Mongo,0,1,0",
            "Redis,0,0,1",
            "unknown,0,0,0"})
    public void connectBasedOnType(String dbType,Integer expectedOracleInvocations,Integer expectedMongoInvocations,Integer expectedRedisInvocations) {



        System.out.println(dbType + "-" + expectedOracleInvocations+ "-" + expectedMongoInvocations+ "-" + expectedRedisInvocations);
        connect(dbType);

        Mockito.verify(connector,Mockito.times(expectedOracleInvocations)).connectOracle(credential);
        Mockito.verify(connector,Mockito.times(expectedMongoInvocations)).connectMongo(credential);
        Mockito.verify(connector,Mockito.times(expectedRedisInvocations)).connectRedis(credential);



    }

     void connect(String dbType) {

        if(dbType.equalsIgnoreCase("Oracle")){
            connector.connectOracle(credential);
        }else  if(dbType.equalsIgnoreCase("Mongo")){
            connector.connectMongo(credential);
        }else if(dbType.equalsIgnoreCase("Redis")){
            connector.connectRedis(credential);
         }

    }

    void connectDec(String dbType) {
        Filter.If(() -> dbType.equalsIgnoreCase("Oracle"))
                .thenDo(() -> connector.connectOracle(credential))
                .elseIf(() -> dbType.equalsIgnoreCase("Mongo"))
                .thenDo(() ->  connector.connectMongo(credential))
                .elseIf(() ->  dbType.equalsIgnoreCase("Redis"))
                .thenDo(() -> connector.connectRedis(credential))
                .execute();
    }
}
