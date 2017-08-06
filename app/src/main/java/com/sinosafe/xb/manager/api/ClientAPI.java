package com.sinosafe.xb.manager.api;

import com.sinosafe.xb.manager.adapter.home.LoanConditionBean;
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
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

import static com.sinosafe.xb.manager.api.APIManager.CACHE_CONTROL_NETWORK;

/**
 * 类名称：ClientAPI<br>
 * 内容摘要： //客户端访问网络API。<br>
 * 属性描述：<br>
 * 方法描述：<br>
 * 修改备注：   <br>
 * 创建时间： 2016/9/9 10:43<br>
 * 公司：深圳市华移科技股份有限公司<br>
 *
 * @author hesy<br>
 */
public interface ClientAPI {

    /**
     * 下载文件
     * @param fileUrl
     * @return
     */
    @Streaming
    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);

    /**
     * 查询系统图片
     * @return
     */
    @GET("CommonAPI/getSysPics")
    Observable<BaseEntity<List<CycleVpEntity>>> getSysPics(@Query("type") String type);

    /**
     * 登陆
     * @param actorno
     * @param password
     * @return
     */
    @GET("manager/account/managerLogin")
    Observable<BaseEntity<LoginUserBean>> login(@Query("actorno") String actorno, @Query("password") String password);


    /**
     * 退出登录
     * @param token
     * @return
     */
    @POST("manager/account/managerLoginOut")
    Observable<BaseEntity<BaseEntity>> logout(@Query("token") String token);


    /**
     * 获取短信验证码
     * @param mobile
     * @return
     */
    @POST("client/account/sendCode")
    Observable<BaseEntity<SMScode>> sendCode(@Query("mobile") String mobile);

    /**
     * 获取短信验证码--0：征信授权书；1：在线签投保单
     * @param token
     * @return
     */
    @GET("CommonAPI/sendSMSOnSXSWithManager")
    Observable<BaseEntity> sendSignMobileCode3rd(@Query("token")String token,@Query("user_id")String userId,
                                                 @Query("serno") String serno,@Query("text_type") String text_type);


    /**
     * 修改登录用户信息
     * @param token
     * @param tel_num
     * @param code
     * @param cornet
     * @param avatar
     * @return
     */
    @PUT("manager/account/editManagerById")
    Observable<BaseEntity<LoginUserBean>> editManagerById(@Query("token") String token, @Query("tel_num") String tel_num,
                                            @Query("code") String code, @Query("cornet") String cornet,
                                            @Query("landline") String landline,@Query("avatar") String avatar);


    /**查看我的客户
     *
     * @param token
     * @param curPage
     * @return
     */
    @GET("manager/account/getMyCusListById")
    Observable<BaseEntity<List<MyCustomerBean>>> getMyCusListById(@Query("token") String token,
                                                @Query("curPage") String curPage);


    /**
     * 添加或编辑我的客户
     * @param fieter
     * @return
     */
    @FormUrlEncoded
    @POST("manager/account/addAndEditCustomerById")
    Observable<BaseEntity<BaseEntity>> addAndEditCustomerById(@FieldMap Map<String,String> fieter);


    /**
     * 删除历史头像
     * @param stringMap
     * @return
     */
    @FormUrlEncoded
    @POST("manager/account/delHeadPhoto")
    Observable<BaseEntity<BaseEntity>> delHeadPhoto(@FieldMap Map<String,String> stringMap);


    /**
     * 查询公告列表
     * @param maps
     * @return
     */
    @POST("getPostList")
    Observable<BaseEntity<List<GongGaoBean>>> getPostList(@QueryMap Map<String, String> maps);


    /**
     * 查询消息列表
     * @param maps
     * @return
     */
    @GET("manager/account/getMessages")
    Observable<BaseEntity<List<MessageBean>>> getMessages(@QueryMap Map<String, String> maps);



    /**
     * 查询受代理的业务
     * @param fieter
     * @return
     */
    @GET("manager/business/checkTask")
    Observable<BaseEntity<List<YeWuBean>>> checkTask(@QueryMap Map<String,String> fieter);


    /**
     * 查询待办事项总数
     * @param fieter
     * @return
     */
    @GET("manager/business/waitProcess")
    Observable<BaseEntity<WaitProcess>> waitProcess(@QueryMap Map<String,String> fieter);

    /**
     * 版本更新
     * @param version_type
     * @return
     */
    @GET("checkVersion")
    Observable<BaseEntity<VersionBean>> checkVersion(@Query("version_type") String version_type);


    /**
     * 受理待受理业务
     * @param fieter
     * @return
     */
    @POST("manager/business/bussinessAccept")
    Observable<BaseEntity> bussinessAccept(@QueryMap Map<String,String> fieter);


    /**
     * 查询消费货贷款详情及审批进度
     * @param fieter
     * @return
     */
    @GET("manager/business/queryLoanDetail")
    Observable<BaseEntity> queryLoanDetail(@QueryMap Map<String,String> fieter);


    /**
     * 查询消费货详情中的影像资料
     * @param fieter
     * @return
     */
    @GET("manager/business/queryLoanImageFile")
    Observable<BaseEntity> queryLoanImageFile(@QueryMap Map<String,String> fieter);



    /**
     * 查询消费贷产品列表
     *
     * @param maps
     * @return
     */
    @GET("manager/account/queryProducts")
    Observable<BaseEntity<List<LoanProductBean>>> getProductList(@QueryMap Map<String, String> maps);


    /**
     * 查询筛选条件列表
     *
     * @return
     */
    @GET("client/consumLoan/getProductCriteriaList")
    Observable<BaseEntity<List<LoanConditionBean>>> getProductCriteriaList();


    /**
     * 加载产品费率列表
     *
     * @param prd_id
     * @return
     */
    @GET("client/consumLoan/getProductRateListByPrdId")
    Observable<BaseEntity<List<LoanRateListBean>>> getProductRateList(@Query("prd_id") String prd_id);


    /**
     * 加载产品介绍信息
     * @param prd_id
     * @return
     */
    @GET("client/consumLoan/getProductById")
    Observable<BaseEntity<LoanIntroduce>> getProductById(@Query("prd_id") String prd_id);


    /**
     * 获取产品常见问题列表
     * @param prd_id
     * @return
     */
    @GET("client/consumLoan/getProductProblemListById")
    Observable<BaseEntity<List<LoanProblemBean>>> getProductProblemList(@Query("prd_id") String prd_id);

    /**
     * 通过身份证号查询客户的信息
     * @param token
     * @param cert_code
     * @return
     */
    @GET("manager/consumLoan/queryUserByCertCode")
    Observable<BaseEntity<LoanUserInfo>> queryUserByCertCode(@Query("token") String token, @Query("cert_code") String cert_code,
                                                             @Query("prd_id") String prd_id);


    /**
     * 保存或者编辑客户信息
     * @param fieter
     * @return
     */
    @FormUrlEncoded
    @POST("manager/consumLoan/saveOrEditUser")
    Observable<BaseEntity> saveOrEditUser(@FieldMap Map<String,String> fieter);


    /**
     * 客户紧急联系人增加或修改
     * @param fieter
     * @return
     */
    @FormUrlEncoded
    @POST("manager/consumLoan/saveOrEditContactor")
    Observable<BaseEntity> saveOrEditContactor(@FieldMap Map<String,String> fieter);


    /**
     * 删除紧急联系人
     * @param fieter
     * @return
     */
    @FormUrlEncoded
    @POST("manager/consumLoan/delUserContacto")
    Observable<BaseEntity> delUserContacto(@FieldMap Map<String,String> fieter);

    /**
     * 用于申请贷款时填写完身份证号，银行卡号后验证是否存在申请纪录
     * @param fieter
     * @return
     */
    @FormUrlEncoded
    @POST("manager/consumLoan/saveCurrency")
    Observable<BaseEntity> saveCurrency(@FieldMap Map<String,String> fieter);

    /**
     * 用于经理端申请消费贷
     * @param fieter
     * @return
     */
    @FormUrlEncoded
    @POST("manager/consumLoan/managerLoanApply")
    Observable<BaseEntity> managerLoanApply(@FieldMap Map<String,String> fieter);


    /**
     * 用于获取上传影像资料回执
     * @param fieter
     * @return
     */
    @FormUrlEncoded
    @POST("manager/account/imageDocUploadReceipt")
    Observable<BaseEntity> imageDocUploadReceipt(@FieldMap Map<String,String> fieter);

    /**
     * 地区数据查询
     *
     * @param type      1省、2市、3县/区
     * @param parent_id 查询省则传all
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK)
    @GET("getAreaList")
    Observable<BaseEntity<List<AreaListBean>>> getAreaList(@Query("type") String type, @Query("parent_id") String parent_id);


    /**
     * 经理业绩整体、当日情况查询
     * @param token
     * @param report_type
     * @return
     */
    @GET("manager/business/queryReportTotal")
    Observable<BaseEntity<YeJiReport>> queryReportTotal(@Query("token") String token, @Query("report_type") String report_type);

    /**
     * 排行榜总体排名查询
     * @param token
     * @return
     */
    @GET("manager/business/queryMgrRanking")
    Observable<BaseEntity<RankingList>> queryMgrRanking(@Query("token") String token);


    /**
     * 排行榜分类排名查询
     * @param map
     * @return
     */
    @GET("manager/business/queryReportRanking")
    Observable<BaseEntity<YeJiRanking>> queryReportRanking(@QueryMap Map<String,String> map);


    /**
     * 微贷点击保存按钮，保存输入信息
     * @param map
     * @return
     */
    @POST("manager/microLoan/saveLoanMsg")
    Observable<BaseEntity> saveLoanMsg(@QueryMap Map<String,String> map);

    /**
     * 查询申请中数据列表
     * @param token
     * @return
     */
    @GET("manager/business/queryApplyingList")
    Observable<BaseEntity<List<ApplyingBean>>> queryApplyingList(@Query("token") String token,
                                                                 @Query("curPage") String curPage);


    /**
     * 获取企联系统的缴费地址
     * @param map
     * @return
     */
    @POST("manager/business/payment")
    Observable<BaseEntity> payment(@QueryMap Map<String,String> map);


    /**
     * 社保公积金页面跳转
     * @return
     */
    @FormUrlEncoded
    @POST("CommonAPI/authPage")
    Observable<BaseEntity> authPage(@FieldMap Map<String, String> maps);

    /**
     * 查询还款计划及还款明细
     * @return
     */
    @GET("manager/business/queryRepayDetail")
    Observable<BaseEntity> queryRepayDetail(@QueryMap Map<String, String> maps);


    /**
     * 电子签署个人征信业务授权书之后保存电子授权书
     * @return
     */
    @FormUrlEncoded
    @POST("manager/consumLoan/electronicSignature")
    Observable<BaseEntity> electronicSignature(@FieldMap Map<String, String> maps);


    /**
     *  申请中业务列表判断是否授权
     * @return
     */
    @GET("manager/business/applyAuth")
    Observable<BaseEntity> applyAuth(@QueryMap Map<String, String> maps);


    /**
     *  查询产品判断是否授权
     * @return
     */
    @POST("manager/consumLoan/queryAuthByCertCode")
    Observable<BaseEntity> queryAuthByCertCode(@QueryMap Map<String, String> maps);


    /**
     *  分享
     * @return
     */
    @GET("shareLogin")
    Observable<BaseEntity> shareLogin(@QueryMap Map<String, String> maps);


    /**
     * 发邮件、打印投保单
     * @param fieter
     * @return
     */
    @FormUrlEncoded
    @POST("manager/business/sendInsuranceEmail")
    Observable<BaseEntity> sendInsuranceEmail(@FieldMap Map<String,String> fieter);


    /**
     * 查看查看保单、投保单、个人征信授权书
     * @param serno
     * @return
     */
    @GET("manager/business/checkReport")
    Observable<BaseEntity> checkReport(@Query("serno") String serno,
                                       @Query("type") String type,@Query("token") String token);


    /**
     * 在线签投保单事项签署投保单
     * @return
     */
    @FormUrlEncoded
    @POST("CommonAPI/electronicSignature")
    Observable<BaseEntity> electronicSignature2(@FieldMap Map<String, String> maps);

    /**
     * 签署投保单，信保系统生成投保单
     * @return
     */
    @FormUrlEncoded
    @POST("manager/consumLoan/signInsurance")
    Observable<BaseEntity> signInsurance(@FieldMap Map<String, String> maps);


    /**
     *  查询经理放款笔数、保费收入数据，用于趋势图展示
     * @return
     */
    @POST("manager/business/queryTendency")
    Observable<BaseEntity> queryTendency(@QueryMap Map<String, String> maps);

    /**
     * 保存极光channel_id
     * @return
     */
    @GET("manager/account/updateChannelId")
    Observable<BaseEntity> updateChannelId(@QueryMap Map<String, String> maps);

    /**
     * 读取删除消息
     * @return
     */
    @GET("manager/account/readOrDelMesg")
    Observable<BaseEntity> readOrDelMesg(@QueryMap Map<String, String> maps);


    /**
     *  删除申请的记录
     * @return
     */
    @POST("CommonAPI/delSerno")
    Observable<BaseEntity> delSerno(@QueryMap Map<String, String> maps);

    /**
     *  查询经理放款笔数、保费收入全部的数据
     * @return
     */
    @POST("manager/business/queryTendencyAll")
    Observable<BaseEntity> queryTendencyAll(@QueryMap Map<String, String> maps);


    /**
     * 查询银行卡类型
     * @return
     */
    //@GET("client/lianpay/getCardNosBank")
    //Observable<BaseEntity> getCardNosBank(@QueryMap Map<String, String> maps);

}
