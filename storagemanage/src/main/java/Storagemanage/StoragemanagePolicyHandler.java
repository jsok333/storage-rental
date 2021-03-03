package Storagemanage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import Storagemanage.config.kafka.KafkaProcessor;

@Service
public class StoragemanagePolicyHandler {
	@Autowired
	StoragemanageRepository 택시관리Repository;

	@StreamListener(KafkaProcessor.INPUT)
	public void onStringEventListener(@Payload String eventString) {

	}

	@StreamListener(KafkaProcessor.INPUT)
	public void whenever호출취소됨_(@Payload StoragecallCancelled 호출취소됨) {
		System.out.println("##### EVT TYPE[StoragecallCancelled]  : " + 호출취소됨.getEventType());
		if (호출취소됨.isMe()) {
			System.out.println("##### listener  : " + 호출취소됨.toJson());

			if (호출취소됨.getId() != null)
				// Correlation id 는 '고객휴대폰번호' 임
				택시관리Repository.findById(Long.valueOf(호출취소됨.getId())).ifPresent((택시관리) -> {
					택시관리.set호출상태("호출요청취소됨");
					택시관리Repository.save(택시관리);
				});
		}
	}

	@StreamListener(KafkaProcessor.INPUT)
	public void whenever택시할당요청됨_(@Payload StoragemanageAssigned 택시할당요청됨) {
		System.out.println("##### EVT TYPE[StoragemanageAssigned]  : " + 택시할당요청됨.getEventType());
		if (택시할당요청됨.isMe()) {
			System.out.println("##### listener[StorageassignCompleted]  : " + 택시할당요청됨.toJson());

			if (택시할당요청됨.getId() != null)
				// Correlation id 는 '고객휴대폰번호' 임
				택시관리Repository.findById(Long.valueOf(택시할당요청됨.getId())).ifPresent((택시관리) -> {
					택시관리.set호출상태(택시할당요청됨.get호출상태());
					택시관리Repository.save(택시관리);
				});

//        	StoragemanageRepository.findBy고객휴대폰번호(StoragemanageAssigned.get고객휴대폰번호()).ifPresent((Storagemanage) -> {
//				System.out.println("StoragemanageAssigned = " + Storagemanage.get고객휴대폰번호());
//				Storagemanage.set호출상태(StoragemanageAssigned.get호출상태());
//				StoragemanageRepository.save(Storagemanage);
//			});
//            Storagemanage 관리 = new Storagemanage();
//            관리.set호출상태(StorageassignCompleted.get호출상태());
//            관리.set택시기사이름(StorageassignCompleted.get택시기사이름());
//            관리.set택시기사전화번호(StorageassignCompleted.get택시기사전화번호());
//            관리.set택시번호(StorageassignCompleted.get택시번호());
//            StoragemanageRepository.save(관리);
		}
	}

//    @StreamListener(KafkaProcessor.INPUT)
//    public void whenever택시할당확인됨_(@Payload StorageassignCompleted StorageassignCompleted){
//    	System.out.println("##### EVT TYPE[StorageassignCompleted]  : " + StorageassignCompleted.getEventType());
//        if(StorageassignCompleted.isMe()){
//            System.out.println("##### listener  : " + StorageassignCompleted.toJson());
//            Storagemanage 관리 = new Storagemanage();
//            관리.set호출상태(StorageassignCompleted.get할당상태());
//            관리.set택시기사이름(StorageassignCompleted.get택시기사이름());
//            관리.set택시기사전화번호(StorageassignCompleted.get택시기사전화번호());
//            관리.set택시번호(StorageassignCompleted.get택시번호());
//            StoragemanageRepository.save(관리);
//        }
//    }

}
