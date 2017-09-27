/**********************************************************
 * PDFファイルの結合サンプル
 *   flying-saucer(com.lowagie.text) 使用Ver
 **********************************************************/
package tutorial.chapterEX;

import java.io.File;
import java.io.FileOutputStream;

import com.lowagie.text.pdf.PdfCopyFields;
import com.lowagie.text.pdf.PdfReader;

/**
 * Simple table renderer example.
 */
// @WrapToTest
public class CexE05_OELSample {

	// ファイルパス
	public static final String SRC1 = "src/main/resources/pdf/sample01.pdf";     // 結合元ファイル１
    public static final String SRC2 = "src/main/resources/pdf/sample02.pdf";     // 結合元ファイル２
    public static final String DEST = "results/chapterEx/OEL_Sample02.pdf";      // 出力先ファイル

	public static void main(String args[])  {
		File file = new File(DEST);
		file.getParentFile().mkdirs();
		try {
			new CexE05_OELSample().createPdf(DEST);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	public void createPdf(String dest) throws Exception {

		// PDFのインスタンス生成（out）
        PdfCopyFields copy = new PdfCopyFields(new FileOutputStream(dest));

		// 一つ目のPDFを出力
        copy.addDocument(new PdfReader(SRC1));

		// 二つ目のPDFを出力
        copy.addDocument(new PdfReader(SRC2));

		// 出力ファイルのインスタンスを閉じる
        copy.close();

	}
}