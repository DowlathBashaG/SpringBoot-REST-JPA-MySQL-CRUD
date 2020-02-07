package io.dowlathbasha.restjpamysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.dowlathbasha.restjpamysql.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

}
