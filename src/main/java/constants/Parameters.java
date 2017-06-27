package constants;

/**
 * This class contains parameters used by the whole application.
 *
 */
public class Parameters {

	public final static String ROLE_ADMIN = "ROLE_ADMIN";
	public final static String ROLE_USER = "ROLE_USER";
	
	public final static String KEY_DATASOURCE_DRIVER = "spring.datasource.driver";
	public final static String KEY_DATASOURCE_URL = "spring.datasource.url";
	public final static String KEY_DATASOURCE_USERNAME = "spring.datasource.username"; 
	public final static String KEY_DATASOURCE_PASSWORD = "spring.datasource.password"; 
	
	public final static String KEY_DATASOURCE_DIALECT = "spring.datasource.dialect";
	public final static String KEY_DATASOURCE_SHOW_SQL = "spring.datasource.show_sql";
	public final static String KEY_DATASOURCE_AUTO_DDL = "spring.datasource.auto";

	public final static String KEY_JWT_AUTH_HEADER = "${jwt.auth.header}";
	public final static String KEY_JWT_EXPIRE_HOURS = "${jwt.expire.hours}";
	public final static String KEY_JWT_SECRET = "${jwt.secret}";
	
	public final static String KEY_SECURITY_OPEN_API_PATTERN = "${api.pattern.open}";
	public final static String KEY_SECURITY_SECURE_API_PATTERN = "${api.pattern.secure}";
	
	public final static String KEY_MODEL_PACKAGE = "model";
}
