/**********************************************************
 * PDFファイルの編集出力サンプル
 *   com.lowagie.text.pdf.PdfPTable 使用Ver
 *   ※ com.itextpdf.layout.element.Table では表の高さの取得方法が見つからなかったため
 **********************************************************/
package tutorial.chapterEX;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Simple table renderer example.
 */
// @WrapToTest
public class CexE04_PdfPTableSample {

	// クラス定義
	public static final String DEST = "results/chapterEx/OEL_PdfPTableSample.pdf";    // 出力ファイル

	public static void main(String args[]) throws IOException {

		// 出力ディレクトリ作成
		File file = new File(DEST);
		file.getParentFile().mkdirs();

		// PDF生成
		try {
			new CexE04_PdfPTableSample().createPdf(DEST);
		} catch (Exception e) {
			//
			e.printStackTrace();
		}
	}


	/*
	 * PDF生成
	 */
	public void createPdf(String dest) throws Exception {

		// PDFのインスタンス生成
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, new FileOutputStream(dest));

System.out.println("A4サイズ：" + PageSize.A4.getHeight() + ":" + PageSize.A4.getWidth());

		// フッター（ページ番号）
		Font fontFooter = createTextFont();
		HeaderFooter footer = new HeaderFooter(new Phrase("-- ", fontFooter), new Phrase(" --", fontFooter));   // ページ番号のみ
//		int pageCount = document.getPageNumber();
//		HeaderFooter footer = new HeaderFooter(new Phrase("Page ", fontFooter), new Phrase(" of " + pageCount, fontFooter));
		footer.setAlignment(Element.ALIGN_CENTER);              // 中央寄せ
		footer.setBorder(Rectangle.NO_BORDER);                  // 罫線なし
		document.setFooter(footer);

		document.setMargins(30, 30, 50, 20);                    // 余白を設定
		// documentへの設定はopen前までに行わないと無効

		document.open();

System.out.println("documentサイズ：" + (document.getPageSize().getHeight() - document.topMargin() - document.bottomMargin())  + ":"
                + (document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin()));

		// 一番外側のテーブル（外枠）定義
		PdfPTable outerTable = createOuterTable();

System.out.println("OuterTableサイズ[01]：" + outerTable.getTotalHeight() + ":" + outerTable.getTotalWidth());

		// 見出し文字列を宣言
		final String HeaderKihonJouhou = "基本情報";
		final String HeaderGaisetsu = "概説";
		final String HeaderDokuseiShiken = "毒性試験の結果";
		final String HeaderYakubutsu = "薬物動態";

		// *********************************
		// 基本情報エリアの編集
		// *********************************
		// 見出し編集
		outerTable.addCell(editHeader(HeaderKihonJouhou));

System.out.println("OuterTableサイズ[02]：" + outerTable.getTotalHeight() + ":" + outerTable.getTotalWidth());

		// 内容編集
		PdfPTable kihonTable = new PdfPTable(new float[] { 1.5f, 0.5f, 8f });      // 固定レイアウト（列幅を割合で指定）
		kihonTable.setWidthPercentage(100);                                        // テーブル幅調整（外枠の列幅一杯）

		// 表内の値設定
		String[] kihonContent = {"品目", "：", "イミダフェナシン",
				                 "OEL-TWA", "：", "3.21 micrograms/m3",
				                 "カテゴリ", "：", "1",
				                 "OEL数式", "："};
		// 基本情報テーブルの編集
		editContent(kihonTable, kihonContent, false);

		// 画像だけ別途編集
		String imgPath = "src/main/resources/img/数式.png";        // 画像ファイルのパス
		Image imgFormula = Image.getInstance(imgPath);             // 画像を生成
		imgFormula.setWidthPercentage(100);                        // 画像のサイズを列幅に合わせる
		PdfPCell kihonCell = new PdfPCell();
		kihonCell.addElement(imgFormula);                          // セルに画像追加
		kihonCell.setBorder(Rectangle.NO_BORDER);                  // 罫線なし
		kihonTable.addCell(kihonCell);                             // セルを基本情報テーブルに追加

		// 基本情報テーブルを外枠テーブルに追加
		PdfPCell outerCell = new PdfPCell();              // 基本情報テーブルを格納するセルを生成
		outerCell.setPadding(10);                         // 余白設定
		outerCell.setMinimumHeight(150);                  // セルの高さ設定（最低保証値）
		outerCell.addElement(kihonTable);                 // 基本情報テーブルをセルに追加
		outerTable.addCell(outerCell);                    // 外枠テーブルにセルを追加

System.out.println("OuterTableサイズ[03]：" + outerTable.getTotalHeight() + ":" + outerTable.getTotalWidth());

		// *********************************
		// 概説エリアの編集
		// *********************************
		// 見出し編集
		PdfPCell gaisetsuHeaderCell = editHeader(HeaderGaisetsu);

		// 内容編集
		outerCell = new PdfPCell();                       // 新規セルを生成
		Font fontText = createTextFont();                 // 本文用フォント

		// 外枠テーブルのセルに直接編集
		outerCell.setPadding(10);                         // 余白設定
		outerCell.setMinimumHeight(120);                  // セルの高さ設定（最低保証値）
		// 本文編集
		outerCell.addElement(new Phrase("OEL以下でも薬効に関連する症状が発現する可能性がある。", fontText));
		outerCell.addElement(new Phrase("また、ヒトでの代表的な副作用の口渇はラットで評価できているか分からない。", fontText));
		outerCell.addElement(new Phrase("薬効に関連する作用を許容するかどうかでOEL値が異なる。", fontText));

		// 必要なら改ページ
		if (doNewPage(document, outerTable, gaisetsuHeaderCell, outerCell)) {
			// 改ページがあった場合のみ外枠テーブルを初期化
			outerTable = createOuterTable();
		}

		// 外枠テーブルに追加
		outerTable.addCell(gaisetsuHeaderCell);                      // 見出し
		outerTable.addCell(outerCell);                               // 内容

System.out.println("OuterTableサイズ[04]：" + outerTable.getTotalHeight() + ":" + outerTable.getTotalWidth());


		// *********************************
		// 毒性試験結果エリアの編集
		// *********************************
		// 見出し編集
		PdfPCell dokuseiHeaderCell = editHeader(HeaderDokuseiShiken);

		// 内容編集
		PdfPTable dokuseiTable = new PdfPTable(new float[] { 6f, 4f });   // 固定レイアウト（列幅を割合で指定）
		dokuseiTable.setWidthPercentage(100);                             // テーブル幅調整（外枠の列幅一杯）

		// 表内の値設定
		String[] dokuseiContent = {"LOAEL", "1000 µg/kg/day",
				                   "試験期間による不確実係数", "1（12ヶ月）",
				                   "動物種による不確実係数（Ufa）", "5（イヌ）"};

		// 毒性試験テーブルの編集
		editContent(dokuseiTable, dokuseiContent, true);

		// 毒性試験テーブルを外枠テーブルに追加
		outerCell = new PdfPCell();                           // 新規セルを生成
		outerCell.setPadding(10);                             // 余白設定
		outerCell.setMinimumHeight(120);                      // セルの高さ設定（最低保証値）
		outerCell.addElement(dokuseiTable);

		// 必要なら改ページ
		if (doNewPage(document, outerTable, dokuseiHeaderCell, outerCell)) {
			// 改ページがあった場合のみ外枠テーブルを初期化
			outerTable = createOuterTable();
		}

		// 外枠テーブルに追加
		outerTable.addCell(dokuseiHeaderCell);                   // 見出し
		outerTable.addCell(outerCell);                           // 内容

System.out.println("OuterTableサイズ[05]：" + outerTable.getTotalHeight() + ":" + outerTable.getTotalWidth());

		// *********************************
		// 薬物動態エリアの編集
		// *********************************
		// 見出し編集
		PdfPCell yakubutsuHeaderCell = editHeader(HeaderYakubutsu);

		// 内容編集
		PdfPTable yakubutsuTable = new PdfPTable(new float[] { 6f, 4f }); // 固定レイアウト（列幅を割合で指定）
		yakubutsuTable.setWidthPercentage(100);                           // テーブル幅調整（外枠の列幅一杯）


		// 表内の値設定
		String[] yakubutsuContent = {"LOAEL", "500 µg/kg/day",
				                     "試験期間による不確実係数", "1（12ヶ月）",
				                     "動物種による不確実係数（Ufa）", "5（イヌ）"};

// 改ページ確認用
//		String[] yakubutsuContent = {
//		"LOAEL", "1000 µg/kg/day",
//        "試験期間による不確実係数", "1（12ヶ月）",
//        "動物種による不確実係数（Ufa）", "5（イヌ）",
//		"LOAEL", "2000 µg/kg/day",
//        "試験期間による不確実係数", "1（12ヶ月）",
//        "動物種による不確実係数（Ufa）", "5（イヌ）",
//		"LOAEL", "2000 µg/kg/day",
//        "試験期間による不確実係数", "1（12ヶ月）",
//        "動物種による不確実係数（Ufa）", "5（イヌ）",
//		"LOAEL", "2000 µg/kg/day",
//        "試験期間による不確実係数", "1（12ヶ月）",
//        "動物種による不確実係数（Ufa）", "5（イヌ）",
//		"LOAEL", "2000 µg/kg/day",
//        "試験期間による不確実係数", "1（12ヶ月）",
//        "動物種による不確実係数（Ufa）", "5（イヌ）",
//		"LOAEL", "3000 µg/kg/day",
//        "試験期間による不確実係数", "1（12ヶ月）",
//        "動物種による不確実係数（Ufa）", "5（イヌ）"
//        };

		// 薬物動態テーブルの編集
		editContent(yakubutsuTable, yakubutsuContent, true);

		// 薬物動態テーブルを外枠テーブルに追加
		outerCell = new PdfPCell();                               // 新規セルを生成
		outerCell.setPadding(10);                                 // 余白設定
		outerCell.setMinimumHeight(150);                          // セルの高さ設定（最低保証値）
		outerCell.addElement(yakubutsuTable);

		// 必要なら改ページ
		if (doNewPage(document, outerTable, yakubutsuHeaderCell, outerCell)) {
			// 改ページがあった場合のみ外枠テーブルを初期化
			outerTable = createOuterTable();
		}

		// 外枠テーブルに追加
		outerTable.addCell(yakubutsuHeaderCell);                  // 見出し
		outerTable.addCell(outerCell);                            // 内容

		// 外枠テーブルをPDFへ印刷
		document.add(outerTable);
System.out.println("OuterTableサイズ[06]：" + outerTable.getTotalHeight() + ":" + outerTable.getTotalWidth());


		// PDFファイルを閉じる
		document.close();

	}

	/**
	 * 見出し用フォント生成
	 * @return 見出し用フォント
	 * @throws Exception
	 */
	private Font createTitleFont() throws Exception {
		Font font = new Font(BaseFont.createFont("C:/Windows/Fonts/meiryo.ttc,0",
				             BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED),12, Font.BOLD);       // メイリオ太字
		font.setColor(Color.WHITE);                                                           // 文字色
		// 戻り値返却
		return font;
	}

	/**
	 * 本文用フォント生成
	 * @return 本文用フォント
	 * @throws Exception
	 */
	private Font createTextFont() throws Exception {
		Font font =new Font(BaseFont.createFont("C:/Windows/Fonts/msgothic.ttc,0",
				            BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED),12);                   // MSゴシック
		// 戻り値返却
		return font;
	}

	/**
	 * 外枠テーブル生成（レポートのレイアウトを整えるためのテーブル）
	 * @return 外枠テーブル
	 */
	private PdfPTable createOuterTable() {
		PdfPTable outerTable = new PdfPTable(new float[] { 1 });           // 列数：1
//		outerTable.setWidthPercentage(100);                                // 幅はページ幅一杯
		outerTable.setTotalWidth(520);                                     // 表幅を固定（Table高さ取得のため必須）
		outerTable.setLockedWidth(true);                                   // Document.add時にテーブル幅を変えない

		// 戻り値返却
		return outerTable;
	}

	/**
	 * 見出し編集
	 * @param txt 見出し文字列
	 * @return 見出し用セル
	 * @throws Exception
	 */
	private PdfPCell editHeader(String txt) throws Exception {

		// フォント定義
		Font fontTitle = createTitleFont();                           // 見出し用フォント

		Phrase ph = new Phrase(txt, fontTitle);
		PdfPCell cell = new PdfPCell(ph);                             // 書式設定
		cell.setBackgroundColor(Color.DARK_GRAY);                     // 背景色
		cell.setFixedHeight(24);                                      // 高さ固定
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);              // 高さ中寄せ

		// 戻り値返却
		return cell;
	}

	/**
	 * 内容編集
	 * @param tbl 編集先テーブル
	 * @param content 設定する文字列配列
	 * @param isBorder 罫線の有無（true:有,false:無）
	 * @throws Exception
	 */
	private void editContent(PdfPTable tbl, String[] content, boolean isBorder) throws Exception {

		// フォント定義
		Font fontText = createTextFont();                                 // 本文用フォント

		for (int i = 0; i < content.length; i++) {

			Phrase ph = new Phrase(content[i], fontText);
			PdfPCell cell = new PdfPCell(ph);                             // 書式設定
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);              // 高さ中寄せ

			if (!isBorder) {
				cell.setBorder(Rectangle.NO_BORDER);                      // 罫線なし
			}

			tbl.addCell(cell);
		}
	}

	/**
	 * 改ページ（要否判断含む）
	 * @param document 改ページ判断対象のドキュメント
	 * @param tbl ドキュメント内で編集中のテーブル
	 * @param header テーブルに追加する見出しセル
	 * @param content テーブルに追加する内容セル
	 * @return 改ページ有無（true:改ページ有、false:改ページ無）
	 * @throws Exception
	 */
	private boolean doNewPage(Document document, PdfPTable tbl, PdfPCell header, PdfPCell content) throws Exception {

		PdfPTable tmpTbl = new PdfPTable(tbl);       // 一時テーブル生成

		float heightBuff = 44f;                      // 高さ上限微調整用
		float limitHeight = document.getPageSize().getHeight() - document.topMargin() - document.bottomMargin() - heightBuff;

		// テーブル高さ計算のため、一時テーブルに見出しと内容を一旦追加する
		tmpTbl.addCell(header);
		tmpTbl.addCell(content);

		// 追加後のテーブル高さで改ページ要否を判断する
		if (tmpTbl.getTotalHeight() > limitHeight) {
			// テーブルをPDFへ印刷
			document.add(tbl);
			// 改ページ
			document.newPage();

			// 戻り値返却（改ページあり）
			return true;

		} else {
			// 戻り値返却（改ページなし）
			return false;
		}
	}
}