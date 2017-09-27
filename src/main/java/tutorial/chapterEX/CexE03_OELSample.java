/**********************************************************
 * PDFファイルの結合サンプル
 *   com.itextpdf.layout.element.Table 使用Ver
 **********************************************************/
package tutorial.chapterEX;

import java.io.File;
import java.io.IOException;

import com.lowagie.text.pdf.PdfDocument;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Simple table renderer example.
 */
// @WrapToTest
public class CexE03_OELSample {

	// public static final String DATA =
	// "src/main/resources/data/premier_league.csv";
    public static final String SRC1 = "src/main/resources/pdf/sample01.pdf";
    public static final String SRC2 = "src/main/resources/pdf/sample02.pdf";
    public static final String DEST = "results/chapterEx/OEL_Sample02.pdf";

	public static void main(String args[]) throws IOException {
		File file = new File(DEST);
		file.getParentFile().mkdirs();
		new CexE03_OELSample().createPdf(DEST);
	}

	public void createPdf(String dest) throws IOException {

		// PDFのインスタンス生成（out）
        PdfDocument pdf = new PdfDocument(new PdfWriter(dest));
        PdfMerger merger = new PdfMerger(pdf);

		// PDFのインスタンス生成（in1）
        PdfDocument firstSourcePdf = new PdfDocument(new PdfReader(SRC1));

		// 一つ目のPDFを出力
        merger.merge(firstSourcePdf, 1, firstSourcePdf.getNumberOfPages());

		// PDFのインスタンス生成（in2）
        PdfDocument secondSourcePdf = new PdfDocument(new PdfReader(SRC2));

		// 二つ目のPDFを出力
        merger.merge(secondSourcePdf, 1, secondSourcePdf.getNumberOfPages());

		// 各インスタンスを閉じる
        firstSourcePdf.close();
        secondSourcePdf.close();
        pdf.close();

	}
}