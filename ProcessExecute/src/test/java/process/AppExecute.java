package process;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * アプリケーション実行用クラス
 */
public class AppExecute{

	private String exeFilePath;
	private File directory;
	private short timeOutSec;

	/**
	 * 簡易版コンストラクタ
	 * 作業フォルダ指定なし、タイムアウトは60秒。
	 * @param filePath 実行ファイルのファイルパス名
	 */
	public AppExecute(String filePath) {
		this(filePath, null, (short)60);
	}

	/**
	 * 詳細版コンストラクタ
	 * @param filePath 実行ファイルのファイルパス名
	 * @param directory 作業ディレクトリ
	 * @param timeOutSec タイムアウト(秒)
	 */
	public AppExecute(String filePath, File directory, short timeOutSec) {
		this.exeFilePath = filePath;
		this.directory = directory;
		this.timeOutSec = timeOutSec;
	}

	/**
	 * 実行処理
	 * コンストラクタで指定した実行ファイルを、引数の起動パラメータを指定して起動する。
	 * 起動に失敗した場合(何かしらの例外が発生した場合)は-1を返す。
	 * 起動したが、タイムアウト時間が経過したらプロセスを強制終了し、-1を返す。
	 * @param param 起動時パラメータ
	 * @return リターンコード
	 */
	public int execute(String... param) {
		try {
			List<String> paramList = new ArrayList<String>();
			paramList.add(exeFilePath);
			Collections.addAll(paramList,param);
			ProcessBuilder pb = new ProcessBuilder(paramList);
			pb.directory(directory);
			pb.inheritIO(); // サブプロセスの標準入出力をJavaプロセスに統合
			Process p = pb.start();
			if(p.waitFor(timeOutSec, TimeUnit.SECONDS)) {
				return p.exitValue();
			}
			p.destroy();
			fail(String.format("タイムアウトエラー：%d(秒)以内に処理が終了しなかった",timeOutSec));
		} catch (Exception e) {}
		return -1;
	}
}
