package com.sinosafe.xb.manager.model;


import com.sinosafe.xb.manager.adapter.home.LoanConditionBean;
import com.sinosafe.xb.manager.api.APIManager;
import com.sinosafe.xb.manager.api.ClientAPI;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.bean.GongGaoBean;
import com.sinosafe.xb.manager.bean.LoanIntroduce;
import com.sinosafe.xb.manager.bean.LoanProblemBean;
import com.sinosafe.xb.manager.bean.LoanProductBean;
import com.sinosafe.xb.manager.bean.LoanRateListBean;
import com.sinosafe.xb.manager.bean.LoanUserInfo;
import com.sinosafe.xb.manager.bean.LoginUserBean;
import com.sinosafe.xb.manager.bean.MessageBean;
import com.sinosafe.xb.manager.bean.MyCustomerBean;
import com.sinosafe.xb.manager.bean.SMScode;
import com.sinosafe.xb.manager.bean.VersionBean;
import com.sinosafe.xb.manager.bean.WaitProcess;
import com.sinosafe.xb.manager.bean.YeWuBean;
import com.sinosafe.xb.manager.module.home.weidai.bean.ApplyingBean;
import com.sinosafe.xb.manager.module.yeji.bean.RankingList;
import com.sinosafe.xb.manager.module.yeji.bean.YeJiRanking;
import com.sinosafe.xb.manager.module.yeji.bean.YeJiReport;

import java.util.List;
import java.util.Map;

import luo.library.base.bean.AreaListBean;
import luo.library.base.widget.banner.CycleVpEntity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.QueryMap;
import rx.Observable;


/**
 * 类名称：   LoginModelImp
 * 内容摘要： //登录。
 * 修改备注：
 * 创建时间： 2017/5/11
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class ClientModel {

    public final static ClientAPI clientAPI = APIManager.mRetrofit.create(ClientAPI.class);


    /**
     * 下载文件
     * @param fileUrl
     * @return
     */
    public static  Call<ResponseBody> downloadFileWithDynamicUrlSync(String fileUrl){

        return  clientAPI.downloadFileWithDynamicUrlSync(fileUrl);
    }


    /**
     * 查询系统图片
     * @param type
     * @return
     */
    public static Observable<BaseEntity<List<CycleVpEntity>>> getSysPics(String type){
        return  clientAPI.getSysPics(type);
    }

    /**
     * 登录
     * @param actorno
     * @param password
     * @return
     */
    public static Observable<BaseEntity<LoginUserBean>> login(String actorno, String password){
        return  clientAPI.login(actorno,password);
    }

    /**
     * 退出登录
     * @param token
     * @return
     */
    public static Observable<BaseEntity<BaseEntity>> logout( String token){

        return  clientAPI.logout(token);
    }


    /**
     * 获取短信验证码
     * @param mobile
     * @return
     */
    public static Observable<BaseEntity<SMScode>> sendCode( String mobile){

        return  clientAPI.sendCode(mobile);
    }


    /**
     * 获取短信验证码--电子签署
     * @param token
     * @return
     */
    public static Observable<BaseEntity> sendSignMobileCode3rd( String token,String userId,String serno,String text_type){

        return  clientAPI.sendSignMobileCode3rd(token,userId,serno,text_type);
    }


    /**
     * 修改登录用户信息
     * @param token
     * @param tel_num
     * @param code
     * @param cornet
     * @param avatar
     * @return
     */
    public static Observable<BaseEntity<LoginUserBean>> editManagerById(String token, String tel_num,
                                          String code, String cornet,
                                          String landline,String avatar){

        return clientAPI.editManagerById(token, tel_num, code,cornet,landline,avatar);
    }


    /**
     * 删除历史头像
     * @param stringMap
     * @return
     */
    public static Observable<BaseEntity<BaseEntity>> delHeadPhoto(@QueryMap Map<String,String> stringMap){
        return clientAPI.delHeadPhoto(stringMap);
    }

    /**
     * 查询公告列表
     * @param maps
     * @return
     */
    public static Observable<BaseEntity<List<GongGaoBean>>> getPostList(Map<String, String> maps){

        return clientAPI.getPostList(maps);
    }

    /**
     * 查询消息列表
     * @param maps
     * @return
     */
    public static Observable<BaseEntity<List<MessageBean>>> getMessages(@QueryMap Map<String, String> maps){

        return clientAPI.getMessages(maps);
    }

    /**
     * 查看我的客户
     * @param token
     * @param curPage
     * @return
     */
    public static Observable<BaseEntity<List<MyCustomerBean>>> getMyCusListById(String token,
                                                                  String curPage){

        return clientAPI.getMyCusListById(token, curPage);
    }


    /**
     * 添加或编辑我的客户
     * @param fieter
     * @return
     */
    public static Observable<BaseEntity<BaseEntity>> addAndEditCustomerById(Map<String,String> fieter){

        return clientAPI.addAndEditCustomerById(fieter);

    }

    /**
     * 查询受代理的业务
     * @param fieter
     * @return
     */

    public static Observable<BaseEntity<List<YeWuBean>>> checkTask(Map<String,String> fieter){

        return  clientAPI.checkTask(fieter);
    }


    /**
     * 查询待办事项总数
     * @param fieter
     * @return
     */
    public static Observable<BaseEntity<WaitProcess>> waitProcess(Map<String,String> fieter){

        return  clientAPI.waitProcess(fieter);
    }


    /**
     * 版本更新
     * @param version_type
     * @return
     */
    public static Observable<BaseEntity<VersionBean>> checkVersion(String version_type){

        return  clientAPI.checkVersion(version_type);
    }


    /**
     * 受理待受理业务
     * @param fieter
     * @return
     */
    public static Observable<BaseEntity> bussinessAccept(Map<String,String> fieter){

        return  clientAPI.bussinessAccept(fieter);
    }


    /**
     * 查询消费货详情中的影像资料
     * @param fieter
     * @return
     */
    public static Observable<BaseEntity> queryLoanImageFile(Map<String,String> fieter){

        return  clientAPI.queryLoanImageFile(fieter);
    }


    /**
     * 查询筛选条件列表
     *
     * @return
     */
    public static Observable<BaseEntity<List<LoanConditionBean>>> getProductCriteriaList(){

        return  clientAPI.getProductCriteriaList();
    }


    /**
     * 查询消费贷产品列表
     *
     * @param maps
     * @return
     */
    public static Observable<BaseEntity<List<LoanProductBean>>> getProductList(Map<String, String> maps){

        return  clientAPI.getProductList(maps);

    }


    /**
     * 加载产品费率列表
     *
     * @param prd_id
     * @return
     */
    public static Observable<BaseEntity<List<LoanRateListBean>>> getProductRateList(String prd_id){

        return  clientAPI.getProductRateList(prd_id);
    }

    /**
     * 加载产品介绍信息
     * @param prd_id
     * @return
     */
    public static Observable<BaseEntity<LoanIntroduce>> getProductById(String prd_id){

        return  clientAPI.getProductById(prd_id);
    }

    /**
     * 获取产品常见问题列表
     * @param prd_id
     * @return
     */
    public static Observable<BaseEntity<List<LoanProblemBean>>> getProductProblemList(String prd_id){

        return  clientAPI.getProductProblemList(prd_id);
    }


    /**
     * 通过身份证号查询客户的信息
     * @param token
     * @param cert_code
     * @return
     */
    public static Observable<BaseEntity<LoanUserInfo>> queryUserByCertCode(String token,String cert_code,String prd_id){

        return  clientAPI.queryUserByCertCode(token,cert_code,prd_id);
    }

    /**
     * 用于申请贷款时填写完身份证号，银行卡号后验证是否存在申请纪录
     * @param fieter
     * @return
     */
    public static Observable<BaseEntity> saveCurrency(Map<String,String> fieter){

        return  clientAPI.saveCurrency(fieter);
    }




    /**
     * 保存或者编辑客户信息
     * @param fieter
     * @return
     */
    public static Observable<BaseEntity> saveOrEditUser(Map<String,String> fieter){

        return  clientAPI.saveOrEditUser(fieter);
    }


    /**
     * 客户紧急联系人增加或修改
     * @param fieter
     * @return
     */
    public static Observable<BaseEntity> saveOrEditContactor(Map<String,String> fieter){

        return  clientAPI.saveOrEditContactor(fieter);
    }

    /**
     * 删除紧急联系人
     * @param fieter
     * @return
     */
    public static Observable<BaseEntity> delUserContacto( Map<String,String> fieter){

        return  clientAPI.delUserContacto(fieter);
    }

    /**
     * 地区数据查询
     *
     * @param type      1省、2市、3县/区
     * @param parent_id 查询省则传all
     * @return
     */
    public static Observable<BaseEntity<List<AreaListBean>>> getAreaList(String type,String parent_id){

        return  clientAPI.getAreaList(type,parent_id);
    }

    /**
     * 用于经理端申请消费贷
     * @param fieter
     * @return
     */
    public static Observable<BaseEntity> managerLoanApply(Map<String,String> fieter){

        return  clientAPI.managerLoanApply(fieter);
    }

    /**
     * 用于获取上传影像资料回执
     * @param fieter
     * @return
     */
    public static Observable<BaseEntity> imageDocUploadReceipt(Map<String,String> fieter){

        return  clientAPI.imageDocUploadReceipt(fieter);
    }


    /**
     * 经理业绩整体、当日情况查询
     * @param token
     * @param report_type
     * @return
     */
    public static Observable<BaseEntity<YeJiReport>> queryReportTotal(String token,String report_type){

        return  clientAPI.queryReportTotal(token,report_type);
    }

    /**
     * 排行榜总体排名查询
     * @param token
     * @return
     */
    public static Observable<BaseEntity<RankingList>> queryMgrRanking(String token){

        return  clientAPI.queryMgrRanking(token);
    }


    /**
     * 排行榜分类排名查询
     * @param map
     * @return
     */
    public static Observable<BaseEntity<YeJiRanking>> queryReportRanking(Map<String,String> map){

        return  clientAPI.queryReportRanking(map);
    }


    /**
     * 微贷点击保存按钮，保存输入信息
     * @param map
     * @return
     */
    public static Observable<BaseEntity> saveLoanMsg(Map<String,String> map){

        return  clientAPI.saveLoanMsg(map);
    }

    /**
     * 查询申请中数据列表
     * @param token
     * @return
     */
    public static Observable<BaseEntity<List<ApplyingBean>>> queryApplyingList(String token,
                                                                               String curPage){

        return  clientAPI.queryApplyingList(token,curPage);
    }


    /**
     * 获取企联系统的缴费地址
     * @param map
     * @return
     */
    public static Observable<BaseEntity> payment( Map<String,String> map){

        return  clientAPI.payment(map);
    }


    /**
     * 社保公积金页面跳转
     * @return
     */
    public static Observable<BaseEntity> authPage(Map<String, String> maps){

        return  clientAPI.authPage(maps);
    }

    /**
     * 查询还款计划及还款明细
     * @return
     */
    public static Observable<BaseEntity> queryRepayDetail(@QueryMap Map<String, String> maps){

        return  clientAPI.queryRepayDetail(maps);
    }


    /**
     * 电子签署个人征信业务授权书之后保存电子授权书
     * @return
     */
    public static Observable<BaseEntity> electronicSignature(Map<String, String> maps){

        return  clientAPI.electronicSignature(maps);
    }

    /**
     *  申请中业务列表判断是否授权
     * @return
     */
    public static Observable<BaseEntity> applyAuth(Map<String, String> maps){

        return  clientAPI.applyAuth(maps);
    }

    /**
     *  判断产品是否授权
     * @return
     */
    public static Observable<BaseEntity> queryAuthByCertCode( Map<String, String> maps){

        return  clientAPI.queryAuthByCertCode(maps);
    }

    /**
     *  分享
     * @return
     */
    public static Observable<BaseEntity> shareLogin(@QueryMap Map<String, String> maps){

        return  clientAPI.shareLogin(maps);
    }


    /**
     * 发邮件、打印投保单
     * @param maps
     * @return
     */
    public static Observable<BaseEntity> sendInsuranceEmail(Map<String,String> maps){

        return  clientAPI.sendInsuranceEmail(maps);
    }


    /**
     * 查看查看保单、投保单、个人征信授权书
     * @param serno
     * @return
     */
    public static Observable<BaseEntity> checkReport(String serno, String type,String token){

        return  clientAPI.checkReport(serno,type,token);
    }


    /**
     * 在线签投保单事项签署投保单
     * @return
     */
    public static Observable<BaseEntity> electronicSignature2( Map<String, String> maps){

        return  clientAPI.electronicSignature2(maps);
    }


    /**
     * 签署投保单，信保系统生成投保单
     * @return
     */
    public static Observable<BaseEntity> signInsurance(@FieldMap Map<String, String> maps){

        return  clientAPI.signInsurance(maps);
    }


    /**
     *  查询经理放款笔数、保费收入数据，用于趋势图展示
     * @return
     */
    public static Observable<BaseEntity> queryTendency(Map<String, String> maps){

        return  clientAPI.queryTendency(maps);
    }


    /**
     * 保存极光channel_id
     * @return
     */
    public static Observable<BaseEntity> updateChannelId(Map<String, String> maps){

        return  clientAPI.updateChannelId(maps);
    }

    /**
     * 读取删除消息
     * @return
     */
    public static Observable<BaseEntity> readOrDelMesg(Map<String, String> maps){

        return  clientAPI.readOrDelMesg(maps);
    }

    /**
     *  删除申请的记录
     * @return
     */
    public static Observable<BaseEntity> delSerno(@QueryMap Map<String, String> maps){

        return  clientAPI.delSerno(maps);
    }


    /**
     *  查询经理放款笔数、保费收入全部的数据
     * @return
     */
    public static Observable<BaseEntity> queryTendencyAll(Map<String, String> maps){

        return  clientAPI.queryTendencyAll(maps);
    }

}
