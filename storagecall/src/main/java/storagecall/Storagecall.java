package storagecall;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import storagecall.external.Storagemanage;
import storagecall.external.StoragemanageService;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name="Storagecall_table")
public class Storagecall {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String tel;
    private String 호출위치;
    private String 호출상태; //호출,호출중,호출확정,호출취소
    private Integer 예상요금;
    
	
    @PostPersist
    public void onPostPersist(){
//        Storagecalled Storagecalled = new Storagecalled();
//        BeanUtils.copyProperties(this, Storagecalled);
//        Storagecalled.publishAfterCommit();
    	
    	System.out.println("휴대폰번호 " + getTel());
        System.out.println("호출위치 " + get호출위치());
        System.out.println("호출상태 " + get호출상태());
        System.out.println("예상요금 " + get예상요금());
        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.   	
    	if(getTel() != null)
		{
    		System.out.println("SEND###############################" + getId());
			Storagemanage storagemanage = new Storagemanage();
			storagemanage.setId(getId());
			storagemanage.setOrderId(String.valueOf(getId()));
			storagemanage.set고객휴대폰번호(getTel());
	        if(get호출위치()!=null)
				storagemanage.set호출위치(get호출위치());
	        if(get호출상태()!=null)
				storagemanage.set호출상태(get호출상태());
	        if(get예상요금()!=null)
				storagemanage.set예상요금(get예상요금());
	        
	        // mappings goes here
	        StoragecallApplication.applicationContext.getBean(StoragemanageService.class).storageManageCall(storagemanage);
		}

    }

	@PreRemove
	public void onPreRemove(){
		StoragecallCancelled 호출취소됨 = new StoragecallCancelled();
		BeanUtils.copyProperties(this, 호출취소됨);
		호출취소됨.publishAfterCommit();

		//Following code causes dependency to external APIs
		// it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

		//Storagemanage Storagemanage = new Storagemanage();
		// mappings goes here
		//Storagemanage.setId(getId());
		//Storagemanage.setOrderId(String.valueOf(getId()));
		//Storagemanage.set호출상태("호출취소");
		//Storagemanage.set고객휴대폰번호(get휴대폰번호());
		
		// mappings goes here
		//StoragecallApplication.applicationContext.getBean(StoragemanageService.class).택시할당요청(Storagemanage);
	}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


	public String getTel() {
		return tel;
	}


	public void setTel( String tel ) {
		this.tel = tel;
	}


	public String get호출위치() {
		return 호출위치;
	}


	public void set호출위치(String 호출위치) {
		this.호출위치 = 호출위치;
	}


	public String get호출상태() {
		return 호출상태;
	}


	public void set호출상태(String 호출상태) {
		this.호출상태 = 호출상태;
	}


	public Integer get예상요금() {
		return 예상요금;
	}


	public void set예상요금(Integer 예상요금) {
		this.예상요금 = 예상요금;
	}


}
