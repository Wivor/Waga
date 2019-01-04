import java.sql.*
import java.util.Properties

object Connect {
    internal var username = "root" // provide the username
    internal var password = "Mudz!n1337" // provide the corresponding password
    internal var conn: Connection? = null

    fun getConnection(username : String, password : String) : Connection? {
        val connectionProps = Properties()
        connectionProps.put("user", username)
        connectionProps.put("password", password)
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance()
            conn = DriverManager.getConnection(
                "jdbc:" + "mysql" + "://" +
                        "35.228.137.161" + "/" +
                        "inwentarz",
                connectionProps)
        } catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
        } catch (ex: Exception) {
            // handle any errors
            ex.printStackTrace()
        }
        return conn
    }
}