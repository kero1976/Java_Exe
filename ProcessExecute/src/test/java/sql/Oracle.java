package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Oracle操作用クラス
 */
public class Oracle {

	private String ip, dbname, username, password;

	/**
	 * 詳細版コンストラクタ。DB接続に必要な情報をすべてセット。
	 * @param ip DBのIPアドレス
	 * @param dbname DB名
	 * @param username DBのユーザ名
	 * @param password DBのパスワード
	 */
	public Oracle(String ip, String dbname, String username, String password){
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
			Class.forName("oracle.jdbc.OracleDriver");
			String url = String.format("jdbc:oracle:thin:@%s:1521/%s", ip, dbname);

	        try (Connection con = DriverManager.getConnection(url, username, password)) {
	            try (Statement stmt = con.createStatement()) {
	                stmt.executeUpdate(sql);
	            }
	        }catch(Exception e) {
	        	System.out.println(e);
	        }
	        return true;
		}catch(Exception e) {
			return false;
		}
	}
}
