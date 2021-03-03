package Storagemanage;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface StoragemanageRepository extends PagingAndSortingRepository<Storagemanage, Long>{

	Optional<Storagemanage> findBy고객휴대폰번호( String get고객휴대폰번호);


}