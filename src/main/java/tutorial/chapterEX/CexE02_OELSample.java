/**********************************************************
 * PDFファイルの編集出力サンプル
 *   com.itextpdf.layout.element.Table 使用Ver
 **********************************************************/
package tutorial.chapterEX;

import java.io.File;
import java.io.IOException;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.lowagie.text.pdf.BaseFont;

/**
 * Simple table renderer example.
 */
// @WrapToTest
public class CexE02_OELSample {

	// クラス定義
	public static final String DEST = "results/chapterEx/OEL_Sample.pdf";    // 出力ファイル

	public static void main(String args[]) throws IOException {

		// 出力ディレクトリ作成
		File file = new File(DEST);
		file.getParentFile().mkdirs();

		// PDF生成
		new CexE02_OELSample().createPdf(DEST);
	}


	/*
	 * PDF生成
	 */
	public void createPdf(String dest) throws IOException {

		// PDFのインスタンス生成
		PdfWriter writer = new PdfWriter(dest);
		PdfDocument pdf = new PdfDocument(writer);
		PageSize ps = PageSize.A4;                                         // ページサイズ：A4縦
		Document document = new Document(pdf, ps);
System.out.println("A4サイズ：" + PageSize.A4.getHeight() + ":" + PageSize.A4.getWidth());
System.out.println("documentサイズ：" + document.getPageEffectiveArea(ps).getHeight() + ":" + document.getPageEffectiveArea(ps).getWidth());
		document.setMargins(30, 30, 30, 30); // 余白を設定
System.out.println("documentサイズ：" + document.getPageEffectiveArea(ps).getHeight() + ":" + document.getPageEffectiveArea(ps).getWidth());

		// フォント定義
		// 見出し用
		PdfFont fontTitle = PdfFontFactory.createFont("C:/Windows/Fonts/meiryo.ttc,0", BaseFont.IDENTITY_H);  //メイリオ（UniCode）
		// 本文用
		PdfFont fontText = PdfFontFactory.createFont("C:/Windows/Fonts/msgothic.ttc,0", BaseFont.IDENTITY_H);  //ゴシック（UniCode）
//		PdfFont fontTitle0 = PdfFontFactory.createFont("C:/Windows/Fonts/meiryo.ttc,0", BaseFont.);
//		PdfFont fontTitle2 = PdfFontFactory.createFont("C:/Windows/Fonts/meiryo.ttc,0", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

		// 一番外側のテーブル（外枠）定義
		Table outerTable = new Table(new float[] { 1 });               // 列数は1
//		outerTable.setWidthPercent(100)                                // 幅はページ幅一杯
		outerTable.setWidth(520)                                       // 表幅固定
		          .setTextAlignment(TextAlignment.LEFT)                // 表内のテキストは左寄せ
				  .setHorizontalAlignment(HorizontalAlignment.CENTER); // 表はページの中央寄せ
System.out.println("OuterTableサイズ[01]：" + outerTable.getHeight() + ":" + outerTable.getWidth());

		// 見出し文字列を宣言
		final String headerKihonJouhou = "基本情報";
		final String headerGaisetsu = "概説";
		final String headerDokuseiShiken = "毒性試験の結果";
		final String headerYakubutsu = "薬物動態";

		// *********************************
		// 基本情報エリア
		// *********************************
		// 見出し編集
		Cell cell = new Cell()
				.setFont(fontTitle).setBold().setBackgroundColor(Color.DARK_GRAY).setFontColor(Color.WHITE); // 書式設定
		cell.add(headerKihonJouhou);                             // 文字列追加
		outerTable.addCell(cell);                                // セルをテーブルに追加
System.out.println("OuterTableサイズ[02]：" + outerTable.getHeight() + ":" + outerTable.getWidth());
System.out.println("Cellサイズ[01]：" + cell.getHeight() + ":" + cell.getWidth());

		// 内容編集
		// Table kihonTable = new Table(new float[]{1.5f,0.5f,8f});             // 自動レイアウト（列幅推奨値を割合で指定）
		Table kihonTable = new Table(new float[] { 1.5f, 0.5f, 8f }, true);     // 固定レイアウト（列幅を割合で指定）
		kihonTable.setWidthPercent(100);                                        // テーブル幅調整

		// 表内の値設定
		editKihonTable(kihonTable, "品目");
		editKihonTable(kihonTable, "：");
		editKihonTable(kihonTable, "イミダフェナシン");
		editKihonTable(kihonTable, "OEL-TWA");
		editKihonTable(kihonTable, "：");
		editKihonTable(kihonTable, "3.21 micrograms/m3");
		editKihonTable(kihonTable, "カテゴリ");
		editKihonTable(kihonTable, "：");
		editKihonTable(kihonTable, "1");
		editKihonTable(kihonTable, "OEL数式");
		editKihonTable(kihonTable, "：");

		String imgPath = "src/main/resources/img/数式.png";
		Image imgformula = new Image(ImageDataFactory.create(imgPath));
		imgformula.setWidthPercent(100);                                        // サイズを列幅に合わせる
		kihonTable.addCell(new Cell().setBorder(null).add(imgformula));
System.out.println("KihonTableサイズ[01]：" + kihonTable.getHeight() + ":" + kihonTable.getWidth());
		// kihonTable.setBorder(Border.NO_BORDER);                  // TABLEに対するBorder指定は効かない模様

		cell = new Cell();
		cell.setPaddings(10, 10, 10, 10); // 余白設定
		cell.setMinHeight(150); // セルの高さ設定（最低保証値）

		outerTable.addCell(cell.add(kihonTable)); // 基本情報テーブルをセルに設定してセルを外枠テーブルに追加

		// *********************************
		// 概説エリア
		// *********************************
		// 見出し
		cell = new Cell().setFont(fontTitle).setBold().setBackgroundColor(Color.DARK_GRAY).setFontColor(Color.WHITE); // 書式設定
		cell.add(headerGaisetsu); // 文字列追加
		outerTable.addCell(cell); // セルをテーブルに追加

		// 内容
		cell = new Cell().setFont(fontText); // 書式設定
		cell.setPaddings(10, 10, 10, 10); // 余白設定
		cell.setMinHeight(120); // セルの高さ設定（最低保証値）
		cell.add("OEL以下でも薬効に関連する症状が発現する可能性がある。"); // 文字列追加
		cell.add("また、ヒトでの代表的な副作用の口渇はラットで評価できているか分からない。"); // 文字列追加
		cell.add("薬効に関連する作用を許容するかどうかでOEL値が異なる。"); // 文字列追加

		outerTable.addCell(cell); // セルをテーブルに追加

		// *********************************
		// 毒性試験の結果エリア
		// *********************************
		// 見出し
		cell = new Cell().setFont(fontTitle).setBold().setBackgroundColor(Color.DARK_GRAY).setFontColor(Color.WHITE); // 書式設定
		cell.add(headerDokuseiShiken); // 文字列追加
		outerTable.addCell(cell); // セルをテーブルに追加

		// 内容
		Table dokuseiTable = new Table(new float[] { 6f, 4f }); // 自動レイアウト（列幅推奨値を割合で指定）
		dokuseiTable.setWidthPercent(100); // 幅を可能なだけ全て使う

		// 表内の値設定
		Cell dokuseiCell = new Cell().setFont(fontText); // 書式設定
		dokuseiCell.add("LOAEL");
		dokuseiTable.addCell(dokuseiCell);
		dokuseiCell = new Cell().setFont(fontText);
		dokuseiCell.add("1000 µg/kg/day");
		dokuseiTable.addCell(dokuseiCell);
		dokuseiCell = new Cell().setFont(fontText);
		dokuseiCell.add("試験期間による不確実係数");
		dokuseiTable.addCell(dokuseiCell);
		dokuseiCell = new Cell().setFont(fontText);
		dokuseiCell.add("1（12ヶ月）");
		dokuseiTable.addCell(dokuseiCell);
		dokuseiCell = new Cell().setFont(fontText);
		dokuseiCell.add("動物種による不確実係数（Ufa）");
		dokuseiTable.addCell(dokuseiCell);
		dokuseiCell = new Cell().setFont(fontText);
		dokuseiCell.add("5（イヌ）");
		dokuseiTable.addCell(dokuseiCell);

		cell = new Cell();
		cell.setPaddings(10, 10, 10, 10); // 余白設定
		cell.setMinHeight(120); // セルの高さ設定（最低保証値）
		outerTable.addCell(cell.add(dokuseiTable)); // テーブルをセルに設定してセルをテーブルに追加

		// *********************************
		// 薬物動態エリア
		// *********************************
		// 見出し
		cell = new Cell().setFont(fontTitle).setBold().setBackgroundColor(Color.DARK_GRAY).setFontColor(Color.WHITE); // 書式設定
		cell.add(headerYakubutsu); // 文字列追加
		outerTable.addCell(cell); // セルをテーブルに追加

		// 内容
		Table yakubutsuTable = new Table(new float[] { 6f, 4f }); // 自動レイアウト（列幅推奨値を割合で指定）
		yakubutsuTable.setWidthPercent(100); // 幅を可能なだけ全て使う

		// 表内の値設定
		Cell yakubutsuCell = new Cell().setFont(fontText); // 書式設定
		yakubutsuCell.add("LOAEL");
		yakubutsuTable.addCell(yakubutsuCell);
		yakubutsuCell = new Cell().setFont(fontText);
		yakubutsuCell.add("500 µg/kg/day");
		yakubutsuTable.addCell(yakubutsuCell);
		yakubutsuCell = new Cell().setFont(fontText);
		yakubutsuCell.add("試験期間による不確実係数");
		yakubutsuTable.addCell(yakubutsuCell);
		yakubutsuCell = new Cell().setFont(fontText);
		yakubutsuCell.add("1（12ヶ月）");
		yakubutsuTable.addCell(yakubutsuCell);
		yakubutsuCell = new Cell().setFont(fontText);
		yakubutsuCell.add("動物種による不確実係数（Ufa）");
		yakubutsuTable.addCell(yakubutsuCell);
		yakubutsuCell = new Cell().setFont(fontText);
		yakubutsuCell.add("5（イヌ）");
		yakubutsuTable.addCell(yakubutsuCell);

		cell = new Cell();
		cell.setPaddings(10, 10, 10, 10); // 余白設定
		cell.setMinHeight(150); // セルの高さ設定（最低保証値）
		outerTable.addCell(cell.add(yakubutsuTable)); // テーブルをセルに設定してセルをテーブルに追加

		//// BufferedReader br = new BufferedReader(new FileReader(DATA));
		// String line = br.readLine();
		// process(table, line, bold, true);
		// while ((line = br.readLine()) != null) {
		// process(table, line, font, false);
		// }
		// br.close();

		document.add(outerTable);
		System.out.println("OuterTableサイズ[99]：" + outerTable.getHeight() + ":" + outerTable.getWidth());
		System.out.println("ドキュメントサイズ：" + document.getHeight() + ":" + document.getWidth());

		// Close document
		document.close();

	}


	private void editTable(Table tbl, String txt, boolean isBorder) throws IOException {

		// フォント定義
		PdfFont fontText = PdfFontFactory.createFont("C:/Windows/Fonts/msgothic.ttc,0", BaseFont.IDENTITY_H);  //ゴシック（UniCode）

		// セル定義
		Cell cell = new Cell().setFont(fontText);
		if (!isBorder) {
			cell.setBorder(null);          // 書式設定
		}

		cell.add(txt);
		tbl.addCell(cell);

	}

	private void editKihonTable(Table tbl, String txt) throws IOException {

		//
		editTable(tbl, txt, false);

	}




}