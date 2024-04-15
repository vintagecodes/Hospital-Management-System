package sec.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import sec.payLoad.JwtResponse;

@Repository
public interface LogonInfoRepository extends MongoRepository<JwtResponse, String>
{
	void deleteByUsername(String username);
	JwtResponse findByUsername(String username);
}
