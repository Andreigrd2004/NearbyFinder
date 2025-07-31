package com.java_app.demo.apikey;

import com.java_app.demo.apikey.model.ApiKey;
import com.java_app.demo.user.CustomUser;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KeysRepository extends JpaRepository<ApiKey, String> {
  List<ApiKey> findByCustomUser(CustomUser customUser);

  boolean existsByName(String name);

  boolean existsByNameAndCustomUser(String name, CustomUser customUser);

  boolean existsById(Integer id);

  void deleteByName(String name);

  void deleteByCustomUserId(Integer customUserId);

  boolean existsByValue(String value);

  void deleteApiKeyById(Integer id);

  List<ApiKey> findAllByCustomUser(CustomUser customUser);

  ApiKey findApiKeyById(Integer id);

  @Modifying
  @Query(value = "UPDATE ApiKey k SET k.name = :newName WHERE k.name = :name")
  void updateApiKey(@Param("name") String apiKeyName, @Param("newName") String newName);

  boolean existsApiKeyByCustomUser_Id(Integer userId);
}
