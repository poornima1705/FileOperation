package com.poorni.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poorni.springboot.model.Doc;

@Repository
public interface DocRepository extends JpaRepository<Doc, String>{


}
