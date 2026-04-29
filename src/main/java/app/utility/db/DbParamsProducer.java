package app.utility.db;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

@ApplicationScoped
public class DbParamsProducer {

    @Named("dbParamHost")
    @Produces
    private String dbParamHost = "localhost";

    @Named("dbParamPort")
    @Produces
    private int dbParamPort = 5432;   // PostgreSQL default port

    @Named("dbParamName")
    @Produces
    private String dbParamName = "loan_db";

    @Named("dbParamUser")
    @Produces
    private String dbParamUser = "postgres";

    @Named("dbParamPwd")
    @Produces
    private String dbParamPwd = "newpassword";
}
