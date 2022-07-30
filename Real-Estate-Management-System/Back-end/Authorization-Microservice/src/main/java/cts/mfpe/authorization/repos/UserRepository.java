package cts.mfpe.authorization.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cts.mfpe.authorization.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsername(String userName);

}
