package com.sinosafe.xb.manager.utils.fileupload;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.kincai.libjpeg.ImageUtils;
import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.api.APIManager;
import com.sinosafe.xb.manager.bean.FileBean;
import com.sinosafe.xb.manager.utils.Constant;
import com.yzj.trans.XmlConfige;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import Decoder.BASE64Encoder;
import luo.library.base.utils.MyLog;

/**
 * 影像上传
 * Created by john lee on 2017/5/22.
 */

public class FileUploadPresenter2 {

    private String msgURL = APIManager.FILE_UPLOAD_URL + "Interface_Cluster/IImgXmlUpLoadSyn";//上传报文
    private String fileURL = APIManager.FILE_UPLOAD_URL + "TransServer2.0/ISerFIleUp";//上传图片文件
    private String sysCode = null;
    private String batchCode = null;
    private String scanTime = null;

    private String retFileId;
    private FileUploadCallback fileUploadCallback;
    private List<FileBean> fileLists = new ArrayList<>();
    private List<String> fileIds = new ArrayList<>();

    //保存压缩后的路径
    private List<String> compressPaths = new ArrayList<>();

    private List<String> locList;
    private List<String> desList;
    private List<String> photosDes;
    private String prdCode;
    private String serno;

    public FileUploadPresenter2(FileUploadCallback fileUploadCallback) {
        this.fileUploadCallback = fileUploadCallback;
    }

    /**
     * 上传影像系统
     *
     * @param locList 本地文件
     * @param desList 文件描述
     * @param prdCode 功能编号
     * @param serno   业务流水号（唯一）
     */
    public void fileUpLoad(List<String> locList, List<String> desList, String prdCode, String serno,List<String> photosDes) {
        fileLists.clear();
        fileIds.clear();

        compressPaths.clear();
        this.locList = locList;
        this.desList = desList;
        this.photosDes = photosDes;
        this.prdCode = prdCode;
        this.serno = serno;

        //压缩图片
        new ImageCompressAsyncTask().execute();
        /*
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date uploadDate = new Date();
        String uploadTime = sdf.format(uploadDate);
        String separator = File.separator;

        Map<String, String> param = new HashMap<>();
        param.put("BatchFlag", "0");// 是否批扫  0：非批扫，1：批扫   默认0
        param.put("BusinessCode", BaseMainActivity.loginUserBean.getActorno());// TODO 身份证上传所需
        param.put("SysCode", Constant.SYSCODE);// 系统编号
        param.put("FunCode", prdCode);// 功能编号
        param.put("OrgCode", BaseMainActivity.loginUserBean.getOrgid());// 机构编号---登录用户的组织机构号
        param.put("OperCode",  BaseMainActivity.loginUserBean.getActorname()+"");// 操作人---固定
        param.put("FlwCode", serno);// 业务流水号（唯一）-----登录用户的工号，贷款用流水号
        param.put("ScanTime", uploadTime);// 扫描日期，注意时间格式
        param.put("MainDocument", "M1001");
        param.put("AttachedDocument", "A1001");
        param.put("BranchCode", "");//分支机构号
        param.put("WdCode", "");//网点号
        param.put("ModelCode", "");//理赔系统必传

        FileBean fileBean = null;
        for (int i = 0; i < locList.size(); i++) {
            fileBean = new FileBean();
            //文件路径
            String filePath = locList.get(i);
            //原文件名
            String oldFileName = locList.get(i).substring(locList.get(i).lastIndexOf(separator) + 1,
                    locList.get(i).length());

            //同步到影像系统的新文件名
            String newFileName = UUID.randomUUID().toString().replace("-", "").toUpperCase() +
                    oldFileName.substring(oldFileName.lastIndexOf("."));

            //文件类型
            String fileType = desList.get(i);

            fileBean.setFileId(newFileName); // 这个是有UUID32生成加上【.文件类型】,这个在你数据表上应该保存下来
            fileBean.setFileName(oldFileName); // 原文件名称
            fileBean.setFilePath(filePath);// 文件路径
            fileBean.setFileType(fileType); // 这里需要换成红色选择框里面的值GFX_00101/GFX_00201/GFX_00301
            fileBean.setMainFlag("M");
            fileBean.setSideFlag("F");
            fileLists.add(fileBean);
        }
        cleanRetFileId();
        String xml = null;
        try {
            xml = buildXml(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(xml))
            uploadReqMsg(xml);
        new MyAsyncTask().execute();*/
    }


    /**
     * 异步压缩图片
     */
    private class ImageCompressAsyncTask extends AsyncTask<String,Integer,String>{
        @Override
        protected String doInBackground(String... params) {
            try {
                for (int i = 0; i < locList.size(); ++i) {
                    String locFile = locList.get(i);
                    String compressPath = Constant.getImageFile(photosDes.get(i)).getPath();
                    ImageUtils.compressBitmap(locFile,compressPath);
                    compressPaths.add(compressPath);

                    MyLog.e("恭喜~~~~~~~~~图片压缩成功-----------------");
                }
            } catch (Exception e) {
                MyLog.e("图片压缩失败-----------------"+e.getMessage());
            }
            return "";
        }
        @Override
        protected void onPostExecute(String Str) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date uploadDate = new Date();
            String uploadTime = sdf.format(uploadDate);
            String separator = File.separator;

            Map<String, String> param = new HashMap<>();
            param.put("BatchFlag", "0");// 是否批扫  0：非批扫，1：批扫   默认0
            param.put("BusinessCode", BaseMainActivity.loginUserBean.getActorno());// TODO 身份证上传所需
            param.put("SysCode", Constant.SYSCODE);// 系统编号
            param.put("FunCode", prdCode);// 功能编号
            param.put("OrgCode", BaseMainActivity.loginUserBean.getOrgid());// 机构编号---登录用户的组织机构号
            param.put("OperCode",  BaseMainActivity.loginUserBean.getActorname()+"");// 操作人---固定
            param.put("FlwCode", serno);// 业务流水号（唯一）-----登录用户的工号，贷款用流水号
            param.put("ScanTime", uploadTime);// 扫描日期，注意时间格式
            param.put("MainDocument", "M1001");
            param.put("AttachedDocument", "A1001");
            param.put("BranchCode", "");//分支机构号
            param.put("WdCode", "");//网点号
            param.put("ModelCode", "");//理赔系统必传

            FileBean fileBean = null;
            for (int i = 0; i < compressPaths.size(); i++) {
                fileBean = new FileBean();
                //文件路径
                String filePath = compressPaths.get(i);
                //原文件名
                String oldFileName = compressPaths.get(i).substring(compressPaths.get(i).lastIndexOf(separator) + 1,compressPaths.get(i).length());

                //同步到影像系统的新文件名
                String newFileName  = UUID.randomUUID().toString().replace("-", "").toUpperCase() +
                        oldFileName.substring(oldFileName.lastIndexOf("."));

                //文件类型
                String fileType = desList.get(i);

                fileBean.setFileId(newFileName); // 这个是有UUID32生成加上【.文件类型】,这个在你数据表上应该保存下来
                fileBean.setFileName(oldFileName); // 原文件名称
                fileBean.setFilePath(filePath);// 文件路径
                fileBean.setFileType(fileType); // 这里需要换成红色选择框里面的值GFX_00101/GFX_00201/GFX_00301
                fileBean.setMainFlag("M");
                fileBean.setSideFlag("F");
                fileLists.add(fileBean);
            }
            cleanRetFileId();
            String xml = null;
            try {
                xml = buildXml(param);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!TextUtils.isEmpty(xml))
                uploadReqMsg(xml);
            new MyAsyncTask().execute();
        }
    }

    /**
     * 异步获取base64的图片
     */
    private class MyAsyncTask extends AsyncTask<String, Integer, List<String>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<String> doInBackground(String... params) {
            List<String> encodeFiles = new ArrayList<>();
            try {
                for (int i = 0; i < fileLists.size(); ++i) {
                    FileBean fileBean = fileLists.get(i);
                    File file;
                    if (!isEmpty(fileBean.getFilePath())) {
                        file = new File(fileBean.getFilePath());
                    } else {
                        file = fileBean.getImgFile();
                    }
                    Object in = new FileInputStream(file);
                    BASE64Encoder encoder = new BASE64Encoder();
                    byte[] bytes = new byte[((InputStream) in).available()];
                    ((InputStream) in).read(bytes);
                    String fileStr = encoder.encode(bytes);
                    encodeFiles.add(fileStr);
                    ((InputStream) in).close();
                }
                //上传
                fileUpload(encodeFiles);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return encodeFiles;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List<String> result) {
            super.onPostExecute(result);
            for(int i=0;i<compressPaths.size();i++){
                new File(compressPaths.get(i)).delete();
            }
            if(result==null){
                fileUploadCallback.uploadFail();
            }else{
                fileUploadCallback.uploadSuccess();
            }
        }
    }



    /**
     * 文件上传
     *
     * @param encodeFiles 编码后的图片
     */
    private void fileUpload(List<String> encodeFiles) throws TransformerException, IOException, HttpException {
        MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
        HttpClient fileClient = new HttpClient(connectionManager);
        fileClient.getParams().setParameter("http.socket.timeout", new Integer(30000));
        fileClient.getParams().setParameter("http.protocol.content-charset", "UTF-8");
        for (int i = 0; i < fileLists.size(); ++i) {
            FileBean fileBean = fileLists.get(i);
            fileIds.add(fileBean.getFileId());
            PostMethod filePost = new PostMethod(this.fileURL);
            NameValuePair methodPair = new NameValuePair("method", "trans_res");
            NameValuePair sysCodePair = new NameValuePair("syscode", this.sysCode); //系统编号
            NameValuePair batchCodePair = new NameValuePair("batchcode", this.batchCode);
            NameValuePair folderNamePair = new NameValuePair("foldername", this.scanTime);//上传时间
            NameValuePair fileNamePair = new NameValuePair("filename", fileBean.getFileName());//原文件名
            NameValuePair filePair = new NameValuePair("file", encodeFiles.get(i));
            String finish = i == fileLists.size() - 1 ? "1" : "0";
            NameValuePair isFinishPair = new NameValuePair("isfinish", finish);
            filePost.addParameter(methodPair);
            filePost.addParameter(sysCodePair);
            filePost.addParameter(batchCodePair);
            filePost.addParameter(folderNamePair);
            filePost.addParameter(fileNamePair);
            filePost.addParameter(filePair);
            filePost.addParameter(isFinishPair);
            int fileResult = fileClient.executeMethod(filePost);
            if (fileResult == 200) {
               MyLog.e("upladFiles 上传成功 fileName:" + fileBean.getFileId());
            }
            filePost.releaseConnection();
        }
        fileClient.getHttpConnectionManager().closeIdleConnections(0L);
        MyLog.e("upladFiles 上传完成 batchCode:" + this.batchCode);
        this.sysCode = null;
        this.batchCode = null;
        this.scanTime = null;
    }

    /**
     * xml 上传
     *
     * @param xml
     */
    public void uploadReqMsg(String xml) {
        MyLog.e("xml=========="+xml);
        new Thread(new UpLoadThread(xml)).start();
    }

    class UpLoadThread implements Runnable{
        private String xml;
        public UpLoadThread(String xml) {
            this.xml = xml;
        }
        @Override
        public void run() {
            try {
                HttpClient client = new HttpClient();
                client.getParams().setParameter("http.socket.timeout", new Integer(10000));
                client.getParams().setParameter("http.protocol.content-charset", "UTF-8");
                PostMethod post = new PostMethod(msgURL);
                post.setParameter("Connection", "keep-Alive");
                post.setRequestEntity(new StringRequestEntity(xml, "text/plain", "utf-8"));
                int result = client.executeMethod(post);
                if (result == 200) {
                    MyLog.e("uploadReqMsg 请求成功");
                }
                post.releaseConnection();
                client.getHttpConnectionManager().closeIdleConnections(0L);
                MyLog.e("uploadReqMsg 报文上传成功");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private void setRetFileId(String fileId) {
        if (retFileId != null && !"".equals(retFileId)) {
            retFileId = retFileId + "," + fileId;
        } else {
            retFileId = fileId;
        }
    }

    private void cleanRetFileId() {
        retFileId = null;
    }

    private String buildXml(Map<String, String> param) throws ParserConfigurationException, TransformerConfigurationException{
        String batchFlag = (String) param.get("BatchFlag");
        String sysCode = (String) param.get("SysCode");
        this.sysCode = sysCode;
        String funCode = (String) param.get("FunCode");
        String orgCode = (String) param.get("OrgCode");
        String operCode = (String) param.get("OperCode");
        String flwCode = (String) param.get("FlwCode");
        String scanTime = (String) param.get("ScanTime");
        this.scanTime = scanTime;
        String startTime = com.yzj.util.DateUtil.dateToString((Date) null, (String) null);
        String mainDocument = (String) param.get("MainDocument");
        String attachedDocument = (String) param.get("AttachedDocument");
        String branchCode = (String) param.get("BranchCode");
        String wdCode = (String) param.get("WdCode");

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dbf.newDocumentBuilder();
        Document document = builder.newDocument();
        document.setXmlVersion("1.0");
        document.setXmlStandalone(false);
        Element messageEl = document.createElement("message");
        messageEl.setAttribute("code", "trans_res2");
        Element messageTypeEl = document.createElement("message_type");
        messageTypeEl.setTextContent("1");
        messageEl.appendChild(messageTypeEl);
        Element sysHeadEl = document.createElement("sys_head");
        Element sysCodeEl = document.createElement("sys_code");
        sysCodeEl.setTextContent(sysCode);
        sysHeadEl.appendChild(sysCodeEl);
        Element orgCodeEl = document.createElement("org_code");
        orgCodeEl.setTextContent(orgCode);
        sysHeadEl.appendChild(orgCodeEl);
        Element branchCodeEl = document.createElement("branchbk_code");
        branchCodeEl.setTextContent(branchCode);
        sysHeadEl.appendChild(branchCodeEl);
        Element wdCodeEl = document.createElement("wd_code");
        wdCodeEl.setTextContent(wdCode);
        sysHeadEl.appendChild(wdCodeEl);
        Element startTimeEl = document.createElement("star_time");
        startTimeEl.setTextContent(startTime);
        sysHeadEl.appendChild(startTimeEl);
        Element batchFlagEl = document.createElement("batch_flg");
        batchFlagEl.setTextContent(batchFlag);
        sysHeadEl.appendChild(batchFlagEl);
        Element scanTimeEl = document.createElement("scan_time");
        scanTimeEl.setTextContent(scanTime);
        sysHeadEl.appendChild(scanTimeEl);
        messageEl.appendChild(sysHeadEl);
        Element funHeadEl = document.createElement("fun_head");
        Element funCodeEl = document.createElement("fun_code");
        funCodeEl.setTextContent(funCode);
        funHeadEl.appendChild(funCodeEl);
        Element flwCodeEl = document.createElement("flw_code");
        flwCodeEl.setTextContent(flwCode);
        funHeadEl.appendChild(flwCodeEl);
        Element batchCodeEl = document.createElement("batch_code");
        String batchCode = com.yzj.util.DateUtil.dateToString((Date) null, "yyyyMMddHHmmssFFF") + "00" + System.currentTimeMillis();
        this.batchCode = batchCode;
        batchCodeEl.setTextContent(batchCode);
        funHeadEl.appendChild(batchCodeEl);
        funHeadEl.appendChild(startTimeEl);
        Element operCodeEl = document.createElement("launcher");
        operCodeEl.setTextContent(operCode);
        funHeadEl.appendChild(operCodeEl);
        Element mainDocumenteEl = document.createElement("property");
        mainDocumenteEl.setAttribute("code", "mainDocument");
        mainDocumenteEl.setTextContent(mainDocument);
        sysHeadEl.appendChild(mainDocumenteEl);
        Element attachedDocumenteEl = document.createElement("property");
        attachedDocumenteEl.setAttribute("code", "attachedDocument");
        attachedDocumenteEl.setTextContent(attachedDocument);
        sysHeadEl.appendChild(attachedDocumenteEl);

        for (int imgTreeEl = 0; imgTreeEl < fileLists.size(); ++imgTreeEl) {
            FileBean tranStaEl = (FileBean) fileLists.get(imgTreeEl);
            String fileId = tranStaEl.getFileId();
            String fileName = tranStaEl.getFileName();
            String fileType = tranStaEl.getFileType();
            String mainFlag = tranStaEl.getMainFlag();
            String sideflag = tranStaEl.getSideFlag();
            String nameTemp = "";
            String imageIdTemp = "";
            if (fileId != null && !"".equals(fileId) && fileId.length() > 32) {
                imageIdTemp = fileId.substring(0, 32);
                nameTemp = fileId;
            } else {
                String imageEl = fileName.split("\\.")[1];
                String uUid = com.yzj.util.UUID32.getUUID();
                imageIdTemp = uUid;
                nameTemp = uUid + "." + imageEl;
            }

            Element var49 = document.createElement("image");
            var49.setAttribute("code", fileType);
            var49.setAttribute("mainflag", mainFlag);
            var49.setAttribute("sideflag", sideflag);
            var49.setAttribute("image_id", imageIdTemp);
            var49.setAttribute("name", nameTemp);
            var49.setAttribute("scan_name", nameTemp);
            var49.setAttribute("disname", fileName);
            var49.setAttribute("sheet_id", com.yzj.util.UUID32.getUUID());
            var49.setAttribute("orderno", String.valueOf(imgTreeEl + 1));
            var49.setAttribute("version", "1");
            tranStaEl.setFileName(nameTemp);
            funHeadEl.appendChild(var49);
        }

        messageEl.appendChild(funHeadEl);
        Element var50 = document.createElement("img_tree");
        messageEl.appendChild(var50);
        Element var51 = document.createElement("trans_sta");
        var51.setTextContent("60");
        messageEl.appendChild(var51);
        document.appendChild(messageEl);
        return XmlConfige.documentToString(document);
    }

    public interface FileUploadCallback{
        void uploadSuccess();
        void uploadFail();
    }

    private boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

}
