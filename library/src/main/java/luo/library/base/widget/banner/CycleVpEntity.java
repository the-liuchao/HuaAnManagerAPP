package luo.library.base.widget.banner;

/**
 * Banner 信息
 */
public class CycleVpEntity {
	private int id;
	private String create_date;
	private String remark;
	private String pic_url;
	//效果类型：0图片-无效果，1跳转产品，2跳转商品，3解析html
	private String effect_type;
	//效果对象：html则为标签
	private String effect_bean;

	//效果对象：产品Id或者商品Id
	private String effect_obj_id;

	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return this.id;
	}
	public void setCreate_date(String create_date){
		this.create_date = create_date;
	}
	public String getCreate_date(){
		return this.create_date;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}
	public String getRemark(){
		return this.remark;
	}
	public void setPic_url(String pic_url){
		this.pic_url = pic_url;
	}
	public String getPic_url(){
		return this.pic_url;
	}

	public String getEffect_type() {
		return effect_type;
	}

	public void setEffect_type(String effect_type) {
		this.effect_type = effect_type;
	}

	public String getEffect_bean() {
		return effect_bean;
	}

	public void setEffect_bean(String effect_bean) {
		this.effect_bean = effect_bean;
	}
	public String getEffect_obj_id() {
		return effect_obj_id;
	}

	public void setEffect_obj_id(String effect_obj_id) {
		this.effect_obj_id = effect_obj_id;
	}
}
