package com.zero.blobdemo.repository;

import com.zero.blobdemo.model.Doc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocRepository extends JpaRepository<Doc, Integer> {
}
