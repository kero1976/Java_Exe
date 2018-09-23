package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * SQLServer操作用クラス
 */
public class SQLServer {

	private String ip, dbname, username, password;

	/**
	 * 詳細版コンストラクタ。DB接続に必要な情報をすべてセット。
	 * @param ip DBのIPアドレス
	 * @param dbname DB名
	 * @param username DBのユーザ名
	 * @param password DBのパスワード
	 */
	public SQLServer(String ip, String dbname, String username, String password){
		this.ip = ip;
		this.dbname = dbname;
		this.username = username;
		this.password = password;
	}

	/**
	 * SQL文の実行
	 * @param sql SQL文
	 * @return true:成功、false:失敗(なにかしら例外発生)
	 */
	public boolean ExecuteUpdate(String sql) {
		try {
			String url = String.format("jdbc:sqlserver://%s:1433;databaseName=%s;user=%s;password=%s",
					ip, dbname, username, password);
			try (Connection con = DriverManager.getConnection(url)) {
	            try (Statement stmt = con.createStatement()) {
	                stmt.executeUpdate(sql);
	            }
	        }
	        return true;
		}catch(Exception e) {
			return false;
		}

	}
}
