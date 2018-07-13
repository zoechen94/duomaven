package com.util.spring.utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.springframework.util.ResourceUtils;

import java.io.*;

/**
 * Created by 陈亚兰 on 2018/4/16.
 */
public class CustomXWPFDocument extends XWPFDocument{
    public CustomXWPFDocument(InputStream in) throws IOException {
        super(in);
    }

    /**
     *
     */
    public CustomXWPFDocument() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @param pkg
     * @throws IOException
     */
    public CustomXWPFDocument(OPCPackage pkg) throws IOException {
        super(pkg);
        // TODO Auto-generated constructor stub
    }  // picAttch 图片后面追加的字符串 可以是空格
    public void createPicture(XWPFParagraph paragraph,int id, int width, int height,String blipId) throws FileNotFoundException, InvalidFormatException {
        final int EMU = 9525;
        width *= EMU;
        height *= EMU;
        if (paragraph == null) {
            paragraph = createParagraph();
        }
        CTInline inline = paragraph.createRun().getCTR().addNewDrawing()
                .addNewInline();
        String picXml=readToString("classpath:picxml.xml");
        // CTGraphicalObjectData graphicData =
        // inline.addNewGraphic().addNewGraphicData();
        XmlToken xmlToken = null;
        try {
            xmlToken = XmlToken.Factory.parse(picXml);
        } catch (XmlException xe) {
            xe.printStackTrace();
        }
        inline.set(xmlToken);
        // graphicData.set(xmlToken);
        inline.setDistT(0);
        inline.setDistB(0);
        inline.setDistL(0);
        inline.setDistR(0);
        CTPositiveSize2D extent = inline.addNewExtent();
        extent.setCx(width);
        extent.setCy(height);
        CTNonVisualDrawingProps docPr = inline.addNewDocPr();
        docPr.setId(id);
        docPr.setName("docx_img_ " + id);
        docPr.setDescr("docx Picture");
    }

    public String readToString(String fileName) throws FileNotFoundException {
        String encoding = "UTF-8";
        File file = ResourceUtils.getFile(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }
}
